package umlobject;

import java.lang.Math;
import java.util.List;
import javafx.scene.Group;
import javafx.scene.layout.VBox;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;

/*
 * UML relationship representation.
 * @author Samuel Carroll
 * @author Torrance Graham
 * @author Quinn Minnich
 * @author Thomas Russoniello
 * @version 0.3
 * @since 0.3
 */
public class Relationship extends UMLConnector {
  /*
   * underlying model.
   */
  public Group group;
  /*
   * underlying model's children.
   */
  public Polygon shape;
  public Note startText, endText;
  public List<Segment> segments;
  public List<Pivot> pivots;

  public Line startLine, endLine;

  /*
   * Resets start and end Lines to be in default position so shape and note positions can be reset.
   * * Used to line all shapes back up with each other after a bad movement (NaN delta).
   * @postcondition Positions of first and last segment (may be the same one), and notes are reset
   * * to default (shape is resest in each individual relationship Class implementation).
   */
  public void reset() {
    if (!shape.getPoints().isEmpty()) {
      shape.getPoints().clear();
    }
    endLine.setStartX(0);
    endLine.setStartY(0);
    endLine.setEndX(50);
    endLine.setEndY(0);
    startLine.setStartX(0);
    startLine.setStartY(0);
    startLine.setEndX(50);
    startLine.setEndY(0);
    startText.move(20, 0);
    endText.move(20, 0);
  }

  /*
   * Removes start and end Notes from group's children, effectively hiding them from the scene.
   * @postcondition start and end Notes are removed from group's children.
   */
  public void hideText() {
    group.getChildren().remove(startText.getModel());
    group.getChildren().remove(endText.getModel());
  }

  /*
   * Adds start and end Notes to group's children, effectively making them visible in the scene.
   * @postcondition start and end Notes are added to group's children.
   */
  public void showText() {
    group.getChildren().add(startText.getModel());
    group.getChildren().add(endText.getModel());
  }

  /*
   * Getter for the contents of startText Note.
   * @return contents of startText Note as String.
   */
  public String getStartText() {
    return startText.getText();
  }

  /*
   * Getter for the contents of startText Note.
   * @return contents of startText Note as String.
   */
  public String getEndText() {
    return endText.getText();
  }

  /*
   * Changes text of startText Note to given string if the resulting width of startText is <= 20.
   * @param newText String for startText Note's text to potentially be changed to.
   * @return Boolean of whether startText's text was actually changed.
   * @postcondition Text of startText Note is changed to newText unless the resulting width of
   * * startText would be > 20 (in that case nothing is changed).
   */
  public boolean setStartText(String newText) {
    String temp = getStartText();
    startText.setText(newText);
    if (startText.text.getLayoutBounds().getWidth() > 20) {
      startText.setText(temp);
      return false;
    }
    return true;
  }

  /*
   * Changes text of endText Note to given string if the resulting width of endText is <= 20.
   * @param newText String for endText Note's text to potentially be changed to.
   * @return Boolean of whether endText's text was actually changed.
   * @postcondition Text of endText Note is changed to newText unless the resulting width of
   * * endText would be > 20 (in that case nothing is changed).
   */
  public boolean setEndText(String newText) {
    String temp = getEndText();
    endText.setText(newText);
    if (endText.text.getLayoutBounds().getWidth() > 20) {
      endText.setText(temp);
      return false;
    }
    return true;
  }

