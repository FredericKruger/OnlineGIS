package de.netze.onlinegis.client.common.retriever;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.netze.onlinegis.shared.common.networkelements.SimpleGemeinde;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("common_gemeinde")
public interface RetrieveGemeindeService extends RemoteService {
	ArrayList<SimpleGemeinde> retrieveGemeinde(String name) throws IllegalArgumentException;
}
