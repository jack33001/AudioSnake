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
			int debug_counter = 0;
			
			while ((numBytesRead = audioInputStream.read(audioBuffer)) != -1) {
				numFramesRead = numBytesRead / bytesPerFrame;
				totalFramesRead += numFramesRead;
				
				byte[] tempBuffer = new byte[audioBuffer.length];
				for (int i = 0; i < audioBuffer.length; i++) tempBuffer[i] = audioBuffer[i];
				audioDataBytes.add(tempBuffer);
				
				debug_counter++;
			}
			System.out.println(totalFramesRead);
			
			//for (int i = 0; i < 20; i++) System.out.print(audioDataBytes.get(0)[i] + " ");
			//System.out.println();
		}
		catch (Exception e) {
			System.out.println("Failed to open audio file");
		}
	}
	
	public byte[] getSampleBlock(int startIndex) { //returns block of samples (from audioDataBytes ArrayList), of size Constants.WAV_BLOCK_SIZE
		int numSamples = audioDataBytes.get(startIndex).length;
		byte[] output = new byte[numSamples];
		for (int i = 0; i < numSamples; i++) {
			output[i] = audioDataBytes.get(startIndex)[i];
		}
		return output;
	}
	
	public static void main(String[] args) {
		System.out.println("Loading .wav file at " + Constants.WAVFILE_LOCATION);
		WavFile music = new WavFile(Constants.WAVFILE_LOCATION);
		for (byte byt : music.getSampleBlock(5)) System.out.print(byt + " ");
	}
}
