package de.netze.onlinegis.client.auskunftsystem.retriever;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.netze.onlinegis.shared.auskunftsystem.networkelements.ElectricNetwork;

/**
 * The async counterpart of <code>RetrieveGemeindeService</code>.
 */
public interface RetrieveElectricNetworkServiceAsync {
	void retrieveCompleteStation(int[] id, String gemeinde, AsyncCallback<ArrayList<ElectricNetwork>> callback)
			throws IllegalArgumentException;
}
