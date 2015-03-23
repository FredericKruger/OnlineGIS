package de.netze.onlinegis.client.mastapp.retriever;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.netze.onlinegis.shared.mastapp.networkelements.RegionalZentrum;

/**
 * The async counterpart of <code>RetrieveGemeindeService</code>.
 */
public interface RetrieveRegionalZentrumServiceAsync {
	void retrieveRegionalZentrum(AsyncCallback<ArrayList<RegionalZentrum>> callback)
			throws IllegalArgumentException;
}
