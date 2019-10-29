package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.switches.OpenCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.util.AutoFillAction;

class SpecificModeParserTest {

    private SpecificModeParser specificModeParser;

    @BeforeEach
    void setUp() {
        specificModeParser = new SpecificModeParser();
        specificModeParser.add(OpenCommand.class, null);
    }

    @Test
    void add() {

    }

    @Test
    void parseCommand() {
        try {
            Command out = specificModeParser.parseCommand("open");
            assertTrue(out instanceof OpenCommand);
        } catch (ParseException e) {
            System.err.println(e);
        }
    }

    @Test
    void getAutoFill() {
        List<AutoFillAction> out = specificModeParser.getAutoFill("open");
        boolean foundMatch = false;
        for (AutoFillAction a : out) {
            if (a.toString().equals("open")) {
                foundMatch = true;
            }
        }
        assertTrue(foundMatch);
    }
}
