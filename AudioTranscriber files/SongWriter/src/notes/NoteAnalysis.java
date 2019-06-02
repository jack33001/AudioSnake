package notes;
import constants.Constants;

//Use this frequency chart for pitch reference: http://pages.mtu.edu/~suits/notefreqs.html

//import constants.Constants;

public class NoteAnalysis {
	
	private static final double[] baseFrequencies = {27.50, 30.87, 16.35, 18.35, 20.6, 21.83, 24.5};
	private static final double[] baseSharpFrequencies = {17.32, 19.45, 23.12, 25.96, 29.14};
	
	private static double log(double num, int base) {
		return Math.log(num) / Math.log(base);
	}
	
	public static String getNote(double frequency) {
		double note;
		double noteDist;
		double minDist = 1.0;
		int minFreq = 0;
		double minNote = 0.0;
		
		for (int i = 0; i < baseFrequencies.length; i++) {
			//for each base frequency, find how close to an octave the note is
			note = log(frequency / baseFrequencies[i], 2); 
			noteDist = Math.abs(note - Math.round(note));
			
			//hunt for closest match to whole note
			if (noteDist < minDist) {
				minDist = noteDist;
				minFreq = i;
				minNote = note;
			}
		}

		for (int j = 0; j < baseSharpFrequencies.length; j++) { //check if note is sharp
			note = log(frequency / baseSharpFrequencies[j], 2);
			noteDist = Math.abs(note - Math.round(note));
			
			if (noteDist < minDist) { //if a sharp frequency is closer than the whole note, must be sharp/flat
				//System.out.println(minNote);
				
				if (minNote - Math.round(minNote) < 0) { //make sure we only get sharp notes
					System.out.println((char)(64 + minFreq) + "#" + (Math.round(minNote)));
					return (char)(64 + minFreq) + "#" + (Math.round(minNote));
				}
				else {
					System.out.println((char)(65 + minFreq) + "#" + (Math.round(minNote)));
					return (char)(65 + minFreq) + "#" + (Math.round(minNote));
				}
			}
		}

		System.out.println((char)(65 + minFreq) + "" + (Math.round(minNote)));
		return (char)(65 + minFreq) + "" + (Math.round(minNote));
	}
	
	public static int[] getNotes(double[] frequencies) {
		int[] noteArr = new int[Constants.NUM_NOTES];
		for (int i = 0; i < noteArr.length; i++) noteArr[i] = 0;
		
		for (int i = 0; i < frequencies.length; i++) {
			String note = getNote(frequencies[i]);	
			int noteVal;
			
			switch (note.charAt(0)) {
				case 'C':
					noteVal = 0;
					break;
				case 'D':
					noteVal = 2;
					break;
				case 'E':
					noteVal = 4;
					break;
				case 'F':
					noteVal = 5;
					break;
				case 'G':
					noteVal = 7;
					break;
				case 'A':
					noteVal = 9;
					break;
				case 'B':
					noteVal = 11;
					break;
				default:
					noteVal = 0;
					break;
			}
			noteArr[(note.length() == 2) ? noteVal + 12*(int)(note.charAt(1) - '0') : noteVal + 1 + 12*(int)(note.charAt(2) - '0')] = 1;
			
		}
		
		return noteArr;
	}

	
	public static void main(String[] args) {
		double[] frequencies = {16.35, 18.35, 20.6, 23.12, 29.14, 24.5, 2093.0};
		
		int notes[] = getNotes(frequencies);
		
		for (int note : notes) System.out.print(note + " ");
	}
}
