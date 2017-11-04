package juml;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Map;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import umlobject.*;

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
	@Test
	public void testAddClassBox(){
		//Mimics controller
		Pane pane = new Pane();
		Controller a = new Controller();
		a.setPane(pane);
		
		//add a ClassBox to the pane/hashmap
		ClassBox cb1 = new ClassBox(0,0);
		a.addObject(cb1);
		assertTrue(pane.getChildren().size() ==1);	
		
		//add another ClassBox to the pane/hashmap
		ClassBox cb2 = new ClassBox(2,3);
		a.addObject(cb2);
		assertTrue(pane.getChildren().size() ==2);	
		
		//add another ClassBox to the pane/hashmap
		ClassBox cb3 = new ClassBox(5,5);
		a.addObject(cb3);
		assertTrue(pane.getChildren().size() ==3);	
		
		//add another ClassBox to the pane/hashmap
		ClassBox cb4 = new ClassBox(6,7);
		a.addObject(cb4);
		assertTrue(pane.getChildren().size() ==4);	
		
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
	
	// test delete on points/--no connectors
    @Test
    public void testDeleteOnPoints(){
		//Mimics controller
		Pane pane = new Pane();
		Controller a = new Controller();
		a.setPane(pane);
		
		Point p1 = new Point(1,2);
		a.addObject(p1);
		assertTrue(pane.getChildren().size() ==1);	
		
		Point p2 = new Point(2,3);
		a.addObject(p2);
		assertTrue(pane.getChildren().size() ==2);	
		
		a.deleteObject(p1);
		assertTrue(pane.getChildren().size() ==1);
		
		
		
    }
	
 	// test delete on ClassBoxes/--no connectors
 	@Test
 	public void testDeleteOnClassBoxes(){
 		//Mimics controller
 		Pane pane = new Pane();
 		Controller a = new Controller();
 		a.setPane(pane);
 		
 		//add a ClassBox to the pane/hashmap
 		ClassBox cb1 = new ClassBox(0,0);
 		a.addObject(cb1);
 		assertTrue(pane.getChildren().size() ==1);	
 		
 		//add another ClassBox to the pane/hashmap
 		ClassBox cb2 = new ClassBox(2,3);
 		a.addObject(cb2);
 		assertTrue(pane.getChildren().size() ==2);	
 		
 		a.deleteObject(cb1);
 		assertTrue(pane.getChildren().size() == 1);
 		
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
	
	@Test
    public void testClassBoxGeneralTests() {
        Pane pane = new Pane();
        Controller c = new Controller();
        c.setPane(pane);
        ClassBox box1 = new ClassBox(0,0);
        c.addObject(box1);
        ClassBox box2 = new ClassBox(0,0);
        c.addObject(box2);
        assertTrue(box1.getConnections().size()==0);
        assertTrue(box2.getConnections().size()==0);
        assertTrue(pane.getChildren().size()==2);
       
        UMLConnector con = new UMLConnector(box1, box2);
        c.addObject(con);
       
        assertTrue(box1.getConnections().size()==1);
        assertTrue(box2.getConnections().size()==1);
        assertTrue(pane.getChildren().size()==3);
       
        c.deleteObject(box1);
        assertTrue(pane.getChildren().size()==1);
       
        assertTrue(box1.getConnections().size()==0);
       
    }

}
