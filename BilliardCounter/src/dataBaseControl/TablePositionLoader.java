package dataBaseControl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 당구 테이블의 위치 정보를 load하는 클래스
 * @author Kwon
 *
 */
public class TablePositionLoader {
	private final BilliardCounterConnector CONNECTOR = new BilliardCounterConnector();		// DB에 연결해주는 클래스의 객체
	
	private String sql;							// 실행할 sql문의 내용을 담을 String 변수
	private PreparedStatement pstmt = null;		// sql문을 실행할 PreparedStatement
	
	/**
	 * DB에 load할 정보를 가져야할 Table이 존재하지 않을 때 DB에 Table을 생성해주는 매서드
	 * @throws SQLException
	 */
	private void createTableIfNotExists() throws SQLException{
		Statement stmt = CONNECTOR.getConnection().createStatement();
		stmt.execute(
				"CREATE TABLE IF NOT EXISTS billiard_counter.CREATED_TABLE_INFO("
				+ "TABLE_NUM INT(11) PRIMARY KEY NOT NULL,"
				+ "TABLE_ROW INT(11) NOT NULL,"
				+ "TABLE_COL INT(11) NOT NULL);"
				);
		stmt.close();
	}
	/**
	 * 위치 정보를 통해 당구 테이블의 번호를 반환하는 매서드
	 * @param tableRow 원하는 당구 테이블의 row
	 * @param tableCol 원하는 당구 테이블의 col
	 * @return 원하는 당구 테이블의 테이블 번호
	 */
	public int getTableNum(int tableRow, int tableCol){
		int tableNum = 0;
		
		try{
			CONNECTOR.connectToDB();
			this.createTableIfNotExists();
			
			sql = "SELECT TABLE_NUM FROM BILLIARD_COUNTER.CREATED_TABLE_INFO WHERE TABLE_ROW = ? AND TABLE_COL = ?;";
			pstmt = CONNECTOR.getConnection().prepareStatement(sql);
			pstmt.setInt(1, tableRow);
			pstmt.setInt(2, tableCol);
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()){
				tableNum = rs.getInt("TABLE_NUM");
			}
			
			rs.close();
			pstmt.close();
			pstmt = null;
			CONNECTOR.disconnectFromDB();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return tableNum;
	}
}
