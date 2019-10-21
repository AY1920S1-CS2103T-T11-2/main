package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.appCommands.AddCommand;
import seedu.address.logic.commands.appCommands.ClearCommand;
import seedu.address.logic.commands.appCommands.DeleteCommand;
import seedu.address.logic.commands.appCommands.EditCommand;
import seedu.address.logic.commands.appCommands.ExitCommand;
import seedu.address.logic.commands.appCommands.FindCommand;
import seedu.address.logic.commands.appCommands.HelpCommand;
import seedu.address.logic.commands.appCommands.ListCommand;
import seedu.address.logic.commands.gameCommands.GuessCommand;
import seedu.address.logic.commands.gameCommands.SkipCommand;
import seedu.address.logic.commands.gameCommands.StopCommand;
import seedu.address.logic.commands.loadCommands.CreateCommand;
import seedu.address.logic.commands.loadCommands.ExportCommand;
import seedu.address.logic.commands.loadCommands.ImportCommand;
import seedu.address.logic.commands.loadCommands.RemoveCommand;
import seedu.address.logic.commands.settingCommands.DifficultyCommand;
import seedu.address.logic.commands.switches.BankCommand;
import seedu.address.logic.commands.switches.HomeCommand;
import seedu.address.logic.commands.switches.LoadScreenCommand;
import seedu.address.logic.commands.switches.StartCommand;
import seedu.address.logic.commands.switches.SwitchToSettingsCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class AddressBookParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        /*
        Step 10.
        Additional commands to be done
        Have 2 separate user modes: Game, Normal
         */

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case BankCommand.COMMAND_WORD:
            return new BankCommandParser().parse(arguments);

        case CreateCommand.COMMAND_WORD:
            return new CreateCommandParser().parse(arguments);

        case ExportCommand.COMMAND_WORD:
            return new ExportCommandParser().parse(arguments);

        case ImportCommand.COMMAND_WORD:
            return new ImportCommandParser().parse(arguments);

        case RemoveCommand.COMMAND_WORD:
            return new RemoveCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case HomeCommand.COMMAND_WORD:
            return new HomeCommand();

        case LoadScreenCommand.COMMAND_WORD:
            return new LoadScreenCommand();

        case StartCommand.COMMAND_WORD:
            return new StartCommand();

        case GuessCommand.COMMAND_WORD:
            return new GuessCommandParser().parse(arguments);

        case SkipCommand.COMMAND_WORD:
            return new SkipCommand();

        case StopCommand.COMMAND_WORD:
            return new StopCommand();

        case SwitchToSettingsCommand.COMMAND_WORD:
            return new SwitchToSettingsCommand();

        case DifficultyCommand.COMMAND_WORD:
            return new DifficultyCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
