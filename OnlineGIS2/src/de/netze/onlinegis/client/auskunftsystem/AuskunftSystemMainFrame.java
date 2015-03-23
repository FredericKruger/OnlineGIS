package de.netze.onlinegis.client.auskunftsystem;

import java.util.ArrayList;
import java.util.List;

import org.gwtopenmaps.openlayers.client.LonLat;
import org.gwtopenmaps.openlayers.client.Map;
import org.gwtopenmaps.openlayers.client.MapOptions;
import org.gwtopenmaps.openlayers.client.MapWidget;
import org.gwtopenmaps.openlayers.client.Projection;
import org.gwtopenmaps.openlayers.client.control.LayerSwitcher;
import org.gwtopenmaps.openlayers.client.control.OverviewMap;
import org.gwtopenmaps.openlayers.client.control.ScaleLine;
import org.gwtopenmaps.openlayers.client.control.SelectFeature;
import org.gwtopenmaps.openlayers.client.event.VectorFeatureSelectedListener;
import org.gwtopenmaps.openlayers.client.feature.VectorFeature;
import org.gwtopenmaps.openlayers.client.geometry.LineString;
import org.gwtopenmaps.openlayers.client.geometry.LinearRing;
import org.gwtopenmaps.openlayers.client.geometry.Point;
import org.gwtopenmaps.openlayers.client.geometry.Polygon;
import org.gwtopenmaps.openlayers.client.layer.GoogleV3;
import org.gwtopenmaps.openlayers.client.layer.GoogleV3MapType;
import org.gwtopenmaps.openlayers.client.layer.GoogleV3Options;
import org.gwtopenmaps.openlayers.client.layer.OSM;
import org.gwtopenmaps.openlayers.client.layer.TransitionEffect;
import org.gwtopenmaps.openlayers.client.layer.Vector;
import org.gwtopenmaps.openlayers.client.layer.WMS;
import org.gwtopenmaps.openlayers.client.layer.WMSOptions;
import org.gwtopenmaps.openlayers.client.layer.WMSParams;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.cellview.client.CellTree;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.StackLayoutPanel;

import de.netze.onlinegis.client.OnlineGIS2;
import de.netze.onlinegis.client.auskunftsystem.gui.dialogs.BrowseStationDialog_AuskunftSystem;
import de.netze.onlinegis.client.auskunftsystem.gui.elements.NetworkStyles;
import de.netze.onlinegis.client.auskunftsystem.gui.elements.StationTreeModel;
import de.netze.onlinegis.client.auskunftsystem.retriever.RetrieveElectricNetworkService;
import de.netze.onlinegis.client.auskunftsystem.retriever.RetrieveElectricNetworkServiceAsync;
import de.netze.onlinegis.shared.auskunftsystem.networkelements.Arc;
import de.netze.onlinegis.shared.auskunftsystem.networkelements.ElectricNetwork;
import de.netze.onlinegis.shared.auskunftsystem.networkelements.Node;
import de.netze.onlinegis.shared.auskunftsystem.networkelements.Node_VKS_Flavour;
import de.netze.onlinegis.shared.auskunftsystem.networkelements.SimpleStation;

public class AuskunftSystemMainFrame{

	private DockLayoutPanel mapPanel;
	private DockLayoutPanel mainPanel;

	private MenuBar menu;

	private Map map;
	private MapWidget mapWidget;

	private ArrayList<ElectricNetwork> listOfNetworks;

	private ArrayList<Vector> listOfLayers;
	
	private ArrayList<Node> nodeList;
	private ArrayList<VectorFeature> featureList;

	private NetworkStyles networkStyle;
	
	private StationTreeModel stationGemeindeTreeModel;
	
	private OnlineGIS2 mainFrame;
	
	private class StationListCallBack implements AsyncCallback<ArrayList<ElectricNetwork>>{

		@Override
		public void onFailure(Throwable caught){
			Window.alert("Unauble to obtain server response: " + caught.getMessage());
		}

		@Override
		public void onSuccess(ArrayList<ElectricNetwork> list){
			listOfNetworks = list;
			displayNetwork();
			stationGemeindeTreeModel.clear();
			stationGemeindeTreeModel.updateModel();
		}
	}

