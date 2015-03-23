package de.netze.onlinegis.client.mastapp.retriever;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.netze.onlinegis.shared.mastapp.networkelements.Bezirk;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("mastapp_bezirk")
public interface RetrieveBezirkService extends RemoteService {
	ArrayList<Bezirk> retrieveBezirk(String name) throws IllegalArgumentException;
}
