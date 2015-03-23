package de.netze.onlinegis.shared.auskunftsystem.networkelements;

import java.io.Serializable;
import java.util.ArrayList;

import org.postgresql.geometric.PGpath;
import org.postgresql.geometric.PGpoint;

import de.netze.onlinegis.shared.common.coordinates.LonLatCoordinates;
import de.netze.onlinegis.shared.common.networkelements.GISObject;

public class Arc extends GISObject implements Serializable{

	public static int STANDARD = 1;
	public static int HAUSANSCHLUSS = 2;
	public static int INTERNAL = 3;
	
	private ArrayList<LonLatCoordinates> listOfCoordinates = new ArrayList<>();
	
	private ArrayList<Node> listOfNodes = new ArrayList<>();
	
	private int type;
	
	private boolean isFreileitung;
	
	public Arc(){}
	
	public void addNode(Node node){this.listOfNodes.add(node);}
	
	/*private boolean connects(LonLatCoordinates coord){
		double tolerance = 0.0002469135802;
		
		if(getDistance(p, path.points[0])<tolerance
				|| getDistance(p, path.points[path.points.length-1])<tolerance){
				return true;
		}
		
		return false;
	}*/
	
	/*private double getDistance(LonLatCoordinates p1, LonLatCoordinates p2){
		double distance = 0.0;
		
		distance = Math.sqrt((p1.x-p2.x)*(p1.x-p2.x) + (p1.y-p2.y)*(p1.y-p2.y));
		
		return distance;
	}*/
	
	/*public boolean isConnected(Node node){		
		return connects(node.getPoint());
	}*/
	
	public boolean 							getIsFreileitung(){return this.isFreileitung;}
	public ArrayList<LonLatCoordinates> 	getLine(){return this.listOfCoordinates;}
	public ArrayList<Node> 					getListOfNodes(){return this.listOfNodes;}
	public int 								getType(){return this.type;}

	public void setIsFreileitung(boolean b){this.isFreileitung = b;}
	public void setListOfCoordinates(ArrayList<LonLatCoordinates> listOfCoordinates){this.listOfCoordinates = listOfCoordinates;}
	public void setListOfNodes(ArrayList<Node> listOfNodes){this.listOfNodes = listOfNodes;}
	public void setType(int type){this.type = type;}
}
