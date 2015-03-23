package de.netze.onlinegis.shared.common.coordinates;

import java.io.Serializable;

public class XYCoordinates implements Serializable{

	private double x;
	private double y;
	
	public XYCoordinates(){
		this.x = 0.0;
		this.y = 0.0;
	}
	
	public XYCoordinates(double x, double y){
		this.x = x;
		this.y = y;
	}
	
	public double getDistance(XYCoordinates coord){
		double distance = 0.0;
		
		distance = Math.sqrt((x-coord.x)*(x-coord.x) + (y-coord.y)*(y-coord.y));
		
		return distance;
	}
	
	public String toString(){
		String s = getX() + ";" + getY();
		return s;
	}
	
	/* GETTER */
	public double getX(){return this.x;}
	public double getY(){return this.y;}
	
	/* SETTER */
	public void setX(double x){this.x = x;}
	public void setY(double y){this.y = y;}
}
