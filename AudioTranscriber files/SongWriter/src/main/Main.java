package main;

import util.AePlayWave;
import constants.Constants;
import notes.BPMDetector;
import snake.Snake;

public class Main {
	public static void main(String args[]) {
		BPMDetector myDetector = new BPMDetector(Constants.WAVFILE_LOCATION); //create BPMDetector object (automatically finds all beats)
		Snake snake = new Snake(myDetector); //create snake object
		snake.startSnake(); //start the snake rendering
		
		AePlayWave aw = new AePlayWave(Constants.WAVFILE_LOCATION); //create object to play wav file
	    aw.start();   //play the wavfile
	}
}
