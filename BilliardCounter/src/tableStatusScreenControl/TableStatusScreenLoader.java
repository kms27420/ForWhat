package tableStatusScreenControl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 
 * TableStatusScreenList의 정보가 저장된 file에서 정보를 불러와 TableStatusScreen들을 load해주는 클래스
 * @author Kwon
 *
 */
public class TableStatusScreenLoader{
	private static final String FILE_PATH = "src\\resource\\SavedFile.txt";
	private static int storedRow, storedCol;
	
	private TableStatusScreenLoader(){}
	
	/**
	 * 저장되어있던 Row의 값을 불러와 storedRow에 불러온다
	 */
	private static void loadStoredRow(){
		try{
			FileReader loadedFile = new FileReader(FILE_PATH);
			char[] buf = new char[6];
			loadedFile.read(buf);
			storedRow = (buf[0] - '0') * 100;
			storedRow += (buf[1] - '0') * 10;
			storedRow += (buf[2] - '0');
			loadedFile.close();
		}catch(FileNotFoundException e){
			storedRow = 0;
			e.printStackTrace();
		}
		catch(IOException e){e.printStackTrace();}
	}
	/**
	 * 저장되어있던 Col의 값을 불러와 storedCol에 불러온다
	 */
	private static void loadStoredCol(){
		try{
			FileReader loadedFile = new FileReader(FILE_PATH);
			char[] buf = new char[6];
			loadedFile.read(buf);
			storedCol = (buf[3] - '0') * 100;
			storedCol += (buf[4] - '0') * 10;
			storedCol += (buf[5] - '0');
			
			if(storedCol == 999)
				storedCol = -1;
			loadedFile.close();
		}catch(FileNotFoundException e){
			storedCol = -1;
			e.printStackTrace();
		}
		catch(IOException e){e.printStackTrace();}
	}
	/**
	 * 저장되어있는 TableStatusScreen개수에 맞게 TableStatusScreenList의 TableStatusScreen들을 생성해준다.
	 */
	public static void loadTableStatusScreens(){
		loadStoredRow();
		loadStoredCol();
		for(int i = 0; i <= storedRow; i++){
			if(i == storedRow){
				for(int j = 0; j <= storedCol; j++){
					TableStatusScreenList.createTableStatusScreen();
				}
			}
			else{
				for(int j = 0; j < TableStatusScreenList.getMaxCol(); j++){
					TableStatusScreenList.createTableStatusScreen();
				}
			}
		}
	}
}
