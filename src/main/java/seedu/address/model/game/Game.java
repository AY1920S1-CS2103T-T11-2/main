package seedu.address.model.game;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.model.appsettings.DifficultyEnum;
import seedu.address.model.card.Card;
import seedu.address.model.card.FormattedHint;
import seedu.address.model.wordbank.ReadOnlyWordBank;

/**
 * Represents a game session using Cards from a specified WordBank.
 * Guarantees: WordBank is not null, and that WordBank is not empty.
 */
public class Game {

    private DifficultyEnum currentGameDifficulty;
    private boolean isOver = false;

    // Shuffled Deck of Cards using cards from ReadOnlyWordBank
    private final List<Card> shuffledDeckOfCards;

    // Stateful field Index that updates as game progresses.
    private Index cardIndex;

    /**
     * Constructor for Game instance that takes in a WordBank.
     * WordBank must not be null.
     * @param wordBank WordBank that current Game session will run on.
     */
    public Game(ReadOnlyWordBank wordBank, CardShuffler cardShuffler, DifficultyEnum difficulty) {
        requireAllNonNull(wordBank, cardShuffler);
        this.cardIndex = Index.fromZeroBased(0);
        this.shuffledDeckOfCards = setShuffledDeckOfCards(wordBank, cardShuffler);
        this.currentGameDifficulty = difficulty;
    }

    public DifficultyEnum getCurrentGameDifficulty() {
        return currentGameDifficulty;
    }

    /**
     * Returns current Card at the current index. Throws {@code UnsupportedOperationException}
     * if game has already ended (no more available cards).
     */
    public Card getCurrCard() throws UnsupportedOperationException {
        if (isOver()) {
            throw new UnsupportedOperationException("Game is already Over");
        }
        Card currentCard = shuffledDeckOfCards.get(cardIndex.getZeroBased());
        return currentCard;
    }


    /**
     * Returns meaning of current Card at the current index as a string.
     * Throws {@code UnsupportedOperationException} if game has already ended (no more available cards).
     */
    public String getCurrQuestion() throws UnsupportedOperationException {
        return getCurrCard().getMeaning().toString();
    }

    public FormattedHint getHintFormatForCurrCard() {
        return getCurrCard().getHint();
    }

    public int getHintFormatSizeOfCurrCard() {
        return getCurrCard().getHintFormatSize();
    }

    /**
     * Returns true if the user's guess is correct.
     * @param inputGuess User's input guess of the game's current card.
     * @throws UnsupportedOperationException if game has already ended.
     */
    public boolean checkGuess(Guess inputGuess) throws UnsupportedOperationException {
        return inputGuess.matches(getCurrCard().getWord());
    }

    private Index getCurrIndex() {
        return cardIndex;
    }

    /**
     * Returns the state of the Game, whether it is over. Note once a Game has been stopped, it
     * cannot be resumed at any point, for now
     * @return True if game is indeed terminated.
     */
    public boolean isOver() {
        if (isOver) {
            return true;
        } else {
            isOver = getCurrIndex().getZeroBased() >= shuffledDeckOfCards.size() ? true : false;
            return isOver;
        }
    }

    public void forceStop() {
        isOver = true;
    }

    public void moveToNextCard() {
        cardIndex.increment();
    }

    /**
     * Generates a randomly shuffled deck of cards based on input {@code wordBank} and returns
     * it as a {@code List<Card>}.
     */
    private List<Card> setShuffledDeckOfCards(ReadOnlyWordBank wordBank, CardShuffler cardShuffler) {
        ArrayList<Card> shuffledDeckOfCards = new ArrayList<>();

        // Copy over all cards in input WordBank
        Index tempIndex = Index.fromZeroBased(0);
        while (tempIndex.getZeroBased() < wordBank.size()) {
            shuffledDeckOfCards.add(tempIndex.getZeroBased(), wordBank.getCard(tempIndex));
            tempIndex.increment();
        }
        cardShuffler.shuffle(shuffledDeckOfCards);
        return shuffledDeckOfCards;
    }

    /**
     * Functional interface that represents a specific method to shuffle the Cards for this current
     * game session.
     */
    @FunctionalInterface
    public interface CardShuffler {
        void shuffle(List<Card> cardsToShuffle);
    }
}