	public void displayNetwork(){
		//Remove all Layers
		for(int i=0; i<listOfLayers.size(); i++){
			mapWidget.getMap().removeLayer(listOfLayers.get(i));
		}
		listOfLayers = new ArrayList<Vector>();
		
		//reinitialize hashmaps
		nodeList = new ArrayList<>();
		featureList = new ArrayList<>();

		//go through all the network
		for(int i=0; i<listOfNetworks.size(); i++){
			String layerName = listOfNetworks.get(i).getCompleteStation().getID() + " " + listOfNetworks.get(i).getCompleteStation().getName();
			final Vector vectorLayer = new Vector(layerName);
			listOfLayers.add(vectorLayer);

			//draw Area of Station
			if(!listOfNetworks.get(i).getCompleteStation().getAreaCoordinates().isEmpty()){
				List<Point> pointList = new ArrayList<Point>();
				for(int k=0; k<listOfNetworks.get(i).getCompleteStation().getAreaCoordinates().size(); k++){
					LonLat lonLat = new LonLat(listOfNetworks.get(i).getCompleteStation().getAreaCoordinates().get(k).getLongitude(),
							listOfNetworks.get(i).getCompleteStation().getAreaCoordinates().get(k).getLatitude());
					lonLat.transform("EPSG:4326","EPSG:3857");
					Point p = new Point(lonLat.lon(), lonLat.lat());
					pointList.add(p);
				}
				LinearRing[] ring = new LinearRing[1];
				ring[0] = new LinearRing(pointList.toArray(new Point[pointList.size()]));
				Polygon p = new Polygon(ring);
				VectorFeature lineFeature = new VectorFeature(p, 
						networkStyle.getStationAreaStyle());
				vectorLayer.addFeature(lineFeature);
			}


			//draw kabel
			for(int j=0; j<listOfNetworks.get(i).getListOfArcs().size(); j++){
				if(listOfNetworks.get(i).getListOfArcs().get(j).getType()!=Arc.INTERNAL){
					List<Point> pointList = new ArrayList<Point>();

					for(int k=0; k<listOfNetworks.get(i).getListOfArcs().get(j).getLine().size(); k++){
						LonLat lonLat = new LonLat(listOfNetworks.get(i).getListOfArcs().get(j).getLine().get(k).getLongitude(),
								listOfNetworks.get(i).getListOfArcs().get(j).getLine().get(k).getLatitude());
						lonLat.transform("EPSG:4326","EPSG:3857");
						Point p = new Point(lonLat.lon(), lonLat.lat());
						pointList.add(p);
					}

					LineString l = new LineString(pointList.toArray(new Point[pointList.size()]));
					VectorFeature lineFeature = new VectorFeature(l, 
							networkStyle.getStyleFromArc(listOfNetworks.get(i).getListOfArcs().get(j).getIsFreileitung(),
									listOfNetworks.get(i).getListOfArcs().get(j).getType()));
					vectorLayer.addFeature(lineFeature);
					
					//add the the maps
				}
			}

			//draw Nodes
			for(int j=0; j<listOfNetworks.get(i).getListOfNodes().size(); j++){
				if(listOfNetworks.get(i).getListOfNodes().get(j).getTypeID()!=Node.BAUTEILE
						&& listOfNetworks.get(i).getListOfNodes().get(j).getTypeID()!=Node.SCHALTER){
					if(listOfNetworks.get(i).getListOfNodes().get(j).getTypeID()==Node.VERTEILKASTEN
							&& (!((Node_VKS_Flavour)listOfNetworks.get(i).getListOfNodes().get(j)).getAreaCoordinates().isEmpty())){
						List<Point> pointList = new ArrayList<Point>();
						Node_VKS_Flavour node = (Node_VKS_Flavour)listOfNetworks.get(i).getListOfNodes().get(j);
						for(int k=0; k<node.getAreaCoordinates().size(); k++){
							LonLat lonLat = new LonLat(node.getAreaCoordinates().get(k).getLongitude(),
									node.getAreaCoordinates().get(k).getLatitude());
							lonLat.transform("EPSG:4326","EPSG:3857");
							Point p = new Point(lonLat.lon(), lonLat.lat());
							pointList.add(p);
						}
						LinearRing[] ring = new LinearRing[1];
						ring[0] = new LinearRing(pointList.toArray(new Point[pointList.size()]));
						Polygon p = new Polygon(ring);
						VectorFeature lineFeature = new VectorFeature(p, 
								networkStyle.getStyleFromNode(listOfNetworks.get(i).getListOfNodes().get(j).getTypeID()));
						vectorLayer.addFeature(lineFeature);
					}
					else{
						LonLat lonLat = new LonLat(listOfNetworks.get(i).getListOfNodes().get(j).getCoordinates().getLongitude(),
								listOfNetworks.get(i).getListOfNodes().get(j).getCoordinates().getLatitude());
						lonLat.transform("EPSG:4326","EPSG:3857");
						Point p = new Point(lonLat.lon(), lonLat.lat());

						VectorFeature pointFeature = new VectorFeature(p, 
								networkStyle.getStyleFromNode(listOfNetworks.get(i).getListOfNodes().get(j).getTypeID()));

						vectorLayer.addFeature(pointFeature);
												
						//add to hashmaps
						nodeList.add(listOfNetworks.get(i).getListOfNodes().get(j));
						featureList.add(pointFeature);
						
					}
					
				} 
				
			}
			
			LonLat slonLat = new LonLat(listOfNetworks.get(i).getCompleteStation().getCoordinates().getLongitude(),
					listOfNetworks.get(i).getCompleteStation().getCoordinates().getLatitude());
			slonLat.transform("EPSG:4326","EPSG:3857");
			Point ps = new Point(slonLat.lon(), slonLat.lat());
			VectorFeature pointFeatures = new VectorFeature(ps, networkStyle.getStationStyle());
			vectorLayer.addFeature(pointFeatures);
			
			//Window.alert("Test:" + pointFeatures.getFID() + " " + pointFeatures.getFeatureId());

			final SelectFeature selectFeature = new SelectFeature(vectorLayer);
			selectFeature.setAutoActivate(true);
			map.addControl(selectFeature);

			vectorLayer.addVectorFeatureSelectedListener(new VectorFeatureSelectedListener() {
				public void onFeatureSelected(FeatureSelectedEvent eventObject) {
					//Window.alert(eventObject.getVectorFeature().getFID() + " " + eventObject.getVectorFeature().getFeatureId());
				
					if(browseThroughFeatures(eventObject.getVectorFeature())>-1){
						Window.alert("Found feature in List");
					}
	                selectFeature.unSelect(eventObject.getVectorFeature());
				}
			});

			mapWidget.getMap().addLayer(vectorLayer);
			mapWidget.getMap().zoomToExtent(vectorLayer.getDataExtent());
		}
	}

