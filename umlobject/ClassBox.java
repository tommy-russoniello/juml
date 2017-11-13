package umlobject;

import javafx.scene.shape.Rectangle;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;

/*
 * UML class box representation.
 * @author Samuel Carroll
 * @author Torrance Graham
 * @author Quinn Minnich
 * @author Thomas Russoniello
 * @version 0.2
 * @since 0.2
 */
public class ClassBox extends UMLNode {
  /*
   * Width of entire box, limits inner text length as well.
   */
  double width;

  /*
   * Underlying model and it's children.
   */
  VBox box;
  Rectangle separator1, separator2;
  Text name, attributes, methods;

  /*
   * Basic Constructor
   * @param x x coordinate for this to be made on.
   * @param y y coordinate for this to be made on.
   * @postcondition ClassBox instance with width of 80 and the given coordinates is created.
   */
  public ClassBox(double x, double y) {
    this(x, y, 80);
  }

  /*
   * Explicit Constructor
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

  /*
   * Returns underlying model.
   * @return underlying model VBox.
   */
  public Node getModel() {
    return box;
  }

  /*
   * Reassign this to given coordinates.
   * @param newX x coordinate for this to be moved to.
   * @param newY y coordinate for this to be moved to.
   * @postcondition This updates all of its data according to new coordinates.
   */
  public void move(double newX, double newY) {
    box.setLayoutX(newX - (width / 2));
    box.setLayoutY(newY - (getHeight() / 2));
    super.move(newX, newY);
  }

  /*
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

  /*
   * returns maintained width.
   * @return maintained width.
   */
  public double getWidth() {
    return width;
  }

  /*
   * Returns contents of this's name field.
   * @return Contents of this's name field.
   */
  public String getName() {
    return name.getText();
  }

  /*
   * Returns contents of this's attributes field splt into string array.
   * @return Contents of this's attributes field splt into string array.
   */
  public String[] getAttributes() {
    return attributes.getText().split("\\|");
  }

  /*
   * Returns contents of this's methods field split into string array.
   * @return Contents of this's methods field split into string array.
   */
  public String[] getMethods() {
    return methods.getText().split("\\|");
  }
  
	/*
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
		if (startX < originX)
			angle += Math.PI;

		// System.out.println("angle in degrees is "+angle*180/Math.PI);

		double boxAngle = Math.atan(actinghalfHeight / actinghalfWidth);
		System.out.println("Box angle in degrees is " + boxAngle * 180 / Math.PI);
		if ((angle < boxAngle && angle > -boxAngle) || ((angle > Math.PI - boxAngle && angle < Math.PI + boxAngle))) {
			// crossing sides of box
			double xOffset;
			if (startX > originX)
				xOffset = actinghalfWidth + 2;
			else
				xOffset = -actinghalfWidth;
			return originX + xOffset;
		} else {
			// crossing top or bottom of box
			double yOffset;
			if (startY < originY)
				yOffset = actinghalfHeight + 2;
			else
				yOffset = -actinghalfHeight;
			double xOffset = yOffset / Math.tan(angle);
			return originX + xOffset;
		}
	}

	/*
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
		if (startX < originX)
			angle += Math.PI;

		double boxAngle = Math.atan(actinghalfHeight / actinghalfWidth);
		if ((angle < boxAngle && angle > -boxAngle) || ((angle > Math.PI - boxAngle && angle < Math.PI + boxAngle))) {
			double xOffset;
			if (startX < originX)
				xOffset = actinghalfWidth + 2;
			else
				xOffset = -actinghalfWidth;

			// do check if line crosses corner of box
			// if line crosses "bottom" or "top"
			double yOffset = Math.tan(angle) * xOffset;
			return originY + yOffset;
		} else {
			double yOffset;
			if (startY > originY)
				yOffset = actinghalfHeight + 2;
			else
				yOffset = -actinghalfHeight;
			return originY + yOffset;
		}
	}

  
}
