package umlobject;

import javafx.scene.Node;

/**
 * UML object representation.
 * @author Samuel Carroll
 * @author Torrance Graham
 * @author Quinn Minnich
 * @author Thomas Russoniello
 * @version 0.3
 * @since 0.2
 */
public class UMLObject {

  /**
   * The origin x coordinate
   */
  public double originX;

  /**
   * The origin y coordinate
   */
  public double originY;

  /**
   * Reassign this to given coordinates.
   * @param newX x coordinate for this to be moved to.
   * @param newY y coordinate for this to be moved to.
   * @postcondition This updates all of its data according to new coordinates.
   */
  public void move(double newX, double newY) {
    originX = newX;
    originY = newY;
    update();
  }

  public String saveAsString() {
    return "";
  }

  /**
   * Update values to reflect changes made to them.
   * @postcondition All data is updated (refreshed).
   */
  public void update() {}

  /**
   * Changes colors within model to make the object appear highlighted.
   * @postcondition Colors in model are changed to make the object appear highlighted.
   */
  public void highlight() {}

  /**
   * Changes colors within model to make the object appear unhighlighted.
   * @postcondition Colors in model are changed to make the object appear unhighlighted.
   */
  public void unhighlight() {}

   /**
    * Returns underlying model.
    * @return underlying model Node.
    */
   public Node getModel() {
     return null;
   }

   /**
    * Returns origin point x coordinate.
    * @return Origin x coordinate of this.
    */
   public double getOriginX() {
     return originX;
   }

   /**
    * Returns origin point y coordinate.
    * @return Origin y coordinate of this.
    */
   public double getOriginY() {
     return originY;
   }
}
