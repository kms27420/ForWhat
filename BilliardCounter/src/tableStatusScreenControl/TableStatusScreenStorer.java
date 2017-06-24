package tableStatusScreenControl;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 
 * 현재 TableStatusScreenList의 정보를 저장하는 클래스
 * @author Kwon
 *
 */
public class TableStatusScreenStorer {
	private static final String FILE_PATH = "src\\resource\\SavedFile.txt";
	
	private TableStatusScreenStorer(){}
	
	/**
	 * 현재 TableStatusScreenList의 정보를 파일에 저장해주는 매서드
	 */
	public static void storeTableStatusScreens(){
		FileWriter fileWriter;
		char[] buf = new char[6];
		int currentRow = TableStatusScreenList.getCurrentRow();
		int currentCol = TableStatusScreenList.getCurrentCol();
		
		buf[0] = (char)(currentRow / 100 + '0');
		buf[1] = (char)(currentRow / 10 + '0');
		buf[2] = (char)(currentRow % 10+ '0');
		
		buf[3] = (char)(currentCol / 100 + '0');
		buf[4] = (char)(currentCol / 10 + '0');
		buf[5] = (char)(currentCol % 10+ '0');
		try {
			FileReader fileReader = new FileReader(FILE_PATH);
			
			fileWriter = new FileWriter(FILE_PATH);
			
			fileWriter.write(buf);
			fileWriter.flush();
			fileWriter.close();
			
			fileReader.close();
		} catch(FileNotFoundException e){
			try {
				e.printStackTrace();
				
				fileWriter = new FileWriter(FILE_PATH, true);
				
				fileWriter.write(buf);
				fileWriter.flush();
				fileWriter.close();
			} catch (IOException e2) {e2.printStackTrace();}
		}catch (IOException e) {e.printStackTrace();}
	}
}
