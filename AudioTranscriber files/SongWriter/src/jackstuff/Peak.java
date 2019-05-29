package jackstuff;

import java.util.ArrayList;

public class Peak {
	
	public static int indexreached;
	public static ArrayList<Float> store = new ArrayList<Float>();
	
	public static void peak( float[] samples) {
		
		for (int i = 0; i < samples.length; i++) {
			if (samples[i] > 1000) {
			
				store.add(samples[i]);
			
				if(samples[i+1] < 1000) {
				indexreached = i;
			}
			}
			
			i++;
		}
		
		
	}
	
	public static int getIndexReached() {
		return indexreached;
	}
}
