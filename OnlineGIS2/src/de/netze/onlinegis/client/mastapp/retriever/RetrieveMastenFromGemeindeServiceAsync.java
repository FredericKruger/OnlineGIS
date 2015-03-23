package de.netze.onlinegis.client.mastapp.retriever;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.netze.onlinegis.shared.mastapp.networkelements.Mast;

/**
 * The async counterpart of <code>RetrieveGemeindeService</code>.
 */
public interface RetrieveMastenFromGemeindeServiceAsync {
	void retrieveMastFromGemeinde(int id, int source, AsyncCallback<ArrayList<Mast>> callback)
			throws IllegalArgumentException;
}
