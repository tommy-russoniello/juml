package umlobject;

import java.util.Vector;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;

/*
 * UML generalization representation.
 * @author Samuel Carroll
 * @author Torrance Graham
 * @author Quinn Minnich
 * @author Thomas Russoniello
 * @version 0.3
 * @since 0.3
 */
public class Generalization extends Relationship {
  /*
   * Explicit Constructor
   * @param inStart starting UMLNode for Segment to be drawn between.
   * @param inStop stopping UMLNode for Segment to be drawn between.
   * @postcondition Generalization instance with given starting and stopping UMLNodes is created.
   */
  public Generalization(UMLNode inStart, UMLNode inStop) {
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
    shape.setFill(Color.WHITE);

    group = new Group();
    group.getChildren().addAll(mainLine, shape);
    // Move segment to proper starting position.
    update();
  }

  public void resetMainLine() {
    super.resetMainLine();
    shape.getPoints().addAll(new Double [] {
      mainLine.getEndX() - .5,       mainLine.getEndY(),
      mainLine.getEndX() - 13.125,   mainLine.getEndY() - 7.5,
      mainLine.getEndX() - 13.125,   mainLine.getEndY() + 7.5
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
    super.update(6.375);
  }
}
