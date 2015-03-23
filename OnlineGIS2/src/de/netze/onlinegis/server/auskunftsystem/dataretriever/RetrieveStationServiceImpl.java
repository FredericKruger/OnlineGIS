package de.netze.onlinegis.server.auskunftsystem.dataretriever;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.netze.onlinegis.client.auskunftsystem.retriever.RetrieveStationService;
import de.netze.onlinegis.shared.auskunftsystem.networkelements.SimpleStation;
import de.netze.onlinegis.shared.common.database.GISProdConnector;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class RetrieveStationServiceImpl extends RemoteServiceServlet implements
RetrieveStationService {

	@Override
	public ArrayList<SimpleStation> retrieveStation(int id) throws IllegalArgumentException {
		// Verify that the input is valid. 

		GISProdConnector connector = new GISProdConnector();

		ArrayList<SimpleStation> list = new ArrayList<SimpleStation>();

		String request = "SELECT distinct station.id, station.sap_name FROM station, gemeinde where station.gemeinde=gemeinde.id and gemeinde.id=" + id + " order by station.sap_name";

		Statement s;
		try {
			s = connector.getConnection().createStatement();
			ResultSet rs = s.executeQuery(request);
			while(rs.next()){
				SimpleStation g = new SimpleStation();
				g.setID(rs.getInt(1));
				g.setName(rs.getString(2));
				list.add(g);
			}
			rs.close();
			s.close();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			connector.closeConnection();
		}

		return list;

	}

}
