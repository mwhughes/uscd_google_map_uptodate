package roadgraph;

import java.util.ArrayList;

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
import roadgraph.permHelper;
public class mapGraphForSalesmen extends MapGraph{
	
	private ArrayList<GeographicPoint> destinations;
	
	public mapGraphForSalesmen()
	{
		this.destinations = new ArrayList<GeographicPoint>();
	}
	
	public void setDestinations(ArrayList<GeographicPoint> dest)
	{
		this.destinations = dest;
	}
	
	/*
	 * Master method to identify shortest path through all of the destinations.
	 * @param returnTrip specifies whether we should include a return trip to the starting vertex, fix the finalVertex, or take any shortest route through the specified points 
	 * not worrying about the end. 0 means return home. 1 means go to fixed end point, and 2 means find any path from the start.
	 * Note: The first point in destinations is assumed to be the start point. And if an endpoint is specified, it will be the last point of destinations.
	 * 
	 */
	
	
	public List<GeographicPoint> findPath(int returnTrip, Consumer<GeographicPoint> nodeSearched)
	{
		System.out.println("test");
		int permSize = calcSizeOfPerm(returnTrip);
		
		ArrayList<ArrayList<Integer>> permutations = permHelper.permutationBuilderTotal(permSize);
		
		List<GeographicPoint> currentShortestPath = buildPathFromPerm(permutations.get(0), nodeSearched, returnTrip);
		if (currentShortestPath==null){	System.out.println("short path is null 1"); }
		double currentMinDistance = calculateLengthOfPath(currentShortestPath);
		
		for (ArrayList<Integer> curPerm : permutations)
		{
			List<GeographicPoint> currentPath = buildPathFromPerm(curPerm, nodeSearched, returnTrip);
			double currentDistance = calculateLengthOfPath(currentPath);
			
			if (currentDistance<currentMinDistance)
			{
				currentShortestPath = currentPath;
				currentMinDistance = currentDistance;
			}
		}
		
		if (currentShortestPath.size()<1)	{return null;}	
		return pathCleaner(currentShortestPath);
		
		
	}
	
	private List<GeographicPoint> pathCleaner(List<GeographicPoint> pathWithDuplicates)
	{
		List<GeographicPoint> pathWithOutDuplicates = new ArrayList<GeographicPoint>();
		pathWithOutDuplicates.add(pathWithDuplicates.get(0));
		for (int i=1; i<pathWithDuplicates.size(); i++)
		{
			if (pathWithDuplicates.get(i-1) != pathWithDuplicates.get(i) )
			{
				pathWithOutDuplicates.add(pathWithDuplicates.get(i));
			}
		}
		
		
		return pathWithOutDuplicates;
	}
	
	private List<GeographicPoint> buildRetHomePathFromPerm(ArrayList<Integer> perm, Consumer<GeographicPoint> nodeSearched)
	{
		List<GeographicPoint> path = findShortestPartialPathForGivenOrder(this.destinations, perm,  nodeSearched);
		path = completePath(path, this.destinations.get(0), nodeSearched);
		return path;
	}
	
	private List<GeographicPoint> buildFixedEndPathFromPerm(ArrayList<Integer> perm, Consumer<GeographicPoint> nodeSearched)
	{
		ArrayList<GeographicPoint> subDest = new ArrayList<GeographicPoint>();
		for (int i=0; i<this.destinations.size()-1; i++)
		{
			subDest.add(this.destinations.get(i));
		}
		List<GeographicPoint> path = findShortestPartialPathForGivenOrder(subDest, perm,  nodeSearched);
		path = completePath(path, this.destinations.get(this.destinations.size()-1), nodeSearched);
		return path;
	}
	
	private List<GeographicPoint> buildNoEndPathFromPerm(ArrayList<Integer> perm, Consumer<GeographicPoint> nodeSearched)
	{
		List<GeographicPoint> path = findShortestPartialPathForGivenOrder(this.destinations, perm,  nodeSearched);
		return path;
	}
	
	private List<GeographicPoint> buildPathFromPerm(ArrayList<Integer> perm, Consumer<GeographicPoint> nodeSearched, int returnTrip)
	{
		if (returnTrip==0)
		{
			return buildRetHomePathFromPerm(perm, nodeSearched);
		}
		if (returnTrip==1)
		{
			return buildFixedEndPathFromPerm(perm, nodeSearched);
		}
		 return buildNoEndPathFromPerm(perm, nodeSearched);
	}
	
	
	
	private int calcSizeOfPerm(int returnTrip)
	{
		if (returnTrip == 0 || returnTrip == 2)
		{
			return destinations.size() - 1;
		}
		return destinations.size()-2;
	}
	
	
	public void clearDestinations()
	{
		this.destinations = new ArrayList<GeographicPoint>();
	}
	
	public HashSet<ArrayList<GeographicPoint>> getPermutations()
	{
		return null;
	}
	/*
	 * Method takes an arraylist of a given order of geographic points and finds the shortest path through those points in the given order.
	 * The first point is the place to start, and the visitOrder is a permutation representing the order in which to visit the rest.
	 *We will need an additional helper function if they want to calculate shortest path that includes a return trip or some other specified location.
	 */
	public List<GeographicPoint> findShortestPartialPathForGivenOrder(ArrayList<GeographicPoint> placesToVisit, ArrayList<Integer> visitOrder, 
			Consumer<GeographicPoint> nodeSearched)
	{
		List<GeographicPoint> routeList = new ArrayList<GeographicPoint>();
		List<GeographicPoint> firstPath = aStarSearch(placesToVisit.get(0), 
				placesToVisit.get(visitOrder.get(0)), nodeSearched);
		
		if (null == firstPath)
		{
			System.out.println("firstPath in find shortest path is null");
			return null;
		}
		routeList.addAll(firstPath);
		
		for (int i=1; i<visitOrder.size(); i++)
		{
			List<GeographicPoint> nextPath = aStarSearch(placesToVisit.get(visitOrder.get(i-1)), 
					placesToVisit.get(visitOrder.get(i)), nodeSearched);
			if (nextPath == null){ 
				System.out.println("firstPath in find shortest path is null");
				return null;}
			routeList.addAll(nextPath);
		}
		
		return routeList;
	}
	public List<GeographicPoint> completePath(List<GeographicPoint> partialPath, GeographicPoint finalDestination, Consumer<GeographicPoint> nodeSearched)
	{
		if (partialPath==null){return null;}
		GeographicPoint penultimatePoint = partialPath.get(partialPath.size()-1);
		List<GeographicPoint> nextPath = aStarSearch(penultimatePoint, 
				finalDestination, nodeSearched);
		partialPath.addAll(nextPath);
		return partialPath;
	
		
	}
	
	/*
	 * Method compute the length of a path of geographic points that we are considering using to visit all of the destination points
	 */
	public double calculateLengthOfPath(List<GeographicPoint> path)
	{
		double pathLength = 0.0;
		if (path==null)
		{
			return Double.POSITIVE_INFINITY;
		}
		for (int i=0; i<path.size()-1; i++)
		{
			//System.out.println("i is: "+ i);
			
			GeoVertex current = roadVertices.get(path.get(i));
			GeoVertex next = roadVertices.get(path.get(i+1));
			if (current != next){
			GeoEdge curEdge = current.getNeighborsHashMap().get(next);
			
			double curLength = curEdge.getLength();
			pathLength += curLength;
			}
		}
		return pathLength;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		

	}

}