	private int browseThroughFeatures(VectorFeature e){
		for(int i=0; i<featureList.size(); i++){
			if(e.getFeatureId().compareTo(featureList.get(i).getFeatureId())==0) return i;
		}
		return -1;
	}
	
	public void treatStations(BrowseStationDialog_AuskunftSystem myDialog, boolean ok){
		if(ok){
			ArrayList<SimpleStation> selectStations = myDialog.getSelectedStations();
			String gemeinde = myDialog.getSelectedGemeinde();
			

			int[] listOfID = new int[selectStations.size()];
			for(int i=0; i<selectStations.size(); i++){
				listOfID[i] = selectStations.get(i).getID();
			}

			RetrieveElectricNetworkServiceAsync stationRetriever = GWT.create(RetrieveElectricNetworkService.class);
			stationRetriever.retrieveCompleteStation(listOfID, gemeinde, new StationListCallBack());
		}
		myDialog = null;
	}

	private void createMap(){
		MapOptions defaultMapOptions = new MapOptions();
		defaultMapOptions.setProjection("EPSG:3857");
		defaultMapOptions.setDisplayProjection(new Projection("EPSG:3857"));

		//Create a MapWidget and add 2 OSM layers
		mapWidget = new MapWidget("100%", "100%", defaultMapOptions);
	
		OSM osm_1 = OSM.Mapnik("Mapnik");
		OSM osm_2 = OSM.CycleMap("CycleMap");
		osm_1.setIsBaseLayer(true);
		osm_2.setIsBaseLayer(true);

		map = mapWidget.getMap();
		
		map.addLayer(osm_1);
		map.addLayer(osm_2);
		
		/** CREATE GOOGLE LAYER **/
        //Create some Google Layers
        /*GoogleV3Options gHybridOptions = new GoogleV3Options();
        gHybridOptions.setIsBaseLayer(true);
        gHybridOptions.setType(GoogleV3MapType.G_HYBRID_MAP);
        GoogleV3 gHybrid = new GoogleV3("Google Hybrid", gHybridOptions);
 
        GoogleV3Options gNormalOptions = new GoogleV3Options();
        gNormalOptions.setIsBaseLayer(true);
        gNormalOptions.setType(GoogleV3MapType.G_NORMAL_MAP);
        GoogleV3 gNormal = new GoogleV3("Google Normal", gNormalOptions);
 
        GoogleV3Options gSatelliteOptions = new GoogleV3Options();
        gSatelliteOptions.setIsBaseLayer(true);
        gSatelliteOptions.setType(GoogleV3MapType.G_SATELLITE_MAP);
        GoogleV3 gSatellite = new GoogleV3("Google Satellite", gSatelliteOptions);
 
        GoogleV3Options gTerrainOptions = new GoogleV3Options();
        gTerrainOptions.setIsBaseLayer(true);
        gTerrainOptions.setType(GoogleV3MapType.G_TERRAIN_MAP);
        GoogleV3 gTerrain = new GoogleV3("Google Terrain", gTerrainOptions);
        
		map.addLayer(gHybrid);
		map.addLayer(gNormal);
		map.addLayer(gSatellite);
		map.addLayer(gTerrain);*/
		
		/*** TEST WITH WMS LAYER ***/
		 //Create a WMS layer as base layer
	    WMSParams wmsParamsPegel = new WMSParams();
	    wmsParamsPegel.setFormat("image/png");
	    wmsParamsPegel.setLayers("PegelonlineWMS,Pegelpunkte");//
	    wmsParamsPegel.setStyles("");
	    wmsParamsPegel.setParameter("transparent", "true");
	    
	    WMSParams wmsParamsBiotop = new WMSParams();
	    wmsParamsBiotop.setFormat("image/png");
	    wmsParamsBiotop.setLayers("0,1");
	    wmsParamsBiotop.setStyles("");
	    wmsParamsBiotop.setParameter("transparent", "true");
	    //wmsParamsBiotop.setParameter("legendURL","http://rips-dienste.lubw.baden-wuerttemberg.de/arcgis/services/wms/solareEffizienz_neu/MapServer/WMSServer?request=GetLegendGraphic&version=1.3.0&format=image/png&layer=1");

	    //create a WMS layer
	    WMSOptions wmsLayerParamsPegel = new WMSOptions();
	    wmsLayerParamsPegel.setTransitionEffect(TransitionEffect.RESIZE);
	    wmsLayerParamsPegel.setIsBaseLayer(false);
	    wmsLayerParamsPegel.setDisplayOutsideMaxExtent(true);
	    wmsLayerParamsPegel.setProjection("EPSG:3857");
	    
	    WMSOptions wmsLayerParamsBiotop = new WMSOptions();
	    wmsLayerParamsBiotop.setTransitionEffect(TransitionEffect.RESIZE);
	    wmsLayerParamsBiotop.setIsBaseLayer(false);
	    wmsLayerParamsBiotop.setDisplayOutsideMaxExtent(true);
	    wmsLayerParamsBiotop.setProjection("EPSG:3857");
	    
	    String wmsUrlPegel = "http://www.pegelonline.wsv.de/webservices/gis/wms/aktuell/nswhsw";
	    String wmsUrlBiotop = "http://rips-gdi.lubw.baden-wuerttemberg.de/arcgis/services/wms/solareinstrahlung_wms/MapServer/WMSServer";
	    String wmsUrlSolarEffizienz = "http://rips-dienste.lubw.baden-wuerttemberg.de/arcgis/services/wms/solareEffizienz_neu/MapServer/WMSServer";

	    final WMS wmsLayerPegel = new WMS("Pegel", wmsUrlPegel, wmsParamsPegel,
	    		wmsLayerParamsPegel);
	    wmsLayerPegel.setOpacity((float) 0.9);	
	    wmsLayerPegel.setSingleTile(false);
	    wmsLayerPegel.setIsVisible(false);
	    
	    final WMS wmsLayerBiotop = new WMS("Solareinstrahlung", wmsUrlBiotop, wmsParamsBiotop,
	    		wmsLayerParamsBiotop);
	    wmsLayerBiotop.setOpacity((float) 0.5);
	    wmsLayerBiotop.setSingleTile(false);
	    wmsLayerBiotop.setIsVisible(false);
	    
	    final WMS wmsLayerEffizienz = new WMS("Solarpotential", wmsUrlSolarEffizienz, wmsParamsBiotop,
	    		wmsLayerParamsBiotop);
	    wmsLayerEffizienz.setOpacity((float) 0.5);
	    wmsLayerEffizienz.setSingleTile(true);
	    wmsLayerEffizienz.setIsVisible(false);
	    
	    map.addLayer(wmsLayerPegel);
	    map.addLayer(wmsLayerBiotop);
	    map.addLayer(wmsLayerEffizienz);
	    
		/*** TEST WITH WMS LAYER ***/

		//Lets add some default controls to the map
		map.addControl(new LayerSwitcher()); //+ sign in the upperright corner to display the layer switcher
		map.addControl(new OverviewMap()); //+ sign in the lowerright to display the overviewmap
		map.addControl(new ScaleLine()); //Display the scaleline

		//Center and zoom to a location
		LonLat lonLat = new LonLat(6.95, 50.94);
		lonLat.transform("EPSG:4326","EPSG:3857"); //transform lonlat to OSM coordinate system
		//lonLat.transform("EPSG:4326","EPSG:31467"); //transform lonlat to OSM coordinate system
		mapWidget.getMap().setCenter(lonLat, 5);
		map.setMinMaxZoomLevel(3, 22);

		mapPanel = new DockLayoutPanel(Unit.PCT);
		mapPanel.setHeight("100%");
		mapPanel.setWidth("100%");
		//mapPanel.add(mapWidget);
	}
	
