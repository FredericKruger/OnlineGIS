package de.netze.onlinegis.shared.auskunftsystem.networkelements;

import java.io.Serializable;
import java.util.ArrayList;

import org.postgresql.geometric.PGpoint;

import de.netze.onlinegis.shared.common.coordinates.LonLatCoordinates;
import de.netze.onlinegis.shared.common.networkelements.GISObject;

public class Node extends GISObject implements Serializable{
	
	public static int STATION = 1;
	public static int HAUSANSCHLUSS = 2;
	public static int RUCKEINSPEISUNG = 3;
	public static int SCHALTER = 4;
	public static int BAUTEILE = 5;
	public static int VERTEILKASTEN = 6;

	private LonLatCoordinates coordinates;
	
	public int typeID = 0;
	
	private ArrayList<Arc> listOfArcs = new ArrayList<Arc>();
	
	public Node(){}
	
	public void addArc(Arc arc){this.listOfArcs.add(arc);}
	
	public LonLatCoordinates 	getCoordinates(){return this.coordinates;}
	public ArrayList<Arc>		getListOfArcs(){return this.listOfArcs;}
	public int					getTypeID(){return this.typeID;}
	
	public void setListOfArcs(ArrayList<Arc> listOfArcs){this.listOfArcs = listOfArcs;}
	public void setTypeID(int typeID){this.typeID = typeID;}
	public void setCoordinates(LonLatCoordinates coordinates){this.coordinates = coordinates;}
	
}
