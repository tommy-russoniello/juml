package juml;
import javafx.scene.control.TextArea;

import javafx.scene.text.Text;
import umlobject.UMLNode;
import javafx.scene.control.TextField;

public class ModifyText extends Controller{

	UMLNode node;
	Text textField;
	String oldText;
	String newText;
	Boolean onStack;
	TextArea controllerTextArea;
	TextField controllerTextField;
	Boolean isTextArea;
	
	public ModifyText(UMLNode node, Text field, String oldText, TextArea controller) {
		this (node, field, oldText, false);
		isTextArea = true;
		controllerTextArea = controller;
		//doAction();
	}
	
	
	public ModifyText(UMLNode node, Text field, String oldText, TextField controller) {
		this (node, field, oldText, false);
		isTextArea = false;
		controllerTextField = controller;
		//doAction();
	}
	
	
	public ModifyText(UMLNode node, Text field, String oldText, Boolean addToStack) {
		super(addToStack);
		onStack = addToStack;
		textField = field;
		this.oldText = oldText;
		this.newText = oldText;
		this.node = node;
	}
	
	
	public void updateNewText(String updatedText) {
		newText = updatedText;
		// only put self on stack if something has changed
		if (!onStack) {
			onStack = true;
			actions.push(this);
			//System.out.println("Modify text object added to stack");
		}
		//doAction();
	}
	
	public void doAction() {
		textField.setText(newText);
		if (isTextArea) {
			controllerTextArea.setText(newText);
		} else {
			controllerTextField.setText(newText);
			System.out.println("controllerTextField.setText");
		}
		node.update();
	}

	// move from "end" to "start"
	public void undoAction() {
		textField.setText(oldText);
		if (isTextArea) {
			controllerTextArea.setText(oldText);
		} else {
			controllerTextField.setText(oldText);
			System.out.println("controllerTextField.setText");
		}
		node.update();
	}
	
}
