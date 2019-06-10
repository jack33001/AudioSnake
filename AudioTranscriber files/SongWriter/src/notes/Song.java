package notes;

import java.util.ArrayList;
import constants.Constants;

public class Song {
	private int[][] songTable;
	
	public Song(ArrayList<ArrayList<Integer>> frequencyTable) {
		songTable = new int[frequencyTable.size()][Constants.NUM_NOTES];
		
		for (int i = 0; i < songTable.length; i++) {
			songTable[i] = NoteAnalysis.getNotes(frequencyTable.get(i));
		}
	}
	
	public int[][] getSongTable() {
		return songTable;
	}
	
	public int[] getNotesAt(double time) {
		for (int i = 0; i < songTable.length; i++) {
			if (time < i*Constants.TIME_STEP) {
				return songTable[i];
			}
		}
		return songTable[0];
	}
}
