/**
 * @author UCSD MOOC development team and YOU
 * 
 * A class which reprsents a graph of geographic locations
 * Nodes in the graph are intersections between 
 *
 */
package roadgraph;


import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.function.Consumer;

import geography.GeographicPoint;
import util.GraphLoader;

/**
 * @author UCSD MOOC development team and YOU
 * 
 * A class which represents a graph of geographic locations
 * Nodes in the graph are intersections between 
 *
 */
public class MapGraph {
	//TODO: Add your member variables here in WEEK 2
	
	public HashMap<GeographicPoint, GeoVertex> roadVertices;
	public HashSet<GeoEdge> roadEdges;
	
	/** 
	 * Create a new empty MapGraph 
	 */
	public MapGraph()
	{
		// TODO: Implement in this constructor in WEEK 2
		roadVertices = new HashMap<GeographicPoint, GeoVertex>();
		roadEdges = new HashSet<GeoEdge>();
	}
	
	/**
	 * Get the number of vertices (road intersections) in the graph
	 * @return The number of vertices in the graph.
	 */
	public int getNumVertices()
	{
		//TODO: Implement this method in WEEK 2
		return roadVertices.size();
	}
	
	/**
	 * Return the intersections, which are the vertices in this graph.
	 * @return The vertices in this graph as GeographicPoints
	 */
	public Set<GeographicPoint> getVertices()
	{
		//TODO: Implement this method in WEEK 2
		HashSet<GeographicPoint> vertPoints = new HashSet<GeographicPoint>();
		for (GeoVertex gv : this.roadVertices.values())
		{
			vertPoints.add(gv.getPoint());
		}
		return vertPoints;
	}
	
	/**
	 * Get the number of road segments in the graph
	 * @return The number of edges in the graph.
	 */
	public int getNumEdges()
	{
		//TODO: Implement this method in WEEK 2
		return this.roadEdges.size();
	}

	
	
	/** Add a node corresponding to an intersection at a Geographic Point
	 * If the location is already in the graph or null, this method does 
	 * not change the graph.
	 * @param location  The location of the intersection
	 * @return true if a node was added, false if it was not (the node
	 * was already in the graph, or the parameter is null).
	 */
	public boolean addVertex(GeographicPoint location)
	{
		// TODO: Implement this method in WEEK 2
		if ( null != this.roadVertices.get(location)) //  the conditions that ought to be avoided if vertex is added
		{
			return false;
		}
		
		GeoVertex gv = new GeoVertex(location);
		this.roadVertices.put(location, gv);
		
		
		return true;
	}
	
	/**
	 * Adds a directed edge to the graph from pt1 to pt2.  
	 * Precondition: Both GeographicPoints have already been added to the graph
	 * @param from The starting point of the edge
	 * @param to The ending point of the edge
	 * @param roadName The name of the road
	 * @param roadType The type of the road
	 * @param length The length of the road, in km
	 * @throws IllegalArgumentException If the points have not already been
	 *   added as nodes to the graph, if any of the arguments is null,
	 *   or if the length is less than 0.
	 */
	public void addEdge(GeographicPoint from, GeographicPoint to, String roadName,
			String roadType, double length) throws IllegalArgumentException {

		//TODO: Implement this method in WEEK 2
		
		
		GeoVertex fromVert = this.roadVertices.get(from);
		GeoVertex toVert = this.roadVertices.get(to);
		
		//implement illegal argument exceptions eventually
		if (null == fromVert || null == toVert  ) 
		{
		throw new IllegalArgumentException("Invalid Vertices");
		}
		else if (length < 0)
		{
			throw new IllegalArgumentException("Invalid Edge Length");
		}
		else {
		GeoEdge ge = new GeoEdge(fromVert, toVert, roadName, roadType, length);
		this.roadEdges.add(ge);
		
		toVert.addEdge(ge);
		fromVert.addEdge(ge);
		}
	}
	

