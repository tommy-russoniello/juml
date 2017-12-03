package umlaction;

import umlobject.*;

import javafx.scene.shape.Line;

/*
 * Action class for deleting Relationship Pivots.
 * @author Samuel Carroll
 * @author Torrance Graham
 * @author Quinn Minnich
 * @author Thomas Russoniello
 * @version 0.3
 * @since 0.3
 */
public class DeletePivot extends UMLConnectorAction {
  Relationship relationship;
  Pivot pivot;
  Segment preSegment, postSegment;
  int pos;

  public DeletePivot(Relationship inRelationship, Pivot inPivot) {
    if (inRelationship != null && inPivot != null) {
      relationship = inRelationship;
      pivot = inPivot;
      pos = relationship.pivots.indexOf(pivot);
      preSegment = relationship.segments.get(pos);
      postSegment = relationship.segments.get(pos + 1);
      doAction();
    }
  }

  public void doAction() {
    preSegment.disconnect();

    postSegment.disconnect();
    postSegment.start = preSegment.start;
    postSegment.connect();

    if ((Line) preSegment.getModel() == relationship.startLine) {
      relationship.startLine = (Line) postSegment.getModel();
    }

    relationship.group.getChildren().remove(preSegment.getModel());
    relationship.group.getChildren().remove(pivot.getModel());

    relationship.segments.remove(preSegment);
    relationship.pivots.remove(pivot);

    relationship.update(true);
  }

  public void undoAction() {
    relationship.pivots.add(pos, pivot);
    relationship.segments.add(pos, preSegment);

    relationship.group.getChildren().add(pivot.getModel());
    relationship.group.getChildren().add(preSegment.getModel());

    if ((Line) postSegment.getModel() == relationship.startLine) {
      relationship.startLine = (Line) preSegment.getModel();
    }

    postSegment.disconnect();
    postSegment.start = pivot;
    postSegment.connect();

    preSegment.connect();

    relationship.update(true);
  }
}
