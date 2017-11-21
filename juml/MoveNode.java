package juml;
import umlobject.*;

public class MoveNode extends Controller{
	
	double startX;
	double startY;
	double endX;
	double endY;
	UMLNode node;
	
	public MoveNode(UMLNode node, double startX, double startY) {
		this (node, startX, startY, true);
	}
	
	// expects the object top have been moved already
	public MoveNode(UMLNode node, double startX, double startY, Boolean addToStack) {
		super(addToStack);
		System.out.println("move object added to stack");
		this.startX = startX;
		this.startY = startY;
		this.endX = node.getOriginX();
		this.endY = node.getOriginY();
		this.node = node;
		//doAction();
	}
	
	// expects the object to still be at old position
	public MoveNode(UMLNode node, double startX, double startY,  double endX, double endY, Boolean addToStack) {
		super(addToStack);
		System.out.println("move object added to stack");
		this.startX = startX;
		this.startY = startY;
		this.endX = endX;
		this.endY = endY;
		this.node = node;
		doAction();
	}
	
	// move from "start" to "end"
	public void doAction() {
		node.move(endX, endY);
	}

	// move from "end" to "start"
	public void undoAction() {
		node.move(startX, startY);
	}
	
}
