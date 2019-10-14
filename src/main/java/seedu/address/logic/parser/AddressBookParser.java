package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.app.AddCommand;
import seedu.address.logic.commands.app.ClearCommand;
import seedu.address.logic.commands.app.DeleteCommand;
import seedu.address.logic.commands.app.EditCommand;
import seedu.address.logic.commands.app.ExitCommand;
import seedu.address.logic.commands.app.FindCommand;
import seedu.address.logic.commands.app.HelpCommand;
import seedu.address.logic.commands.game.SkipCommand;
import seedu.address.logic.commands.switches.HomeCommand;
import seedu.address.logic.commands.app.ListCommand;
import seedu.address.logic.commands.game.GuessCommand;
import seedu.address.logic.commands.switches.StartCommand;
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

        case StartCommand.COMMAND_WORD:
            return new StartCommandParser().parse(arguments);

        case GuessCommand.COMMAND_WORD:
            return new GuessCommandParser().parse(arguments);

        case SkipCommand.COMMAND_WORD:
            return new SkipCommand();

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
