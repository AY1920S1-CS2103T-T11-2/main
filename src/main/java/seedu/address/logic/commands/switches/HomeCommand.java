package seedu.address.logic.commands.switches;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.SwitchCommand;
import seedu.address.logic.commands.exceptions.ModeSwitchException;
import seedu.address.logic.util.ModeEnum;
import seedu.address.model.Model;

/**
 * Terminates the program.
 */
public class HomeCommand extends SwitchCommand {
    public static final String COMMAND_WORD = "home";
    public static final String MESSAGE_HOME_ACKNOWLEDGEMENT = "Going home page as requested";

    public ModeEnum getNewMode(ModeEnum old) throws ModeSwitchException {
        return ModeEnum.HOME;
    }

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(MESSAGE_HOME_ACKNOWLEDGEMENT, false, false);
    }

}
