package seedu.address.model.wordbanklist;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.model.util.SampleDataUtil;
import seedu.address.model.wordbank.WordBank;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by Card#isSameMeaning(Card) comparison)
 */
public class WordBankList implements ReadOnlyWordBankList {

    private final UniqueWordBankList wordBankList;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        wordBankList = new UniqueWordBankList();
    }

    public WordBankList(List<WordBank> wbl) {
        if (!wbl.isEmpty()) {
            for (WordBank wb : wbl) {
                wordBankList.add(wb);
            }
        } else {
            WordBank sampleWb = SampleDataUtil.getSampleWordBank();
            wordBankList.add(sampleWb);
        }
    }

    //    /**
    //     * Creates a WordBank using the Cards in the {@code toBeCopied}
    //     */
    //    public WordBankList(ReadOnlyWordBankList toBeCopied) {
    //        this();
    //        resetData(toBeCopied);
    //    }

    //// list overwrite operations

    /**
     * Replaces the contents of the card list with {@code cards}.
     * {@code cards} must not contain any cards with the same meaning.
     */
    public void setWordBankList(List<WordBank> wordBankList) {
        this.wordBankList.setWordBankList(wordBankList);
    }

    /**
     * Resets the existing data of this {@code WordBank} with {@code newData}.
     */
    public void resetData(ReadOnlyWordBankList newData) {
        requireNonNull(newData);

        setWordBankList(newData.getWordBankList());
    }

    //// card-level operations

    /**
     * Returns true if a card with the same meaning as {@code card} exists in the word bank.
     */
    @Override
    public boolean hasWordBank(WordBank wordBank) {
        requireNonNull(wordBank);
        return wordBankList.contains(wordBank);
    }

    /**
     * Adds a card to the word bank.
     * A card with the same meaning must not already exist in the word bank.
     */
    public void addBank(WordBank wordBank) {
        wordBankList.add(wordBank);
    }

    //    /**
    //     * Replaces the given card {@code target} in the list with {@code editedCard}.
    //     * {@code target} must exist in the word bank.
    //     * The card meaning of {@code editedCard} must not be the same as another existing card in the word bank.
    //     */
    //    public void setWordBankList(WordBank target, WordBank editedCard) {
    //        requireNonNull(editedCard);
    //
    //        wordBankList.setWordBankList(target, editedCard);
    //    }

    /**
     * Removes {@code key} from this {@code WordBank}.
     * {@code key} must exist in the word bank.
     */
    public void removeWordBank(WordBank wordBankName) {
        wordBankList.remove(wordBankName);
    }

    //// util methods

    @Override
    public String toString() {
        return wordBankList.asUnmodifiableObservableList().size() + " cards";
        // TODO: refine later
    }

    @Override
    public int size() {
        return wordBankList.size();
    }

    @Override
    public ObservableList<WordBank> getWordBankList() {
        return wordBankList.asUnmodifiableObservableList();
    }

    @Override
    public WordBank getWordBank(String name) {
        for (WordBank wb : wordBankList) {
            if (wb.getName().equals(name)) {
                return wb;
            }
        }
        return this.getWordBank("sample"); // returning the sample bank if can't find
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof WordBank // instanceof handles nulls
                && wordBankList.equals(((WordBankList) other).wordBankList));
    }

    @Override
    public int hashCode() {
        return wordBankList.hashCode();
    }
}
