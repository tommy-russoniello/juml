package umlobject;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 * UMLConnector Class for connecting UMLNodes with a plain line.
 * @author Samuel Carroll
 * @author Torrance Graham
 * @author Quinn Minnich
 * @author Thomas Russoniello
 * @version 0.3
 * @since 0.3
 */
public class Segment extends UMLConnector {

  /**
   *The underlying model of the segment.
   */
  public Line line;

  /**
   * Explicit Constructor
   * @param inStart starting UMLNode for line to be drawn between.
   * @param inStop stopping UMLNode for line to be drawn between.
   * @param isDotted flag determing whether Segment will contain a dotted line or not.
   * @param isDefault flag determining whether Segment's line will be between the starting and
   *  stopping UMLNodes or be at the default coordinates.
   * @postcondition Segment instance with given starting and stopping UMLNodes (solid or dotted
   * depending on isDotted boolean) is created.
   */
  public Segment(UMLNode inStart, UMLNode inStop, boolean isDotted, boolean isDefault) {
    start = inStart;
    stop = inStop;
    if (isDefault) {
      line = new Line(0, 0, 50, 0);
      originX = 0;
      originY = 0;
    } else {
      line = new Line();
      originX = start.getOriginX();
      originY = start.getOriginY();
      // Set line to proper starting position.
      update();
    }
    line.setStrokeWidth(2);
    if (isDotted) {
      line.getStrokeDashArray().addAll(6d, 12d);
    }
   }

  /**
   * Basic Constructor
   * @param inStart starting UMLNode for line to be drawn between.
   * @param inStop stopping UMLNode for line to be drawn between.
   * @postcondition Solid Segment instance with given starting and stopping UMLNodes is created.
   */
   public Segment(UMLNode inStart, UMLNode inStop) {
     this(inStart, inStop, false, false);
   }

  /**
   * Standard Constructor
   * @param inStart starting UMLNode for line to be drawn between.
   * @param inStop stopping UMLNode for line to be drawn between.
   * @param dotted flag determing whether Segment will contain a dotted line or not.
   * @postcondition Segment instance with given starting and stopping UMLNodes (solid or dotted
   * depending on isDotted boolean) is created.
   */
  public Segment(UMLNode inStart, UMLNode inStop, boolean isDotted) {
    this(inStart, inStop, isDotted, false);
  }

  /**
   * "Redraws" underlying Group model's Line to be between starting and stopping UMLNodes and it's
   *  shape to be at the end of the line on the stopping side at the same angle as the Line. Used
   *  when the starting and or the stopping UMLNode has been moved, or when initially setting
   *  position.
   * @postcondition Underlying Group model's Line is reassigned to current coordinates of starting
   *  and stopping UMLNodes' anchor points and underlying Group model's shape is reassigned to
   *  end at stopping UMLNode's anchor point, rotated to match the Line's angle.
   */
  public void update() {
    originX = start.getOriginX();
    originY = start.getOriginY();
    line.setStartX(start.getAnchorX(stop.getOriginX(), stop.getOriginY()));
    line.setStartY(start.getAnchorY(stop.getOriginX(), stop.getOriginY()));
    line.setEndX(stop.getAnchorX(start.getOriginX(), start.getOriginY()));
    line.setEndY(stop.getAnchorY(start.getOriginX(), start.getOriginY()));
  }

  /**
   * Changes color of underlying Group model's line(s) to make the object appear highlighted.
   * @postcondition Color of underlying Group model's line(s) and shape changed to blue.
   */
  public void highlight() {
    line.setStroke(Color.BLUE);
  }

  /**
   * Changes color of underlying Group model's line(s) to make the object appear
   * unhighlighted.
   * @postcondition Color of underlying Group model's line(s) changed to black.
   */
  public void unhighlight() {
    line.setStroke(Color.BLACK);
  }

  /**
   * Returns underlying model.
   * @return Underlying Group model.
   */
  public Node getModel() {
    return line;
  }
}
