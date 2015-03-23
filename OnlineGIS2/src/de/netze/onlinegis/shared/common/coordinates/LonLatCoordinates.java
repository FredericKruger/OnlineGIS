package de.netze.onlinegis.shared.common.coordinates;

import java.io.Serializable;

public class LonLatCoordinates implements Serializable{
	
	private double longitude;
	private double latitude;
	
	public LonLatCoordinates(){
		this.longitude = 0.0;
		this.latitude = 0.0;
	}
	
	public LonLatCoordinates(double longitude, double latitude){
		this.longitude = longitude;
		this.latitude = latitude;
	}
	
	/* GETTER */
	public double 	getLongitude(){return this.longitude;}
	public double	getLatitude(){return this.latitude;}
	
	/* SETTER */
	public void setLongitude(double longitude){this.longitude = longitude;}
	public void setLatitude(double latitude){this.latitude = latitude;}

}
