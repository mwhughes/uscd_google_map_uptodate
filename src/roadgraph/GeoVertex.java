package roadgraph;
import java.util.Comparator;

import java.util.*;

import geography.GeographicPoint;
/*
 * A class that represents a vertex of a directed graph of GeographicPoints
 *  
 */


public class GeoVertex implements Comparator<GeoVertex>, Comparable<GeoVertex>{
	
	
	private GeographicPoint vPoint; //the geographic point associated with the vertex
	private double distanceFromStart; //This is a variable to store the distance from startVertex
	private double minimalDistanceFromGoal;
	private double totalDistance;
	
	private HashMap<GeoVertex, GeoEdge> neighbors; // a hashmap to keep track of neighboring out-vertices
													//and the associated edges
	private HashMap<GeoVertex, GeoEdge> neighborsIn; //same as above but for in-vertices.
	
	//Constructor Method
	
	public GeoVertex(GeographicPoint p)
	{
		this.vPoint = p;
		this.neighbors = new HashMap<GeoVertex, GeoEdge>();
		this.neighborsIn = new HashMap<GeoVertex, GeoEdge>();
		this.distanceFromStart = Double.POSITIVE_INFINITY;
		this.minimalDistanceFromGoal = Double.POSITIVE_INFINITY;
		this.totalDistance = Double.POSITIVE_INFINITY;
	
	}
	
	public int compare(GeoVertex o1, GeoVertex o2)
	{
		if (o1.totalDistance < o2.totalDistance)
		{
			return -1;
		}
		if (o1.totalDistance > o2.totalDistance)
		{
			return 1;
		}
		return 0;
	}
	
	public int compareTo(GeoVertex v)
	{
		if (this.totalDistance < v.totalDistance)
		{
			return -1;
		}
		if (this.totalDistance > v.totalDistance)
		{
			return 1;
		}
		
		return 0;
	}
	
	
	// getter method for the associated point
	public GeographicPoint getPoint()
	{
		return vPoint;
	}
	
	/*
	 * Getter method that returns the set of neighbors that can be 
	 * accessed from this vertex in the graph
	 */
	public Set<GeoVertex> getNeighbors()
	{
		return neighbors.keySet();
	}
	/*
	 * Getter method that returns the set of neighbors that can be reached directly 
	 * from this vertex in the graph
	 */
	public Set<GeoVertex> getNeighborsIn()
	{
		return neighborsIn.keySet();
	}
	
	public HashMap<GeoVertex, GeoEdge> getNeighborsHashMap()
	{
		return neighbors;
	}
	
	/*
	 * Method called when we add an edge to the graph on the
	 *  to and from vertices of the edge by the MapGraph class.
	 *  
	 * Method determines whether the vertex the method has been called on is a to or from-point
	 * of the edge. 
	 * 
	 * If the Vertex that the method is called on is a from-point of the edge e, 
	 * then the method maps the to-point to the edge in the neighbors map,
	 * otherwise the method maps the from-point to the edge in the neighborsIn map.
	 * 
	 *  We thus keep track of all of the neighbors in and out and maintain access to the associated edges.
	 *
	 * 
	 */
	public void addEdge( GeoEdge e)
	{
		//write this method to add an edge depending on whether this vertex is in or out
		if (this.getPoint().equals( e.getFromPoint() ))
		{
			this.neighbors.put(e.getToVertex(), e);
			
		}
		if (this.getPoint().equals( e.getToPoint() ))
		{	
			this.neighborsIn.put(e.getFromVertex(), e);			
		}
	}
	
	/*
	 * Getter method that returns the map to all of out or in-edges depending on the 
	 * boolean input to the method.
	 * 
	 * This method will allow us to access the Edge objects associated with each out or in Neighbors, 
	 * and thus access all of the properties of paths that we have found in the graph.
	 * 
	 * Set wantOutEdges to true to access the map of out vertices to out-edges, and 
	 * false to access the map of in-vertices to in-edges.
	 */
	
	public HashMap<GeoVertex, GeoEdge> getMapofEdges(boolean wantOutEdges)
	{
		if (wantOutEdges)
		{
		return neighbors;
		}
		return neighborsIn;
	}
	
	public double getStartDistance()
	{
		return this.distanceFromStart;
	}
	
	/*
	 * Used to initialize a vertex as the start vertex
	 */
	public void setAsStartVertex()
	{
		this.distanceFromStart = 0.0;
	}
	
	/*
	 * Setter method that updates the distance from the start based on current distance of vertex accessed
	 * from.
	 * 
	 *only changes the distance if current distance is longer than the newly discovered path from the start.
	 */
	
	
	public boolean updateStartDistance(GeoVertex fromVertex)
	{
			GeoEdge fromEdge = this.neighborsIn.get(fromVertex);
			double distBasedOnThisPath = fromVertex.getStartDistance()+ fromEdge.getLength();
			if (distBasedOnThisPath < this.distanceFromStart)
			{
				this.distanceFromStart = distBasedOnThisPath;
				
				return true;
			}	
			else 
			{
				return false;
			}
		
	}

	public void setMinDistanceFromGoal(GeoVertex goal)
	{
		this.minimalDistanceFromGoal = goal.getPoint().distance(this.getPoint());
	}
	
	public void updateTotalDistance(boolean includeDistanceFrom)
	{
		if (includeDistanceFrom)
		{
			this.totalDistance = this.minimalDistanceFromGoal + this.distanceFromStart;
		}
		else 
		{
			this.totalDistance = this.distanceFromStart;
		}
	}
	
	public void initializeDistances()
	{
		this.distanceFromStart = Double.POSITIVE_INFINITY;
		this.minimalDistanceFromGoal = Double.POSITIVE_INFINITY;
		this.totalDistance = Double.POSITIVE_INFINITY;
	}
	
	
	
	
	
	
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
