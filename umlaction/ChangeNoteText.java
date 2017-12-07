package umlaction;

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
  
  /** 
   * The note controller. 
   */
  NoteController noteController;
  
  /** 
   * The note. 
   */
  Note note;
  
  /** 
   * The alt text. 
   */
  String altText;
  
  /** 
   * The width. 
   */
  double width;

  /**
   * Instantiates a new change note text.
   *
   * @param inNote the in note
   * @param newText the new text
   */
  public ChangeNoteText(Note inNote, String newText) {
    noteController = null;
    note = inNote;
    altText = newText;
    doAction();
  }

  /**
   * Instantiates a new change note text.
   *
   * @param inNote the in note
   * @param newText the new text
   * @param inController the in controller
   */
  public ChangeNoteText(Note inNote, String newText, NoteController inController) {
    noteController = inController;
    note = inNote;
    altText = newText;
    doAction();
  }

  /**
   * @see umlaction.UMLAction#doAction()
   */
  public void doAction() {
    if (noteController != null) {
      noteController.noteText.setText(altText);
    }
    width = note.getWidth();
    String temp = altText;
    altText = note.getText();
    note.setText(temp);
  }

  /**
   * @see umlaction.UMLAction#undoAction()
   */
  public void undoAction() {
    if (noteController != null) {
      noteController.noteText.setText(altText);
    }
    String temp = altText;
    altText = note.getText();
    note.setText(temp);
    note.setWidth(width);
  }
}
