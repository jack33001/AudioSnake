package jackstuff;

public class Constants {
	//Number of samples to take (higher number more accurate but slower
	public static int numsamples = (int)(Math.pow(2,16));
	//Sample rate of the file (total frequency range is 1/samplerate)
	public static int samplerate = 44100;
	//Threshold of amplitudes to be considered a peak
	public static int threshold = 2500;
}