package de.netze.onlinegis.server.common.dataretriever;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.netze.onlinegis.client.common.retriever.RetrieveGemeindeService;
import de.netze.onlinegis.shared.common.database.GISProdConnector;
import de.netze.onlinegis.shared.common.networkelements.SimpleGemeinde;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class RetrieveGemeindeServiceImpl extends RemoteServiceServlet implements
RetrieveGemeindeService {

	@Override
	public ArrayList<SimpleGemeinde> retrieveGemeinde(String input) throws IllegalArgumentException {
		// Verify that the input is valid. 
		if (input.compareTo("")!=0) {
			GISProdConnector connector = new GISProdConnector();

			ArrayList<SimpleGemeinde> list = new ArrayList<SimpleGemeinde>();

			String request = "SELECT id, name FROM gemeinde where lower(name) like lower('%" + input + "%') order by name";

			Statement s;
			try {
				s = connector.getConnection().createStatement();
				ResultSet rs = s.executeQuery(request);
				while(rs.next()){
					SimpleGemeinde g = new SimpleGemeinde();
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

		return new ArrayList<SimpleGemeinde>();
	}

}
