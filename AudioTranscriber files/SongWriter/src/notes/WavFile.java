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
protected ArrayList<byte[]> audioDataBytes = new ArrayList<byte[]>();

	public WavFile(String filePath) {
		int totalFramesRead = 0;
		File fileIn = new File(filePath);
		try {
			
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(fileIn);
			
			bytesPerFrame = audioInputStream.getFormat().getFrameSize();

			if (bytesPerFrame == AudioSystem.NOT_SPECIFIED) {
				bytesPerFrame = 1;
			}
			
			byte[] audioBuffer = new byte[Constants.WAV_BLOCK_SIZE*bytesPerFrame];
			
			int numBytesRead = 0;
			int numFramesRead = 0;
			
			while ((numBytesRead = audioInputStream.read(audioBuffer)) != -1) {
				numFramesRead = numBytesRead / bytesPerFrame;
				totalFramesRead += numFramesRead;
				
				if (numBytesRead == Constants.WAV_BLOCK_SIZE*bytesPerFrame) {
					byte[] tempBuffer = new byte[audioBuffer.length];
					for (int i = 0; i < audioBuffer.length; i++) tempBuffer[i] = audioBuffer[i];
					audioDataBytes.add(tempBuffer);
				}
			}
			
			System.out.println("\nLoaded .wav file at " + filePath);
			System.out.printf("Read %d sample blocks\n", audioDataBytes.size());
			System.out.printf("Read %d frames\n", totalFramesRead);
		}
		catch (Exception e) {
			System.out.println("Failed to open audio file: " + e);
			System.exit(0);
		}
	}
	
	public byte[] getSampleBlock(int startIndex) { //returns block of samples (from audioDataBytes ArrayList), of size Constants.WAV_BLOCK_SIZE
		if (startIndex < audioDataBytes.size()) {
			int numSamples = audioDataBytes.get(startIndex).length;
			byte[] output = new byte[numSamples];
			for (int i = 0; i < numSamples; i++) {
				output[i] = audioDataBytes.get(startIndex)[i];
			}
			return output;
		}
		else {
			byte[] output = new byte[Constants.WAV_BLOCK_SIZE*bytesPerFrame];
			return output;
		}
	}
	
	public byte[] getAudioData(int channel) {
		ArrayList<Byte> samples = new ArrayList<Byte>();
		int counter = 0;
		for (byte[] block : audioDataBytes) {
			for (byte sample : block) {
				if (counter % bytesPerFrame == channel) { //prevents IndexOutOfBounds exception and only looks at one of the audio tracks (normally two, L and R)
					samples.add(sample);
				}
				counter++;
			}
		}
		
		byte[] samplesArr = new byte[samples.size()];
		for (int i = 0; i < samplesArr.length; i++) samplesArr[i] = samples.get(i);
		
		return samplesArr;
	}
	
}
