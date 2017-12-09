package umlaction;

import umlobject.*;

import javafx.scene.shape.Line;

/**
 * Action class for deleting Relationship Pivots.
 * @author Samuel Carroll
 * @author Torrance Graham
 * @author Quinn Minnich
 * @author Thomas Russoniello
 * @version 0.3
 * @since 0.3
 */
public class DeletePivot extends UMLConnectorAction {
  
  /** 
   * The relationship. 
   */
  Relationship relationship;
  
  /** 
   * The pivot. 
   */
  Pivot pivot;
  
  /** 
   * The post segment. 
   */
  Segment preSegment, postSegment;
  
  /** 
   * The position. 
   */
  int pos;

  /**
   * Instantiates a new delete pivot.
   *
   * @param inRelationship the in relationship
   * @param inPivot the in pivot
   */
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

  /**
   * @see umlaction.UMLAction#doAction()
   */
  public void doAction() {
    preSegment.disconnect();

    postSegment.disconnect();
    postSegment.start = preSegment.start;
    connectToPivots(postSegment);

    if ((Line) preSegment.getModel() == relationship.startLine) {
      relationship.startLine = (Line) postSegment.getModel();
    }

    relationship.group.getChildren().remove(preSegment.getModel());
    relationship.group.getChildren().remove(pivot.getModel());

    relationship.segments.remove(preSegment);
    relationship.pivots.remove(pivot);

    relationship.update(true);
  }

  /**
   * @see umlaction.UMLAction#undoAction()
   */
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
    connectToPivots(postSegment);

    connectToPivots(preSegment);

    relationship.update(true);
  }

  public void connectToPivots(Segment s) {
 	    if (s.start instanceof Pivot) {
 	    	s.start.getConnections().addElement(s);
 	    }
 	    if (s.stop instanceof Pivot) {
 	    	s.stop.getConnections().addElement(s);
 	    }
  }
}
