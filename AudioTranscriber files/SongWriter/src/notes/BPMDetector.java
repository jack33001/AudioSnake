	package notes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import constants.Constants;
import util.Plot2D;

public class BPMDetector extends WavFile {
	private int[] audioEnergyHistoryBuffer = new int[Constants.WAV_BLOCKS_BUFFER_SIZE];
	private ArrayList<Integer> tempoBuffer = new ArrayList<Integer>();
	private ArrayList<Integer> beatLocations = new ArrayList<Integer>();
	private int[] bpm = null;
	
	public BPMDetector(String filePath) {
		super(filePath);
		getBPM();
	}
	
	public ArrayList<Integer> getBeatLocations() { //returns ArrayList of beat indices, based on sample block list
		return beatLocations;
	}
	
	public int getSongLength() { //returns length of song in seconds
		return super.audioDataBytes.size() / (Constants.WAV_SAMPLE_RATE / Constants.WAV_BLOCK_SIZE);
	}
	
	//find instantaneous energy by looking at last 1024 frames (~1/43rd second) and finding total energy
	private int getLocalInstantEnergy(int index) {  
		
		int energy = 0;
		for (byte sample : getSampleBlock(index)) {
			energy += Math.pow(sample, 2);
		}
		return energy;
	}
	
	public double getLocalInstantEnergy(double time) {
		
		int index = (int) Math.round(time * Constants.WAV_SAMPLE_RATE/Constants.WAV_BLOCK_SIZE); //convert seconds to sample
		
		return getLocalInstantEnergy(index);
	}
	
	//find average energy for last second by averaging instantaneous energies
	private double getLocalAverageEnergy(int index) { 
		for (int i = index; i < index + Constants.WAV_BLOCKS_BUFFER_SIZE; i++) {
			audioEnergyHistoryBuffer[i - index] = getLocalInstantEnergy(i);
		}

		double sum = 0;
		for (int instEnergy : audioEnergyHistoryBuffer) {
			sum += instEnergy;
		}	
		return sum / audioEnergyHistoryBuffer.length;
	}
	
	public double getLocalAverageEnergy(double time) {
		int index = (int) Math.round(time * Constants.WAV_SAMPLE_RATE/Constants.WAV_BLOCK_SIZE); //convert seconds to sample

		return getLocalAverageEnergy(index);
	}
	
	//if the instantaneous energy is a certain threshold above average, we say it's a beat
	private boolean isBeat(int index) {
		return getLocalInstantEnergy(index) > Constants.ENERGY_PULSE_CONSTANT*getLocalAverageEnergy(index);
	}
	
	//median function for ArrayList, returns median value of Integer ArrayList. Used for implementing smoothing buffer
	public int median(ArrayList<Integer> list) {
		ArrayList<Integer> temp = new ArrayList<Integer>(list);
		Collections.sort(temp);
		return temp.get(temp.size() / 2);
	}
	
	public int getTempoAt(double time) {
		if (bpm == null) {
			getBPM(); //if we haven't already searched for all the beats in the song, do it before running this script
		}
		int timeIndex = (int) Math.round(time * Constants.WAV_SAMPLE_RATE/Constants.WAV_BLOCK_SIZE); //convert seconds to sample
		int indexCounter = 0;
		while (indexCounter < getBeatLocations().size() - 1 && timeIndex > getBeatLocations().get(indexCounter)) {
			indexCounter++; 
		}
		
		if (indexCounter > 0) {
			return (bpm[indexCounter - 1] + bpm[indexCounter])/2; //average of tempos at beats above and below index
		}
		else return (bpm[0] + bpm[1])/2; //in case we call the method for time 0
	}
	
