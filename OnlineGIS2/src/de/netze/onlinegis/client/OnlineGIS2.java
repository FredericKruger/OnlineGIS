package de.netze.onlinegis.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.netze.onlinegis.client.auskunftsystem.AuskunftSystemMainFrame;
import de.netze.onlinegis.client.mastapp.MastAppMainFrame;

/* EXAMPLES
 * Gemeinde: Bietigheim
 * Station: BIETI-E-UST-037
 */

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class OnlineGIS2 implements EntryPoint {

	private void loadAuskunftSystem(){
		RootLayoutPanel.get().clear();
		RootLayoutPanel.get().add(new AuskunftSystemMainFrame(this).getMainPanel());
	}
	
	private void loadMastApp(){
		RootLayoutPanel.get().clear();
		RootLayoutPanel.get().add(new MastAppMainFrame(this).getMainPanel());
	}
	
	public void loadStartPanel(){
		RootLayoutPanel.get().clear();
				
		VerticalPanel auskunftPanel = new VerticalPanel();
		auskunftPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		auskunftPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		Image auskunftImage = new Image(AppBundle.INSTANCE.bwImage());
		auskunftImage.setPixelSize(128, 128);
		PushButton auskunftButton = new PushButton(auskunftImage, new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				loadAuskunftSystem();
			}
		});
		auskunftPanel.add(auskunftButton);
		auskunftPanel.add(new com.google.gwt.user.client.ui.Label("Auskunft"));
		
		VerticalPanel mastenPanel = new VerticalPanel();
		mastenPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		mastenPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		Image mastenImage = new Image(AppBundle.INSTANCE.masteImage());
		mastenImage.setPixelSize(128, 128);
		PushButton mastenButton = new PushButton(mastenImage,new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				loadMastApp();
			}
		});
		mastenPanel.add(mastenButton);
		mastenPanel.add(new Label("Masten Bewertung"));
		
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		horizontalPanel.setWidth("100%");
		horizontalPanel.setHeight("100%");
		horizontalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		horizontalPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		horizontalPanel.add(auskunftPanel);
		horizontalPanel.add(mastenPanel);
		
			
		RootLayoutPanel.get().add(horizontalPanel);
	}
	
	@Override
	public void onModuleLoad(){
		loadStartPanel();
	}
}
