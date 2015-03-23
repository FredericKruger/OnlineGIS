package de.netze.onlinegis.shared.mastapp.networkelements;

import java.io.Serializable;

import de.netze.onlinegis.shared.common.networkelements.GISObject;

@SuppressWarnings("serial")
public class Bezirk extends GISObject implements Serializable{

	private String name;
	
	public Bezirk(){}
	
	public String getName(){return this.name;}
	public void setName(String name){this.name = name;}
	
}
