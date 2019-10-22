package seedu.address.ui;

import javafx.scene.layout.StackPane;


/**
 * Class to handle updating of Ui.
 */
public class UpdateUi {

    private ModularDisplay modularDisplay;
    private CurrentModeFooter currentModeFooter;

    public UpdateUi(ModularDisplay modularDisplay, CurrentModeFooter currentModeFooter) {
        this.modularDisplay = modularDisplay;
        this.currentModeFooter = currentModeFooter;
    }

    //Use string for now, eventually use mode.
    /**
     * Updates the mode in {@code MainWindow}.
     *
     * @param mode The mode to update to.
     */
    public void updateMode(String mode) {
        String firstArg = mode.split(" ")[0];
        if (firstArg.equals("home")) {
            currentModeFooter.changeMode("home");
        } else if (firstArg.equals("start")) {
            currentModeFooter.changeMode("game");
        } else if (firstArg.equals("load")) {
            currentModeFooter.changeMode("load");
        }
    }

    /**
     * Updates the {@code ModularDisplay} in {@code MainWindow}.
     *
     * @param command The command that triggers the update.
     * @param modularDisplayPlaceholder {@code modularDisplayPlaceholder} in {@code MainWindow} that gets updated.
     */
    public void updateModularDisplay(String command, StackPane modularDisplayPlaceholder) {
        String firstArg = command.split(" ")[0];
        if (firstArg.equals("load")) {
            modularDisplay.swapToBanks(modularDisplayPlaceholder);
        } else if (firstArg.equals("bank")) {
            modularDisplay.swapToBankDisplay(modularDisplayPlaceholder);
        } else if (firstArg.equals("list")) {
            modularDisplay.swapToList(modularDisplayPlaceholder);
        } else if (firstArg.equals("help")) {
            //modularDisplay.swapToBanks(modularDisplayPlaceholder);
        } else if (firstArg.equals("settings")) {
            modularDisplay.swapToSettings(modularDisplayPlaceholder);
        } else {
            modularDisplay.swapToLoadDisplay(modularDisplayPlaceholder);
        }
    }

}
