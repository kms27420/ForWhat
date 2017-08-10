package dataBaseControl;

import java.sql.SQLException;

import java.sql.Statement;

/**
 * 당구 테이블의 위치 정보를 DB에 저장해주는 클래스
 * @author Kwon
 *
 */
public class TablePositionSaver {
	private final BilliardCounterConnector CONNECTOR = new BilliardCounterConnector();	// DB에 연결해주는 클래스의 객체
	
	private Statement stmt = null;			// sql문을 실행할 Statement
	private String sql;						// sql문의 내용을 담을 String 변수
	
	/**
	 * 본 클래스가 객체화될 때 즉시 내용을 DB에 저장해주는 생성자
	 * @param screenNumber 저장할 당구 테이블의 번호
	 * @param row 저장할 당구 테이블의 row
	 * @param col 저장할 당구 테이블의 col
	 */
	public TablePositionSaver(int screenNumber, int row, int col){
		this.saveTablePosition(screenNumber, row, col);
	}
	
	/**
	 * DB에 저장해야할 Table이 존재하지 않을 때 DB에 Table을 생성해주는 매서드
	 * @throws SQLException
	 */
	private void createTableIfNotExists() throws SQLException{
		stmt.execute(
				"CREATE TABLE IF NOT EXISTS billiard_counter.CREATED_TABLE_INFO("
				+ "TABLE_NUM INT(11) PRIMARY KEY NOT NULL,"
				+ "TABLE_ROW INT(11) NOT NULL,"
				+ "TABLE_COL INT(11) NOT NULL);"
				);
	}
	/**
	 * DB에 당구테이블의 위치정보를 저장하는 매서드
	 * @param screenNumber 당구테이블의 번호
	 * @param row 당구테이블의 row
	 * @param col 당구테이블의 col
	 */
	private void saveTablePosition(int screenNumber, int row, int col){
		try{
			CONNECTOR.connectToDB();
			stmt = CONNECTOR.getConnection().createStatement();
			
			this.createTableIfNotExists();
			
			sql = "INSERT INTO billiard_counter.CREATED_TABLE_INFO VALUES(" + screenNumber + ", " + row + ", " + col + ");";
			stmt.executeUpdate(sql);
			
			stmt.close();
			stmt = null;
			CONNECTOR.disconnectFromDB();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
