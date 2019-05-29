package jackstuff;

public class Testing {
	public static void main(String[] args) {
		float[] test = {1111,1112,1113,1114,1115,1116,1115,1114,1113,1112};
		Peak.peak(test);
		System.out.println(Peak.indexreached);
		System.out.println();
		
		for(float val: Peak.store) {
			System.out.println(val);
		}

		System.out.println(Peak.store.size());
	}
}
