package umlobject;

import java.util.Scanner;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.text.Font;

/**
 * UML note representation.
 * @author Samuel Carroll
 * @author Torrance Graham
 * @author Quinn Minnich
 * @author Thomas Russoniello
 * @version 0.3
 * @since 0.3
 */
public class Note extends UMLNode {

  /**
   * Underlying model and it's children.
   */
  public VBox box;

  /** The text of the note. */
  public Text text;

  /**
   * Width of entire box, limits inner text length as well.
   */
  double width;

<<<<<<< HEAD

	/**
	 * Build from string method
	 * @param input The scanner from which the object can read in its save string
	 * @postcondition Object will have constructed itself from the information
	 *                provided by input
	 */
public Note(Scanner input) {
	    this(input.nextDouble(), input.nextDouble(), input.nextDouble());
	    text.setText(buildString(input, input.nextInt()));
	}

  /**
   * Save method; stored as "delimiter x y (upper right corner) width, chacactersInText text\n"
   * @postcondition generates a string with the necessary information for the object to rebuild itself.
   */
  public String saveAsString() {
		int numTextChars = text.getText().length();
		return "Note: " + box.getLayoutX() +" "+ box.getLayoutY() + " " + width+ " " + numTextChars + " "+text.getText();
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


=======
>>>>>>> Comment Updates
  /**
   * Basic Constructor
   * @param x x coordinate for this to be made on.
   * @param y y coordinate for this to be made on.
   * @postcondition Note instance with width of 40 and the given coordinates is created.
   */
  public Note(double x, double y) {
    this(x, y, 40);
  }

  /**
   * Explicit Constructor
   * @param x x coordinate for this to be made on.
   * @param y y coordinate for this to be made on.
   * @param w Width that this will be created with.
   * @postcondition Note instance with given width and coordinates is created.
   */
  public Note(double x, double y, double w) {
    width = w;
    box = new VBox();
    box.setLayoutX(x);
    box.setLayoutY(y);
    box.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));

    text = new Text("text");
    text.setWrappingWidth(width);
    text.setTextAlignment(TextAlignment.CENTER);
    text.setFont(Font.font("Verdana", 10));

    box.getChildren().add(text);
    originX = x + (width / 2);
    originY = y + (getHeight() / 2);
  }

  /**
   * Returns underlying model.
   * @return underlying model VBox.
   */
  public Node getModel() {
    return box;
  }

  /**
   * Reassign this to given coordinates.
   * @param newX x coordinate for this to be moved to.
   * @param newY y coordinate for this to be moved to.
   * @postcondition This updates all of its data according to new coordinates.
   */
  public void move(double newX, double newY) {
	if(newX - (getWidth()/2) < 0 || newY - (getHeight()/2) < 0)
	   return;
	box.setLayoutX(newX - (width / 2));
    box.setLayoutY(newY - (getHeight() / 2));
    super.move(newX, newY);
  }

  /**
   * Calculates and returns total height of underlying model (VBox).
   * @return Total height of underlying Vbox model.
   */
  public double getHeight() {
    double height = 0;
    for (Node n: box.getChildren()) {
      height += n.getBoundsInParent().getHeight();
    }
    return height;
  }

  /**
   * Returns maintained width.
   * @return maintained width.
   */
  public double getWidth() {
    return width;
  }

  /**
   * Returns contents of this's text field.
   * @return Contents of this's text field.
   */
  public String getText() {
    return text.getText();
  }

  /**
   * Returns the x coordinate of the point to which a connector should anchor if
   * joined to this node.
   *
   * @return returns the calculated x coordinate.
   */
  public double getAnchorX(double startX, double startY) {
  	double actinghalfWidth = (getWidth()) / 2;
  	double actinghalfHeight = (getHeight()) / 2;
  	double deltaX = startX - originX;
  	double deltaY = originY - startY;
  	double angle = Math.atan(deltaY / deltaX);
  	if (startX < originX) {
  		angle += Math.PI;
    }

  	double boxAngle = Math.atan(actinghalfHeight / actinghalfWidth);
  	if ((angle < boxAngle && angle > -boxAngle) ||
      ((angle > Math.PI - boxAngle && angle < Math.PI + boxAngle))) {
  		// crossing sides of box
  		double xOffset;
  		if (startX > originX) {
  			xOffset = actinghalfWidth + 2;
      } else {
  			xOffset = -actinghalfWidth;
      }
  		return originX + xOffset;
  	} else {
  		// crossing top or bottom of box
  		double yOffset;
  		if (startY < originY) {
	  		yOffset = actinghalfHeight + 2;
  		} else {
  			yOffset = -actinghalfHeight;
      }
  		double xOffset = yOffset / Math.tan(angle);
  		return originX + xOffset;
  	}
  }

	/**
  	 * Returns the y coordinate of the point to which a connector should anchor if
  	 * joined to this node.
  	 *
  	 * @return returns the calculated y coordinate.
  	 */
  	public double getAnchorY(double startX, double startY) {
  		double actinghalfWidth = (getWidth()) / 2;
  		double actinghalfHeight = (getHeight()) / 2;
  		double deltaX = startX - originX;
  		double deltaY = originY - startY;
  		double angle = Math.atan(deltaY / deltaX);
  		if (startX < originX) {
  			angle += Math.PI;
      }

  		double boxAngle = Math.atan(actinghalfHeight / actinghalfWidth);
  		if ((angle < boxAngle && angle > -boxAngle) ||
      ((angle > Math.PI - boxAngle && angle < Math.PI + boxAngle))) {
  			double xOffset;
  			if (startX < originX) {
  				xOffset = actinghalfWidth + 2;
  			} else {
  				xOffset = -actinghalfWidth;
        }

  			// do check if line crosses corner of box
  			// if line crosses "bottom" or "top"
  			double yOffset = Math.tan(angle) * xOffset;
  			return originY + yOffset;
  		} else {
  			double yOffset;
  			if (startY > originY) {
  				yOffset = actinghalfHeight + 2;
  			} else {
  				yOffset = -actinghalfHeight;
        }
  			return originY + yOffset;
  		}
  }

  /**
   * Reassign value of text to newText.
   * @param newText new string for text to be changed to.
   * @postcondition This updates the string value in text.
   */
  public void setText(String newText){
    text.setWrappingWidth(0);
	  text.setText(newText);
  }

  /**
   * Changes width of all of this's components to match given width.
   * @param newWidth New width for this to be set to.
   * @postcondition All of this's components are altered to match given width for this.
   */
  public void setWidth(double newWidth) {
    width = newWidth;
    text.setWrappingWidth(width);
    originX = box.getLayoutX() + (width / 2);
    originY = box.getLayoutY() + (getHeight() / 2);

    for (UMLConnector connector : connections) {
      connector.update();
    }
  }

  /**
   * Changes color of underlying VBox model to make the object appear highlighted.
   * @postcondition Color of underlying VBox model changed to blue.
   */
  public void highlight() {
    box.setStyle("-fx-border-color: blue;");
  }

  /**
   * Changes color of underlying VBox model to make the object appear unhighlighted.
   * @postcondition Color of underlying VBox model changed to black.
   */
  public void unhighlight() {
    box.setStyle("-fx-border-color: black;");
  }
}
