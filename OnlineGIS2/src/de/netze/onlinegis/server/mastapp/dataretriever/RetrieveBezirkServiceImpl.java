package de.netze.onlinegis.server.mastapp.dataretriever;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.netze.onlinegis.client.mastapp.retriever.RetrieveBezirkService;
import de.netze.onlinegis.shared.common.database.MastenAuskunftConnector;
import de.netze.onlinegis.shared.mastapp.networkelements.Bezirk;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class RetrieveBezirkServiceImpl extends RemoteServiceServlet implements
RetrieveBezirkService {

	@Override
	public ArrayList<Bezirk> retrieveBezirk(String input) throws IllegalArgumentException {
		// Verify that the input is valid. 
		if (input.compareTo("")!=0) {
			MastenAuskunftConnector connector = new MastenAuskunftConnector();

			ArrayList<Bezirk> list = new ArrayList<Bezirk>();

			String request = "SELECT id, name FROM bezirkszentrum where lower(name) like lower('" + input + "%') order by name";

			Statement s;
			try {
				s = connector.getConnection().createStatement();
				ResultSet rs = s.executeQuery(request);
				while(rs.next()){
					Bezirk g = new Bezirk();
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

		return new ArrayList<Bezirk>();
	}

}
