package jackstuff;

import java.util.Arrays;
public class ArrayChopper{
	
	
	public static float[] chop(float[] peaksin, int length){
		float[] copy = Arrays.copyOfRange(peaksin, length, peaksin.length);
		return copy;
	}

}
