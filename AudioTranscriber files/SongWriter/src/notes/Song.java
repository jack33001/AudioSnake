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
			if (i*Constants.TIME_STEP > time) {

				//System.out.println(i + " " + time);

				//for (int j : songTable[i]) System.out.print(j);
				//System.out.println();

				return songTable[i];
			}
		}
	return songTable[0];
	}
}
