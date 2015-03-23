package de.netze.onlinegis.client.auskunftsystem.retriever;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.netze.onlinegis.shared.auskunftsystem.networkelements.SimpleStation;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("auskunftsystem_station")
public interface RetrieveStationService extends RemoteService {
	ArrayList<SimpleStation> retrieveStation(int id) throws IllegalArgumentException;
}
