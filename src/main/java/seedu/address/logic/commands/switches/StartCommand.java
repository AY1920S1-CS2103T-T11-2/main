/*
Step 14.

It has to override execute() from command interface.

Interacts with Game interface.
Extends to Step 15 in Game.java
 */
package seedu.address.logic.commands.switches;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.SwitchCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.exceptions.ModeSwitchException;
import seedu.address.logic.util.ModeEnum;
import seedu.address.model.Model;
import seedu.address.model.game.Game;
import seedu.address.model.util.SampleDataUtil;
import seedu.address.model.wordbank.ReadOnlyWordBank;
import seedu.address.model.wordbank.WordBank;
import seedu.address.model.wordbanklist.WordBankList;
import seedu.address.storage.wordbanks.JsonWordBankListStorage;

/**
 * Starts the game.
 */
public class StartCommand extends SwitchCommand {

    public static final String COMMAND_WORD = "start";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Starts the word bank identified by the index number used in the displayed card list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";
    private static final String MESSAGE_GAME_IN_PROGRESS = "A game session is still in progress!"
            + " (Use 'stop' to terminate) Guess the word:";


    public StartCommand() {}

    public ModeEnum getNewMode(ModeEnum old) throws ModeSwitchException {
        return ModeEnum.GAME;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (model.getGame() != null && !model.getGame().isOver()) {
            throw new CommandException(MESSAGE_GAME_IN_PROGRESS
                    + "\n" + model.getGame().getCurrQuestion());
        }

        String wordBankName = model.getWordBank().getName();
        WordBankList wbList = model.getWordBankList();
        WordBank wordBank = wbList.getWordBank(wordBankName);

        Game newGame = new Game(wordBank, x -> Collections.shuffle(x));
        model.setGame(newGame);
        String currQuestion = model.getGame().getCurrQuestion();
        return new StartCommandResult(wordBankName, currQuestion);
    }
}
