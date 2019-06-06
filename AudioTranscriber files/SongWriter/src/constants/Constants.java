package constants;

//store all program constants here

public class Constants {
	public static final double TIME_STEP = 0.01;
	public static final int NUM_NOTES = 85;
	
	public static final String WAVFILE_LOCATION = "samples/Hearthstone.wav";
	public static final int WAV_SAMPLE_RATE = 44100;
	public static final int WAV_BLOCK_SIZE = 1024;
	public static final double WAV_BLOCK_BUFFER_MULT = 5.0;
	public static final int WAV_BLOCKS_BUFFER_SIZE = (int)(WAV_SAMPLE_RATE*WAV_BLOCK_BUFFER_MULT/WAV_BLOCK_SIZE); //.wav files have 44100 samples per second
	public static final double ENERGY_PULSE_CONSTANT = 1.1; //how much higher local instant sound energy must be than local average sound energy to register a beat
	/*
	 * Determined constant values:
	 * Superstition: 1.17
	 * Hearthstone: 1.1
	 */
	
	public static final int TEMPO_BUFFER_LENGTH = 11; //number of values to find median of for bpm array. Should be an odd number
	
}
