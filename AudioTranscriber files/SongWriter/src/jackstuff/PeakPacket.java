package jackstuff;

import java.util.ArrayList;

public class PeakPacket {
	
public static ArrayList<ArrayList> peaks = new ArrayList<ArrayList>();
		
	public static void addPeak(ArrayList<Float> input){
			peaks.add(input);
	}
}
