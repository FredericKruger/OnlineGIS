package de.netze.onlinegis.client.auskunftsystem.retriever;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.netze.onlinegis.shared.auskunftsystem.networkelements.ElectricNetwork;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("auskunftsystem_retrieveElectricNetwork")
public interface RetrieveElectricNetworkService extends RemoteService {
	ArrayList<ElectricNetwork> retrieveCompleteStation(int id[], String gemeinde) throws IllegalArgumentException;
}
