package constants;

//store all program constants here

public class Constants {
	public static final double TIME_STEP = 0.01;
	public static final int NUM_NOTES = 85;
	public static final int MIN_NOTE = 16; //(in other words, the shortest note length allowed)
	
	public static final String WAVFILE_LOCATION = "C:\\Users\\pawlakj4700\\Downloads\\africa-toto.wav";
	public static final int WAV_BLOCK_SIZE = 1024;
	public static final int WAV_BLOCKS_PER_SECOND = 43;
	public static final double ENERGY_PULSE_CONSTANT = 1.3; //how much higher local instant sound energy must be than local average sound energy to register a beat
	public static final int TEMPO_BUFFER_LENGTH = 1; //number of values to find median of for bpm array. Should be an odd number
}
