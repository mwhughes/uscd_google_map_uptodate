package application.controllers;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import application.MapApp;
import application.MarkerManager;
import application.SelectManager;
import application.CLabel;
import application.services.GeneralService;
import application.services.RouteService;
import gmapsfx.javascript.object.GoogleMap;
import gmapsfx.javascript.object.LatLong;
import gmapsfx.javascript.object.LatLongBounds;
import gmapsfx.javascript.object.MVCArray;
import gmapsfx.shapes.Polyline;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleGroup;
import javafx.util.StringConverter;

public class RouteController {
	// Strings for slider labels
	public static final int BFS = 3;
    public static final int A_STAR = 2;
    public static final int DIJ = 1;
	public static final int DISABLE = 0;
	public static final int START = 1;
	public static final int DESTINATION = 2;

    private int selectedToggle = DIJ;

    private RouteService routeService;
    private Button displayButton;
    private Button hideButton;
    private Button startButton;
    private Button point1Button;
    private Button point2Button;
    private Button point3Button;
    private Button point4Button;
    private Button resetButton;
    private Button destinationButton;
    private Button visualizationButton;

    private ToggleGroup group;
    private CLabel<geography.GeographicPoint> startLabel;
    private CLabel<geography.GeographicPoint> point1Label;
    private CLabel<geography.GeographicPoint> point2Label;
    private CLabel<geography.GeographicPoint> point3Label;
    private CLabel<geography.GeographicPoint> point4Label;
    private CLabel<geography.GeographicPoint> endLabel;
    private CLabel<geography.GeographicPoint> pointLabel;
    private SelectManager selectManager;
    private MarkerManager markerManager;



	public RouteController(RouteService routeService, Button displayButton, Button hideButton,
						   Button resetButton, Button startButton, Button point1Button, 
						   Button point2Button, Button point3Button, Button point4Button,
						   Button destinationButton,
						   ToggleGroup group, List<RadioButton> searchOptions, Button visualizationButton,
						   CLabel<geography.GeographicPoint> startLabel, 
						   CLabel<geography.GeographicPoint> point1Label,
						   CLabel<geography.GeographicPoint> point2Label,
						   CLabel<geography.GeographicPoint> point3Label,
						   CLabel<geography.GeographicPoint> point4Label,
						   CLabel<geography.GeographicPoint> endLabel,
						   CLabel<geography.GeographicPoint> pointLabel, SelectManager manager, MarkerManager markerManager) {
        // save parameters
        this.routeService = routeService;
		this.displayButton = displayButton;
        this.hideButton = hideButton;
		this.startButton = startButton;
		this.point1Button = point1Button;
		this.point2Button = point2Button;
		this.point3Button = point3Button;
		this.point4Button = point4Button;
		this.resetButton = resetButton;
		this.destinationButton = destinationButton;
        this.group = group;
        this.visualizationButton = visualizationButton;

        // maybe don't need references to labels;
		this.startLabel = startLabel;
		this.point1Label = point1Label;
		this.point2Label = point2Label;
		this.point3Label = point3Label;
		this.point4Label = point4Label;
		this.endLabel = endLabel;
        this.pointLabel = pointLabel;
        this.selectManager = manager;
        this.markerManager = markerManager;

        setupDisplayButtons();
        setupRouteButtons();
        setupVisualizationButton();
        setupLabels();
        setupToggle();
        //routeService.displayRoute("data/sampleroute.map");
	}


	private void setupDisplayButtons() {
		displayButton.setOnAction(e -> {
            if(startLabel.getItem() != null ) {
            		ArrayList<geography.GeographicPoint> destination = new ArrayList<geography.GeographicPoint>();
            		
            		geography.GeographicPoint start = startLabel.getItem();
            		System.out.println("start: " + start);
            		destination.add(start);
            		
            		geography.GeographicPoint point1 = point1Label.getItem();
            		System.out.println(point1Label.toString());
            		if (null != point1){
            			System.out.println("step 0.5");
            		destination.add(point1);
            		}
            		
            		geography.GeographicPoint point2 = point2Label.getItem();
            		if (null != point2){
            		destination.add(point2);
            		}
            		
            		geography.GeographicPoint point3 = point3Label.getItem();
            		if (null != point3){
            		destination.add(point3);
            		}
            		
            		geography.GeographicPoint point4 = point4Label.getItem();
            		if (null != point4){
            		destination.add(point4);
            		}
            		
            		geography.GeographicPoint endPoint = endLabel.getItem();
            		System.out.println("endPoint: "+ endPoint);
            		if (null != endPoint){
            		destination.add(endPoint);
            		}
            		
            		System.out.println(destination.size());
            		 
            		if (null != start && destination.size()>1)
            		{
            			int returnTrip = 0;
	            		if (endPoint != null){
	            			returnTrip = 1;
	            		}
	            		else {
	            			returnTrip = 2;
	            		}
	            		
	            	System.out.println("test2");
        			routeService.displayRoute(startLabel.getItem(), endLabel.getItem(), destination, selectedToggle, returnTrip);
            		}
            }
            else {
            	MapApp.showErrorAlert("Route Display Error", "Make sure to choose points for both start and destination.");
            }
		});

        hideButton.setOnAction(e -> {
        	routeService.hideRoute();
        });

        //TODO -- implement
        resetButton.setOnAction( e -> {

            routeService.reset();
        });
	}

    private void setupVisualizationButton() {
    	visualizationButton.setOnAction( e -> {
    		markerManager.startVisualization();
    	});
    }

    private void setupRouteButtons() {
    	startButton.setOnAction(e -> {
            //System.out.println();
            selectManager.setStart();
    	});
    	
    	point1Button.setOnAction(e -> {
            //System.out.println();
            selectManager.setPoint1();
    	});
    	
    	point2Button.setOnAction(e -> {
            //System.out.println();
            selectManager.setPoint2();
    	});
    	point3Button.setOnAction(e -> {
            //System.out.println();
            selectManager.setPoint3();
    	});
    	point4Button.setOnAction(e -> {
            //System.out.println();
            selectManager.setPoint4();
    	});

        destinationButton.setOnAction( e-> {
            selectManager.setDestination();
        });
    }


    private void setupLabels() {


    }

    private void setupToggle() {
    	group.selectedToggleProperty().addListener( li -> {
            if(group.getSelectedToggle().getUserData().equals("Dijkstra")) {
            	selectedToggle = DIJ;
            }
            else if(group.getSelectedToggle().getUserData().equals("A*")) {
            	selectedToggle = A_STAR;
            }
            else if(group.getSelectedToggle().getUserData().equals("BFS")) {
            	selectedToggle = BFS;
            }
            else {
            	System.err.println("Invalid radio button selection");
            }
    	});
    }




}
