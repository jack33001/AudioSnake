package jackstuff;

import java.awt.Color;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * A simple example that shows how to use the {@link Plot} class.
 * Note that the plots will not be entirely synchronous to the
 * music playback. This is just an example, you should not do
 * real-time plotting with the Plot class it is just not made for
 * this.
 * 
 * @author mzechner
 *
 */
public class PlotExample 
{
	public static void main( String[] argv ) throws FileNotFoundException, Exception
	{
		WaveDecoder decoder = new WaveDecoder( new FileInputStream( "C:\\\\Users\\\\butlerj2906\\\\Desktop\\\\AudioTranscriber files\\\\CMajor.wav" ));
		ArrayList<Float> allSamples = new ArrayList<Float>( );
		float[] samples = new float[1024];

		System.out.println(samples.length);
		
		while( decoder.readSamples( samples ) > 0 )
		{
			for( int i = 0; i < samples.length; i++ )
				allSamples.add( samples[i] );
		}

		System.out.println("Samples: " + samples.length);
		
		samples = new float[allSamples.size()];
		for( int i = 0; i < samples.length; i++ )
			samples[i] = allSamples.get(i);


		System.out.println("Allsamples: " + allSamples.size());
		System.out.println("Samples: " + samples.length);
		
		
		Plot plot = new Plot( "Wave Plot", 512, 512 );
		plot.plot( samples, 44100 / 1000, Color.red );
	}
}
