package de.netze.onlinegis.shared.common.networkelements;

import java.io.Serializable;

@SuppressWarnings("serial")
public class GISObject implements Serializable{

	private int id;
	
	public GISObject(){}
		
	/* GETTER */
	public int getID(){return this.id;}
	
	/* SETTER */
	public void setID(int id){this.id = id;}
}
