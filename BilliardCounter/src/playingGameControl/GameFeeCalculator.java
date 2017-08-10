package playingGameControl;

public class GameFeeCalculator extends Thread{
	private final int BASE_TIME = 30;
	private final int FEE_PER_MIN = 150;
	
	private int usedTime = 0;
	private int totalFee = 0;
	
	private boolean isBeingPlayed = true;
	
	public GameFeeCalculator(){
		this.start();
	}
	
	public int getUsedTime(){
		return usedTime;
	}
	public int getTotalFee(){
		return totalFee;
	}
	public boolean getIsBeingPlayed(){
		return isBeingPlayed;
	}
	
	@Override
	public void run(){
		usedTime = 0;
		totalFee = BASE_TIME * FEE_PER_MIN;
		
		while(true){
			if(!isBeingPlayed)
				break;
			synchronized (this) {
				this.notify();
			}
			try{
				if(usedTime > 30 && usedTime % 10 == 1){
					totalFee += FEE_PER_MIN * 10;
				}
				sleep(60 * 1000);
				usedTime++;
			}catch(InterruptedException e2){	
				e2.printStackTrace();
				
				isBeingPlayed = false;
			}
		}
	}	
}

