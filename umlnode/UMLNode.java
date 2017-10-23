package umlnode;
import com.sun.javafx.geom.BaseBounds;
import com.sun.javafx.geom.transform.BaseTransform;
import com.sun.javafx.jmx.MXNodeAlgorithm;
import com.sun.javafx.jmx.MXNodeAlgorithmContext;
import com.sun.javafx.sg.prism.NGNode;

import javafx.scene.Node;

import javafx.scene.layout.Region;
//import umlnode.Connector;


public interface UMLNode {
  public double getOriginX();
  public double getOriginY();
 public void addConnnector(Connector c);
}


/*
public class UMLNode extends Region {

  public Node node;
  public int originX;
  public int originY;

/*
 * All UMLNodes must implement a `getModel()` method which returns the underlying JavaFX structure
 */

/*
  public int getOriginX() {
    return originX;
  }

  public int getOriginY() {
    return originY;
  }


}
*/