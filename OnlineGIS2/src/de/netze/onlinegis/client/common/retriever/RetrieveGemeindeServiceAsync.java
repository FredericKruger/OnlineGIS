package de.netze.onlinegis.client.common.retriever;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.netze.onlinegis.shared.common.networkelements.SimpleGemeinde;

/**
 * The async counterpart of <code>RetrieveGemeindeService</code>.
 */
public interface RetrieveGemeindeServiceAsync {
	void retrieveGemeinde(String input, AsyncCallback<ArrayList<SimpleGemeinde>> callback)
			throws IllegalArgumentException;
}
