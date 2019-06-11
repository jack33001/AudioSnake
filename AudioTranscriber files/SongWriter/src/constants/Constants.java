package constants;

//store all program constants here

public class Constants {
	public static final int HIGHEST_OCTAVE = 7;
	public static final int NUM_NOTES = 12*HIGHEST_OCTAVE;
	
	public static final String WAVFILE_LOCATION = "samples/Superstition.wav";
	public static final int WAV_SAMPLE_RATE = 44100;
	public static final int WAV_BLOCK_SIZE = 1024;
	public static final double WAV_BLOCK_BUFFER_MULT = 5.0;
	public static final int WAV_BLOCKS_BUFFER_SIZE = (int)(WAV_SAMPLE_RATE*WAV_BLOCK_BUFFER_MULT/WAV_BLOCK_SIZE); //.wav files have 44100 samples per second
	public static final double ENERGY_PULSE_CONSTANT = 1.17; //how much higher local instant sound energy must be than local average sound energy to register a beat
	/*
	 * Determined constant values:
	 * Superstition: 1.17
	 * Hearthstone: 1.1
	 */
	
	public static final int TEMPO_BUFFER_LENGTH = 11; //number of values to find median of for bpm array. Should be an odd number
	public static final double PULSE_FACTOR = 4;
	public static final int FADE_CONST = 10;
	public static final int baseSnakeSize = 8;
	public static final double SPEED_CONSTANT = 0.01;
	
	public static final double TIME_STEP = (double)WAV_BLOCK_SIZE / WAV_SAMPLE_RATE;
	
	public static final double NOTE_THRESHOLD = 4.75; //threshold to register note frequencies as valid melody notes	
	/*
	 * Determined constant values:
	 * Hells Bells: 5.5
	 * Superstition: 4.75
	 */
}
