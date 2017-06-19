
public class Counter {
	static private int tableCount = 0;
	
	static public int getTableCount(){
		return tableCount;
	}
	static public void addTableCount(){
		tableCount++;
	}
}
