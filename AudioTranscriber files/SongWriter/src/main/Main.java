package main;

import util.AePlayWave;

import java.io.File;
import java.io.FileNotFoundException;

import javax.sound.sampled.AudioSystem;

import constants.Constants;
import jackstuff.Fourier;
import notes.BPMDetector;
import notes.Song;
import snake.Snake;
import userio.MusicPlayer;

public class Main {
	public static void main(String args[]) throws FileNotFoundException, Exception {
		
		Song music = new Song(Fourier.FFTFile(Constants.WAVFILE_LOCATION));
		BPMDetector detector = new BPMDetector(Constants.WAVFILE_LOCATION); //create BPMDetector object (automatically finds all beats)
		Snake snake = new Snake(detector, music); //create snake object
		new AePlayWave(Constants.WAVFILE_LOCATION).start();
		
		snake.startSnake(); //start the snake rendering
		//MusicPlayer mp = new MusicPlayer();
	}
}