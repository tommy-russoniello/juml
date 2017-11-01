package umlobject;

import javafx.scene.Node;

// UML object representation.
public class UMLObject {
  // Origin point coordinates.
  public double originX;
  public double originY;

  // Reassign this to given coordinates
  public void move(double newX, double newY) {
    originX = newX;
    originY = newY;
    update();
  }

  // Update values to reflect changes made to them.
  public void update() {}

  // Returns underlying model.
   public Node getModel() {
     return null;
   }

   // Returns origin point x coordinate.
   public double getOriginX() {
     return originX;
   }

   // Returns origin point y coordinate.
   public double getOriginY() {
     return originY;
   }
}
