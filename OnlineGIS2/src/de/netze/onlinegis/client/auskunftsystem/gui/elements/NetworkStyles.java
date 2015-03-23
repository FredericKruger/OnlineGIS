package de.netze.onlinegis.client.auskunftsystem.gui.elements;

import org.gwtopenmaps.openlayers.client.Style;

import de.netze.onlinegis.shared.auskunftsystem.networkelements.Node;

public class NetworkStyles {

	private Style stationStyle;
	private Style stationAreaStyle;
	private Style hausanschlussStyle;
	private Style ruckeinspeisungStyle;
	private Style vksStyle;
	
	private Style kabelStyle;
	private Style freileitungStyle;
	
	public NetworkStyles(){

		stationStyle = new Style();
		stationStyle.setExternalGraphic("http://google-maps-icons.googlecode.com/files/powersubstation.png");
		stationStyle.setGraphicSize(32, 37);
		stationStyle.setGraphicOffset(-16, -37); //anchor on bottom center
		stationStyle.setFillOpacity(1.0);
		
		stationAreaStyle = new Style();
		stationAreaStyle.setPointRadius(5);
		stationAreaStyle.setFillColor("red");
		stationAreaStyle.setStrokeColor("red");
		stationAreaStyle.setStrokeWidth(2);
		stationAreaStyle.setFillOpacity(0.9);
		
		hausanschlussStyle = new Style();
		hausanschlussStyle.setPointRadius(5);
		hausanschlussStyle.setFillColor("orange");
		hausanschlussStyle.setStrokeColor("orange");
		hausanschlussStyle.setStrokeWidth(2);
		hausanschlussStyle.setFillOpacity(0.9);
		
		vksStyle = new Style();
		vksStyle.setPointRadius(5);
		vksStyle.setFillColor("black");
		vksStyle.setStrokeColor("black");
		vksStyle.setStrokeWidth(2);
		vksStyle.setFillOpacity(0.9);
		
		ruckeinspeisungStyle = new Style();
		ruckeinspeisungStyle.setPointRadius(5);
		ruckeinspeisungStyle.setFillColor("green");
		ruckeinspeisungStyle.setStrokeColor("green");
		ruckeinspeisungStyle.setStrokeWidth(2);
		ruckeinspeisungStyle.setFillOpacity(0.9);
		
		kabelStyle = new Style();
		kabelStyle.setFillColor("blue");
		kabelStyle.setStrokeColor("blue");
		kabelStyle.setStrokeWidth(3);
		kabelStyle.setFillOpacity(0.9);
		
		freileitungStyle = new Style();
		freileitungStyle.setFillColor("blue");
		freileitungStyle.setStrokeColor("blue");
		freileitungStyle.setStrokeWidth(3);
		freileitungStyle.setFillOpacity(0.9);
		freileitungStyle.setStrokeDashstyle("dash");

	}
	
	public Style getStyleFromNode(int type){
		if(type==Node.STATION) return stationStyle;
		else if(type==Node.HAUSANSCHLUSS) return hausanschlussStyle;
		else if(type==Node.RUCKEINSPEISUNG) return ruckeinspeisungStyle;
		else if(type==Node.VERTEILKASTEN) return vksStyle;
		else return null;
	}
	
	public Style getStyleFromArc(boolean freileitung, int type){
		if(freileitung){
			return freileitungStyle;
		}
		else return kabelStyle;
	}
	
	public Style getStationStyle(){return this.stationStyle;}
	public Style getStationAreaStyle(){return this.stationAreaStyle;}
	public Style getHausanschlussStyle(){return this.hausanschlussStyle;}
	public Style getRuckeinspeisungStyle(){return this.ruckeinspeisungStyle;}
	public Style getVKSStyle(){return this.vksStyle;}

}
