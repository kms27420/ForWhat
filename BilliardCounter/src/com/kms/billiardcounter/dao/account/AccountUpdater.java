package com.kms.billiardcounter.dao.account;

import java.sql.Connection;
import java.sql.Statement;

import com.kms.billiardcounter.dao.connection.BilliardCounterConnector;

public class AccountUpdater {

	private AccountUpdater() {}
	
	private static void createTableIfNotExists( Connection conn, Statement stmt ) throws Exception{
		
		stmt.execute(
				"CREATE TABLE IF NOT EXISTS billiard_counter.ACCOUNT("
				+ "ID VARCHAR(15) NOT NULL,"
				+ "PASSWORD VARCHAR(20) NOT NULL,"
				+ "PRIMARY KEY(ID));"
				);
		
	}
	
	public static final boolean saveAccountToDB( String id, String password ) {
		
		try {
			
			Connection conn = BilliardCounterConnector.getConnection();
			Statement stmt = conn.createStatement();
			String sql = "INSERT INTO billiard_counter.ACCOUNT "
					+ "VALUES('" + id + "', '" + password + "');";
			
			createTableIfNotExists( conn, stmt );
			
			stmt.executeUpdate( sql );
			
			stmt.close();
			conn.close();
			
			return true;
			
		} catch( Exception e ) {
			
			e.printStackTrace();
			
			return false;
			
		}
		
	}
	
	public static final boolean updatePassword( String id, String password ) {
		
		try {
			
			Connection conn = BilliardCounterConnector.getConnection();
			Statement stmt = conn.createStatement();
			String sql = "UPDATE billiard_counter.ACCOUNT "
					+ "SET PASSWORD = '" + password + "' "
					+ "WHERE ID = '" + id + "';";
			
			stmt.executeUpdate( sql );
			
			stmt.close();
			conn.close();
			
			return true;
			
		} catch( Exception e ) {
			
			e.printStackTrace();
			
			return false;
			
		}
		
	}
	
}
