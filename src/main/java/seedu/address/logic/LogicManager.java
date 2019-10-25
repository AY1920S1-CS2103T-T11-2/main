package seedu.address.logic;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.IOException;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Logger;

import javafx.collections.ObservableList;

import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.loadcommands.CreateCommand;
import seedu.address.logic.commands.loadcommands.ExportCommand;
import seedu.address.logic.commands.loadcommands.ImportCommand;
import seedu.address.logic.commands.loadcommands.RemoveCommand;
import seedu.address.logic.parser.ParserManager;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.util.AutoFillAction;
import seedu.address.logic.util.ModeEnum;
import seedu.address.model.Model;
import seedu.address.model.appsettings.AppSettings;
import seedu.address.model.card.Card;
import seedu.address.model.card.FormattedHint;
import seedu.address.model.globalstatistics.GlobalStatistics;
import seedu.address.model.wordbank.ReadOnlyWordBank;
import seedu.address.model.wordbank.WordBank;
import seedu.address.model.wordbankstatslist.WordBankStatisticsList;
import seedu.address.statistics.GameStatistics;
import seedu.address.statistics.WordBankStatistics;
import seedu.address.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic, UiLogicHelper {
    public static final String FILE_OPS_ERROR_MESSAGE = "File operation failed";

    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;

    private final ParserManager parserManager;


    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        parserManager = new ParserManager();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;

        /*
        Step 10.
        Modify parseCommand()
        2 user modes: Game mode and Normal mode
        */

        parserManager.updateState(model.bankLoaded(), model.gameIsOver());

        Command command = parserManager.parseCommand(commandText);

        /*
        Step 11.
        Extends to Step 13 in Command.java

        commandResult = command.execute(model, game);
         */
        //commandResult = command.execute(model);

        commandResult = command.execute(model);

        parserManager.updateState(model.bankLoaded(), model.gameIsOver());

        // todo need to save wordbankstatistics after deletion.
        // todo possible solution -> just save on every command like how the word bank is saved.
        // todo currently, on deletion, the statistics is deleted on the model, and will be saved only if
        // todo a game is played with that word bank. If no game is played, and the app is closed, the statistics
        // todo will stay there forever...
        /*
        Step 12.
        We save game here too.
        Similar methods to saveWordBanks();
         */
        try {
            if (getMode().equals(ModeEnum.SETTINGS)) {
                storage.saveAppSettings(model.getAppSettings(), model.getAppSettingsFilePath());
            }
            ReadOnlyWordBank wb = model.getWordBank();

            if (!wb.getName().equals("Empty wordbank")) {
                storage.saveWordBank(wb);
            }
            if (command instanceof CreateCommand) {
                storage.addWordBank(wb);
            }
            if (command instanceof RemoveCommand) {
                storage.removeWordBank(((RemoveCommand) command).getWordBankName());
            }
            if (command instanceof ExportCommand) {
                File dir = ((ExportCommand) command).getDirectory();
                Path filePath = Paths.get(dir.toString());
                storage.saveWordBank(((ExportCommand) command).getWordBank(), filePath);
            }
            if (command instanceof ImportCommand) {
                File dir = ((ImportCommand) command).getDirectory();
                String wordBankName = ((ImportCommand) command).getWordBankName() + ".json";
                Path filePath = Paths.get(dir.toString(), wordBankName);
                WordBank wordBank = (WordBank) storage.createWordBank(filePath).get();
                storage.saveWordBank(wordBank);
                model.getWordBankList().addBank(wordBank);
            }

        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        } catch (DataConversionException e) {
            e.printStackTrace();
        }

        return commandResult;
    }

    @Override
    public ReadOnlyWordBank getAddressBook() {
        return model.getWordBank();
    }

    @Override
    public ObservableList<Card> getFilteredPersonList() {
        return model.getFilteredCardList();
    }

    @Override
    public ObservableList<WordBank> getFilteredWordBankList() {
        return model.getFilteredWordBankList();
    }

    @Override
    public Path getAddressBookFilePath() {
        return model.getWordBankFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }

    @Override
    public void saveUpdatedWbStatistics(GameStatistics gameStatistics) throws CommandException {
        try {
            WordBankStatistics currWbStats;
            if (model.getWordBankStatistics() == null) {
                currWbStats = WordBankStatistics.getEmpty("sample");
            } else {
                currWbStats = model.getWordBankStatistics();
            }
            currWbStats.update(gameStatistics, model.getCurrentGameDifficulty());

            Path targetPath = Path.of(model.getUserPrefs().getDataFilePath().toString(), "wbstats",
                    currWbStats.getWordBankName() + ".json");

            storage.saveWordBankStatistics(currWbStats, targetPath);
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }
    }

    @Override
    public void incrementPlay() throws CommandException {
        try {
            requireNonNull(model.getGlobalStatistics());
            GlobalStatistics globalStats = model.getGlobalStatistics();
            globalStats.addPlay();
            storage.saveGlobalStatistics(globalStats);
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }
    }

    @Override
    public WordBankStatistics getActiveWordBankStatistics() {
        return model.getWordBankStatistics();
    }

    @Override
    public WordBankStatisticsList getWordBankStatisticsList() {
        return model.getWordBankStatisticsList();
    }

    @Override
    public GlobalStatistics getGlobalStatistics() {
        return model.getGlobalStatistics();
    }

    @Override
    public ReadOnlyWordBank getActiveWordBank() {
        return model.getWordBank();
    }

    @Override
    public long getTimeAllowedPerQuestion() {
        return this.model.getTimeAllowedPerQuestion();
    }

    @Override
    public FormattedHint getHintFormatFromCurrentGame() {
        return this.model.getHintFormatFromCurrentGame();
    }

    @Override
    public int getHintFormatSizeFromCurrentGame() {
        return this.model.getHintFormatSizeFromCurrentGame();
    }

    @Override
    public boolean hintsAreEnabled() {
        return model.getHintsEnabled();
    }

    @Override
    public List<AutoFillAction> getMenuItems(String text) {
        return parserManager.getAutoFill(text);
    }

    @Override
    public ModeEnum getMode() {
        return parserManager.getMode();
    }

    @Override
    public List<ModeEnum> getModes() {
        return parserManager.getModes();
    }

    @Override
    public AppSettings getAppSettings() {
        return this.model.getAppSettings();
    }

    @Override
    public String getCurrentQuestion() {
        return model.getGame().getCurrQuestion();
    }

}