	/** Find the path from start to goal using breadth first search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest (unweighted)
	 *   path from start to goal (including both start and goal).
	 */
	public List<GeographicPoint> bfs(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {};
        return bfs(start, goal, temp);
	}
	/** Helper Function for BFS method defined below. 
	 * 
	 * If BFS search finds a path, then this method is called to convert the parent map we have constructed and 
	 * transform it into a list of GeographicPoints that constitutes an unweighted shortest path from start to goal 
	 * 
	 * @param parentMap: The parent map that is constructed by the BFS algorithm.
	 * @return The list of intersections that form the shortest (unweighted)
	 *   path from start to goal (including both start and goal).
	 */
	
	private List<GeographicPoint> parentMapToListConversion(HashMap<GeoVertex, GeoVertex> parentMap, 
			GeoVertex startVert, GeoVertex goalVert )
	{
		List<GeographicPoint> answer = new LinkedList<GeographicPoint>();
		GeoVertex  curVertex = goalVert;
		
		while (curVertex!=startVert)
		{
			((LinkedList) answer).addFirst(curVertex.getPoint());
			curVertex = parentMap.get(curVertex);
		}
		((LinkedList) answer).addFirst(startVert.getPoint());
		
		return answer;
		
	}
	
	
	/** Find the path from start to goal using breadth first search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest (unweighted)
	 *   path from start to goal (including both start and goal).
	 */
	
	
	
	public List<GeographicPoint> bfs(GeographicPoint start, 
			 					     GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		// TODO: Implement this method in WEEK 2
		
		// Hook for visualization.  See writeup.
		
		//Step 1: Initialize searchQueue and visited Vertices
		boolean foundPath = false; //We shall use this variable to track whether we discovered a path
		Queue<GeoVertex> searchQueue = new LinkedList<GeoVertex>();
		HashSet<GeoVertex> visited = new HashSet<GeoVertex>();
		HashMap<GeoVertex, GeoVertex> parentMap = new HashMap<GeoVertex, GeoVertex>();
			
		
		GeoVertex startVertex = this.roadVertices.get(start);
		GeoVertex goalVertex = this.roadVertices.get(goal);
		searchQueue.add(startVertex);
		visited.add(startVertex);
		
		//Step 2: run the BFS search algorithm to construct a parentMap from start to goal
		
		while (searchQueue.size()>0)
		{
			//implement BFS here
			GeoVertex curVertex = searchQueue.remove();
			nodeSearched.accept(curVertex.getPoint());
			if (curVertex.getPoint().equals(goal))
			{
				foundPath = true;
				break;
			}
			
			for (GeoVertex neighbor : curVertex.getNeighbors())
			{
				if (!visited.contains(neighbor))
				{
				visited.add(neighbor);
				parentMap.put(neighbor, curVertex); //each neighbor has curVertex mapped as its parent
				searchQueue.add(neighbor);
				}
			}
			
		}
		
		/*Step 3: Convert the parentMap of vertices to a List of Geographic points that 
		 * makeup a path from start to goal.		
		*/
		
		if (foundPath && parentMap.size()>0)
		{
			return parentMapToListConversion( parentMap, 
					startVertex, goalVertex );
		}

		return null; //returned if no path from start to goal is found.
	}
	
	
	

