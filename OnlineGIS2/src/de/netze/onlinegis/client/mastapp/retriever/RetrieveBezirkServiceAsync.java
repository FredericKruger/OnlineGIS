package de.netze.onlinegis.client.mastapp.retriever;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.netze.onlinegis.shared.mastapp.networkelements.Bezirk;

/**
 * The async counterpart of <code>RetrieveGemeindeService</code>.
 */
public interface RetrieveBezirkServiceAsync {
	void retrieveBezirk(String input, AsyncCallback<ArrayList<Bezirk>> callback)
			throws IllegalArgumentException;
}
