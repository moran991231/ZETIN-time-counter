package Study;

import java.awt.Color;

public class TimerDownThread extends Thread {
	public static boolean isActive = false;
	public static boolean isPaused = false;

	private long targetTime, remainedTime, pausedRemainedTime;
	private int min, sec, ms;
	private String timeStr;
	private static TimeWindow timeWindow;

	public static long limitTime = 240_000;	// 4min = 240_000 = 4*60*1000ms
	public static long penaltyTime; // [ms]

	private final static Color PALE_GREEN = new Color(173, 223, 179);
	private final Color CORAL = new Color(255, 102, 102);

	
	// Generator
	public TimerDownThread(TimeWindow t) { 
		
		targetTime = System.currentTimeMillis() + limitTime ; 
		timeWindow = t;
	}

	@Override
	public void run() {
		try {
			while (true) {
				
				remainedTime = targetTime- penaltyTime - System.currentTimeMillis();
				
				if (remainedTime > 0) {
					min = (int) remainedTime / 60_000;
					sec = (int) remainedTime / 1000 % 60;
					ms = (int) remainedTime % 1000;
					timeStr = String.format("%02d:%02d.%03d", min, sec, ms);
					timeWindow.setTime4minLabelText(timeStr);

					TimerDownThread.sleep(1);
					
				} else {
					TimerDownThread.isActive = false;
					
					timeWindow.setTime4minLabelText("00:00.000");
					timeWindow.time4minLabel.setBackground(CORAL);
					ButtonWindow.penalty4minTextField.setEditable(true);
					ButtonWindow.limitTimeTextField.setEditable(true);
					PlaySound.end4min();
					return;
				}
			}
		} catch (InterruptedException e) {
			// e.printStackTrace();
		}
	}
	
	
	public void pause() {
		pausedRemainedTime = remainedTime+ penaltyTime;
		limitTime= pausedRemainedTime;
	}
	
	public static String longTimeToString(long time) {
		int min = (int) (time / 60_000);
		int sec = (int) (time / 1000 % 60);
		int ms  = (int) (time % 1000);
		return String.format("%02d:%02d.%03d", min, sec, ms);
	}
	
	public static void setLimitTime (String time) {
		int limMin = Integer.parseInt(time.substring(0, 2));
		int limSec = Integer.parseInt(time.substring(3, 5));
		int limMs  = Integer.parseInt(time.substring(6));
		limitTime = limMin * 60_000 + limSec * 1000 + limMs ;
		
		//show genuine remained time on time4minLabel
		long settingTime = TimerDownThread.limitTime - TimerDownThread.penaltyTime;
		timeWindow.setTime4minLabelText(longTimeToString(settingTime));
		timeWindow.time4minLabel.setBackground(PALE_GREEN);
	}
	
	public static void resetSettings() {
		limitTime = 240_000;
		penaltyTime = 0;
	}
	
}
