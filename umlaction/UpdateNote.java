package umlaction;

import juml.*;
import umlobject.*;

/*
 * Action class for updating all fields of a Note.
 * @author Samuel Carroll
 * @author Torrance Graham
 * @author Quinn Minnich
 * @author Thomas Russoniello
 * @version 0.3
 * @since 0.3
 */
public class UpdateNote extends UMLNodeAction {
  NoteController noteController;
  ChangeNoteText text;
  MoveUMLNode move;

  public UpdateNote(Note note, String newText, double newX, double newY) {
    noteController = null;
    text = new ChangeNoteText(note, newText);
    move = new MoveUMLNode(note, newX, newY);
  }

  public UpdateNote(Note note, String newText, double newX, double newY,
    NoteController inController) {
    noteController = inController;
    text = new ChangeNoteText(note, newText, noteController);
    move = new MoveUMLNode(note, newX, newY, noteController);
  }

  public void doAction() {
    text.doAction();
    move.doAction();
  }

  public void undoAction() {
    text.undoAction();
    move.undoAction();
  }
}
