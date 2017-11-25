package umlobject;

import java.util.Vector;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;

/*
 * UML composition representation.
 * @author Samuel Carroll
 * @author Torrance Graham
 * @author Quinn Minnich
 * @author Thomas Russoniello
 * @version 0.3
 * @since 0.3
 */
public class Composition extends Relationship {
  /*
   * Explicit Constructor
   * @param inStart starting UMLNode for Segment to be drawn between.
   * @param inStop stopping UMLNode for Segment to be drawn between.
   * @postcondition Composition instance with given starting and stopping UMLNodes is created.
   */
  public Composition(UMLNode inStart, UMLNode inStop) {
    start = inStart;
    stop = inStop;
    originX = start.getOriginX();
    originY = start.getOriginY();
    segments = new Vector<>();
    pivots = new Vector<>();

    // Initial Segment and Shape drawing.
    segments.add(new Segment(start, stop, false, true));
    mainLine = (Line) segments.firstElement().getModel();
    shape = new Polygon();
    resetMainLine();
    shape.setStroke(Color.BLACK);
    shape.setFill(Color.BLACK);

    group = new Group();
    group.getChildren().addAll(mainLine, shape);
    // Move segment to proper starting position.
    update();
  }

  public void resetMainLine() {
    super.resetMainLine();
    shape.getPoints().addAll(new Double [] {
      mainLine.getEndX() - .5,   mainLine.getEndY(),
      mainLine.getEndX() - 8.75, mainLine.getEndY() - 5,
      mainLine.getEndX() - 17.5,   mainLine.getEndY(),
      mainLine.getEndX() - 8.75, mainLine.getEndY() + 5
    });
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
  public void update() {
    super.update(8.5);
  }

  /*
   * Changes color of underlying Group model's line and shape to make the object appear highlighted.
   * @postcondition Color of underlying Group model's line and shape changed to blue.
   */
  public void highlight() {
    super.highlight();
    shape.setFill(Color.BLUE);
  }

  /*
   * Changes color of underlying Group model's line and shape to make the object appear
   * unhighlighted.
   * @postcondition Color of underlying Group model's line changed to black.
   */
  public void unhighlight() {
    super.unhighlight();
    shape.setFill(Color.BLACK);
  }
}
