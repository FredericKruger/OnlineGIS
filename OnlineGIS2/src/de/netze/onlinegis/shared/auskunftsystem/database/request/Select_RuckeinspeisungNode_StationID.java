package de.netze.onlinegis.shared.auskunftsystem.database.request;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.postgresql.geometric.PGpoint;

import de.netze.onlinegis.shared.auskunftsystem.networkelements.CompleteStation;
import de.netze.onlinegis.shared.auskunftsystem.networkelements.Node;
import de.netze.onlinegis.shared.auskunftsystem.networkelements.Node_Ruckeinspeisung_Flavour;
import de.netze.onlinegis.shared.common.coordinates.LonLatCoordinates;
import de.netze.onlinegis.shared.common.database.GISProdConnector;

public class Select_RuckeinspeisungNode_StationID {
	
	private GISProdConnector connector;
	
	public Select_RuckeinspeisungNode_StationID(){
		this.connector = new GISProdConnector();
	}
	
	public ArrayList<Node> retrieveListOfNodes(int stationID){
		
		ArrayList<Node> listOfNodes = new ArrayList<>();
		
		String request = "SELECT id, lonlatshape, obj_id, hausanschluss FROM ruckeinspeisung_ns where station=" + stationID + "";
		
		Statement s;
		try {
			s = connector.getConnection().createStatement();
			ResultSet rs = s.executeQuery(request);
			while(rs.next()){
				Node_Ruckeinspeisung_Flavour n = new Node_Ruckeinspeisung_Flavour();
				n.setID(rs.getInt(1));
				n.setTypeID(Node.RUCKEINSPEISUNG);
				n.setObjID(rs.getString(3));
				n.setHausanschlussID(rs.getInt(4));
								
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
