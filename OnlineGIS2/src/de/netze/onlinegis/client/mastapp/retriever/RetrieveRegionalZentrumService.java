package de.netze.onlinegis.client.mastapp.retriever;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.netze.onlinegis.shared.mastapp.networkelements.RegionalZentrum;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("mastapp_retrieveregionalzentrum")
public interface RetrieveRegionalZentrumService extends RemoteService {
	ArrayList<RegionalZentrum> retrieveRegionalZentrum() throws IllegalArgumentException;
}
