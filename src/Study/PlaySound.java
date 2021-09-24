package Study;

import java.io.File;
import javax.sound.sampled.*;

public class PlaySound {
	
	private final static String PATH = Record.PATH;
	
	public static void end4min() {

		try {
	        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(
	        				new File(PATH+"노래\\학교종소리.wav").getAbsoluteFile());
	        Clip clip = AudioSystem.getClip();
	        clip.open(audioInputStream);
	        clip.start();
	        
	    } catch(Exception ex) {
	        System.out.println("Error with playing sound.");
	        ex.printStackTrace();
	    }
		
	}
	
	public PlaySound () {
		end4min();
	}
	
	public static void main (String[] arg) {
		//new PlaySound();
	}
}
