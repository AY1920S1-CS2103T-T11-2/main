package seedu.address.model.wordbanklist;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;

import javafx.collections.ObservableList;
import seedu.address.model.card.exceptions.DuplicateWordBankException;
import seedu.address.model.card.exceptions.WordBankNotFoundException;
import seedu.address.model.util.SampleDataUtil;
import seedu.address.model.wordbank.ReadOnlyWordBank;
import seedu.address.model.wordbank.WordBank;

/**
 * Wraps all data at the word bank folder level.
 * Duplicates are not allowed (by WordBank#isSameName(WordBank) comparison)
 */
public class WordBankList implements ReadOnlyWordBankList {

    private final UniqueWordBankList wordBankList;

    public WordBankList(List<WordBank> wbl) {
        wordBankList = new UniqueWordBankList();
        try {
            if (!wbl.isEmpty()) {
                for (WordBank wb : wbl) {
                    wordBankList.add(wb);
                }
            } else {
                WordBank sampleWb = SampleDataUtil.getSampleWordBank();
                wordBankList.add(sampleWb);
            }
        } catch (DuplicateWordBankException e) {
        }
    }

    /**
     * Replaces the contents of the word bank list with {@code List<WordBank>}.
     * {@code List<WordBank>} must not contain any cards with the same meaning.
     */
    public void setWordBankList(List<WordBank> wordBankList) throws DuplicateWordBankException {
        this.wordBankList.setWordBankList(wordBankList);
    }

    /**
     * Adds a card to the word bank.
     * A card with the same meaning must not already exist in the word bank.
     */
    public void addBank(ReadOnlyWordBank wordBank) throws DuplicateWordBankException {
        wordBankList.add((WordBank) wordBank);
    }

    /**
     * Removes {@code key} from this {@code WordBank}.
     * {@code key} must exist in the word bank.
     */
    public void removeWordBank(WordBank wordBankName) throws WordBankNotFoundException {
        wordBankList.remove(wordBankName);
    }

    // util methods

    @Override
    public int size() {
        return wordBankList.size();
    }

    @Override
    public ObservableList<WordBank> getWordBankList() {
        return wordBankList.asUnmodifiableObservableList();
    }

    @Override
    public WordBank getWordBankFromName(String name) throws WordBankNotFoundException {
        for (WordBank wb : wordBankList) {
            if (wb.getName().equals(name)) {
                return wb;
            }
        }
        throw new WordBankNotFoundException();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof WordBankList // instanceof handles nulls
                && wordBankList.equals(((WordBankList) other).wordBankList));
    }

    @Override
    public int hashCode() {
        return wordBankList.hashCode();
    }
}
