package de.netze.onlinegis.shared.mastapp.networkelements;

import java.io.Serializable;

import de.netze.onlinegis.shared.common.coordinates.LonLatCoordinates;
import de.netze.onlinegis.shared.common.networkelements.GISObject;

public class Mast extends GISObject implements Serializable{	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int schadensklasse;
	
	private boolean mittelspannung;
	private boolean niederspannung;
	
	private String werkstoff;
	
	private LonLatCoordinates lonlatCoordinates;
	
	public Mast(){}
	
	/* GETTER */
	public int					getSchadensklasse(){return this.schadensklasse;}
	public boolean				getIsMittelspannung(){return this.mittelspannung;}
	public boolean				getIsNiederspannung(){return this.niederspannung;}
	public String				getWerkstoff(){return this.werkstoff;}
	public LonLatCoordinates	getCoordinates(){return this.lonlatCoordinates;}
	
	/* SETTER */
	public void setSchadensklasse(int schadensklasse){this.schadensklasse = schadensklasse;}
	public void setIsMittelspannung(boolean mittelspannung){this.mittelspannung=mittelspannung;}
	public void setIsNiederspannung(boolean niederspannung){this.niederspannung=niederspannung;}
	public void setWerkstoff(String werkstoff){this.werkstoff=werkstoff;}
	public void setCoordinates(LonLatCoordinates coord){this.lonlatCoordinates=coord;}
}
