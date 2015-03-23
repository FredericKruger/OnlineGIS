package de.netze.onlinegis.server.mastapp.dataretriever;

import java.util.ArrayList;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.netze.onlinegis.client.mastapp.retriever.RetrieveMastenFromGemeindeService;
import de.netze.onlinegis.shared.mastapp.database.request.Select_Mast_BezirkZentrumID;
import de.netze.onlinegis.shared.mastapp.database.request.Select_Mast_GemeindeID;
import de.netze.onlinegis.shared.mastapp.database.request.Select_Mast_RegionalZentrumID;
import de.netze.onlinegis.shared.mastapp.networkelements.Mast;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class RetrieveMastenFromGemeindeServiceImpl extends RemoteServiceServlet implements
RetrieveMastenFromGemeindeService {

	@Override
	public ArrayList<Mast> retrieveMastFromGemeinde(int id, int source) throws IllegalArgumentException {
		// Verify that the input is valid.
		if(source==0){
			return new Select_Mast_GemeindeID().retrieveMasten(id);
		}
		else if(source==1){
			return new Select_Mast_RegionalZentrumID().retrieveMasten(id);
		}
		else if(source==2){
			return new Select_Mast_BezirkZentrumID().retrieveMasten(id);
		}
		else return new ArrayList<>();
	}

}
