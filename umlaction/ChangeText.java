package umlaction;

import javafx.scene.text.Text;

public class ChangeText extends UMLAction {
  Text text;
  String altText;

  public ChangeText(Text inText, String newText) {
    text = inText;
    altText = newText;
    doAction();
  }

  public void doAction() {
    String temp = altText;
    altText = text.getText();
    text.setText(temp);
  }

  public void undoAction() {
    String temp = altText;
    altText = text.getText();
    text.setText(temp);
  }
}
