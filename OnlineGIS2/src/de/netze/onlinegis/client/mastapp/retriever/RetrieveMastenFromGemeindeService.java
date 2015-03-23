package de.netze.onlinegis.client.mastapp.retriever;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.netze.onlinegis.shared.mastapp.networkelements.Mast;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("mastapp_retrievemastenfromgemeinde")
public interface RetrieveMastenFromGemeindeService extends RemoteService {
	ArrayList<Mast> retrieveMastFromGemeinde(int id, int source) throws IllegalArgumentException;
}
