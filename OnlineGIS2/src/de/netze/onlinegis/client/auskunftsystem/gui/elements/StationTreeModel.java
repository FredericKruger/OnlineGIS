package de.netze.onlinegis.client.auskunftsystem.gui.elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.TreeViewModel;

import de.netze.onlinegis.client.OnlineGIS2;
import de.netze.onlinegis.client.auskunftsystem.AuskunftSystemMainFrame;
import de.netze.onlinegis.shared.auskunftsystem.networkelements.Node;
import de.netze.onlinegis.shared.auskunftsystem.networkelements.Node_Hausanschluss_Flavour;
import de.netze.onlinegis.shared.auskunftsystem.networkelements.Node_Ruckeinspeisung_Flavour;

public class StationTreeModel implements TreeViewModel{

	private AuskunftSystemMainFrame mainFrame;

	private ListDataProvider<GemeindeModel> listOfGemeinde;

	public StationTreeModel(AuskunftSystemMainFrame mainFrame){
		super();

		this.mainFrame = mainFrame;

		this.listOfGemeinde = new ListDataProvider<GemeindeModel>();
	}

	public void updateModel(){
		for(int i=0; i<mainFrame.getListOfNetworks().size(); i++){
			GemeindeModel g = new GemeindeModel(mainFrame.getListOfNetworks().get(i).getGemeindeName());
			if(!listOfGemeinde.getList().contains(g)){
				listOfGemeinde.getList().add(g);
			}
			int gemeindeIndex = listOfGemeinde.getList().indexOf(g);
			StationModel station = new StationModel(mainFrame.getListOfNetworks().get(i).getCompleteStation().getName());
			if(!listOfGemeinde.getList().get(gemeindeIndex).getListOfStations().contains(station)){
				station = listOfGemeinde.getList().get(gemeindeIndex).addStation(station);
			
				//add nodes
				for(int j=0; j<mainFrame.getListOfNetworks().get(i).getListOfNodes().size(); j++){
					if(mainFrame.getListOfNetworks().get(i).getListOfNodes().get(j).getTypeID()==Node.HAUSANSCHLUSS){
						station.addHaussanschluss(new HausanschlussModel(((Node_Hausanschluss_Flavour)mainFrame.getListOfNetworks().get(i).getListOfNodes().get(j)).getObjID(), 
								mainFrame.getListOfNetworks().get(i).getListOfNodes().get(j).getID()));
					}
					if(mainFrame.getListOfNetworks().get(i).getListOfNodes().get(j).getTypeID()==Node.RUCKEINSPEISUNG){
						int hid = ((Node_Ruckeinspeisung_Flavour)mainFrame.getListOfNetworks().get(i).getListOfNodes().get(j)).getHausanschlussID();
						String name = ((Node_Ruckeinspeisung_Flavour)mainFrame.getListOfNetworks().get(i).getListOfNodes().get(j)).getObjID();
						HausanschlussModel ha = new HausanschlussModel("", hid);
						int index = station.getHausAnschluss().indexOf(ha);
						station.getHausAnschluss().get(index).addRuckeinspeisung(new RuckeinspeisungModel(name));
					}
				}
			}
		}
	}