	/** Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
		// You do not need to change this method.
        Consumer<GeographicPoint> temp = (x) -> {};
        return dijkstra(start, goal, temp);
	}
	
	/** Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint start, 
										  GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		// TODO: Implement this method in WEEK 3

		// Hook for visualization.  See writeup.
		//nodeSearched.accept(next.getLocation());
		boolean foundPath = false;
		//nodeSearched.accept(next.getLocation());boolean foundPath = false; //We shall use this variable to track whether we discovered a path
		PriorityQueue<GeoVertex> searchQueue = new PriorityQueue<GeoVertex>();
		HashSet<GeoVertex> visited = new HashSet<GeoVertex>();
		HashMap<GeoVertex, GeoVertex> parentMap = new HashMap<GeoVertex, GeoVertex>();
		
		for (GeoVertex v: roadVertices.values())
		{
			v.initializeDistances();
		}
			
		
		GeoVertex startVertex = this.roadVertices.get(start);
		GeoVertex goalVertex = this.roadVertices.get(goal);
		startVertex.setAsStartVertex();
		searchQueue.add(startVertex);
		
		
		while (searchQueue.size()>0)
		{
			
			
			GeoVertex curVertex = searchQueue.remove();
			
			
			nodeSearched.accept(curVertex.getPoint());
			if (curVertex.getPoint().equals(goal))
			{
				foundPath = true;
				break;
				
			}
			if (!visited.contains(curVertex))
			{
				visited.add(curVertex);
				for (GeoVertex neighbor : curVertex.getNeighbors())
				{
					
					if (!visited.contains(neighbor) && neighbor.updateStartDistance(curVertex))
					{
					neighbor.updateTotalDistance(true);
					parentMap.put(neighbor, curVertex); //each neighbor has curVertex mapped as its parent
					searchQueue.add(neighbor);
					}
					neighbor.updateTotalDistance(false);
					
				}
				
			}
			
		}
		
		if (foundPath && parentMap.size()>0)
		{
			return parentMapToListConversion( parentMap, 
					startVertex, goalVertex );
		}

		
		return null;
		
	}

	/** Find the path from start to goal using A-Star search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {};
        return aStarSearch(start, goal, temp);
	}
	
	
	
	
	
	
	
	/** Find the path from start to goal using A-Star search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	
	
	public List<GeographicPoint> aStarSearch(GeographicPoint start, 
											 GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		// TODO: Implement this method in WEEK 3
		
		// Hook for visualization.  See writeup.
		
		boolean foundPath = false;
		//nodeSearched.accept(next.getLocation());boolean foundPath = false; //We shall use this variable to track whether we discovered a path
		PriorityQueue<GeoVertex> searchQueue = new PriorityQueue<GeoVertex>();
		HashSet<GeoVertex> visited = new HashSet<GeoVertex>();
		HashMap<GeoVertex, GeoVertex> parentMap = new HashMap<GeoVertex, GeoVertex>();
		
		for (GeoVertex v: roadVertices.values())
		{
			v.initializeDistances();
		}
			
		
		GeoVertex startVertex = this.roadVertices.get(start);
		GeoVertex goalVertex = this.roadVertices.get(goal);
		startVertex.setAsStartVertex();
		searchQueue.add(startVertex);
		
		
		
		while (searchQueue.size()>0)
		{
			
			
			GeoVertex curVertex = searchQueue.remove();
			
			
			
			nodeSearched.accept(curVertex.getPoint());
			if (curVertex.getPoint().equals(goal))
			{
				foundPath = true;
				break;
				
			}
			if (!visited.contains(curVertex))
			{
				visited.add(curVertex);
				for (GeoVertex neighbor : curVertex.getNeighbors())
				{
					neighbor.setMinDistanceFromGoal(goalVertex);
					if (!visited.contains(neighbor) && neighbor.updateStartDistance(curVertex))
					{
					neighbor.updateTotalDistance(true);
					parentMap.put(neighbor, curVertex); //each neighbor has curVertex mapped as its parent
					searchQueue.add(neighbor);
					}
					neighbor.updateTotalDistance(true);
					
				}
				
			}
			
		}
		
		if (foundPath && parentMap.size()>0)
		{
			return parentMapToListConversion( parentMap, 
					startVertex, goalVertex );
		}

		
		return null;
		
	}

	
	
	public static void main(String[] args)
	{
		/*
		System.out.print("Making a new map...");
		MapGraph theMap = new MapGraph();
		System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/testdata/simpletest.map", theMap);
		System.out.println("DONE.");
		*/
		// You can use this method for testing.  
		
		 //Use this code in Week 3 End of Week Quiz
		MapGraph theMap = new MapGraph();
		System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/maps/utc.map", theMap);
		System.out.println("DONE.");

		GeographicPoint start = new GeographicPoint(32.8648772, -117.2254046);
		GeographicPoint end = new GeographicPoint(32.8660691, -117.217393);
		
		
		List<GeographicPoint> route = theMap.dijkstra(start,end);
		List<GeographicPoint> route2 = theMap.aStarSearch(start,end);

		for (GeographicPoint node: route2)
		{
			System.out.println(node);
		}
			
		
	}
	
}
