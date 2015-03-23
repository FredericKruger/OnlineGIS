package de.netze.onlinegis.shared.auskunftsystem.database.request;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.postgresql.geometric.PGpoint;

import de.netze.onlinegis.shared.auskunftsystem.networkelements.CompleteStation;
import de.netze.onlinegis.shared.auskunftsystem.networkelements.Node;
import de.netze.onlinegis.shared.auskunftsystem.networkelements.Node_Hausanschluss_Flavour;
import de.netze.onlinegis.shared.common.coordinates.LonLatCoordinates;
import de.netze.onlinegis.shared.common.database.GISProdConnector;

public class Select_HausanschlussNode_StationID {
	
	private GISProdConnector connector;
	
	public Select_HausanschlussNode_StationID(){
		this.connector = new GISProdConnector();
	}
	
	public ArrayList<Node> retrieveListOfNodes(int stationID){
		
		ArrayList<Node> listOfNodes = new ArrayList<>();
		
		String request = "SELECT id, lonlatshape, obj_id FROM hausanschluss where station=" + stationID + "";
		
		Statement s;
		try {
			s = connector.getConnection().createStatement();
			ResultSet rs = s.executeQuery(request);
			while(rs.next()){
				Node_Hausanschluss_Flavour n = new Node_Hausanschluss_Flavour();
				n.setID(rs.getInt(1));
				n.setTypeID(Node.HAUSANSCHLUSS);
				n.setObjID(rs.getString(3));
								
				PGpoint point = (PGpoint)rs.getObject(2);
				
				n.setCoordinates(new LonLatCoordinates(point.x, point.y));
				
				listOfNodes.add(n);
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
		
		return listOfNodes;
		
	}
	
	public void close(){
		connector.closeConnection();
	}

	
}
