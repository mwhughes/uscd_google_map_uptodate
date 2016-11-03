package roadgraph;

import geography.GeographicPoint;

/*
 * A class that represents the edges of a directed graph of GeographicPoints
 * Note: The edges are assumed to be directional with an toVertex and a fromVertex
 */


public class GeoEdge {
	
	
	private GeoVertex toVertex; //The vertex associated with the GeographicPoint that edge goes to.
	private GeoVertex fromVertex; //The vertex associated with the GeographicPoint that edge comes from.
	private String roadNameE; //roadName associated with the edge
	private double lengthE; //length of the edge
	private String roadTypeE; // roadType
	
	
	
	/*
	 * Constructor method. 
	 * It is called by MapGraph class.
	 * 
	 * 	 */
	public GeoEdge(GeoVertex fromVert, GeoVertex toVert, String roadName,
			String roadType, double length)
	{
		this.toVertex = toVert;
		this.fromVertex = fromVert;
		this.roadNameE = roadName;
		this.lengthE = length;
		this.roadTypeE = roadType;
	}
	
	/*
	 * Getter Method returns the GeographicPoint associated with the toVertex of the edge
	 */
	public GeographicPoint getToPoint()
	{
		return toVertex.getPoint();
	}
	/*
	 * Getter Method returns the GeographicPoint associated with the fromVertex of the edge
	 */
	public GeographicPoint getFromPoint()
	{
		return fromVertex.getPoint();
	}
	/*
	 * Getter Method returns the length associated with edge
	 */
	public double getLength()
	{
		return lengthE;
	}
	/*
	 * Getter Method returns road name of the edge
	 */
	public String getRoadNameE()
	{
		return roadNameE;
	}
	
	/*
	 * Getter Method returns the road type of the edge
	 */
	
	public String getRoadType()
	{
		return roadTypeE;
	}
	
	/*
	 * Getter Method returns the toVertex of the edge
	 */
	
	public GeoVertex getToVertex()
	{
		return toVertex;
	}
	/*
	 * Getter Method returns the fromVertex of the edge
	 */
	public GeoVertex getFromVertex()
	{
		return fromVertex;
	}

}
