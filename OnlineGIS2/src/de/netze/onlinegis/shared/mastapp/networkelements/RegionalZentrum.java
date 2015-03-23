package de.netze.onlinegis.shared.mastapp.networkelements;

import java.io.Serializable;

import de.netze.onlinegis.shared.common.networkelements.GISObject;

@SuppressWarnings("serial")
public class RegionalZentrum extends GISObject implements Serializable{

	private String name;
	
	public RegionalZentrum(){}
	
	public String getName(){return this.name;}
	public void setName(String name){this.name = name;}
	
}
