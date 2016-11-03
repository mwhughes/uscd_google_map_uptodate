/** Class to manage items selected in the GUI
 * 
 * @author UCSD MOOC development team
 *
 */

package application;
import application.services.GeneralService;
import geography.GeographicPoint;
import gmapsfx.javascript.object.Marker;

public class SelectManager {
    private CLabel<GeographicPoint> pointLabel;
    private CLabel<GeographicPoint> startLabel;
    private CLabel<GeographicPoint> point1Label;
    private CLabel<GeographicPoint> point2Label;
    private CLabel<GeographicPoint> point3Label;
    private CLabel<GeographicPoint> point4Label;
    
    private CLabel<GeographicPoint> destinationLabel;
    private Marker startMarker;
    private Marker point1Marker;
    private Marker point2Marker;
    private Marker point3Marker;
    private Marker point4Marker;
    private Marker destinationMarker;
    private Marker selectedMarker;
    private MarkerManager markerManager;
    private DataSet dataSet;


    public SelectManager() {
        startMarker = null;
        point1Marker = null;
        point2Marker = null;
        point3Marker = null;
        point4Marker = null;
        destinationMarker = null;
        selectedMarker = null;
        pointLabel = null;
        startLabel = null;
        destinationLabel = null;
        dataSet = null;
    }


    public void resetSelect() {
        markerManager.setSelectMode(true);
    }
    public void clearSelected() {
    	selectedMarker = null;
    	pointLabel.setItem(null);
    }

    public void setAndDisplayData(DataSet data) {
    	setDataSet(data);
        //TODO - maybe if markerManager!= null?
        if(markerManager != null) {
            markerManager.displayDataSet();
        }
        else {
        	System.err.println("Error : Marker Manager is null.");
        }
    }

    public void setMarkerManager(MarkerManager manager) { this.markerManager = manager; }
    public void setPoint(GeographicPoint point, Marker marker) {
        // System.out.println("inSetPoint.. passed : " + point);
    	pointLabel.setItem(point);
        selectedMarker = marker;
    }
    public void setDataSet(DataSet dataSet) {
    	this.dataSet = dataSet;
    	if(markerManager != null) {
    		markerManager.setDataSet(dataSet);
    	}
    }

    public void setPointLabel(CLabel<GeographicPoint> label) { this.pointLabel = label; }
    public void setStartLabel(CLabel<GeographicPoint> label) { this.startLabel = label; }
    public void setPoint1Label(CLabel<GeographicPoint> label) { this.point1Label = label; }
    public void setPoint2Label(CLabel<GeographicPoint> label) { this.point2Label = label; }
    public void setPoint3Label(CLabel<GeographicPoint> label) { this.point3Label = label; }
    public void setPoint4Label(CLabel<GeographicPoint> label) { this.point4Label = label; }
    public void setDestinationLabel(CLabel<GeographicPoint> label) { this.destinationLabel = label; }

    public GeographicPoint getPoint() { return pointLabel.getItem(); }


	public GeographicPoint getStart(){return startLabel.getItem();}
	public GeographicPoint getPoint1(){return point1Label.getItem();}
	public GeographicPoint getPoint12(){return point2Label.getItem();}
	public GeographicPoint getPoint3(){return point3Label.getItem();}
	public GeographicPoint getPoint4(){return point4Label.getItem();}
	public GeographicPoint getDestination(){return destinationLabel.getItem();}
	public void setStart() {
		if(pointLabel.getItem() != null) {
        	GeographicPoint point = pointLabel.getItem();
    		startLabel.setItem(point);
            markerManager.setStart(point);
		}
	}
	
	public void setPoint1() {
		if(pointLabel.getItem() != null) {
        	GeographicPoint point = pointLabel.getItem();
    		point1Label.setItem(point);
            markerManager.setPoint1(point);
		}
	}
	
	public void setPoint2() {
		if(pointLabel.getItem() != null) {
        	GeographicPoint point = pointLabel.getItem();
    		point2Label.setItem(point);
            markerManager.setPoint2(point);
		}
	}
	
	public void setPoint3() {
		if(pointLabel.getItem() != null) {
        	GeographicPoint point = pointLabel.getItem();
    		point3Label.setItem(point);
            markerManager.setPoint3(point);
		}
	}
	
	public void setPoint4() {
		if(pointLabel.getItem() != null) {
        	GeographicPoint point = pointLabel.getItem();
    		point4Label.setItem(point);
            markerManager.setPoint4(point);
		}
	}

	public void setDestination() {
		if(pointLabel.getItem() != null) {
        	GeographicPoint point = pointLabel.getItem();
    		destinationLabel.setItem(point);
    		markerManager.setDestination(point);
		}
	}



}