package de.netze.onlinegis.shared.auskunftsystem.networkelements;

import java.io.Serializable;
import java.util.ArrayList;

import org.postgresql.geometric.PGpoint;

import de.netze.onlinegis.shared.common.coordinates.LonLatCoordinates;

public class Node_Ruckeinspeisung_Flavour extends Node implements Serializable{

	private String objID;
	private int hausanschlussID;
	
	public Node_Ruckeinspeisung_Flavour(){}
	
	public void setObjID(String objID){this.objID = objID;}
	public void setHausanschlussID(int hausanschlussID){this.hausanschlussID = hausanschlussID;}
	
	public String getObjID(){return this.objID;}
	public int getHausanschlussID(){return this.hausanschlussID;}
}
