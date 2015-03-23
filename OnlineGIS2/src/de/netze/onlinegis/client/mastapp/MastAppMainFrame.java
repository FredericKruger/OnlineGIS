package de.netze.onlinegis.client.mastapp;

import java.util.ArrayList;

import org.gwtopenmaps.openlayers.client.LonLat;
import org.gwtopenmaps.openlayers.client.Map;
import org.gwtopenmaps.openlayers.client.MapOptions;
import org.gwtopenmaps.openlayers.client.MapWidget;
import org.gwtopenmaps.openlayers.client.control.LayerSwitcher;
import org.gwtopenmaps.openlayers.client.control.OverviewMap;
import org.gwtopenmaps.openlayers.client.control.ScaleLine;
import org.gwtopenmaps.openlayers.client.feature.VectorFeature;
import org.gwtopenmaps.openlayers.client.geometry.Point;
import org.gwtopenmaps.openlayers.client.layer.OSM;
import org.gwtopenmaps.openlayers.client.layer.Vector;
import org.gwtopenmaps.openlayers.client.util.Attributes;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.StackLayoutPanel;

import de.netze.onlinegis.client.AppBundle;
import de.netze.onlinegis.client.OnlineGIS2;
import de.netze.onlinegis.client.mastapp.gui.dialogs.BrowseStationDialog_MastApp;
import de.netze.onlinegis.client.mastapp.gui.elements.MastenStyles;
import de.netze.onlinegis.client.mastapp.retriever.RetrieveMastenFromGemeindeService;
import de.netze.onlinegis.client.mastapp.retriever.RetrieveMastenFromGemeindeServiceAsync;
import de.netze.onlinegis.shared.mastapp.networkelements.Mast;

public class MastAppMainFrame{

	private DockLayoutPanel mapPanel;
	private DockLayoutPanel mainPanel;

	private MenuBar menu;
	
	private Map map;
	private MapWidget mapWidget;

	private ArrayList<Vector> listOfLayers;
	
	private ArrayList<Mast> mastList;
	private ArrayList<VectorFeature> featureList;

	private MastenStyles masteStyles;
	
	private OnlineGIS2 mainFrame;
	
	private class MastListFromGemeindeCallBack implements AsyncCallback<ArrayList<Mast>>{

		@Override
		public void onFailure(Throwable caught){
			Window.alert("Unauble to obtain server response: " + caught.getMessage());
		}

		@Override
		public void onSuccess(ArrayList<Mast> list){
			mastList = null;
			mastList = list;
			displayMasten();
		}
	}
	
	public void displayMasten(){
		//remove everytrthing
		for(int i=0; i<listOfLayers.size(); i++){
			mapWidget.getMap().removeLayer(listOfLayers.get(i));
		}
		listOfLayers = new ArrayList<Vector>();
		
		//reinitialize hashmaps
		featureList = new ArrayList<>();
		
		final Vector holzVectorLayer = new Vector("Holz Masten");
		holzVectorLayer.setStyleMap(masteStyles.getHolzMastenStyleMap());
		final Vector betonVectorLayer = new Vector("Beton Masten");
		betonVectorLayer.setStyleMap(masteStyles.getBetonMastenStyleMap());
		final Vector stahlVectorLayer = new Vector("Stahl Masten");
		stahlVectorLayer.setStyleMap(masteStyles.getStahlMastenStyleMap());
		final Vector sonstigeVectorLayer = new Vector("Sonstige Masten");
		sonstigeVectorLayer.setStyleMap(masteStyles.getSonstigeMastenStyleMap());
		
		listOfLayers.add(holzVectorLayer);
		listOfLayers.add(betonVectorLayer);
		listOfLayers.add(stahlVectorLayer);
		listOfLayers.add(sonstigeVectorLayer);
		
		for(int i=0; i<mastList.size(); i++){
			
			LonLat lonLat = new LonLat(mastList.get(i).getCoordinates().getLongitude(),
					mastList.get(i).getCoordinates().getLatitude());
			lonLat.transform("EPSG:4326","EPSG:900913");
			Point p = new Point(lonLat.lon(), lonLat.lat());
			
			VectorFeature pointFeature = new VectorFeature(p);
			Attributes attributes = new Attributes();
            attributes.setAttribute("schadensklasse", mastList.get(i).getSchadensklasse());
            pointFeature.setAttributes(attributes);
            
            if(mastList.get(i).getWerkstoff().compareTo("HOLZ")==0){
    			holzVectorLayer.addFeature(pointFeature);
    		}
    		else if(mastList.get(i).getWerkstoff().compareTo("BETON")==0){
    			betonVectorLayer.addFeature(pointFeature);
    		}
    		else if(mastList.get(i).getWerkstoff().compareTo("ALU")==0
    				|| mastList.get(i).getWerkstoff().startsWith("STAHL")
    				){
    			stahlVectorLayer.addFeature(pointFeature);
    		}
    		else{
    			sonstigeVectorLayer.addFeature(pointFeature);
    		}
			
            //add to hashmaps
			featureList.add(pointFeature);
			
		}
		
		//Add layers and zoom to extent
		for(int i=0; i<listOfLayers.size(); i++){
			mapWidget.getMap().addLayer(listOfLayers.get(i));
		}
		mapWidget.getMap().zoomToExtent(holzVectorLayer.getDataExtent());
	}
	
