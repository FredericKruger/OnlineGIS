package de.netze.onlinegis.client.mastapp.gui.dialogs;

import java.util.ArrayList;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SingleSelectionModel;

import de.netze.onlinegis.client.common.retriever.RetrieveGemeindeService;
import de.netze.onlinegis.client.common.retriever.RetrieveGemeindeServiceAsync;
import de.netze.onlinegis.client.mastapp.MastAppMainFrame;
import de.netze.onlinegis.client.mastapp.retriever.RetrieveBezirkService;
import de.netze.onlinegis.client.mastapp.retriever.RetrieveBezirkServiceAsync;
import de.netze.onlinegis.client.mastapp.retriever.RetrieveRegionalZentrumService;
import de.netze.onlinegis.client.mastapp.retriever.RetrieveRegionalZentrumServiceAsync;
import de.netze.onlinegis.shared.common.networkelements.SimpleGemeinde;
import de.netze.onlinegis.shared.mastapp.networkelements.Bezirk;
import de.netze.onlinegis.shared.mastapp.networkelements.RegionalZentrum;

public class BrowseStationDialog_MastApp extends DialogBox {

	/*
	 * CREATE A TAP PANEL WITH 3 DIFFERENT WAYS TO RETRIEVE MASTEN:
	 * Gemeinde OK
	 * Bezirkz.
	 * Regionalz.
	 * 
	 */


	private ListBox gemeindelistBox;
	private ListBox bezirkListBox;
	private CellList<RegionalZentrum> regionalZentrumListBox;

	private ArrayList<SimpleGemeinde> listOfGemeinde;
	private ArrayList<RegionalZentrum> listOfRegionalZentrum;
	private ArrayList<Bezirk> listOfBezirk;
	
	private SingleSelectionModel<RegionalZentrum> rzSelectionModel;

