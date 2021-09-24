package Study;

public class TimerUpThread extends Thread {
	
	public static boolean isActive = false;

	private long oldTime, elapsedTime;
	private int min, sec, ms;
	private String timeStr;
	private TimeWindow timeWindow;
	
	public static int penaltyTime; // [ms]

	// Generator
	public TimerUpThread(TimeWindow t) { 
		
		oldTime = System.currentTimeMillis();
		this.timeWindow = t;
	}

	@Override
	public void run() {
		try {
			while (true) {
				elapsedTime = System.currentTimeMillis() - oldTime + penaltyTime;
				min = (int) elapsedTime / 60_000;
				sec = (int) elapsedTime / 1000 % 60;
				ms = (int) elapsedTime % 1000;
				timeStr = String.format("%02d:%02d.%03d", min, sec, ms);

				timeWindow.setTimeDrivingLabelText(timeStr);
				timeWindow.setTimeDrivingLabelColor(Record.compareWithRanking(timeStr));
				TimerUpThread.sleep(1);
			}

		} catch (InterruptedException e) {
			// e.printStackTrace();
		}
	}

}