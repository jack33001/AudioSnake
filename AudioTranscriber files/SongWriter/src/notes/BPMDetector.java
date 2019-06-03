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
	
	public BPMDetector(String filePath) {
		super(filePath);	
	}
	
	public ArrayList<Integer> getBeatLocations() {
		return beatLocations;
	}
	
	//find instantaneous energy by looking at last 1024 frames (~1/43rd second) and finding total energy
	private int getLocalInstantEnergy(int index) {  
		
		int energy = 0;
		for (byte sample : getSampleBlock(index)) {
			energy += Math.pow(sample, 2);
		}
		return energy;
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
	
	/*
	public double[] medianFilter(double[] array) {
		for (int i = 0; i < array.length; i++) {
			
		}
	}
	*/
	
	//returns array of BPM values, each value represents a beat in the song. 
	public int[] getBPM() {
		
		System.out.println("Add time tracking to BPM detection!");
		
		System.out.println("Finding beats...");
		for (int i = 0; i < (super.audioDataBytes.size() - Constants.WAV_BLOCKS_BUFFER_SIZE); i++) {
			if (isBeat(i)) {
				beatLocations.add(i);
			}
			if (i % 1000 == 0) System.out.printf("Searched %d of %d blocks\n", i, super.audioDataBytes.size());
		}
		System.out.printf("Found %d beats\n", beatLocations.size());
		
		int[] bpm = new int[beatLocations.size()];
		
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
		BPMDetector detector = new BPMDetector(Constants.WAVFILE_LOCATION);
		int[] bpms = detector.getBPM();
		
		double[] audioData = Plot2D.toDoubleArray(detector.getAudioData(0));
		double[] fullTimesArray = new double[audioData.length];
		for (int i = 0; i < fullTimesArray.length; i++) fullTimesArray[i] = i / Constants.WAV_SAMPLE_RATE;
		
		ArrayList<Double> averageEnergies = new ArrayList<Double>();
		ArrayList<Double> instantEnergies = new ArrayList<Double>();
		ArrayList<Double> times = new ArrayList<Double>();
		
		int maxGraphBlock = detector.audioDataBytes.size() - Constants.WAV_BLOCKS_BUFFER_SIZE;
		
		for (int i = 0; i < maxGraphBlock; i++) {
			if (i % 1000 == 0) System.out.printf("Found energy for %d of %d blocks\n", i, maxGraphBlock);
			averageEnergies.add(detector.getLocalAverageEnergy(i));
			instantEnergies.add((double)detector.getLocalInstantEnergy(i));
			times.add((double)i * Constants.WAV_BLOCK_SIZE / Constants.WAV_SAMPLE_RATE);
		}

		double[] averageEnergiesArr = new double[averageEnergies.size()];
		double[] instantEnergiesArr = new double[instantEnergies.size()];
		double[] thresholdEnergiesArr = new double[averageEnergies.size()];
		double[] timesArr = new double[times.size()];
		double[] beatTimes = new double[detector.getBeatLocations().size()];
		
		for (int i = 0; i < averageEnergiesArr.length; i++) {
			averageEnergiesArr[i] = averageEnergies.get(i);
			instantEnergiesArr[i] = instantEnergies.get(i);
			thresholdEnergiesArr[i] = averageEnergies.get(i)*Constants.ENERGY_PULSE_CONSTANT;
			timesArr[i] = times.get(i);
		}
		
		for (int i = 0; i < beatTimes.length; i++) {
			beatTimes[i] = detector.getBeatLocations().get(i)*Constants.WAV_BLOCK_SIZE/Constants.WAV_SAMPLE_RATE;
		}
		
		Plot2D plot = new Plot2D("Sound Energy", "Seconds", "Energy", 1600, 600);
		plot.addData("Instantaneous Energy", timesArr, instantEnergiesArr);
		plot.addData("Average Energy", timesArr, averageEnergiesArr);
		plot.addData("Threshold Energy", timesArr, thresholdEnergiesArr);
		plot.showPlot();
		
		Plot2D plot2 = new Plot2D("BPM", "Seconds", "BPM");
		plot2.addData("BPM", beatTimes, Plot2D.toDoubleArray(bpms));
		plot2.showPlot();
		
	}
}