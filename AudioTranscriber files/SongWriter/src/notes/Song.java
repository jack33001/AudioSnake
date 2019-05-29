package notes;
import constants.Constants;
public class Song {
	private NoteAnalysis songTable[];
	
	public Song(double frequencyTable[][]) {
		songTable = new NoteAnalysis[frequencyTable.length];
		
		for (int i = 0; i < songTable.length; i++) {
			songTable[i] = new NoteAnalysis(frequencyTable[i]);
		}
	}
	
	public int[] getNoteColumn(int column) {
		int[] noteColumn = new int[songTable.length];
		
		for (int i = 0; i < songTable.length; i++) {
			noteColumn[i] = songTable[i].getNoteArr()[column];
		}
		
		return noteColumn;
	}
}
