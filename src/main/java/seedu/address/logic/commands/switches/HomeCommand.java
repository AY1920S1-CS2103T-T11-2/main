package seedu.address.logic.commands.switches;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.ModeEnum;
import seedu.address.logic.commands.SwitchCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Terminates the program.
 */
public class HomeCommand extends SwitchCommand {

    public static final String COMMAND_WORD = "home";

    public static final String MESSAGE_HOME_ACKNOWLEDGEMENT = "Going home as requested";


    @Override
    public ModeEnum check(Model model, ModeEnum mode) throws CommandException {
        return ModeEnum.SETTINGS;
    }

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(MESSAGE_HOME_ACKNOWLEDGEMENT, false, false);
    }

}
