package de.netze.onlinegis.shared.common.networkelements;

import java.io.Serializable;

@SuppressWarnings("serial")
public class SimpleGemeinde implements Serializable{

	/**
	 * 
	 */
	private int id = 0;
	private String name = "";
	
	public SimpleGemeinde(){}
	
	public void setID(int id){this.id = id;}
	public void setName(String name){this.name = name;}
	
	public String getName(){return this.name;}
	public int getID(){return this.id;}
	
	@Override
	public String toString(){return this.name;}
	
}
