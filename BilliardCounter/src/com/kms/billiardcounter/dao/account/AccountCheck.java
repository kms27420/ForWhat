package com.kms.billiardcounter.dao.account;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.kms.billiardcounter.dao.connection.BilliardCounterConnector;

public class AccountCheck {

	private AccountCheck() {}
	
	public static final boolean isItCorrectAccount( String id, String password ) {
		
		boolean returnValue = false;
		
		try {
			
			Connection conn = BilliardCounterConnector.getConnection();
			Statement stmt = conn.createStatement();
			String sql = "SELECT * "
					+ "FROM billiard_counter.ACCOUNT "
					+ "WHERE ID = '" + id + "' AND PASSWORD = '" + password + "';";
			
			ResultSet rs = stmt.executeQuery( sql );
			
			if( rs.next() )		returnValue = true;
			
			rs.close();
			stmt.close();
			conn.close();
			
		} catch( Exception e ) {
			
			e.printStackTrace();
			
			return false;
			
		}
		
		return returnValue;
		
	}
	
	public static final boolean isThereAccountInDB() {
		
		boolean returnValue = false;
		
		try {
			
			Connection conn = BilliardCounterConnector.getConnection();
			Statement stmt = conn.createStatement();
			String sql = "SELECT * "
					+ "FROM information_schema.tables  "
					+ "WHERE table_name = 'ACCOUNT';";
			
			ResultSet rs = stmt.executeQuery( sql );
			
			if( rs.next() )	returnValue =  true;
			
			rs.close();
			stmt.close();
			conn.close();
			
		} catch( Exception e ) {
			
			e.printStackTrace();
			
			return false;
			
		}
		
		return returnValue;
		
	}
	
}
