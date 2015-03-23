package de.netze.onlinegis.server.mastapp.dataretriever;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.netze.onlinegis.client.mastapp.retriever.RetrieveRegionalZentrumService;
import de.netze.onlinegis.shared.common.database.MastenAuskunftConnector;
import de.netze.onlinegis.shared.mastapp.networkelements.RegionalZentrum;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class RetrieveRegionalZentrumServiceImpl extends RemoteServiceServlet implements
RetrieveRegionalZentrumService {

	@Override
	public ArrayList<RegionalZentrum> retrieveRegionalZentrum() throws IllegalArgumentException {
		MastenAuskunftConnector connector = new MastenAuskunftConnector();

		ArrayList<RegionalZentrum> list = new ArrayList<RegionalZentrum>();

		String request = "SELECT distinct id, name FROM regionalzentrum where id>0 order by name";

		Statement s;
		try {
			s = connector.getConnection().createStatement();
			ResultSet rs = s.executeQuery(request);
			while(rs.next()){
				RegionalZentrum r = new RegionalZentrum();
				r.setID(rs.getInt(1));
				r.setName(rs.getString(2));
				list.add(r);
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
