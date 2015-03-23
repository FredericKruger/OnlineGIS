package de.netze.onlinegis.shared.auskunftsystem.networkelements;

import java.io.Serializable;
import java.util.ArrayList;

import de.netze.onlinegis.shared.common.coordinates.LonLatCoordinates;

public class CompleteStation extends SimpleStation implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private LonLatCoordinates coordinates;
	private ArrayList<LonLatCoordinates> areaCoordinates;
	
	public CompleteStation(){}

	public void setCoordinates(LonLatCoordinates coordinates){this.coordinates = coordinates;}
	public void setAreaCoordinates(ArrayList<LonLatCoordinates> areaCoordinates){this.areaCoordinates = areaCoordinates;}
	
	public LonLatCoordinates getCoordinates(){return this.coordinates;}
	public ArrayList<LonLatCoordinates> getAreaCoordinates(){return this.areaCoordinates;}
}
