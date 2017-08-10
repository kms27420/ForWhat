package dataBaseControl;

import java.sql.SQLException;
import java.sql.Statement;

import gameFeeInfoHandling.GameFeeInfoManager;
import playingGameControl.GameFeeInfo;

/**
 * Game list의 내용을 DB에 저장해주는 클래스
 * @author Kwon
 *
 */
public class GameListSaver {
	private final BilliardCounterConnector CONNECTOR = new BilliardCounterConnector();	// DB에 연결해주는 클래스의 객체
	
	private Statement stmt = null;				// sql문을 실행할 Statement
	private String sql;							// 실행할 sql문을 담을 String변수
	
	/**
	 * 본 클래스가 객체화될 때 즉시 저장하는 작업을 실행하는 생성자
	 * @param gameFeeInfoManager DB에 저장할 내용을 담고 있는 GameFeeInfoManager
	 */
	public GameListSaver(GameFeeInfoManager gameFeeInfoManager){
		this.saveInfoToDB(gameFeeInfoManager);
	}
	
	/**
	 * DB에 저장할 장소인 Table이 존재하지 않을 경우 DB에  Table을 생성해주는 매서드
	 * @throws SQLException
	 */
	private void createTableIfNotExists() throws SQLException{
		stmt.execute(
				"CREATE TABLE IF NOT EXISTS billiard_counter.GAME_LIST("
				+ "DATE VARCHAR(6) NOT NULL,"
				+ "START_TIME VARCHAR(8) NOT NULL,"
				+ "END_TIME VARCHAR(8) NOT NULL,"
				+ "GAME_NUM INT(11) NOT NULL,"
				+ "TABLE_NUM INT(11) NOT NULL,"
				+ "USED_TIME INT(11) NOT NULL,"
				+ "FEE INT(11) NOT NULL,"
				+ "PRIMARY KEY(DATE, START_TIME, GAME_NUM, TABLE_NUM));"
				);
	}
	/**
	 * 저장할 내용을 DB에 저장해주는 매서드
	 * @param gameFeeInfoManager 저장할 내용을 담고 있는 GameFeeInfoManager
	 */
	private void saveInfoToDB(GameFeeInfoManager gameFeeInfoManager){
		try{
			CONNECTOR.connectToDB();
			stmt = CONNECTOR.getConnection().createStatement();
			
			this.createTableIfNotExists();
			
			GameFeeInfo tmpInfo;
			String date, startTime, endTime;
			int gameNum, tableNum, usedTime, fee;
			
			for(int i = 0; i < gameFeeInfoManager.getGameCount(); i++){
				tmpInfo = gameFeeInfoManager.getGameFeeInfo()[i];
				
				date = tmpInfo.getDateOfGame();
				startTime = tmpInfo.getStartTime();
				endTime = tmpInfo.getEndTime();
				gameNum = tmpInfo.getGameNum();
				tableNum = tmpInfo.getTableNum();
				usedTime = tmpInfo.getUsedTime();
				fee = tmpInfo.getFee();
				
				sql = "INSERT INTO billiard_counter.GAME_LIST VALUES('" + date + "', '" + startTime + "', '" + endTime + "', " 
				+ gameNum + ", " + tableNum + ", " + usedTime + ", " + fee + ");";
				
				stmt.executeUpdate(sql);
			}
			
			stmt.close();
			stmt = null;
			CONNECTOR.disconnectFromDB();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
}
