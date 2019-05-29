package jackstuff;

import java.awt.Color;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Fourier {
	public static void main( String[] argv ) throws FileNotFoundException, Exception
	{
		//Number of samples to take (higher number more accurate but slower
		int numsamples = Constants.numsamples;
		//Sample rate of the file (total frequency range is 1/samplerate)
		int samplerate = Constants.samplerate;
		//File input
		String filelocation = "C:\\Users\\butlerj2906\\Desktop\\AudioTranscriber files\\CMajor.wav";
		WaveDecoder decoder = new WaveDecoder( new FileInputStream( filelocation ) );
		//Contains all the samples within the entire file
		ArrayList<Float> allSamples = new ArrayList<Float>( );
		//Contains the aformentioned samples, but in a format that FFT will take
		float[] samples = new float[numsamples];
		//Actual instance of the transform
		FFT fft = new FFT(numsamples, samplerate );
	
		
		//number of samples = 441000/filelength
		
		//No idea why you need to use the decoder on an empty array, but I think it's important
		while( decoder.readSamples( samples ) > 0 )
		{
			for( int i = 0; i < samples.length; i++ )
				allSamples.add( samples[i] );
		}
		
		System.out.println("Allsamples: " + allSamples.size());
		
		//need to make this detect if allsamples.length is a power of two
		while (true) {
			if (!(PowerOfTwo.isPowerOfTwo(allSamples.size())))
				allSamples.add(null);
			else
				break;
		}
		
		
		
		
		//Sets the samples matrix equal to the first samples.length number of values in allSamples
		samples = new float[allSamples.size()];
		for( int i = 0; i < samples.length; i++ )
			samples[i] = allSamples.get(i);
		
		System.out.println("Time size: " + FFT.timeSize);
		System.out.println("Samples: " + samples.length);
		
		//Performing the fourier transform
		fft.forward(samples);
		
		
		//Creates an array that holds amplitudes at each sample location
		float [] frequencypeaks = fft.getSpectrum();
		
		
		//Turning the fft output into an arraylist for easy manipulation
		ArrayList<Float> freqpeaks = new ArrayList<Float>();
		for (float freq : frequencypeaks) {
			freqpeaks.add(freq);
		}
		
		//Finds peaks and fills an arraylist with their indices
		ArrayUnpacking.ArraySorter(freqpeaks);
		
		
		//Printing peak frequencies
		ArrayList <Integer> indices = ArrayUnpacking.getIndices();
		for(int i = 0;i < indices.size(); i++) {
		System.out.println("Approx Frequency of Peak " + (i+1) + ": " + (indices.get(i) * samplerate / numsamples));
		}
		
		Plot plot = new Plot( "Output Spectrum", 512, 512);
		plot.plot(fft.getSpectrum(), 32, Color.red );
		
	}
		
}


