package de.netze.onlinegis.shared.auskunftsystem.networkelements;

import java.io.Serializable;
import java.util.ArrayList;

import org.postgresql.geometric.PGpoint;

import de.netze.onlinegis.shared.common.coordinates.LonLatCoordinates;

public class Node_Hausanschluss_Flavour extends Node implements Serializable{

	private String objID;
	
	public Node_Hausanschluss_Flavour(){}
	
	public void setObjID(String objID){this.objID = objID;}
	public String getObjID(){return this.objID;}
}