	public int[] getBPM() {
		
		System.out.println("\nFinding beats...");
		
		//hunt through all the song's sample blocks, and add the block index to beatLocations if it is a beat
		for (int i = 0; i < (super.audioDataBytes.size() - Constants.WAV_BLOCKS_BUFFER_SIZE); i++) {
			if (isBeat(i) && !isBeat(i - 1) && !isBeat(i - 2)) {
				beatLocations.add(i);
			}
			if (i % 1000 == 0) System.out.printf("Searched %d of %d blocks\n", i, super.audioDataBytes.size());
		}
		System.out.printf("Found %d beats\n", beatLocations.size());
		
		bpm = new int[beatLocations.size()];
		
		for (int i = 1; i < beatLocations.size(); i++) { //use buffer to reduce spikes in tempo
			tempoBuffer.add((int)(Math.round(60.0 / ((beatLocations.get(i) - beatLocations.get(i - 1)) / 43.0)))); //add bpm value to buffer
			if (tempoBuffer.size() > Constants.TEMPO_BUFFER_LENGTH) { //if buffer is larger than TEMPO_BUFFER_LENGTH values, remove the oldest buffer value
				tempoBuffer.remove(0);
			}
			bpm[i] = median(tempoBuffer); //return the median value of the tempo as the tempo at that beat.
		}
		return bpm;
	}
	
	public static void main(String args[]) throws IOException { //run this to help determine proper value for ENERGY_PULSE_CONSTANT
		
		BPMDetector detector = new BPMDetector(Constants.WAVFILE_LOCATION); //create object for wav file analysis
		
		ArrayList<Double> averageEnergies = new ArrayList<Double>();
		ArrayList<Double> instantEnergies = new ArrayList<Double>();
		ArrayList<Double> times = new ArrayList<Double>();
		
		int maxGraphBlock = detector.audioDataBytes.size() - Constants.WAV_BLOCKS_BUFFER_SIZE; //max block index in wav file we can search without causing index error
		
		//collect energy data for plotting
		System.out.println("\nFinding energy data...");
		for (int i = 0; i < maxGraphBlock; i++) {
			if (i % 1000 == 0) System.out.printf("Found energy for %d of %d blocks\n", i, maxGraphBlock);
			averageEnergies.add(detector.getLocalAverageEnergy(i));
			instantEnergies.add((double)detector.getLocalInstantEnergy(i));
			times.add((double)i * Constants.WAV_BLOCK_SIZE / Constants.WAV_SAMPLE_RATE);
		}

		//all x and y axis arrays for plotting
		double[] averageEnergiesArr = new double[averageEnergies.size()];
		double[] instantEnergiesArr = new double[instantEnergies.size()];
		double[] thresholdEnergiesArr = new double[averageEnergies.size()];
		double[] timesArr = new double[times.size()];
		
		double[] tempoArr = new double[detector.getSongLength()];
		double[] beatTimes = new double[tempoArr.length];
		
		//store data for plotting energy
		for (int i = 0; i < averageEnergiesArr.length; i++) {
			averageEnergiesArr[i] = averageEnergies.get(i);
			instantEnergiesArr[i] = instantEnergies.get(i);
			thresholdEnergiesArr[i] = averageEnergies.get(i)*Constants.ENERGY_PULSE_CONSTANT;
			timesArr[i] = times.get(i);
		}
		
		//store data for plotting bpm
		detector.getBeatLocations(); //create beat locations array 
		for (int i = 0; i < detector.getSongLength(); i++) {
			tempoArr[i] = detector.getTempoAt(i);
			beatTimes[i] = i;
		}
		
		Plot2D plot = new Plot2D("Sound Energy", "Seconds", "Energy", 1600, 600);
		plot.addData("Instantaneous Energy", timesArr, instantEnergiesArr);
		plot.addData("Average Energy", timesArr, averageEnergiesArr);
		plot.addData("Threshold Energy", timesArr, thresholdEnergiesArr);
		plot.showPlot();
		
		Plot2D plot2 = new Plot2D("BPM", "Seconds", "BPM");
		plot2.addData("BPM", beatTimes, tempoArr);
		plot2.showPlot();
	}
}