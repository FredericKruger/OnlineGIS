package de.netze.onlinegis.client.mastapp.gui.elements;

import org.gwtopenmaps.openlayers.client.Style;
import org.gwtopenmaps.openlayers.client.StyleMap;
import org.gwtopenmaps.openlayers.client.filter.ComparisonFilter;
import org.gwtopenmaps.openlayers.client.filter.ComparisonFilter.Types;
import org.gwtopenmaps.openlayers.client.style.Rule;
import org.gwtopenmaps.openlayers.client.style.SymbolizerPoint;

import de.netze.onlinegis.shared.mastapp.networkelements.Mast;

public class MastenStyles {

	public static int pointRadius = 8;
	
	private Style circleStyle;
	private Style squareStyle;
	private Style triangleStyle;
	private Style starStyle;
	
	private StyleMap circleStyleMap;
	private StyleMap squareStyleMap;
	private StyleMap triangleStyleMap;
	private StyleMap starStyleMap;

	private Rule[] schadensklasseRules;
	
	public MastenStyles(){
		circleStyle = new Style();
		initialize(circleStyle, "circle");
		squareStyle = new Style();
		initialize(squareStyle, "square");
		triangleStyle = new Style();
		initialize(triangleStyle, "triangle");
		starStyle = new Style();
		initialize(starStyle, "star");
		
		initializeStyleMaps();
	}
	
	private void initializeStyleMaps(){
		//Create rules
        schadensklasseRules = new Rule[6];
 
        createRule(0, 0, "white");
        createRule(1, 1, "green");
        createRule(2, 2, "orange");
        createRule(3, 3, "yellow");
        createRule(4, 4, "red");
        createRule(5, -1, "grey");
                
        circleStyleMap = new StyleMap(circleStyle);
        circleStyleMap.addRules(schadensklasseRules, "default");
        
        squareStyleMap = new StyleMap(squareStyle);
        squareStyleMap.addRules(schadensklasseRules, "default");
        
        triangleStyleMap = new StyleMap(triangleStyle);
        triangleStyleMap.addRules(schadensklasseRules, "default");
        
        starStyleMap = new StyleMap(starStyle);
        triangleStyleMap.addRules(schadensklasseRules, "default");
	}
	
	private void createRule(int id, int value, String color){
		 //schadensklasse 0
        schadensklasseRules[id] = new Rule();
        ComparisonFilter filter = new ComparisonFilter();
        filter.setType(Types.EQUAL_TO);
        filter.setProperty("schadensklasse");
        filter.setNumberValue(value);
 
        SymbolizerPoint symbolizer = new SymbolizerPoint();
        symbolizer.setFillColor(color);
        schadensklasseRules[id].setFilter(filter);
        schadensklasseRules[id].setSymbolizer(symbolizer);
	}
	
	private void initialize(Style s, String graphicName){
		s.setPointRadius(pointRadius);
		s.setStrokeWidth(2);
		s.setFillOpacity(0.9);
		s.setGraphicName(graphicName);
		//s.setFillColor(fillColor);
		s.setStrokeColor("black");
	}
	
	public StyleMap getStyleFromMast(Mast m){
		if(m.getWerkstoff().compareTo("HOLZ")==0){
			return circleStyleMap;
		}
		else if(m.getWerkstoff().compareTo("BETON")==0){
			return squareStyleMap;
		}
		else if(m.getWerkstoff().compareTo("ALU")==0
				|| m.getWerkstoff().startsWith("STAHL")
				){
			return triangleStyleMap;
		}
		else{
			return starStyleMap;
		}
	}
	
	public StyleMap getHolzMastenStyleMap(){return this.circleStyleMap;}
	public StyleMap getBetonMastenStyleMap(){return this.squareStyleMap;}
	public StyleMap getStahlMastenStyleMap(){return this.triangleStyleMap;}
	public StyleMap getSonstigeMastenStyleMap(){return this.starStyleMap;}

}
