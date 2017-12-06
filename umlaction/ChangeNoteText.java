package umlaction;

import javafx.scene.text.Text;
import umlobject.*;
import juml.*;

/*
 * Action class for changing the text of a Note.
 * @author Samuel Carroll
 * @author Torrance Graham
 * @author Quinn Minnich
 * @author Thomas Russoniello
 * @version 0.3
 * @since 0.3
 */
public class ChangeNoteText extends UMLNodeAction {
  NoteController noteController;
  Note note;
  String altText;

  public ChangeNoteText(Note inNote, String newText) {
    noteController = null;
    note = inNote;
    altText = newText;
    doAction();
  }

  public ChangeNoteText(Note inNote, String newText, NoteController inController) {
    noteController = inController;
    note = inNote;
    altText = newText;
    doAction();
  }

  public void doAction() {
    if (noteController != null) {
      noteController.noteText.setText(altText);
    }
    String temp = altText;
    altText = note.getText();
    note.setText(temp);
  }

  public void undoAction() {
    if (noteController != null) {
      noteController.noteText.setText(altText);
    }
    String temp = altText;
    altText = note.getText();
    note.setText(temp);
  }
}
