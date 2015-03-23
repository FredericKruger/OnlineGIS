package de.netze.onlinegis.shared.auskunftsystem.database.request;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.postgresql.geometric.PGpoint;
import org.postgresql.geometric.PGpolygon;

import de.netze.onlinegis.shared.auskunftsystem.networkelements.Node;
import de.netze.onlinegis.shared.auskunftsystem.networkelements.Node_VKS_Flavour;
import de.netze.onlinegis.shared.common.coordinates.LonLatCoordinates;
import de.netze.onlinegis.shared.common.database.GISProdConnector;

public class Select_CompleteVKS_NS_ID {
	
	private GISProdConnector connector;
	
	public Select_CompleteVKS_NS_ID(){
		this.connector = new GISProdConnector();
	}
	
	public ArrayList<Node> retrieveNodes(int id){
		
		ArrayList<Node> listOfNodes = new ArrayList<Node>();
		
		String request = "SELECT id, lonlatshape, lonlatarea FROM verteilkasten_ns where station=" + id + "";
		
		Statement s;
		try {
			s = connector.getConnection().createStatement();
			ResultSet rs = s.executeQuery(request);
			while(rs.next()){
				Node_VKS_Flavour g = new Node_VKS_Flavour();
				g.setID(rs.getInt(1));
				g.setTypeID(Node.VERTEILKASTEN);
				
				PGpoint point = (PGpoint)rs.getObject(2);
				PGpolygon polygon = (PGpolygon)rs.getObject(3);
				
				ArrayList<LonLatCoordinates> areaCoordinates = new ArrayList<>();
				if(polygon!=null){
					for(int i=0; i<polygon.points.length; i++){
						areaCoordinates.add(new LonLatCoordinates(polygon.points[i].x, polygon.points[i].y));
					}
				}
				g.setAreaCoordinates(areaCoordinates);
				
				g.setCoordinates(new LonLatCoordinates(point.x, point.y));
				
				listOfNodes.add(g);
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