	/**
	 * Get the {@link NodeInfo} that provides the children of the specified
	 * value.
	 */
	public <T> NodeInfo<?> getNodeInfo(T value) {
		if (value == null) {
			// LEVEL 0 => ROOT.
			// We passed null as the root value. Return the composers.

			Cell<GemeindeModel> cell = new AbstractCell<GemeindeModel>() {
				@Override
				public void render(
						com.google.gwt.cell.client.Cell.Context context,
						GemeindeModel value, SafeHtmlBuilder sb) {

					if (value != null) {
						//sb.appendHtmlConstant("    "); 
						sb.appendEscaped(value.getName());
					}	
				}
			};
			return new DefaultNodeInfo<GemeindeModel>(listOfGemeinde, cell);
		}
		else if(value instanceof GemeindeModel){
			//LEVEL 1 => STATION
			ListDataProvider<StationModel> dataProvider = new ListDataProvider<StationModel>(((GemeindeModel) value).getListOfStations());
			
			Cell<StationModel> cell = new AbstractCell<StationModel>() {
				@Override
				public void render(
						com.google.gwt.cell.client.Cell.Context context,
						StationModel value, SafeHtmlBuilder sb) {

					if (value != null) {
						//sb.appendHtmlConstant("    "); 
						sb.appendEscaped(value.getName());
					}	
				}
			};
			return new DefaultNodeInfo<StationModel>(dataProvider, cell);
		}
		else if(value instanceof StationModel){
			//LEVEL 2 => HAUSANSCHLUSS
			ListDataProvider<HausanschlussModel> dataProvider = new ListDataProvider<HausanschlussModel>(((StationModel) value).getHausAnschluss());
			
			Cell<HausanschlussModel> cell = new AbstractCell<HausanschlussModel>() {
				@Override
				public void render(
						com.google.gwt.cell.client.Cell.Context context,
						HausanschlussModel value, SafeHtmlBuilder sb) {

					if (value != null) {
						//sb.appendHtmlConstant("    "); 
						sb.appendEscaped(value.getName());
					}	
				}
			};
			return new DefaultNodeInfo<HausanschlussModel>(dataProvider, cell);
		}
		else if(value instanceof HausanschlussModel){
			//LEVEL 3 => Einspeisung
			ListDataProvider<RuckeinspeisungModel> dataProvider = new ListDataProvider<RuckeinspeisungModel>(((HausanschlussModel) value).getPlaylists());
			
			Cell<RuckeinspeisungModel> cell = new AbstractCell<RuckeinspeisungModel>() {
				@Override
				public void render(
						com.google.gwt.cell.client.Cell.Context context,
						RuckeinspeisungModel value, SafeHtmlBuilder sb) {

					if (value != null) {
						//sb.appendHtmlConstant("    "); 
						sb.appendEscaped(value.getName());
					}	
				}
			};
			return new DefaultNodeInfo<RuckeinspeisungModel>(dataProvider, cell);
		}
		else
			return null;
	}

	/**
	 * Check if the specified value represents a leaf node. Leaf nodes cannot be
	 * opened.
	 */
	public boolean isLeaf(Object value) {
		// The maximum length of a value is ten characters.
		return false;
	}
	
	public void clear(){
		this.listOfGemeinde.getList().clear();
	}

	private static class GemeindeModel{
		private final String name;
		private final List<StationModel> playlists = new ArrayList<StationModel>();

		public GemeindeModel(String name) {
			this.name = name;
		}

		/**
		 * Add a playlist to the composer.
		 * 
		 * @param playlist the playlist to add
		 */
		public StationModel addStation(StationModel playlist) {   
			playlists.add(playlist);
			return playlist;
		}

		public String getName() {
			return name;
		}

		/**
		 * Return the rockin' playlist for this composer.
		 */
		public List<StationModel> getListOfStations() {
			return playlists;
		}

		@Override 
		public boolean equals(Object o) {
			// TODO Auto-generated method stub
			return ((GemeindeModel)o).getName().compareTo(getName())==0;
		}
	}

	private static class StationModel{
		private final String name;
		private final List<HausanschlussModel> listOfHausanschluss = new ArrayList<HausanschlussModel>();

		public StationModel(String name) {
			this.name = name;
		}

		/**
		 * Add a playlist to the composer.
		 * 
		 * @param playlist the playlist to add
		 */
		public HausanschlussModel addHaussanschluss(HausanschlussModel playlist) {   
			listOfHausanschluss.add(playlist);
			return playlist;
		}

		public String getName() {
			return name;
		}

		/**
		 * Return the rockin' playlist for this composer.
		 */
		public List<HausanschlussModel> getHausAnschluss() {
			return listOfHausanschluss;
		}

		@Override
		public boolean equals(Object o) {
			// TODO Auto-generated method stub
			return ((StationModel) o).getName().compareTo(getName())==0;
		}
	}

	private static class HausanschlussModel{
		private final String name;
		private final int id;
		private final List<RuckeinspeisungModel> listOfRuckeinspeisung = new ArrayList<RuckeinspeisungModel>();

		public HausanschlussModel(String name, int id) {
			this.name = name;
			this.id = id;
		}

		public RuckeinspeisungModel addRuckeinspeisung(RuckeinspeisungModel ruckeinspeisung) {   
			listOfRuckeinspeisung.add(ruckeinspeisung);
			return ruckeinspeisung;
		}

		public String getName() {return name;}
		public int getID(){return this.id;}
		public List<RuckeinspeisungModel> getPlaylists() {return listOfRuckeinspeisung;}
		
		@Override
		public boolean equals(Object o){
			return ((HausanschlussModel)o).getID()==getID();			
		}
	}

	private static class RuckeinspeisungModel{
		private final String name;

		public RuckeinspeisungModel(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}
	}

}
