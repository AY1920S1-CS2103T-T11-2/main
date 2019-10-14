package seedu.address.model.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalCards.ABRA;
import static seedu.address.testutil.TypicalCards.BUTTERFREE;
import static seedu.address.testutil.TypicalCards.CHARIZARD;
import static seedu.address.testutil.TypicalCards.DITTO;

import org.junit.jupiter.api.Test;

import seedu.address.model.wordbank.WordBank;
import seedu.address.testutil.WordBankBuilder;

public class GameTest {

    @Test
    public void makeGuess() {
        WordBankBuilder wordBankBuilder = new WordBankBuilder();
        wordBankBuilder.withCard(ABRA);
        WordBank wb = wordBankBuilder.build();
        Game game = new Game(wb);

        // Makes correct guess; guess Abra as Abra
        assertTrue(game.checkGuess(new Guess(ABRA.getWord().toString())));

        // Makes wrong guess; guess Abra as Butterfree
        assertFalse(game.checkGuess(new Guess(BUTTERFREE.getWord().toString())));

        // When a guess is made after game has already ended
        assertThrows(UnsupportedOperationException.class, () -> {
            game.moveToNextCard();
            game.checkGuess(new Guess(BUTTERFREE.getWord().toString()));
        });
    }

    @Test
    public void nullWordBankPassedIntoConstructor_throwsNullPointerException() {
        WordBank wb = null;
        assertThrows(NullPointerException.class, () -> new Game(wb));
    }

    @Test
    public void moveToNextCard() {
        WordBankBuilder wordBankBuilder = new WordBankBuilder();
        wordBankBuilder.withCard(ABRA);
        wordBankBuilder.withCard(BUTTERFREE);
        wordBankBuilder.withCard(CHARIZARD);
        wordBankBuilder.withCard(DITTO);
        WordBank wb = wordBankBuilder.build();
        Game game = new Game(wb);

        game.moveToNextCard();

        // Current card is BUTTERFREE now.
        assertEquals(game.getCurrQuestion(), BUTTERFREE.getMeaning().toString());

        // Current card is NOT ABRA.
        assertNotEquals(game.getCurrQuestion(), ABRA.getMeaning().toString());

        game.moveToNextCard();
        game.moveToNextCard();

        // Current card is DITTO now.
        assertEquals(game.getCurrQuestion(), DITTO.getMeaning().toString());

        // Current card is NOT CHARIZARD now.
        assertNotEquals(game.getCurrQuestion(), CHARIZARD.getMeaning().toString());

        game.moveToNextCard();

        // Game should be over now.
        assertThrows(UnsupportedOperationException.class, () -> game.getCurrQuestion());
    }

    @Test
    public void isOver() {
        WordBankBuilder wordBankBuilder = new WordBankBuilder();
        wordBankBuilder.withCard(ABRA);
        wordBankBuilder.withCard(BUTTERFREE);
        WordBank wb = wordBankBuilder.build();
        Game game = new Game(wb);

        // Game has not ended, 2 cards left.
        assertEquals(false, game.isOver());

        game.moveToNextCard();

        // Game has not ended, 1 card left.
        assertEquals(false, game.isOver());

        game.moveToNextCard();

        // Game has ended, 0 card left.
        assertEquals(true, game.isOver());
    }

    @Test
    public void showCurrQuestion() {
        WordBankBuilder wordBankBuilder = new WordBankBuilder();
        wordBankBuilder.withCard(ABRA);
        wordBankBuilder.withCard(BUTTERFREE);
        WordBank wb = wordBankBuilder.build();
        Game game = new Game(wb);

        // ABRA card shows correctly.
        assertEquals(ABRA.getMeaning().toString(), game.getCurrQuestion());

        game.moveToNextCard();

        // BUTTERFREE card shows correctly.
        assertEquals(BUTTERFREE.getMeaning().toString(), game.getCurrQuestion());

        game.moveToNextCard();

        // Attempting to show current card's meaning when game already ended
        assertThrows(UnsupportedOperationException.class, () -> game.getCurrQuestion());
    }

}
