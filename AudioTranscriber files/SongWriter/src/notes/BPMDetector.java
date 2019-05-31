package notes;

import java.util.ArrayList;
import java.util.Collections;
import constants.Constants;

public class BPMDetector extends WavFile {
	private int[] audioEnergyHistoryBuffer = new int[Constants.WAV_BLOCKS_PER_SECOND];
	private ArrayList<Integer> tempoBuffer = new ArrayList<Integer>();
	
	public BPMDetector(String filePath) {
		super(filePath);
			
	}
	
	private int getLocalInstantEnergy(int index) {
		
		int energy = 0;
		for (byte sample : getSampleBlock(index)) {
			energy += Math.pow(sample, 2);
		}
		return energy;
	}
	
	private double getLocalAverageEnergy(int index) {
		for (int i = index; i < index + Constants.WAV_BLOCKS_PER_SECOND; i++) {
			audioEnergyHistoryBuffer[i - index] = getLocalInstantEnergy(i);
		}

		double sum = 0;
		for (int instEnergy : audioEnergyHistoryBuffer) {
			sum += instEnergy;
		}	
		return sum / audioEnergyHistoryBuffer.length;
	}
	
	private boolean isBeat(int index) {
		return getLocalInstantEnergy(index) > Constants.ENERGY_PULSE_CONSTANT*getLocalAverageEnergy(index);
	}
	
	public int median(ArrayList<Integer> list) {
		ArrayList<Integer> temp = new ArrayList<Integer>(list);
		Collections.sort(temp);
		return temp.get(temp.size() / 2);
	}

	public int[] getBPM() {
		
		ArrayList<Integer> beatLocations = new ArrayList<Integer>();
		
		for (int i = 0; i < (super.audioDataBytes.size() - Constants.WAV_BLOCKS_PER_SECOND); i++) {
			if (isBeat(i)) {
				beatLocations.add(i);
				//System.out.println(i);
			}
		}
		
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
	
	public static void main(String args[]) {
		BPMDetector detector = new BPMDetector(Constants.WAVFILE_LOCATION);
		
		for (int bpm : detector.getBPM()) System.out.println(bpm);
		
	}
}
