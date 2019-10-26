package seedu.address.logic.commands.appcommands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.AppCommand;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;

/**
 * Clears the word bank.
 */
public class ClearCommand extends AppCommand {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Word bank has been cleared!";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.clearWordBank();
        model.clearWordBankStatistics();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}