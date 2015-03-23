package de.netze.onlinegis.shared.auskunftsystem.networkelements;

import java.io.Serializable;
import java.util.ArrayList;

public class ElectricNetwork implements Serializable{

	private String gemmeindeName;
	
	private CompleteStation station;
	private ArrayList<Node> listOfNodes = new ArrayList<>();
	private ArrayList<Arc> listOfArcs = new ArrayList<>();
	
	public ElectricNetwork(){};
	
	public void addNode(Node n){this.listOfNodes.add(n);}
	public void addNodes(ArrayList<Node> list){this.listOfNodes.addAll(list);}
	
	public void addArc(Arc a){this.listOfArcs.add(a);}
	public void addArcs(ArrayList<Arc> list){this.listOfArcs.addAll(list);}
	
	/* GETTER */
	public CompleteStation		getCompleteStation(){return this.station;}
	public ArrayList<Node>		getListOfNodes(){return this.listOfNodes;}
	public ArrayList<Arc>		getListOfArcs(){return this.listOfArcs;}
	public String				getGemeindeName(){return this.gemmeindeName;}
	
	/* SETTER */
	public void setCompleteStation(CompleteStation station){this.station = station;}
	public void setGemeindeName(String gemeindeName){this.gemmeindeName = gemeindeName;}
	
}
