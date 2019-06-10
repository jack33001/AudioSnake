package jackstuff;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import constants.Constants;

public class ArrayUnpacking {
	
	public static int size;
	public static ArrayList<Integer> indices = new ArrayList<Integer>();
	
	public static void ArraySorter(ArrayList<Float> input, double[] median) {
		
		ArrayList<Integer> centervals = new ArrayList<Integer>();
		int centerval = 0;
		int buffer = 1;
		ArrayList<List<Float>> peaks = new ArrayList<List<Float>>();
		
		
		for(int i = 1; i < input.size() - 1; i++) {
			if(input.get(i) > median[i] && input.get(i) > input.get(i-1) && input.get(i) > input.get(i+1)) {
			centerval = i;
			centervals.add(centerval);
			indices.add(i);
			}
		}
	
			for(int i = 0; i < centervals.size(); i++) {
				if (i != 0 && i != centervals.size()) {
				List<Float> temp = input.subList(centervals.get(i) - buffer, centervals.get(i) + (buffer + 1)); 
				peaks.add(temp);
				}
			}
			size = indices.size();;
		}
	
	public static int getsize() {
		return size;
	}
	
	public static ArrayList<Integer> getIndices(){
		return indices;
	}
	
	public static void ResetIndices() {
		indices.clear();
	}
	
	public static double median(double[] numArray) {
	double[] replacement = numArray;
	Arrays.sort(replacement);
		
	double median;
	if (replacement.length % 2 == 0)
	    median = ((double)replacement[replacement.length/2] + (double)replacement[replacement.length/2 - 1])/2;
	else
	    median = (double) replacement[replacement.length/2];
	
	
	return median;
	}
	
}