  /*
   * "Redraws" underlying Group model's Segments to be between starting and stopping UMLNodes,
   * * its shape to be at the end of the Segment on the stopping side at the same angle as the
   * * last Segment, and the notes to be on the starting of the first segment and the ending of the
   * * last segment. Used when the starting, stopping, or pivot UMLNodes have been moved, or when
   * * initially setting position.
   * @postcondition Underlying Group model's Segments are reassigned to current coordinates of
   * * starting, stopping, and pivot UMLNodes' anchor points, underlying Group model's shape is
   * * reassigned to end at stopping UMLNode's anchor point (rotated to match the last Segment's
   * * angle), and start/endText Notes are reassigned to be at start of first segment and end of
   * * last segment, respectively.
   */
  public void update(double midPoint, boolean isReset) {
    final double startTextMidPoint = 20, endTextMidPoint = 30;
    double startAngle, endAngle, initShapeX, initShapeY, initStartX, initStartY, initEndX, initEndY,
      deltaShapeX = 0, deltaShapeY = 0, deltaStartX = 0, deltaStartY = 0, deltaEndX = 0, deltaEndY = 0;
    if (!isReset) {
      // Calculate initial coordinates of shape and notes
      endAngle = calcArcTanAngle(endLine, "end");
      initShapeX = endLine.getEndX() + midPoint * Math.cos(endAngle);
      initShapeY = endLine.getEndY() - midPoint * Math.sin(endAngle);
      initEndX = endLine.getEndX() + endTextMidPoint * Math.cos(endAngle);
      initEndY = endLine.getEndY() - endTextMidPoint * Math.sin(endAngle);
      startAngle = calcArcTanAngle(startLine, "start");
      initStartX = startLine.getStartX() + startTextMidPoint * Math.cos(startAngle);
      initStartY = startLine.getStartY() - startTextMidPoint * Math.sin(startAngle);

      updateSegments();

      // Calculate new coordinates of shape and notes and the delta of them from their initial
      // * coordinates.
      endAngle = calcArcTanAngle(endLine, "end");
      deltaShapeX = (endLine.getEndX() + midPoint * Math.cos(endAngle)) - initShapeX;
      deltaShapeY = (endLine.getEndY() - midPoint * Math.sin(endAngle)) - initShapeY;
      deltaEndX = (endLine.getEndX() + endTextMidPoint * Math.cos(endAngle)) - initEndX;
      deltaEndY = (endLine.getEndY() - endTextMidPoint * Math.sin(endAngle)) - initEndY;
      startAngle = calcArcTanAngle(startLine, "start");
      deltaStartX = (startLine.getStartX() + startTextMidPoint * Math.cos(startAngle)) - initStartX;
      deltaStartY = (startLine.getStartY() - startTextMidPoint * Math.sin(startAngle)) - initStartY;
    }

    // If delta is NaN then reset segments, shape, and notes and move them back to their initial
    // * positions.
    if (Double.isNaN(deltaEndX) || Double.isNaN(deltaEndY) ||
        Double.isNaN(deltaStartX) || Double.isNaN(deltaStartY) || isReset) {
      reset();
      shape.setTranslateX(0);
      shape.setTranslateY(0);
      endText.getModel().setTranslateX(0);
      endText.getModel().setTranslateY(0);
      startText.getModel().setTranslateX(0);
      startText.getModel().setTranslateY(0);

      // Repeat above logic to move segments, notes, and shape back into place after resetting.
      //---------------------------------------------------------------------

      endAngle = calcArcTanAngle(endLine, "end");
      initShapeX = endLine.getEndX() + midPoint * Math.cos(endAngle);
      initShapeY = endLine.getEndY() - midPoint * Math.sin(endAngle);
      initEndX = endLine.getEndX() + endTextMidPoint * Math.cos(endAngle);
      initEndY = endLine.getEndY() - endTextMidPoint * Math.sin(endAngle);
      startAngle = calcArcTanAngle(startLine, "start");
      initStartX = startLine.getStartX() + startTextMidPoint * Math.cos(startAngle);
      initStartY = startLine.getStartY() - startTextMidPoint * Math.sin(startAngle);

      updateSegments();

      endAngle = calcArcTanAngle(endLine, "end");
      deltaShapeX = (endLine.getEndX() + midPoint * Math.cos(endAngle)) - initShapeX;
      deltaShapeY = (endLine.getEndY() - midPoint * Math.sin(endAngle)) - initShapeY;
      deltaEndX = (endLine.getEndX() + endTextMidPoint * Math.cos(endAngle)) - initEndX;
      deltaEndY = (endLine.getEndY() - endTextMidPoint * Math.sin(endAngle)) - initEndY;
      startAngle = calcArcTanAngle(startLine, "start");
      deltaStartX = (startLine.getStartX() + startTextMidPoint * Math.cos(startAngle)) - initStartX;
      deltaStartY = (startLine.getStartY() - startTextMidPoint * Math.sin(startAngle)) - initStartY;

      //---------------------------------------------------------------------
    }

    // Move shape and notes.
    shape.setTranslateX(shape.getTranslateX() + deltaShapeX);
    shape.setTranslateY(shape.getTranslateY() + deltaShapeY);
    endText.getModel().setTranslateX(endText.getModel().getTranslateX() + deltaEndX);
    endText.getModel().setTranslateY(endText.getModel().getTranslateY() + deltaEndY);
    startText.getModel().setTranslateX(startText.getModel().getTranslateX() + deltaStartX);
    startText.getModel().setTranslateY(startText.getModel().getTranslateY() + deltaStartY);

    // Calculate last Segment angle and rotate shape to match it.
    double rise = endLine.getStartY() - endLine.getEndY();
    double run = endLine.getStartX() - endLine.getEndX();
    double rotateAngle = Math.atan2(run, rise);
    rotateAngle *= (180 / Math.PI);
    rotateAngle = (rotateAngle + 90) % 360;
    shape.setRotate(-rotateAngle);

    // Reset Notes to be on top of lines if they were already visible.
    if (textHidden()) {
      hideText();
      showText();
    }
  }

