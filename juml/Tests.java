package juml;

import umlobject.*;
import javafx.scene.layout.Pane;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;



import static org.junit.Assert.*;

import org.junit.Test;

public class Tests {

	//tests:
	
	// test adding points
	@Test
	public void testAddPoints(){
		//Mimics controller
		Pane pane = new Pane();
		Controller a = new Controller();
		a.setPane(pane);
		
		//add a point to the pane/hashmap
		Point p1 = new Point (1,2);
		a.addObject(p1);
		assertTrue(pane.getChildren().size() ==1);	
		
		//add another point to the pane/hashmap
		Point p2 = new Point (4,7);
		a.addObject(p2);
		assertTrue(pane.getChildren().size() ==2);
		
		//add another point to the pane/hashmap
		Point p3 = new Point (2,3);
		a.addObject(p3);
		assertTrue(pane.getChildren().size() ==3);
		
		//add another point to the pane/hashmap
		Point p4 = new Point (1,2);
		a.addObject(p4);
		assertTrue(pane.getChildren().size() ==4);
	}
	
	// test adding ClassBox
	//@Test
	//ClassBox requires a mock application
	public void testAddClassBox(){
		//Mimics controller
		Pane pane = new Pane();
		Controller a = new Controller();
		a.setPane(pane);
		
		//add a ClassBox to the pane/hashmap
		ClassBox cb1 = new ClassBox (1.0,2.0);
		a.addObject(cb1);
		assertTrue(pane.getChildren().size() ==1);	
		/*
		//add another ClassBox to the pane/hashmap
		ClassBox cb2 = new ClassBox (2.0,3.0);
		a.addObject(cb2);
		assertTrue(pane.getChildren().size() ==2);	
		
		//add another ClassBox to the pane/hashmap
		ClassBox cb3 = new ClassBox (5.0,5.0);
		a.addObject(cb3);
		assertTrue(pane.getChildren().size() ==3);	
		
		//add another ClassBox to the pane/hashmap
		ClassBox cb4 = new ClassBox (6.0,7.0);
		a.addObject(cb4);
		assertTrue(pane.getChildren().size() ==4);	
		*/
	}
	
	// test adding connection to points
	@Test
	public void testAddConnectorsOnPoints() {
		//Mimics controller
		Pane pane = new Pane();
		Controller a = new Controller();
		a.setPane(pane);
		
		//add a point to the pane/hashmap
		Point p1 = new Point (1,2);
		a.addObject(p1);
		assertTrue(pane.getChildren().size() ==1);	
		
		//add another point to the pane/hashmap
		Point p2 = new Point (4,7);
		a.addObject(p2);
		assertTrue(pane.getChildren().size() ==2);
		
		//add another point to the pane/hashmap
		Point p3 = new Point (2,3);
		a.addObject(p3);
		assertTrue(pane.getChildren().size() ==3);
		
		//add another point to the pane/hashmap
		Point p4 = new Point (1,2);
		a.addObject(p4);
		assertTrue(pane.getChildren().size() ==4);
		
		//Add Connector (line) between two points
		UMLConnector c1 = new UMLConnector(p1, p2);
		a.addObject(c1);
		assertTrue(pane.getChildren().size() ==5);
		
		//Add Connector (line) between two points
		UMLConnector c2 = new UMLConnector(p1, p3);
		a.addObject(c2);
		assertTrue(pane.getChildren().size() ==6);
		
		//Add Connector (line) between two points
		UMLConnector c3 = new UMLConnector(p2, p4);
		a.addObject(c3);
		assertTrue(pane.getChildren().size() ==7);
		
		//Add Connector (line) between two points
		UMLConnector c4 = new UMLConnector(p1, p2);
		a.addObject(c4);
		assertTrue(pane.getChildren().size() ==8);
	}
	
	// test delete on points/ classBoxes--no connectors
    @Test
    public void testDeleteOnPointsAndClassBoxes(){
		//Mimics controller
		Pane pane = new Pane();
		Controller a = new Controller();
		a.setPane(pane);
		
		Point p1 = new Point (1,2);
		a.addObject(p1);
		assertTrue(pane.getChildren().size() ==1);	
		
		Point p2 = new Point (2,3);
		a.addObject(p2);
		assertTrue(pane.getChildren().size() ==2);	
		
		a.deleteObject(p1);
		assertTrue(pane.getChildren().size() ==1);
		
		
    }
	

	// test delete on just connectors
	@Test
	public void testDeleteOnConnectors() {
		//Mimics controller
		Pane pane = new Pane();
		Controller a = new Controller();
		a.setPane(pane);
		
		Point p1 = new Point (1,2);
		a.addObject(p1);
		assertTrue(pane.getChildren().size() ==1);	
		
		Point p2 = new Point (2,3);
		a.addObject(p2);
		assertTrue(pane.getChildren().size() ==2);
		
		UMLConnector c = new UMLConnector(p1, p2);
		a.addObject(c);
		assertTrue(pane.getChildren().size() ==3);	
		// test that each point has added one connection
		assertTrue(p1.getConnections().size() ==1);
		assertTrue(p2.getConnections().size() ==1);
		
		
		c.disconnect();
		a.deleteObject(c);
		assertTrue(pane.getChildren().size() ==2);	
		
		// see if points removed connection
		assertTrue(p1.getConnections().size() ==0);
		assertTrue(p2.getConnections().size() ==0);
	}
	
	// test delete points that are connected
	@Test
	public void testDeleteConnectedPoints() {
		//Mimics controller
		Pane pane = new Pane();
		Controller a = new Controller();
		a.setPane(pane);
		
		assertTrue(pane.getChildren().isEmpty());
		
		// these always go together
		Point p1 = new Point (1,2);
		a.addObject(p1);
		assertTrue(pane.getChildren().size() ==1);		
		
		Point p2 = new Point (2,3);
		a.addObject(p2);
		assertTrue(pane.getChildren().size() ==2);
		
		UMLConnector c1 = new UMLConnector(p1, p2);
		a.addObject(c1);
		assertTrue(pane.getChildren().size() ==3);
		
		// test that each point has added one connection
		assertTrue(p1.getConnections().size() ==1);
		assertTrue(p2.getConnections().size() ==1);
		
		//this should delete the connector too
		a.deleteObject(p1);
		assertTrue(pane.getChildren().size() ==1);
		
		
		// see if both point removed the connector
		assertTrue(p1.getConnections().size() ==0);
		assertTrue(p2.getConnections().size() ==0);
		
		//add p3
		Point p3 = new Point (1,2);
		a.addObject(p3);
		assertTrue(pane.getChildren().size() ==2);	
		//add another point
		Point p4 = new Point (3,4);
		a.addObject(p4);
		assertTrue(pane.getChildren().size() ==3);
		
		
		//Test deleting a point and consequently both lines
		UMLConnector c2 = new UMLConnector(p3, p2);
		a.addObject(c2);
		assertTrue(pane.getChildren().size() ==4);
		
		UMLConnector c3 = new UMLConnector(p4, p3);
		a.addObject(c3);
		assertTrue(pane.getChildren().size() ==5);
		
		//by deleting p1, both lines will delete as well
		a.deleteObject(p3);
		assertTrue(pane.getChildren().size() ==2);
		
		assertTrue(p2.getConnections().size() ==0);
		assertTrue(p4.getConnections().size() ==0);
		
	}
}