	//crs=EPSG:31467&dpiMode=7&featureCount=10&format=image/jpeg&layers=0&styles=default&url=http://rips-dienste.lubw.baden-wuerttemberg.de/arcgis/services/wms/solareEffizienz_neu/MapServer/WMSServer

	private void createStationScheDialog(){
		// Instantiate the dialog box and show it.
		BrowseStationDialog_AuskunftSystem myDialog = new BrowseStationDialog_AuskunftSystem(this);

		myDialog.center();
		myDialog.show();
	}

	private void createMenuBar(){
		// Make some sub-menus that we will cascade from the top menu.
		Command browseStationCommand = new Command() {
			@Override
			public void execute() {
				//load Station Form Panel
				createStationScheDialog();
			}
		};
		
		Command closeAppCommand = new Command() {
			
			@Override
			public void execute() {
				// TODO Auto-generated method stub
				mainFrame.loadStartPanel();
			}
		};

		MenuBar fileMenu = new MenuBar(true);
		fileMenu.addItem("Station Suchen", browseStationCommand);
		//fileMenu.addItem("NETZlabor suchen", );
		fileMenu.addItem("Schliessen", closeAppCommand);

		// Make a new menu bar, adding a few cascading menus to it.
		this.menu = new MenuBar();
		menu.addItem("Datei", fileMenu);
	}
	
