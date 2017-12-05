package umlobject;

import java.util.ArrayList;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;

/**
 * UML generalization representation.
 * @author Samuel Carroll
 * @author Torrance Graham
 * @author Quinn Minnich
 * @author Thomas Russoniello
 * @version 0.3
 * @since 0.3
 */
public class Generalization extends Relationship {

  /**
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
    segments = new ArrayList<>();
    pivots = new ArrayList<>();
    startText = new Note(0, 0, 20);
    endText = new Note(0, 0, 20);

    // Initial Segment and Shape drawing.
    segments.add(new Segment(start, stop, false, true));
    startLine = endLine = (Line) segments.get(0).getModel();

    shape = new Polygon();
    reset();
    shape.setStroke(Color.BLACK);
    shape.setFill(Color.WHITE);

    group = new Group();
    group.getChildren().addAll(endLine, shape);
    // Move segment to proper starting position.
    update();
  }

  /**
   * Resets start and end Lines to be in default position so shape and note positions can be reset.
   * * Used to line all shapes back up with each other after a bad movement (NaN delta).
   * @postcondition Positions of first and last segment (may be the same one), notes, and shape are
   * * reset to default.
   */
  public void reset() {
    super.reset();
    shape.getPoints().addAll(new Double [] {
      endLine.getEndX() - .5,       endLine.getEndY(),
      endLine.getEndX() - 13.125,   endLine.getEndY() - 7.5,
      endLine.getEndX() - 13.125,   endLine.getEndY() + 7.5
    });
  }

  /**
   * "Redraws" underlying Group model's Segments to be between starting and stopping UMLNodes,
   *  its shape to be at the end of the Segment on the stopping side at the same angle as the
   *  last Segment, and the notes to be on the starting of the first segment and the ending of the
   *  last segment. Used when the starting, stopping, or pivot UMLNodes have been moved, or when
   *  initially setting position.
   * @postcondition Underlying Group model's Segments are reassigned to current coordinates of
   *  starting, stopping, and pivot UMLNodes' anchor points, underlying Group model's shape is
   *  reassigned to end at stopping UMLNode's anchor point (rotated to match the last Segment's
   *  angle), and start/endText Notes are reassigned to be at start of first segment and end of
   *  last segment, respectively.
   */
  public void update() {
    super.update(6.375, false);
  }

  /**
   * @param isReset is a boolean stating whether a reset needs to be done on the segments of the
   * relationship. This update is used after a pivot has been deleted.
   */
  public void update(boolean isReset) {
    super.update(6.375, isReset);
  }
  
  public String getLineType(){
	  return "Generalization";
  }
}
