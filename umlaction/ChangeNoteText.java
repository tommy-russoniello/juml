package umlaction;

import javafx.scene.text.Text;
import umlobject.*;

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
  Note note;
  String altText;

  public ChangeNoteText(Note inNote, String newText) {
    note = inNote;
    altText = newText;
    doAction();
  }

  public void doAction() {
    String temp = altText;
    altText = note.getText();
    note.setText(temp);
  }

  public void undoAction() {
    String temp = altText;
    altText = note.getText();
    note.setText(temp);
  }
}
