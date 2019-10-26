package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Represents a command executed on banks.
 */
public abstract class HomeCommand extends Command {
    public abstract CommandResult execute(Model model) throws CommandException;
}