  public void update(double midPoint) {
    update(midPoint, false);
  }

  public void update(boolean isReset) {
    // This is just so update can be called this way on any given relationship. This method should
    // * be implemented in any direct subclasses of this Class.
  }

  /*
   * Returns whether or not this's notes are hidden.
   * @return Boolean value for whether or not this's note are hidden.
   */
  public boolean textHidden() {
    return group.getChildren().contains(startText.getModel()) &&
      group.getChildren().contains(startText.getModel());
  }


  /*
   * updates all segments in the Relationship.
   * @postcondition all segments in the Relationship update the coordinates of their lines.
   */
  public void updateSegments() {
    if (pivots.isEmpty()) {
      segments.get(0).update();
    } else {
      for(Pivot pivot : pivots) {
        pivot.updateSegments();
      }
    }
  }

  /*
   * Calculates arctangent angle of given line for given direction.
   * @param line Line for angle to be calculated on.
   * @param direction Direction of given line for angle to be calculated with.
   * @return Arctangent angle of given line for given direction.
   */
  public double calcArcTanAngle(Line line, String direction) {
    double deltaX, deltaY, posAngle;
    if (direction.equals("start")) {
      deltaX = line.getEndX() - line.getStartX();
      deltaY = line.getStartY() - line.getEndY();
      posAngle = Math.atan(deltaY/deltaX);
      if (line.getEndX() < line.getStartX()) {
        posAngle += Math.PI;
      }
    } else if(direction.equals("end")) {
      deltaX = line.getStartX() - line.getEndX();
      deltaY = line.getEndY() - line.getStartY();
      posAngle = Math.atan(deltaY/deltaX);
      if (line.getStartX() < line.getEndX()) {
        posAngle += Math.PI;
      }
    } else {
      posAngle = Double.NaN;
    }
    return posAngle;
  }

  /*
   * Returns list of segments in this UMLConnector
   * @return List of segments in this UMLConnector
   */
  public List<Segment> getSegments() {
    return segments;
  }

 /*
  * Returns list of pivots in this UMLConnector
  * @return List of pivots in this UMLConnector
  */
  public List<Pivot> getPivots() {
    return pivots;
  }

  /*
   * Changes color of underlying Group model's line and shape to make the object appear highlighted.
   * @postcondition Color of underlying Group model's line and shape changed to blue.
   */
  public void highlight() {
    for (Node child : group.getChildren()) {
      if (child instanceof VBox) {
        VBox vbox = (VBox) child;
        vbox.setStyle("-fx-border-color: blue;");
      } else if (child instanceof Shape) {
        Shape shape = (Shape) child;
        shape.setStroke(Color.BLUE);
      }
    }
  }

  /*
   * Changes color of underlying Group model's line and shape to make the object appear
   * unhighlighted.
   * @postcondition Color of underlying Group model's line changed to black.
   */
  public void unhighlight() {
    for (Node child : group.getChildren()) {
      if (child instanceof VBox) {
        VBox vbox = (VBox) child;
        vbox.setStyle("-fx-border-color: white;");
      } else if (child instanceof Shape) {
        Shape shape = (Shape) child;
        shape.setStroke(Color.BLACK);
      }
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
