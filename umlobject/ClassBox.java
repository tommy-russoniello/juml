package umlobject;

import javafx.scene.shape.Rectangle;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;

public class ClassBox extends UMLNode {
  double width;
  Rectangle separator1, separator2;
  VBox box;
  Text name, attributes, methods;

  public ClassBox(double x, double y) {
    this(x, y, 80);
  }

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

  public Node getModel() {
    return box;
  }

  public void move(double newX, double newY) {
    box.setLayoutX(newX - (width / 2));
    box.setLayoutY(newY - (getHeight() / 2));
    super.move(newX, newY);
  }

  public double getHeight() {
    double height = 0;
    for (Node n: box.getChildren()) {
      height += n.getBoundsInParent().getHeight();
    }
    return height;
  }

  public double getWidth() {
    return width;
  }

  public String getName() {
    return name.getText();
  }

  public String[] getAttributes() {
    return attributes.getText().split("\\|");
  }

  public String[] getMethods() {
    return methods.getText().split("\\|");
  }
}
