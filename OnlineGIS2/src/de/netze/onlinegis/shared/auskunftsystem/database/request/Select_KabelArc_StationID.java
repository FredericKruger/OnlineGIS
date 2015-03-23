package de.netze.onlinegis.shared.auskunftsystem.database.request;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.postgresql.geometric.PGpath;
import org.postgresql.geometric.PGpoint;

import de.netze.onlinegis.shared.auskunftsystem.networkelements.Arc;
import de.netze.onlinegis.shared.auskunftsystem.networkelements.Node;
import de.netze.onlinegis.shared.common.coordinates.LonLatCoordinates;
import de.netze.onlinegis.shared.common.database.GISProdConnector;

public class Select_KabelArc_StationID {
	
	private GISProdConnector connector;
	
	public Select_KabelArc_StationID(){
		this.connector = new GISProdConnector();
	}
	
	public ArrayList<Arc> retrieveListOfArcs(int stationID){
		
		ArrayList<Arc> listOfArcs = new ArrayList<>();
		
		String request = "SELECT id, freileitung, hausanschlusskabel, schemasection, busbar, lonlatshape FROM kabel_ns where station=" + stationID + "";
		
		Statement s;
		try {
			s = connector.getConnection().createStatement();
			ResultSet rs = s.executeQuery(request);
			while(rs.next()){
				Arc n = new Arc();
				n.setID(rs.getInt(1));
				n.setIsFreileitung(rs.getBoolean(2));
				
				if(rs.getBoolean(3))
					n.setType(Arc.HAUSANSCHLUSS);
				else if(rs.getBoolean(4) || rs.getBoolean(5))
					n.setType(Arc.INTERNAL);
				else
					n.setType(Arc.STANDARD);
								
				PGpath path = (PGpath)rs.getObject(6);
				ArrayList<LonLatCoordinates> listOfCoordinates = new ArrayList<>();
				for(int i=0; i<path.points.length; i++){
					listOfCoordinates.add(new LonLatCoordinates(path.points[i].x, path.points[i].y));
				}
				n.setListOfCoordinates(listOfCoordinates);
				
				listOfArcs.add(n);
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
		
		return listOfArcs;
		
	}
	
	public void close(){
		connector.closeConnection();
	}

	
}
