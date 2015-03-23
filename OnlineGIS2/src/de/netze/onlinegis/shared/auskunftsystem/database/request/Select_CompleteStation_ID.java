package de.netze.onlinegis.shared.auskunftsystem.database.request;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.postgresql.geometric.PGpoint;
import org.postgresql.geometric.PGpolygon;

import de.netze.onlinegis.shared.auskunftsystem.networkelements.CompleteStation;
import de.netze.onlinegis.shared.common.coordinates.LonLatCoordinates;
import de.netze.onlinegis.shared.common.database.GISProdConnector;

public class Select_CompleteStation_ID {
	
	private GISProdConnector connector;
	
	public Select_CompleteStation_ID(){
		this.connector = new GISProdConnector();
	}
	
	public CompleteStation retrieveCompleteStation(int id){
		
		CompleteStation g = new CompleteStation();
		
		String request = "SELECT id, sap_name, lonlatshape, lonlatarea FROM station where id=" + id + "";
		
		Statement s;
		try {
			s = connector.getConnection().createStatement();
			ResultSet rs = s.executeQuery(request);
			while(rs.next()){
				g.setID(rs.getInt(1));
				g.setName(rs.getString(2));
				
				PGpoint point = (PGpoint)rs.getObject(3);
				PGpolygon polygon = (PGpolygon)rs.getObject(4);
				
				ArrayList<LonLatCoordinates> areaCoordinates = new ArrayList<>();
				if(polygon!=null){
					for(int i=0; i<polygon.points.length; i++){
						areaCoordinates.add(new LonLatCoordinates(polygon.points[i].x, polygon.points[i].y));
					}
				}
				g.setAreaCoordinates(areaCoordinates);
				
				g.setCoordinates(new LonLatCoordinates(point.x, point.y));
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
		
		return g;
		
	}
	
	public void close(){
		connector.closeConnection();
	}

	
}
