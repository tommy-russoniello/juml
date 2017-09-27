package umlnode;

import javafx.scene.layout.Region;

public class UMLNode extends Region {

  public int originX;
  public int originY;

/*
 * All UMLNodes must implement a `getModel()` method which returns the underlying JavaFX structure
 */

  public int getOriginX() {
    return originX;
  }

  public int getOriginY() {
    return originY;
  }
}
