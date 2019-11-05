package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_ABRA;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BUTTERFREE;

import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CARD;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_CARD;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.cardcommands.ClearCommand;
import seedu.address.logic.commands.cardcommands.EditCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;


/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager();


    //    @Test
    //    public void execute_allFieldsSpecifiedUnfilteredList_success() {
    //        Card editedCard = new CardBuilder().build();
    //        EditCommand.EditCardDescriptor descriptor = new EditCardDescriptorBuilder(editedCard).build();
    //        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON, descriptor);
    //
    //        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_CARD_SUCCESS, editedCard);
    //
    //        Model expectedModel = new ModelManager(new WordBank(model.toModelType()), new UserPrefs());
    //        expectedModel.setCard(model.getFilteredCardList().get(0), editedCard);
    //
    //        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    //    }

    //    @Test
    //    public void execute_someFieldsSpecifiedUnfilteredList_success() {
    //        Index indexLastCard = Index.fromOneBased(model.getFilteredCardList().size());
    //        Card lastCard = model.getFilteredCardList().get(indexLastCard.getZeroBased());
    //
    //        CardBuilder cardInList = new CardBuilder(lastCard);
    //        Card editedCard = cardInList.withWord(VALID_WORD_BUTTERFREE).withTags(VALID_TAG_BUG).build();
    //
    //        EditCommand.EditCardDescriptor descriptor = new EditCardDescriptorBuilder()
    //                .withWord(VALID_WORD_BUTTERFREE).withTags(VALID_TAG_BUG).build();
    //        EditCommand editCommand = new EditCommand(indexLastCard, descriptor);
    //
    //        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_CARD_SUCCESS, editedCard);
    //
    //        Model expectedModel = new ModelManager(new WordBank(model.toModelType()), new UserPrefs());
    //        expectedModel.setCard(lastCard, editedCard);
    //
    //        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    //    }
    //
    //    @Test
    //    public void execute_noFieldSpecifiedUnfilteredList_success() {
    //        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON, new EditCommand.EditCardDescriptor());
    //        Card editedCard = model.getFilteredCardList().get(INDEX_FIRST_PERSON.getZeroBased());
    //
    //        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_CARD_SUCCESS, editedCard);
    //
    //        Model expectedModel = new ModelManager(new WordBank(model.toModelType()), new UserPrefs());
    //
    //        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    //    }

    //    @Test
    //    public void execute_filteredList_success() {
    //        showCardAtIndex(model, INDEX_FIRST_PERSON);
    //
    //        Card cardInFilteredList = model.getFilteredCardList().get(INDEX_FIRST_PERSON.getZeroBased());
    //        Card editedCard = new CardBuilder(cardInFilteredList).withWord(VALID_WORD_BUTTERFREE).build();
    //        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON,
    //                new EditCardDescriptorBuilder().withWord(VALID_WORD_BUTTERFREE).build());
    //
    //        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_CARD_SUCCESS, editedCard);
    //
    //        Model expectedModel = new ModelManager(new WordBank(model.toModelType()), new UserPrefs());
    //        expectedModel.setCard(model.getFilteredCardList().get(0), editedCard);
    //
    //        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    //    }

    //    @Test
    //    public void execute_duplicateCardUnfilteredList_failure() {
    //        Card firstPerson = model.getFilteredCardList().get(INDEX_FIRST_PERSON.getZeroBased());
    //        EditCommand.EditCardDescriptor descriptor = new EditCardDescriptorBuilder(firstPerson).build();
    //        EditCommand editCommand = new EditCommand(INDEX_SECOND_PERSON, descriptor);
    //
    //        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_CARD);
    //    }

    //    @Test
    //    public void execute_duplicateCardFilteredList_failure() {
    //        showCardAtIndex(model, INDEX_FIRST_PERSON);
    //
    //        // edit person in filtered list into a duplicate in address book
    //        Card personInList = model.toModelType().getCardList().get(INDEX_SECOND_PERSON.getZeroBased());
    //        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON,
    //                new EditCardDescriptorBuilder(personInList).build());
    //
    //        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_CARD);
    //    }

    //    @Test
    //    public void execute_invalidCardIndexUnfilteredList_failure() {
    //        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredCardList().size() + 1);
    //        EditCommand.EditCardDescriptor descriptor =
    //                new EditCardDescriptorBuilder().withWord(VALID_WORD_BUTTERFREE).build();
    //        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);
    //
    //        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_CARD_DISPLAYED_INDEX);
    //    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    //    @Test
    //    public void execute_invalidPersonIndexFilteredList_failure() {
    //        showCardAtIndex(model, INDEX_FIRST_PERSON);
    //        Index outOfBoundIndex = INDEX_SECOND_PERSON;
    //        // ensures that outOfBoundIndex is still in bounds of address book list
    //        assertTrue(outOfBoundIndex.getZeroBased() < model.toModelType().getCardList().size());
    //
    //        EditCommand editCommand = new EditCommand(outOfBoundIndex,
    //                new EditCardDescriptorBuilder().withWord(VALID_WORD_BUTTERFREE).build());
    //
    //        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_CARD_DISPLAYED_INDEX);
    //    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_CARD, DESC_ABRA);

        // same values -> returns true
        EditCommand.EditCardDescriptor copyDescriptor = new EditCommand.EditCardDescriptor(DESC_ABRA);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_CARD, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_CARD, DESC_ABRA)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_CARD, DESC_BUTTERFREE)));
    }

}
