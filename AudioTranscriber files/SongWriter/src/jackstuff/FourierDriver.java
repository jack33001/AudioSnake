package jackstuff;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class FourierDriver {
	public static void main(String[] args) throws FileNotFoundException, Exception {
	ArrayList<ArrayList<Integer>> frames = new ArrayList<ArrayList<Integer>>();
	frames = Fourier.FFTFile("C:\\Users\\butlerj2906\\Desktop\\AudioTranscriber files\\AudioSnake\\AudioTranscriber files\\HellsBells.wav");
		}
}
