package notes;
//http://mziccard.me/2015/05/28/beats-detection-algorithms-1/
//http://archive.gamedev.net/archive/reference/programming/features/beatdetection/index.html
//https://stackoverflow.com/questions/3297749/java-reading-manipulating-and-writing-wav-files/6400178

import java.io.File;
import javax.sound.sampled.*;
import java.util.ArrayList;
import constants.Constants;

public class WavFile {
	
private int bytesPerFrame;
private ArrayList<byte[]> audioDataBytes = new ArrayList<byte[]>();
	
	public WavFile(String filePath) {
		int totalFramesRead = 0;
		File fileIn = new File(filePath);
		try {
			
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(fileIn);
			
			bytesPerFrame = audioInputStream.getFormat().getFrameSize();
			System.out.println(bytesPerFrame);
			if (bytesPerFrame == AudioSystem.NOT_SPECIFIED) {
				bytesPerFrame = 1;
			}
			
			byte[] audioBuffer = new byte[Constants.WAV_BLOCK_SIZE*bytesPerFrame];
			
			int numBytesRead = 0;
			int numFramesRead = 0;
			
			//int debug_counter = 0;
			
			while ((numBytesRead = audioInputStream.read(audioBuffer)) != -1) {
				numFramesRead = numBytesRead / bytesPerFrame;
				totalFramesRead += numFramesRead;
				audioDataBytes.add(audioBuffer);
				
				/*
				if (debug_counter % 10 == 0) {
					for (byte byt : audioDataBytes.get(audioDataBytes.size() - 1)) System.out.print(byt + " ");
					System.out.println();
				}
				debug_counter++;
				*/
			}
			System.out.println(totalFramesRead);
			
		}
		catch (Exception e) {
			
		}
	}
	
	public byte[] getSampleBlock(int startIndex) { //returns block of samples (from audioDataBytes ArrayList), of size Constants.WAV_BLOCK_SIZE
		int numSamples = audioDataBytes.get(startIndex).length;
		for (byte byt : audioDataBytes.get(startIndex)) System.out.print(byt + " ");
		byte[] output = new byte[numSamples];
		for (int i = 0; i < numSamples; i++) {
			System.out.println(audioDataBytes.get(startIndex)[i]);
			output[i] = audioDataBytes.get(startIndex)[i];
		}
		return output;
	}
	
	public static void main(String[] args) {
		System.out.println("Loading .wav file at " + Constants.WAVFILE_LOCATION);
		WavFile music = new WavFile(Constants.WAVFILE_LOCATION);
		for (byte byt : music.getSampleBlock(1)) System.out.print(byt + " ");
	}
}
