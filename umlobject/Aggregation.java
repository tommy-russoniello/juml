package umlobject;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Vector;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import juml.Controller;

/**
 * UML aggregation representation.
 * @author Samuel Carroll
 * @author Torrance Graham
 * @author Quinn Minnich
 * @author Thomas Russoniello
 * @version 0.3
 * @since 0.3
 */
public class Aggregation extends Relationship {

	  /**
	   * Build from string method
	   * @param input The scanner from which the object can read in its save string
	   * @param allNodes List of all nodes currently in the scene.
	   * @param controller Master controller (needed for pivot construction)
	   * @postcondition generates the connector from the save string
	   */
	public Aggregation(Scanner input, Vector<UMLNode> allNodes, Controller controller) {
		super(input, allNodes);
		setUp();
		readInPivots(input, controller);
	}

	  /**
	   * Explicit Constructor
	   * @param inStart starting UMLNode for Segment to be drawn between.
	   * @param inStop stopping UMLNode for Segment to be drawn between.
	   * @postcondition Aggregation instance with given starting and stopping UMLNodes is created.
	   */
	public Aggregation(UMLNode inStart, UMLNode inStop) {
		super(inStart, inStop);
		setUp();
	}

	/**
	 * Sets up the Connector
	 * @postcondition Connect initializes all remaining variables and draws itself
	 */
	private void setUp() {
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
   *  Used to line all shapes back up with each other after a bad movement (NaN delta).
   * @postcondition Positions of first and last segment (may be the same one), notes, and shape are
   *  reset to default.
   */
  public void reset() {
    super.reset();
    shape.getPoints().addAll(new Double [] {
      endLine.getEndX() - .5,   endLine.getEndY(),
      endLine.getEndX() - 8.75, endLine.getEndY() - 5,
      endLine.getEndX() - 17.5,   endLine.getEndY(),
      endLine.getEndX() - 8.75, endLine.getEndY() + 5
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
    super.update(8.5, false);
  }

  /**
   * @param isReset is a boolean stating whether a reset needs to be done on the segments of the
   * relationship. This update is used after a pivot has been deleted.
   */
  public void update(boolean isReset) {
    super.update(8.5, isReset);
  }
}
