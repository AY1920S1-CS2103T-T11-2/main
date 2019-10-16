package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.card.Card;
import seedu.address.model.game.Game;
import seedu.address.model.wordbanklist.WordBankList;
import seedu.address.model.wordbank.ReadOnlyWordBank;
import seedu.address.model.wordbank.WordBank;
import seedu.address.statistics.WordBankStatistics;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private WordBank wordBank;
    private final WordBankList wordBankList;
    private final UserPrefs userPrefs;
    private FilteredList<Card> filteredCards;
    private final FilteredList<WordBank> filteredWordBanks;

    //Placeholder game model
    private Game game = null;

    /**
     * Initializes a ModelManager with the given wordBank and userPrefs.
     */
    public ModelManager(ReadOnlyWordBank wordBank, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(wordBank, userPrefs);

        logger.fine("Initializing with word bank: " + wordBank + " and user prefs " + userPrefs);

        this.wordBank = new WordBank(wordBank, wordBank.getName());
//        this.wordBank = null;
        this.wordBankList = new WordBankList();

        this.userPrefs = new UserPrefs(userPrefs);
        filteredCards = new FilteredList<>(this.wordBank.getCardList());
        filteredWordBanks = new FilteredList<>(this.wordBankList.getWordBankList());
    }

    public ModelManager() {
        this(new WordBank("Empty WordBank"), new UserPrefs());
    }

    // Placeholder setGame method
    public void setGame(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return this.game;
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getWordBankFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setWordBankFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    //=========== WordBank ================================================================================

    @Override
    public void setWordBank(ReadOnlyWordBank wordBank) {
        this.wordBank = (WordBank) wordBank;
        filteredCards = new FilteredList<>(this.wordBank.getCardList());
        // todo set wb stats
//        this.wordBank.resetData(wordBank);
    }

    public void clearWordBank() {
        wordBank.resetData(new WordBank(wordBank.getName()));
        filteredCards = new FilteredList<>(this.wordBank.getCardList());
        //todo clear wb stats
//        this.wordBank.resetData(wordBank);
    }

    @Override
    public ReadOnlyWordBank getWordBank() {
        return wordBank;
    }

    @Override
    public boolean hasCard(Card card) {
        requireNonNull(card);
        return wordBank.hasCard(card);
    }

    @Override
    public void deleteCard(Card target) {
        wordBank.removeCard(target);
    }

    @Override
    public void addCard(Card card) {
        wordBank.addCard(card);
        updateFilteredCardList(PREDICATE_SHOW_ALL_CARDS);
    }

    @Override
    public void setCard(Card target, Card editedCard) {
        requireAllNonNull(target, editedCard);

        wordBank.setCard(target, editedCard);
    }

    //=========== Filtered Card List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Card} backed by the internal list of
     * {@code versionedAddressBook} // todo what is this versionedAddressBook?
     */
    @Override
    public ObservableList<Card> getFilteredCardList() {
        filteredCards = new FilteredList<>(this.wordBank.getCardList());
        return filteredCards;
    }

    /**
     * Returns an unmodifiable view of the list of {@code Card} backed by the internal list of
     * {@code versionedAddressBook} // todo what is this versionedAddressBook?
     */
    @Override
    public ObservableList<WordBank> getFilteredWordBankList() {
        return filteredWordBanks;
    }

    @Override
    public WordBankList getWordBankList() {
        return wordBankList;
    }

    @Override
    public void updateFilteredCardList(Predicate<Card> predicate) {
        requireNonNull(predicate);
        System.out.println("++++++++++");
        System.out.println(wordBank.getName());
        for(Card c : filteredCards) {
            System.out.println(c);
        }
        filteredCards.setPredicate(predicate);
        filteredCards = new FilteredList<>(this.wordBank.getCardList());
        System.out.println("++++++++++");
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return wordBank.equals(other.wordBank)
                && userPrefs.equals(other.userPrefs)
                && filteredCards.equals(other.filteredCards);
    }
}
