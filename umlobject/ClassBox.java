package umlobject;

import javafx.scene.shape.Rectangle;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;

/**
 * UML class box representation.
 * @author Samuel Carroll
 * @author Torrance Graham
 * @author Quinn Minnich
 * @author Thomas Russoniello
 * @version 0.3
 * @since 0.2
 */
public class ClassBox extends UMLNode {

  /**
   * Width of entire box, limits inner text length as well.
   */
  double width;

  /**
   * Underlying model and it's children.
   */
  VBox box;

  /**
   * The two separators that divide the text fields of the classbox.
   */
  Rectangle separator1, separator2;

  /**
   * The three different text fields.
   */
  public Text name, attributes, methods;

  /**
   * Basic Constructor.
   *
   * @param x x coordinate for this to be made on.
   * @param y y coordinate for this to be made on.
   * @postcondition ClassBox instance with width of 80 and the given coordinates is created.
   */
  public ClassBox(double x, double y) {
    this(x, y, 80);
  }

  /**
   * Explicit Constructor.
   *
   * @param x x coordinate for this to be made on.
   * @param y y coordinate for this to be made on.
   * @param w Width that this will be created with.
   * @postcondition ClassBox instance with given width and coordinates is created.
   */
  public ClassBox(double x, double y, double w) {
    width = w;

    box = new VBox();
    box.setLayoutX(x);
    box.setLayoutY(y);
    box.setStyle("-fx-border-color: black;");
    box.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));

    separator1 = new Rectangle(width, 1);
    separator1.setFill(Color.BLACK);
    separator2 = new Rectangle(width, 1);
    separator1.setFill(Color.BLACK);

    name = new Text("class name");
    name.setWrappingWidth(width);
    name.setTextAlignment(TextAlignment.CENTER);

    attributes = new Text("attributes");
    attributes.setWrappingWidth(width);
    attributes.setTextAlignment(TextAlignment.CENTER);

    methods = new Text("methods");
    methods.setWrappingWidth(width);
    methods.setTextAlignment(TextAlignment.CENTER);

    box.getChildren().addAll(name, separator1, attributes, separator2, methods);
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
   * If either coordinate is negative the respective coordinate is set to 0.
   * @param newX x coordinate for this to be moved to.
   * @param newY y coordinate for this to be moved to.
   * @postcondition This updates all of its data according to new coordinates.
   */
  public void move(double newX, double newY) {
  	if(newX - (getWidth()/2) < 0)
  		newX = 0 + (getWidth()/2);
  	if (newY - (getHeight()/2) < 0)
  		newY = 0 + (getHeight()/2);
  	box.setLayoutX(newX - (width / 2));
    box.setLayoutY(newY - (getHeight() / 2));
    super.move(newX, newY);
  }

  /**
   * "Trims" this to be only as wide as the widest of it's text fields with a minimum of 80.
   * @postcondition All of this's components have the width of the widest of this's text fields, or
   * * 80 if the widest text field is thinner than 80.
   */
  public void trim() {
    double greatestLength = Math.max(Math.max(name.getLayoutBounds().getWidth(),
      attributes.getLayoutBounds().getWidth()), methods.getLayoutBounds().getWidth());
    setWidth(greatestLength > 80 ? greatestLength : 80);
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
   * Returns contents of this's name field.
   * @return Contents of this's name field.
   */
  public String getName() {
    return name.getText();
  }

  /**
   * Returns contents of this's attributes field as string.
   * @return Contents of this's attributes field as string.
   */
  public String getAttributes() {
	  return attributes.getText();
  }

  /**
   * Returns contents of this's attributes field splt into String array.
   * @return Contents of this's attributes field splt into String array.
   */
  public String [] getAttributesArray() {
    return attributes.getText().split("\\|");
  }

  /**
   * Returns contents of this's methods field split as String.
   * @return Contents of this's methods field split as String.
   */
  public String getMethods() {
	  return methods.getText();
  }

  /**
   * Returns contents of this's methods field split into string array.
   * @return Contents of this's methods field split into string array.
   */
  public String [] getMethodsArray() {
	  return methods.getText().split("\\|");
  }

  /**
   * Returns the x coordinate of the point to which a connector should anchor if
   * joined to this node.
   *
   * @param startX the starting x value that is used in calculating the anchor
   * @param startY the starting y value that is used in calculating the anchor
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
   * @param startX the starting x value that is used in calculating the anchor
   * @param startY the starting y value that is used in calculating the anchor
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
   * Reassign value of name to newName.
   * @param newName new string for name to be changed to.
   * @postcondition This updates the string value in name.
   */
  public void setName(String newName){
    name.setWrappingWidth(0);
	  name.setText(newName);
    trim();
  }

  /**
   * Reassign value of attributes to newAttributes.
   * @param newAttributes new string for attributes to be changed to.
   * @postcondition This updates the string value in attributes.
   */
  public void setAttributes(String newAttributes){
    attributes.setWrappingWidth(0);
	  attributes.setText(newAttributes);
    trim();
  }

  /**
   * Reassign value of methods to newMethods.
   * @param newMethods new string for methods to be changed to.
   * @postcondition This updates the string value in methods.
   */
  public void setMethods(String newMethods){
    methods.setWrappingWidth(0);
	  methods.setText(newMethods);
    trim();
  }

  /**
   * Changes width of all of this's components to match given width.
   * @param newWidth New width for this to be set to.
   * @postcondition All of this's components are altered to match given width for this.
   */
  public void setWidth(double newWidth) {
    width = newWidth;

    separator1.setWidth(width);
    separator2.setWidth(width);

    name.setWrappingWidth(width);
    attributes.setWrappingWidth(width);
    methods.setWrappingWidth(width);

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
