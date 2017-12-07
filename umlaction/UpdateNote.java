package umlaction;

import juml.*;
import umlobject.*;

/**
 * Action class for updating all fields of a Note.
 * @author Samuel Carroll
 * @author Torrance Graham
 * @author Quinn Minnich
 * @author Thomas Russoniello
 * @version 0.3
 * @since 0.3
 */
public class UpdateNote extends UMLNodeAction {
  
  /** 
   * The note controller. 
   */
  NoteController noteController;
  
  /** 
   * The text. 
   */
  ChangeNoteText text;
  
  /** 
   * The move. 
   */
  MoveUMLNode move;

  /**
   * Instantiates a new update note.
   *
   * @param note the note
   * @param newText the new text
   * @param newX the new X
   * @param newY the new Y
   */
  public UpdateNote(Note note, String newText, double newX, double newY) {
    noteController = null;
    text = new ChangeNoteText(note, newText);
    move = new MoveUMLNode(note, newX, newY);
  }

  /**
   * Instantiates a new update note.
   *
   * @param note the note
   * @param newText the new text
   * @param newX the new X
   * @param newY the new Y
   * @param inNoteController the in note controller
   */
  public UpdateNote(Note note, String newText, double newX, double newY,
    NoteController inNoteController) {
    noteController = inNoteController;
    text = new ChangeNoteText(note, newText, noteController);
    move = new MoveUMLNode(note, newX, newY, noteController);
  }

  /**
   * @see umlaction.UMLAction#doAction()
   */
  public void doAction() {
    text.doAction();
    move.doAction();
  }

  /**
   * @see umlaction.UMLAction#undoAction()
   */
  public void undoAction() {
    text.undoAction();
    move.undoAction();
  }
}
