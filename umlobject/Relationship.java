package umlobject;

import java.lang.Math;
import java.util.Vector;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.Node;
import javafx.scene.shape.Shape;

public class Relationship extends UMLConnector {
  /*
   * underlying model.
   */
  public Group group;
  /*
   * underlying model's children.
   */
  public Polygon shape;
  Vector<Segment> segments;
  Vector<Pivot> pivots;
  Line mainLine;

  public void resetMainLine() {
    if (!shape.getPoints().isEmpty()) {
      shape.getPoints().clear();
    }
    mainLine.setStartX(0);
    mainLine.setStartY(0);
    mainLine.setEndX(50);
    mainLine.setEndY(0);
  }

  /*
   * "Redraws" underlying Group model's Segment to be between starting and stopping UMLNodes and
   * * its shape to be at the end of the Segment on the stopping side at the same angle as the
   * * Segment. Used when the starting and or the stopping UMLNode has been moved, or when initially
   * * setting position.
   * @postcondition Underlying Group model's Segment is reassigned to current coordinates of
   * * starting and stopping UMLNodes' anchor points and underlying Group model's shape is
   * * reassigned to end at stopping UMLNode's anchor point, rotated to match the Segment angle.
   */
  public void update(double midPoint) {
    // Calculate initial coordinates of shape
    double angle = calcArcTanAngle(mainLine);
    double initEndX = mainLine.getEndX() + midPoint * Math.cos(angle);
    double initEndY = mainLine.getEndY() - midPoint * Math.sin(angle);

    updateSegments();

    // Calculate new coordinates of shape and the delta of them from the initial coordinates.
    angle = calcArcTanAngle(mainLine);
    double deltaEndX = (mainLine.getEndX() + midPoint * Math.cos(angle)) - initEndX;
    double deltaEndY = (mainLine.getEndY() - midPoint * Math.sin(angle)) - initEndY;

    // If delta is NaN then reset Segment and shape and move them back to correct position.
    if (Double.isNaN(deltaEndX) || Double.isNaN(deltaEndY)) {
      resetMainLine();
      shape.setTranslateX(0);
      shape.setTranslateY(0);

      // Repeat above logic to move line back into place after resetting.
      //---------------------------------------------------------------------

      angle = calcArcTanAngle(mainLine);
      initEndX = mainLine.getEndX() + midPoint * Math.cos(angle);
      initEndY = mainLine.getEndY() - midPoint * Math.sin(angle);

      updateSegments();

      angle = calcArcTanAngle(mainLine);
      deltaEndX = (mainLine.getEndX() + midPoint * Math.cos(angle)) - initEndX;
      deltaEndY = (mainLine.getEndY() - midPoint * Math.sin(angle)) - initEndY;

      //---------------------------------------------------------------------
    }

    // Move shape.
    shape.setTranslateX(shape.getTranslateX() + deltaEndX);
    shape.setTranslateY(shape.getTranslateY() + deltaEndY);

    // Calculate Segment angle and rotate shape to match it.
    double rise = mainLine.getStartY() - mainLine.getEndY();
    double run = mainLine.getStartX() - mainLine.getEndX();
    double rotateAngle = Math.atan2(run, rise);
    rotateAngle *= (180 / Math.PI);
    rotateAngle = (rotateAngle + 90) % 360;
    shape.setRotate(-rotateAngle);
  }



  public void updateSegments() {
    if (pivots.isEmpty()) {
      segments.firstElement().update();
    } else {
      for(Pivot pivot : pivots) {
        pivot.update();
      }
    }
  }

  public double calcArcTanAngle(Line line) {
    double deltaX = line.getStartX() - line.getEndX();
    double deltaY = line.getEndY() - line.getStartY();
    double posAngle = Math.atan(deltaY/deltaX);
    if (line.getStartX() < line.getEndX()) {
      posAngle += Math.PI;
    }
    return posAngle;
  }

  /*
   * Returns list of segments in this UMLConnector
   * @return List of segments in this UMLConnector
   */
  public Vector<Segment> getSegments() {
    return segments;
  }

 /*
  * Returns list of pivots in this UMLConnector
  * @return List of pivots in this UMLConnector
  */
  public Vector<Pivot> getPivots() {
    return pivots;
  }

  /*
   * Changes color of underlying Group model's line and shape to make the object appear highlighted.
   * @postcondition Color of underlying Group model's line and shape changed to blue.
   */
  public void highlight() {
    for (Node child : group.getChildren()) {
      Shape shape = (Shape) child;
      shape.setStroke(Color.BLUE);
    }
  }

  /*
   * Changes color of underlying Group model's line and shape to make the object appear
   * unhighlighted.
   * @postcondition Color of underlying Group model's line changed to black.
   */
  public void unhighlight() {
    for (Node child : group.getChildren()) {
      Shape shape = (Shape) child;
      shape.setStroke(Color.BLACK);
    }
  }

  /*
   * Returns underlying model.
   * @return Underlying Group model.
   */
  public Node getModel() {
    return group;
  }
}
