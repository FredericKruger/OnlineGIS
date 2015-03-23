package de.netze.onlinegis.client.auskunftsystem.gui.dialogs;

import java.util.ArrayList;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.netze.onlinegis.client.auskunftsystem.AuskunftSystemMainFrame;
import de.netze.onlinegis.client.auskunftsystem.retriever.RetrieveStationService;
import de.netze.onlinegis.client.auskunftsystem.retriever.RetrieveStationServiceAsync;
import de.netze.onlinegis.client.common.retriever.RetrieveGemeindeService;
import de.netze.onlinegis.client.common.retriever.RetrieveGemeindeServiceAsync;
import de.netze.onlinegis.shared.auskunftsystem.networkelements.SimpleStation;
import de.netze.onlinegis.shared.common.networkelements.SimpleGemeinde;

public class BrowseStationDialog_AuskunftSystem extends DialogBox {

	private final AuskunftSystemMainFrame mainFrame;
	
	private final ListBox gemeindelistBox;
	private final ListBox stationlistBox;
	
	private ArrayList<SimpleGemeinde> listOfGemeinde;
	private ArrayList<SimpleStation> listOfStation;
	
	private ArrayList<SimpleStation> selectedStation = new ArrayList<>();

	private class GemeindeListCallBack implements AsyncCallback<ArrayList<SimpleGemeinde>>{

		@Override
		public void onFailure(Throwable caught){
			Window.alert("Unauble to obtain server response: " + caught.getMessage());
		}

		@Override
		public void onSuccess(ArrayList<SimpleGemeinde> list){
			listOfGemeinde = list;
			gemeindelistBox.clear();

			for(int i=0; i<list.size(); i++){
				gemeindelistBox.addItem(list.get(i).getName());
			}
			
			if(!listOfGemeinde.isEmpty()){
				gemeindelistBox.setSelectedIndex(0);
				RetrieveStationServiceAsync stationRetriever = GWT.create(RetrieveStationService.class);
				stationRetriever.retrieveStation(listOfGemeinde.get(0).getID(), new StationListCallBack());
			}
		}
	}
	
	private class StationListCallBack implements AsyncCallback<ArrayList<SimpleStation>>{

		@Override
		public void onFailure(Throwable caught){
			Window.alert("Unauble to obtain server response: " + caught.getMessage());
		}

		@Override
		public void onSuccess(ArrayList<SimpleStation> list){
			listOfStation = list;
			stationlistBox.clear();

			for(int i=0; i<list.size(); i++){
				stationlistBox.addItem(list.get(i).getID() + " " + list.get(i).getName());
			}
		}
	}

	public BrowseStationDialog_AuskunftSystem(final AuskunftSystemMainFrame mainFrame) {
		this.mainFrame = mainFrame;
		
		// Set the dialog box's caption.
		setText("Choose gemeinde");

		// Enable animation.
		setAnimationEnabled(true);

		// Enable glass background.
		setGlassEnabled(true);

		//create a widget to 
		DockLayoutPanel textBoxPanel = new DockLayoutPanel(Unit.PX);
		DockLayoutPanel gemeindeListPanel = new DockLayoutPanel(Unit.PX);
		DockLayoutPanel stationListPanel = new DockLayoutPanel(Unit.PX);

		final TextBox textBox = new TextBox();
		textBox.getElement().getStyle().setWidth(175, Unit.PX);
		textBox.setName("Gemeinde");
		textBox.addValueChangeHandler(new ValueChangeHandler<String>() {
			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				RetrieveGemeindeServiceAsync gemeindeRetriever = GWT.create(RetrieveGemeindeService.class);
				gemeindeRetriever.retrieveGemeinde(textBox.getText(), new GemeindeListCallBack());
			}
		});
		textBoxPanel.setSize("350PX", "30PX");
		textBoxPanel.addWest(new Label("Gemeinde Name: "), 125);
		textBoxPanel.add(textBox);

		//Create a listbox
		gemeindelistBox = new ListBox();
		gemeindelistBox.setName("Gemeinde ListBox");
		gemeindelistBox.addChangeHandler(new ChangeHandler()
		{
			public void onChange(ChangeEvent event)
			{
				int selectedIndex = gemeindelistBox.getSelectedIndex();
				if (selectedIndex > -1){
					System.out.println(selectedIndex);
					RetrieveStationServiceAsync stationRetriever = GWT.create(RetrieveStationService.class);
					stationRetriever.retrieveStation(listOfGemeinde.get(selectedIndex).getID(), new StationListCallBack());
				}
			}
		});
		gemeindelistBox.getElement().getStyle().setWidth(175, Unit.PX);
		gemeindeListPanel.setSize("350PX", "30PX");
		gemeindeListPanel.addWest(new Label("Gemeinde List: "), 125);
		gemeindeListPanel.add(gemeindelistBox);
		
		stationlistBox = new ListBox();
		stationlistBox.setName("Station ListBox");
		stationlistBox.setMultipleSelect(true);
		stationlistBox.setVisibleItemCount(10);
		stationlistBox.getElement().getStyle().setWidth(175, Unit.PX);
		stationListPanel.setSize("350PX", "100PX");
		stationListPanel.addWest(new Label("Station List: "), 125);
		stationListPanel.add(stationlistBox);


		// DialogBox is a SimplePanel, so you have to set its widget 
		// property to whatever you want its contents to be.
		Button ok = new Button("Laden");
		ok.setStyleName("buttonPadding");
		ok.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				selectedStation.clear();
				for(int i=0; i<stationlistBox.getItemCount(); i++){
					if(stationlistBox.isItemSelected(i)){
						selectedStation.add(listOfStation.get(i));
					}
				}
				
				BrowseStationDialog_AuskunftSystem.this.hide();
				mainFrame.treatStations(BrowseStationDialog_AuskunftSystem.this, true);
			}
		});
		
		Button cancelButton = new Button("Abbrechen");
		cancelButton.setStyleName("buttonPadding");
		cancelButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				BrowseStationDialog_AuskunftSystem.this.hide();
				mainFrame.treatStations(BrowseStationDialog_AuskunftSystem.this, false);
			}
		});
		
		HorizontalPanel buttonPanel = new HorizontalPanel();
		buttonPanel.add(ok);
		buttonPanel.add(cancelButton);
		
		VerticalPanel panel = new VerticalPanel();
		panel.setPixelSize(400, 300);
		panel.setSpacing(10);
		panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);

		panel.add(textBoxPanel);
		panel.add(gemeindeListPanel);
		panel.add(stationListPanel);
		panel.add(buttonPanel);

		setWidget(panel);
	}
	
	public ArrayList<SimpleStation> getSelectedStations(){return this.selectedStation;}
	public String 					getSelectedGemeinde(){return this.listOfGemeinde.get(this.gemeindelistBox.getSelectedIndex()).getName();}
}
