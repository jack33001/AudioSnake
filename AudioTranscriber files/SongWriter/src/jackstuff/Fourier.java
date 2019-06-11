package jackstuff;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import constants.Constants;

public class Fourier {
	public static float storeval;

	public static ArrayList<ArrayList<Integer>> FFTFile(String filepath) throws FileNotFoundException, Exception
	{
		System.out.println("Running Fourier transformation calculations...");
		//File input
//		String filepath = "C:\\Users\\butlerj2906\\Desktop\\AudioTranscriber files\\CSharpMajorScale.wav";
		
		FileInputStream input = new FileInputStream(filepath);
		
		//Number of samples to take (higher number more accurate but slower
		int WAV_BLOCK_SIZEs = Constants.WAV_BLOCK_SIZE;
		//Sample rate of the file (total frequency range is 1/WAV_SAMPLE_RATE)
		int WAV_SAMPLE_RATE = Constants.WAV_SAMPLE_RATE;
		//Puts the audio file into something readable by the FFT
		WaveDecoder decoder = new WaveDecoder(input);
		//Contains all the samples within the entire file
		ArrayList<Float> allSamples = new ArrayList<Float>( );
		//Contains the aformentioned samples, but in a format that FFT will take
		float[] samples = new float[WAV_BLOCK_SIZEs];
		//Actual instance of the transform
		FFT fft = new FFT(WAV_BLOCK_SIZEs, WAV_SAMPLE_RATE );
		//Arraylist containing arraylists full of frequencies for each 44t of a second
		ArrayList<ArrayList<Integer>> frames = new ArrayList<ArrayList<Integer>>();
		//Arraylist containing frequencies, that we add to Frames in order to output a pack of frequencies
		ArrayList<Integer> frequencies = new ArrayList<Integer>();
		
		//number of samples = 441000/filelength
		
		//No idea why you need to use the decoder on an empty array, but I think it's important
		while( decoder.readSamples( samples ) > 0 )
		{
			for( int i = 0; i < samples.length; i++ )
				allSamples.add( samples[i] );
		}
		
		
		//need to make this detect if allsamples.length is a power of two
		//while (true) {
		//	if (!(PowerOfTwo.isPowerOfTwo(allSamples.size())))
		//		allSamples.add(null);
		//	else
		//		break;
		//}
		
		//Sets the samples matrix equal to the first samples.length number of values in allSamples
		samples = new float[allSamples.size()];
		for( int i = 0; i < samples.length; i++ )
			samples[i] = allSamples.get(i);
		
		
		int repetitions = samples.length / FFT.timeSize;
		int startpoint =  0;
		
	for (int k = 0; k < repetitions; k++) {
		
		ArrayList<Integer> notefreqs = new ArrayList<Integer>();
		
		if (k % 1000 == 0) {
			System.out.printf("Ran FFT iteration %d of %d\n", k, repetitions);
		}
		
		float[] sampleschunk = Arrays.copyOfRange(samples,startpoint,startpoint+FFT.timeSize);
				
		//Performing the fourier transform
		fft.forward(sampleschunk);
		
		
		//Creates an array that holds amplitudes at each sample location
		float [] frequencypeaks = fft.getSpectrum();
		
		
		//Turning the fft output into an arraylist for easy manipulation
		ArrayList<Float> freqpeaks = new ArrayList<Float>();
		for (float freq : frequencypeaks) {
			freqpeaks.add(freq);
		}
		
		
		
		
		
		
		startpoint += sampleschunk.length;
//		System.out.println();

		
		double[] sampleplot = new double[sampleschunk.length];
		
		for (int n = 0; n < sampleplot.length; n++) {
			sampleplot[n] = (double)sampleschunk[n];
		}
		
		double[] dfrequencypeaks = new double[frequencypeaks.length];
		
		for (int n = 0; n < frequencypeaks.length; n++) {
			dfrequencypeaks[n] = (double)frequencypeaks[n];
		}
		
		double[] average = new double[dfrequencypeaks.length];
		
		for (int n = 0; n < dfrequencypeaks.length; n++) {
			storeval += dfrequencypeaks[n];
		}
		
		storeval /= dfrequencypeaks.length;
		double median = 0;
		
		double[] constavg = new double[dfrequencypeaks.length];
		
		for (int j = 0; j < dfrequencypeaks.length; j++) {
			constavg[j] = storeval;
			}
		
		for (int n = 0; n < dfrequencypeaks.length; n++) {
			median = 0;
			double[] temp = new double[50];
			for (int j = 0; j < temp.length; j++) {
				if (j + n < dfrequencypeaks.length) {
				temp[j] = dfrequencypeaks[j+n];
				}
				else {
					temp[j] = dfrequencypeaks[n];
				}
			}
			
			median = ArrayUnpacking.median(temp);
			
				
			average[n] = median + (storeval * Constants.NOTE_THRESHOLD);
		}		
		
		//Finds peaks and fills an arraylist with their indices
				ArrayUnpacking.ArraySorter(freqpeaks, average);
		
		//Printing peak frequencies
				ArrayList <Integer> indices = ArrayUnpacking.getIndices();
//				System.out.println("Indices: " + indices.size());
				for(int i = 0;i < indices.size(); i++) {
//				System.out.println("Approx Frequency of Peak " + (i+1) + ": " + (indices.get(i) * WAV_SAMPLE_RATE / WAV_BLOCK_SIZEs));
				notefreqs.add(indices.get(i) * WAV_SAMPLE_RATE / WAV_BLOCK_SIZEs);
				}
				
				if (notefreqs.size() == 0) {
					notefreqs.add(0);
				}
		
				frames.add(notefreqs);
				
				
		frequencies.clear();
		ArrayUnpacking.ResetIndices();
	}
	
	
	return frames;
}

	
}



