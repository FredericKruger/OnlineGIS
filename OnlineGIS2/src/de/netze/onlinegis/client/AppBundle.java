package de.netze.onlinegis.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public interface AppBundle extends ClientBundle {

    @Source("img/enbw_picto_baden-wuerttemberg_mit_HG_RGB.png")
    ImageResource bwImage();
    
    @Source("img/enbw_picto_Netznahe Dienstleistungen_mit_HG_RGB.png")
    ImageResource masteImage();
    
    @Source("img/logout.png")
    ImageResource logoutImage();
    
    @Source("img/search.png")
    ImageResource searcheImage();
    
    @Source("img/explore.png")
    ImageResource exploreImage();

    public static final AppBundle INSTANCE = GWT.create(AppBundle.class);

}
