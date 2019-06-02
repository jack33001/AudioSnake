package notes;

import constants.Constants;

public class Song {
	private int[][] songTable;
	private double[][] frequencyTable;
	
	public Song(double[][] frequencyTable) {
		songTable = new int[frequencyTable.length][Constants.NUM_NOTES];
		
		for (int i = 0; i < frequencyTable.length; i++) {
			for (int j = 0; j < frequencyTable[0].length; j++) this.frequencyTable[i][j] = frequencyTable[i][j];
		}
		
		for (int i = 0; i < songTable.length; i++) {
			songTable[i] = NoteAnalysis.getNotes(frequencyTable[i]);
		}
	}
	
	public int[] getNoteColumn(int column) {
		int[] noteColumn = new int[songTable.length];
		
		for (int i = 0; i < songTable.length; i++) {
			noteColumn[i] = NoteAnalysis.getNotes(frequencyTable[i])[column];
		}
		
		return noteColumn;
	}
	
	
}
