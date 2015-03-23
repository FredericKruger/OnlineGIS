package de.netze.onlinegis.shared.mastapp.database.request;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.postgresql.geometric.PGpoint;

import de.netze.onlinegis.shared.common.coordinates.LonLatCoordinates;
import de.netze.onlinegis.shared.common.database.MastenAuskunftConnector;
import de.netze.onlinegis.shared.mastapp.networkelements.Mast;

public class Select_Mast_GemeindeID {
	
	private MastenAuskunftConnector connector;
	
	public Select_Mast_GemeindeID(){
		this.connector = new MastenAuskunftConnector();
	}
	
	public ArrayList<Mast> retrieveMasten(int gemeindeID){
		
		ArrayList<Mast> listOfMast = new ArrayList<>();
		
		String request = "SELECT id, mittelspannung, niederspannung, schadensklasse, werkstoff, lonlatshape from masten where gemeinde=" + gemeindeID + "";
		
		Statement s;
		try {
			s = connector.getConnection().createStatement();
			ResultSet rs = s.executeQuery(request);
			while(rs.next()){
				Mast m = new Mast();
				
				m.setID(rs.getInt(1));
				m.setIsMittelspannung(rs.getBoolean(2));
				m.setIsNiederspannung(rs.getBoolean(3));
				m.setSchadensklasse(rs.getInt(4));
				m.setWerkstoff(rs.getString(5));
				
				PGpoint point = (PGpoint)rs.getObject(6);
				m.setCoordinates(new LonLatCoordinates(point.x, point.y));
				
				listOfMast.add(m);
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
		
		return listOfMast;
	}
	
	public void close(){
		connector.closeConnection();
	}	
}
