package de.netze.onlinegis.shared.auskunftsystem.networkelements;

import java.io.Serializable;

public class SimpleStation implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id = 0;
	private String name = "";
	
	public SimpleStation(){}
	
	public void setID(int id){this.id = id;}
	public void setName(String name){this.name = name;}
	
	public String getName(){return this.name;}
	public int getID(){return this.id;}
	
	@Override
	public String toString(){return this.name;}
	
}
