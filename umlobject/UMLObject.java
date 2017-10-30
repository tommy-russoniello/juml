package umlobject;

import javafx.scene.Node;

public class UMLObject {
  public double originX;
  public double originY;

  public void move(double newX, double newY) {
    originX = newX;
    originY = newY;
    update();
  }

  public void update() {}

  // All UMLObjects must implement this method, returning the underlying JavaFX structure
   public Node getModel() {
     return null;
   }

   public double getOriginX() {
     return originX;
   }

   public double getOriginY() {
     return originY;
   }
}
