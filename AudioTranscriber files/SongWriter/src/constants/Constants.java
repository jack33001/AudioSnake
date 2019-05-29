package constants;

public class Constants {
	public static final double TIME_STEP = 0.01;
	public static final int NUM_NOTES = 85;
	public static final int TEMPO = 120;
	public static final int MIN_NOTE = 16; //(in other words, the shortest note length allowed)
	
	
	public static final double MIN_NOTE_LENGTH = 0.01; //60.0/(TEMPO*MIN_NOTE); //in seconds
	public static final String WAVFILE_LOCATION = "C:\\Users\\pawlakj4700\\Downloads\\africa-toto.wav";
	public static final int WAV_BLOCK_SIZE = 1024;
}