	private void createMap(){
		MapOptions defaultMapOptions = new MapOptions();

		//Create a MapWidget and add 2 OSM layers
		mapWidget = new MapWidget("100%", "100%", defaultMapOptions);

		OSM osm_1 = OSM.Mapnik("Mapnik");
		OSM osm_2 = OSM.CycleMap("CycleMap");
		osm_1.setIsBaseLayer(true);
		osm_2.setIsBaseLayer(true);

		map = mapWidget.getMap();
		
		map.addLayer(osm_1);
		map.addLayer(osm_2);
		/*** TEST WITH WMS LAYER ***/

		//Lets add some default controls to the map
		map.addControl(new LayerSwitcher()); //+ sign in the upperright corner to display the layer switcher
		map.addControl(new OverviewMap()); //+ sign in the lowerright to display the overviewmap
		map.addControl(new ScaleLine()); //Display the scaleline

		//Center and zoom to a location
		LonLat lonLat = new LonLat(6.95, 50.94);
		lonLat.transform("EPSG:4326","EPSG:900913"); //transform lonlat to OSM coordinate system
		mapWidget.getMap().setCenter(lonLat, 5);
		map.setMinMaxZoomLevel(3, 22);


		mapPanel = new DockLayoutPanel(Unit.PCT);
		mapPanel.setHeight("100%");
		mapPanel.setWidth("100%");
		mapPanel.add(mapWidget);
	}

	public void returnFromStationSucheDialog(BrowseStationDialog_MastApp myDialog, boolean ok, int selectedTab){
		if(ok){
			int id=0;
			if(selectedTab==0){ //browse according to gemeinde
				id = myDialog.getSelectedGemeinde();
				
			}
			else if(selectedTab==1){//browse according to zentrum
				id = myDialog.getSelectedRegionalZentrum();
			}
			else if(selectedTab==2){//browse according to regional bezirk
				id = myDialog.getSelectedBezirk();
			}
			RetrieveMastenFromGemeindeServiceAsync mastenRetriever = GWT.create(RetrieveMastenFromGemeindeService.class);
			mastenRetriever.retrieveMastFromGemeinde(id, selectedTab, new MastListFromGemeindeCallBack());	
		}
		myDialog=null;
	}
	
	private void createStationSucheDialog(){
		// Instantiate the dialog box and show it.
		BrowseStationDialog_MastApp myDialog = new BrowseStationDialog_MastApp(this);

		myDialog.center();
		myDialog.show();
	}

	private void createMenuBar(){
		// Make some sub-menus that we will cascade from the top menu.
		Command browseStationCommand = new Command() {
			@Override
			public void execute() {
				//load Station Form Panel
				createStationSucheDialog();
			}
		};
		
		Command closeAppCommand = new Command() {
			
			@Override
			public void execute() {
				// TODO Auto-generated method stub
				mainFrame.loadStartPanel();
			}
		};
		
		// Make a new menu bar, adding a few cascading menus to it.
		this.menu = new MenuBar();
		menu.addItem(AbstractImagePrototype.create(AppBundle.INSTANCE.exploreImage()).getSafeHtml(), browseStationCommand);
		menu.addItem(AbstractImagePrototype.create(AppBundle.INSTANCE.logoutImage()).getSafeHtml(), closeAppCommand);
	}
	
	public MastAppMainFrame(OnlineGIS2 mainFrame) {

		this.mainFrame = mainFrame;
		
		listOfLayers = new ArrayList<>();

		masteStyles = new MastenStyles();
		
		mastList = new ArrayList<>();
		featureList = new ArrayList<>();

		createMap();
		createMenuBar();
 
		StackLayoutPanel informationPanel = new StackLayoutPanel(Unit.PX);
		informationPanel.setWidth("400PX");
		informationPanel.add(new ScrollPanel(new Label("Info")), "Information zur Selection", false, 40);

		StackLayoutPanel workbenchPanel = new StackLayoutPanel(Unit.PX);
		workbenchPanel.add(mapPanel, "Arbeitsplatz", false, 40);

		mainPanel = new DockLayoutPanel(Unit.PCT);
		mainPanel.addNorth(this.menu, 5);
		mainPanel.addSouth(new SimplePanel(), 2);
		mainPanel.addEast(informationPanel, 15);
		mainPanel.add(workbenchPanel);
	}
	
	
	/* GETTER */
	public DockLayoutPanel				getMainPanel(){return this.mainPanel;}
}
