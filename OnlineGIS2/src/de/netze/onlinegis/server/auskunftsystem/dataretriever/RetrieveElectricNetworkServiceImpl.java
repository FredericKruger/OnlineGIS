package de.netze.onlinegis.server.auskunftsystem.dataretriever;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.postgresql.geometric.PGpoint;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.netze.onlinegis.client.auskunftsystem.retriever.RetrieveElectricNetworkService;
import de.netze.onlinegis.shared.auskunftsystem.database.request.Select_CompleteStation_ID;
import de.netze.onlinegis.shared.auskunftsystem.database.request.Select_CompleteVKS_NS_ID;
import de.netze.onlinegis.shared.auskunftsystem.database.request.Select_HausanschlussNode_StationID;
import de.netze.onlinegis.shared.auskunftsystem.database.request.Select_KabelArc_StationID;
import de.netze.onlinegis.shared.auskunftsystem.database.request.Select_RuckeinspeisungNode_StationID;
import de.netze.onlinegis.shared.auskunftsystem.networkelements.CompleteStation;
import de.netze.onlinegis.shared.auskunftsystem.networkelements.ElectricNetwork;
import de.netze.onlinegis.shared.auskunftsystem.networkelements.Node;
import de.netze.onlinegis.shared.common.coordinates.LonLatCoordinates;
import de.netze.onlinegis.shared.common.coordinates.XYCoordinates;
import de.netze.onlinegis.shared.common.database.GISProdConnector;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class RetrieveElectricNetworkServiceImpl extends RemoteServiceServlet implements
RetrieveElectricNetworkService {

	@Override
	public ArrayList<ElectricNetwork> retrieveCompleteStation(int id[], String gemeinde) throws IllegalArgumentException {
		ArrayList<ElectricNetwork> list = new ArrayList<ElectricNetwork>();

		for(int i=0; i<id.length; i++){
			ElectricNetwork network = new ElectricNetwork();
			network.setGemeindeName(gemeinde);
			
			//add complete Station
			Select_CompleteStation_ID stationRetriever = new Select_CompleteStation_ID();
			network.setCompleteStation(stationRetriever.retrieveCompleteStation(id[i]));
			stationRetriever = null;
			
			//set the completeStation as Node
			Node stationNode = new Node();
			stationNode.setID(network.getCompleteStation().getID());
			stationNode.setCoordinates(network.getCompleteStation().getCoordinates());
			stationNode.setTypeID(Node.STATION);
			
			//get VKS
			Select_CompleteVKS_NS_ID vksRetriever = new Select_CompleteVKS_NS_ID();
			network.addNodes(vksRetriever.retrieveNodes(id[i]));
			vksRetriever = null;
			
			//get the hausanschluss
			Select_HausanschlussNode_StationID hausanschlussRetriever = new Select_HausanschlussNode_StationID();
			network.addNodes(hausanschlussRetriever.retrieveListOfNodes(id[i]));
			hausanschlussRetriever = null;
			
			//get Ruckeinspeisung
			Select_RuckeinspeisungNode_StationID einspeisungRetriever = new Select_RuckeinspeisungNode_StationID();
			network.addNodes(einspeisungRetriever.retrieveListOfNodes(id[i]));
			einspeisungRetriever = null;
			
			//get Kabel
			Select_KabelArc_StationID kabelRetriever = new Select_KabelArc_StationID();
			network.addArcs(kabelRetriever.retrieveListOfArcs(id[i]));
			kabelRetriever = null;
			
			list.add(network);
		}

		return list;

	}

}
