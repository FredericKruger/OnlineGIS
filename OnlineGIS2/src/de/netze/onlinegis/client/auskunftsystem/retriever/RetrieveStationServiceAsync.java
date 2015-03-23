package de.netze.onlinegis.client.auskunftsystem.retriever;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.netze.onlinegis.shared.auskunftsystem.networkelements.SimpleStation;

/**
 * The async counterpart of <code>RetrieveGemeindeService</code>.
 */
public interface RetrieveStationServiceAsync {
	void retrieveStation(int id, AsyncCallback<ArrayList<SimpleStation>> callback)
			throws IllegalArgumentException;
}