	public AuskunftSystemMainFrame(OnlineGIS2 mainFrame) {

		this.mainFrame = mainFrame;
		
		listOfLayers = new ArrayList<>();

		networkStyle = new NetworkStyles();
		
		nodeList = new ArrayList<>();
		featureList = new ArrayList<>();

		createMap();
		createMenuBar();

		// Create a model for the tree.
		stationGemeindeTreeModel = new StationTreeModel(this);
	    
	    CellTree tree = new CellTree(stationGemeindeTreeModel, null);
	    
		StackLayoutPanel informationPanel = new StackLayoutPanel(Unit.PX);
		informationPanel.setWidth("400PX");
		informationPanel.add(new ScrollPanel(new Label("Info")), "Information zur Selection", false, 40);
		
		StackLayoutPanel stationListPanel = new StackLayoutPanel(Unit.PX);
		stationListPanel.setWidth("400PX");
		stationListPanel.add(new ScrollPanel(tree), "Station Liste", false, 40);		
		
		StackLayoutPanel workbenchPanel = new StackLayoutPanel(Unit.PX);
		workbenchPanel.add(mapWidget, "Arbeitsplatz", false, 40);

		mainPanel = new DockLayoutPanel(Unit.PCT);
		mainPanel.addNorth(this.menu, 4);
		mainPanel.addSouth(new SimplePanel(), 2);
		mainPanel.addWest(stationListPanel, 15);
		mainPanel.addEast(informationPanel, 15);
		mainPanel.add(workbenchPanel);
	}
	
	
	/* GETTER */
	public DockLayoutPanel				getMainPanel(){return this.mainPanel;}
	public ArrayList<ElectricNetwork> 	getListOfNetworks(){return this.listOfNetworks;}
}