	/**
	 * A custom {@link Cell} used to render a {@link Contact}.
	 */
	private static class RegionalZentrumCell extends AbstractCell<RegionalZentrum> {
		@Override
		public void render(Context context, RegionalZentrum value, SafeHtmlBuilder sb) {
			if (value != null) {
				sb.appendEscaped(value.getName());
			}
		}
	}

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
			}
		}
	}
	
	private class BezirkListCallBack implements AsyncCallback<ArrayList<Bezirk>>{

		@Override
		public void onFailure(Throwable caught){
			Window.alert("Unauble to obtain server response: " + caught.getMessage());
		}

		@Override
		public void onSuccess(ArrayList<Bezirk> list){
			listOfBezirk = list;
			bezirkListBox.clear();

			for(int i=0; i<list.size(); i++){
				bezirkListBox.addItem(list.get(i).getName());
			}

			if(!listOfBezirk.isEmpty()){
				bezirkListBox.setSelectedIndex(0);
			}
		}
	}

	private class RegionalZentrumListCallBack implements AsyncCallback<ArrayList<RegionalZentrum>>{

		@Override
		public void onFailure(Throwable caught){
			Window.alert("Unauble to obtain server response: " + caught.getMessage());
		}

		@Override
		public void onSuccess(ArrayList<RegionalZentrum> list){
			listOfRegionalZentrum = list;
			regionalZentrumListBox.setRowCount(listOfRegionalZentrum.size());
			regionalZentrumListBox.setRowData(0, listOfRegionalZentrum);
			regionalZentrumListBox.setPageSize(listOfRegionalZentrum.size());
			regionalZentrumListBox.redraw();
		}
	}

	private VerticalPanel createBezirkzentrumTab(){
		//create a widget to 
		DockLayoutPanel textBoxPanel = new DockLayoutPanel(Unit.PX);
		DockLayoutPanel bezirkListPanel = new DockLayoutPanel(Unit.PX);

		final TextBox textBox = new TextBox();
		textBox.getElement().getStyle().setWidth(175, Unit.PX);
		textBox.setName("Bezirk");
		textBox.addValueChangeHandler(new ValueChangeHandler<String>() {
			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				RetrieveBezirkServiceAsync retriever = GWT.create(RetrieveBezirkService.class);
				retriever.retrieveBezirk(textBox.getText(), new BezirkListCallBack());
			}
		});
		textBoxPanel.setSize("350PX", "30PX");
		textBoxPanel.addWest(new Label("Bezirk Name: "), 125);
		textBoxPanel.add(textBox);

		//Create a listbox
		bezirkListBox = new ListBox();
		bezirkListBox.setName("Bezirk ListBox");
		bezirkListBox.getElement().getStyle().setWidth(175, Unit.PX);
		bezirkListPanel.setSize("350PX", "30PX");
		bezirkListPanel.addWest(new Label("Bezirk Liste: "), 125);
		bezirkListPanel.add(bezirkListBox);

		VerticalPanel panel = new VerticalPanel();
		panel.setPixelSize(300, 100);
		panel.setSpacing(10);
		panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);

		panel.add(textBoxPanel);
		panel.add(bezirkListPanel);

		return panel;
	}
	
	private VerticalPanel createRegionalZentrumTab(){
		DockLayoutPanel regionalZentrumListPanel = new DockLayoutPanel(Unit.PX);
		//get Data
		RetrieveRegionalZentrumServiceAsync regioRetriever = GWT.create(RetrieveRegionalZentrumService.class);
		regioRetriever.retrieveRegionalZentrum(new RegionalZentrumListCallBack());

		//Create a listbox
		ProvidesKey<RegionalZentrum> keyProvider = new ProvidesKey<RegionalZentrum>() {
			public Object getKey(RegionalZentrum item) {
				// Always do a null check.
				return (item == null) ? null : item.getID();
			}
		};

		rzSelectionModel = new SingleSelectionModel<>(keyProvider);
		// Create a cell to render each value.
		regionalZentrumListBox = new CellList<RegionalZentrum>(new RegionalZentrumCell(), keyProvider);

		//Window.alert("TEST " + listOfRegionalZentrum);
		//regionalZentrumListBox.setRowCount(listOfRegionalZentrum.size(), true);
		regionalZentrumListBox.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);
		regionalZentrumListBox.setSelectionModel(rzSelectionModel);
		
		//regionalZentrumListBox.setName("Gemeinde ListBox");
		//regionalZentrumListBox.getElement().getStyle().setWidth(175, Unit.PX);
		regionalZentrumListPanel.setSize("350PX", "30PX");
		regionalZentrumListPanel.addWest(new Label("Regional Zentrum: "), 125);
		regionalZentrumListPanel.add(regionalZentrumListBox);
		
		VerticalPanel panel = new VerticalPanel();
		panel.setPixelSize(300, 100);
		panel.setSpacing(10);
		panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);

		panel.add(regionalZentrumListPanel);

		return panel;
	}

	private VerticalPanel createGemeindeTab(){
		//create a widget to 
		DockLayoutPanel textBoxPanel = new DockLayoutPanel(Unit.PX);
		DockLayoutPanel gemeindeListPanel = new DockLayoutPanel(Unit.PX);

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
		gemeindelistBox.getElement().getStyle().setWidth(175, Unit.PX);
		gemeindeListPanel.setSize("350PX", "30PX");
		gemeindeListPanel.addWest(new Label("Gemeinde Liste: "), 125);
		gemeindeListPanel.add(gemeindelistBox);

		VerticalPanel panel = new VerticalPanel();
		panel.setPixelSize(300, 100);
		panel.setSpacing(10);
		panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);

		panel.add(textBoxPanel);
		panel.add(gemeindeListPanel);

		return panel;
	}

	public BrowseStationDialog_MastApp(final MastAppMainFrame mainFrame) {
		// Set the dialog box's caption.
		setText("Nach Masten suchen");

		// Enable animation.
		setAnimationEnabled(true);

		// Enable glass background.
		setGlassEnabled(true);


		final TabPanel tabPanel = new TabPanel();

		// DialogBox is a SimplePanel, so you have to set its widget 
		// property to whatever you want its contents to be.
		Button ok = new Button("Laden");
		ok.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				BrowseStationDialog_MastApp.this.hide();
				mainFrame.returnFromStationSucheDialog(BrowseStationDialog_MastApp.this, true, tabPanel.getTabBar().getSelectedTab());
			}
		});
		ok.addStyleName("buttonPadding");

		Button cancelButton = new Button("Abbrechen");
		cancelButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				BrowseStationDialog_MastApp.this.hide();
				mainFrame.returnFromStationSucheDialog(BrowseStationDialog_MastApp.this, false, tabPanel.getTabBar().getSelectedTab());
			}
		});
		cancelButton.addStyleName("buttonPadding");

		HorizontalPanel buttonPanel = new HorizontalPanel();
		buttonPanel.add(ok);
		buttonPanel.add(cancelButton);

		tabPanel.add(createGemeindeTab(), "Gemeinde");
		tabPanel.add(createRegionalZentrumTab(), "Regional Zentrum");
		tabPanel.add(createBezirkzentrumTab(), "Bezirk");

		tabPanel.selectTab(0);

		DockLayoutPanel container = new DockLayoutPanel(Unit.PX);
		container.setSize("600px", "300px");
		container.addSouth(buttonPanel, 50);
		container.add(tabPanel);

		setWidget(container);
	}

	public int getSelectedBezirk(){return this.listOfBezirk.get(this.bezirkListBox.getSelectedIndex()).getID();}
	public int getSelectedRegionalZentrum(){return rzSelectionModel.getSelectedObject().getID();}
	public int getSelectedGemeinde(){return this.listOfGemeinde.get(this.gemeindelistBox.getSelectedIndex()).getID();}
}
