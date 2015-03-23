package de.netze.onlinegis.shared.auskunftsystem.networkelements;

import java.io.Serializable;
import java.util.ArrayList;

import org.postgresql.geometric.PGpoint;

import de.netze.onlinegis.shared.common.coordinates.LonLatCoordinates;

public class Node_VKS_Flavour extends Node implements Serializable{

	private ArrayList<LonLatCoordinates> areaCoordinates;

	public Node_VKS_Flavour(){}
	
	public ArrayList<LonLatCoordinates> 		getAreaCoordinates(){return this.areaCoordinates;}
	
	public void setAreaCoordinates(ArrayList<LonLatCoordinates>  coordinates){this.areaCoordinates = coordinates;}
	
}
