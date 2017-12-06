package umlobject;

import java.lang.Math;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

import javafx.scene.Group;
import javafx.scene.layout.VBox;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;
import juml.Controller;
import umlaction.SplitLine;

/**
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
   * The underlying model for Relationship.
   */
  public Group group;

  /*
   * The underlying model's children.
   */
  public Polygon shape;

  /**
   * The labels that can be place at the beginning and end of the Relationship.
   */
  public Note startText, endText;

  /**
   * Booleans representing whether or not beginning/end Notes are currently visible
   */
  public boolean startTextVisible, endTextVisible;

  /**
   * The list of segments in the Relationship.
   */
  public List<Segment> segments;

  /**
   * The list of pivots in the Relationship.
   */
  public List<Pivot> pivots;

  /**
   * The segments at the beginning and end of the Relationship.
   */
  public Line startLine, endLine;

  /**
   * Build from string method
   * @param input The scanner from which the object can read in its save string
   * @param allNodes List of all nodes currently in the scene.
   * @postcondition generates a Relationship built off of its save string; stops BEFORE it reaches pivot information.
   */
  public Relationship(Scanner input, Vector<UMLNode> allNodes) {
    startTextVisible = false;
    endTextVisible = false;
		start = allNodes.get(input.nextInt());
		stop = allNodes.get(input.nextInt());
		//connect();
		originX = start.getOriginX();
		originY = start.getOriginY();
		segments = new ArrayList<>();
		pivots = new ArrayList<>();
		startText = new Note(0, 0, 20);
		endText = new Note(0, 0, 20);
    int show = -1;
    show = input.nextInt();
		startText.setText(buildString(input, input.nextInt()));
    if (show == 1) {
      startTextVisible = true;
    }

    show = -1;
    show = input.nextInt();
		endText.setText(buildString(input, input.nextInt()));
    if (show == 1) {
      endTextVisible = true;
    }
	}

	/**
	 * Reads in just pivot information from a scanner, adding pivots in the order read
	 * @precondition Pivots are listed in the correct order
	 * @param input The scanner from which the text can be read
     * @param Controler The main controller; needed for pivot construction
	 * @postcondition All pivots will have been added to the relationship
	 */
	public void readInPivots(Scanner input, Controller controller) {
		String nextToken = input.next();
		while (nextToken.equals("Pivot:")){
			double x = input.nextDouble();
			double y = input.nextDouble();
			// add pivots here
			new SplitLine (this, segments.get(segments.size()-1), x, y, controller);
			nextToken = input.next();
		}
		update(true);
	}

	/**
	 * Build text
	 * @precondition The text to read in will end with a \n character
	 * @param input The scanner from which the text can be read
     * @param numChars The number of chars to read in
	 * @postcondition The method will read in lines until it has generated a string with the given number of chars
	 */
	public String buildString(Scanner input, int numChars) {
		String result = input.nextLine();
		if (numChars==0) {
			return "";
		}
		result = result.substring(1, result.length());
		//System.out.println(result.length() + ","+result);
		while(result.length() != numChars) {
			result += "\n";
			//System.out.println(result.length() + ","+result);
			result += input.nextLine();
		}
		return result;
	}


	  /**
	   * Basic Constructor.
	   * @param inStart starting Node
	   * @param inStop y ending Node
	   * @postcondition Relationship between start and stop nodes.
	   */
	public Relationship(UMLNode inStart, UMLNode inStop) {
    startTextVisible = false;
    endTextVisible = false;
		start = inStart;
		stop = inStop;
		originX = start.getOriginX();
		originY = start.getOriginY();
		segments = new ArrayList<>();
		pivots = new ArrayList<>();
		startText = new Note(0, 0, 20);
		endText = new Note(0, 0, 20);
	}

	  /**
	   * Save method. Adds onto UMLConnector save string. Stored as "chacactersInStartText startText\n chacactersInEndText endText\n
	   * pivotInfo1 pivotInfo2 ... EndPivots"
	   * @postcondition generates a string with the necessary information for the object to rebuild itself.
	   */
	public String saveAsString() {
		String result = "";
		int numStartTextChars = startText.getText().length();
		int numEndTextChars = endText.getText().length();
    int showStartText = 0, showEndText = 0;
    if (startTextVisible()) {
      showStartText = 1;
    }
    if (endTextVisible()) {
      showEndText = 1;
    }
		result += + showStartText + " " + numStartTextChars + " " + startText.getText() + "\n" +
      showEndText + " " + numEndTextChars + " " + endText.getText() + "\n";

		for (Pivot pivot : pivots) {
			result += pivot.saveAsString() + " ";
		}


		result += "EndPivots: ";
		return result;
	}



  /**
   * Resets start and end Lines to be in default position so shape and note positions can be reset.
   *  Used to line all shapes back up with each other after a bad movement (NaN delta).
   * @postcondition Positions of first and last segment (may be the same one), and notes are reset
   *  to default (shape is resest in each individual relationship Class implementation).
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

  /**
   * Removes start Note from group's children, effectively hiding it from the scene.
   * @postcondition start Note is removed from group's children.
   */
  public void hideStartText() {
    group.getChildren().remove(startText.getModel());
    startTextVisible = false;
  }

  /**
   * Removes end Note from group's children, effectively hiding it from the scene.
   * @postcondition end Note is removed from group's children.
   */
  public void hideEndText() {
    group.getChildren().remove(endText.getModel());
    endTextVisible = false;
  }

  /**
   * Removes start and end Notes from group's children, effectively hiding them from the scene.
   * @postcondition start and end Notes are removed from group's children.
   */
  public void hideText() {
    hideStartText();
    hideEndText();
  }

  /**
   * Adds start Note to group's children, effectively making it visible in the scene.
   * @postcondition start Note is added to group's children.
   */
  public void showStartText() {
    if (!group.getChildren().contains(startText.getModel())) {
      group.getChildren().add(startText.getModel());
      startTextVisible = true;
    }
  }

  /**
   * Adds end Note to group's children, effectively making it visible in the scene.
   * @postcondition end Note is added to group's children.
   */
  public void showEndText() {
    if (!group.getChildren().contains(endText.getModel())) {
      group.getChildren().add(endText.getModel());
      endTextVisible = true;
    }
  }

  /**
   * Adds start and end Notes to group's children, effectively making them visible in the scene.
   * @postcondition start and end Notes are added to group's children.
   */
  public void showText() {
    showStartText();
    showEndText();
  }

  /**
   * Getter for the contents of startText Note.
   * @return contents of startText Note as String.
   */
  public String getStartText() {
    return startText.getText();
  }

  /**
   * Getter for the contents of startText Note.
   * @return contents of startText Note as String.
   */
  public String getEndText() {
    return endText.getText();
  }

  /**
   * Changes text of startText Note to given string if the resulting width of startText is <= 20.
   * @param newText String for startText Note's text to potentially be changed to.
   * @return Boolean of whether startText's text was actually changed.
   * @postcondition Text of startText Note is changed to newText unless the resulting width of
   *  startText would be > 20 (in that case nothing is changed).
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

  /**
   * Changes text of endText Note to given string if the resulting width of endText is <= 20.
   * @param newText String for endText Note's text to potentially be changed to.
   * @return Boolean of whether endText's text was actually changed.
   * @postcondition Text of endText Note is changed to newText unless the resulting width of
   *  endText would be > 20 (in that case nothing is changed).
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

  /**
   * "Redraws" underlying Group model's Segments to be between starting and stopping UMLNodes,
   *  its shape to be at the end of the Segment on the stopping side at the same angle as the
   *  last Segment, and the notes to be on the starting of the first segment and the ending of the
   *  last segment. Used when the starting, stopping, or pivot UMLNodes have been moved, or when
   *  initially setting position.
   * @param midPoint the mid point
   * @param isReset the is reset
   * @postcondition Underlying Group model's Segments are reassigned to current coordinates of
   *  starting, stopping, and pivot UMLNodes' anchor points, underlying Group model's shape is
   *  reassigned to end at stopping UMLNode's anchor point (rotated to match the last Segment's
   *  angle), and start/endText Notes are reassigned to be at start of first segment and end of
   *  last segment, respectively.
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
    if (startTextVisible()) {
      hideStartText();
      showStartText();
    }
    if (endTextVisible()) {
      hideEndText();
      showEndText();
    }
  }

  /**
   * Calls the update method where isReset is false.
   *
   * @param midPoint is the mid-point of the line.
   */
  public void update(double midPoint) {
    update(midPoint, false);
  }

  /**
   * This is just so update can be called this way on any given relationship. This method should
   * be implemented in any direct subclasses of this Class.
   * @param isReset
   */
  public void update(boolean isReset) {

  }

  /**
   * Returns whether or not this's notes are hidden.
   * @return Boolean value for whether or not this's note are hidden.
   */
  public boolean textVisible() {
    return ( startTextVisible() || endTextVisible() );
  }

  /**
   * Returns whether or not this's start note is hidden.
   * @return Boolean value for whether or not this's start note is hidden.
   */
  public boolean startTextVisible() {
   return startTextVisible;
  }

  /**
   * Returns whether or not this's end note is hidden.
   * @return Boolean value for whether or not this's end note is hidden.
   */
  public boolean endTextVisible() {
   return endTextVisible;
  }

  /**
   * Updates all segments in the Relationship.
   * @postcondition all segments in the Relationship update the coordinates of their lines.
   */
  public void updateSegments() {
    for (Segment segment : segments) {
      segment.update();
    }
  }

  /**
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

  /**
   * Returns a list of segments in this UMLConnector.
   * @return List of segments in this UMLConnector
   */
  public List<Segment> getSegments() {
    return segments;
  }

  /**
   * Returns a list of pivots in this UMLConnector.
   * @return List of pivots in this UMLConnector
   */
  public List<Pivot> getPivots() {
    return pivots;
  }

  /**
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

  /**
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

  /**
   * Returns underlying model.
   * @return Underlying Group model.
   */
  public Node getModel() {
    return group;
  }
}
