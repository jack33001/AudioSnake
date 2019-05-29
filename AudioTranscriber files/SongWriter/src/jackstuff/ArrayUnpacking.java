package jackstuff;

import java.util.ArrayList;
import java.util.List;

public class ArrayUnpacking {
	
	public static int size;
	public static ArrayList<Integer> indices = new ArrayList<Integer>();
	
	public static void ArraySorter(ArrayList<Float> input) {
		
		ArrayList<Integer> centervals = new ArrayList<Integer>();
		int centerval = 0;
		int buffer = 2;
		ArrayList<List<Float>> peaks = new ArrayList<List<Float>>();
		
		for(int i = 1; i < input.size() - 1; i++) {
			if(input.get(i) > Constants.threshold && input.get(i) > input.get(i-1) && input.get(i) > input.get(i+1)) {
			centerval = i;
			centervals.add(centerval);
			indices.add(i);
			}
		}
	
			for(int i = 0; i < centervals.size(); i++) {
				List<Float> temp = input.subList(centervals.get(i) - buffer, centervals.get(i) + (buffer + 1)); 
				peaks.add(temp);
			}
			size = indices.size();;
		}
	
	public static int getsize() {
		return size;
	}
	
	public static ArrayList<Integer> getIndices(){
		return indices;
	}
}


