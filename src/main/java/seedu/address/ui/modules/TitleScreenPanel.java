package seedu.address.ui.modules;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.ui.UiPart;

/**
 * Panel containing the introduction screen.
 */
public class TitleScreenPanel extends UiPart<Region> {
    private static final String FXML = "TitleScreenPanel.fxml";
    private static final String MESSAGE_INTRODUCTION = "Welcome to Dukemon!";
    private static final String MESSAGE_INFORMATION = "Have you ever wanted to sit down and memorise things\n"
            + "but was always too lazy to do so? Well then this is your lucky day because this is the solution\n"
            + "for you! The Dukedex allows you to put in any terms you want to memorise along with its definitions\n"
            + "and tags. You can then start the game right away with \"start [INDEX]\".";

    @FXML
    private Label introduction;

    @FXML
    private Label body;

    public TitleScreenPanel() {
        super(FXML);
        introduction.setText(MESSAGE_INTRODUCTION);
        body.setText(MESSAGE_INFORMATION);
    }

}
