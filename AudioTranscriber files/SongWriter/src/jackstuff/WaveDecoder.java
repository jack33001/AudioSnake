package jackstuff;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.PrintStream;












public class WaveDecoder
{
  private final float MAX_VALUE = 3.051851E-5F;
  



  private final EndianDataInputStream in;
  


  private final int channels;
  


  private final float sampleRate;
  


  private final int size;
  



  public WaveDecoder(InputStream stream)
    throws Exception
  {
    if (stream == null) {
      throw new IllegalArgumentException("Input stream must not be null");
    }
    in = new EndianDataInputStream(new BufferedInputStream(stream, 1048576));
    if (!in.read4ByteString().equals("RIFF")) {
      throw new IllegalArgumentException("not a wav");
    }
    in.readIntLittleEndian();
    
    if (!in.read4ByteString().equals("WAVE")) {
      throw new IllegalArgumentException("expected WAVE tag");
    }
    if (!in.read4ByteString().equals("fmt ")) {
      throw new IllegalArgumentException("expected fmt tag");
    }
    if (in.readIntLittleEndian() != 16) {
      throw new IllegalArgumentException("expected wave chunk size to be 16");
    }
    if (in.readShortLittleEndian() != 1) {
      throw new IllegalArgumentException("expected format to be 1");
    }
    channels = in.readShortLittleEndian();
    sampleRate = in.readIntLittleEndian();
    if (sampleRate != 44100.0F)
      throw new IllegalArgumentException("Not 44100 sampling rate");
    in.readIntLittleEndian();
    in.readShortLittleEndian();
    int fmt = in.readShortLittleEndian();
    
    if (fmt != 16) {
      throw new IllegalArgumentException("Only 16-bit signed format supported");
    }
    if (!in.read4ByteString().equals("data")) {
      throw new RuntimeException("expected data tag");
    }
    size = in.readIntLittleEndian();
  }
  









  public int readSamples(float[] samples)
  {
    int readSamples = 0;
    for (int i = 0; i < samples.length; i++)
    {
      float sample = 0.0F;
      try
      {
        for (int j = 0; j < channels; j++)
        {
          int shortValue = in.readShortLittleEndian();
          sample += shortValue * 3.051851E-5F;
        }
        sample /= channels;
        samples[i] = sample;
        readSamples++;
      }
      catch (Exception ex)
      {
        break;
      }
    }
    
    return readSamples;
  }
  
  public static void main(String[] args) throws FileNotFoundException, Exception
  {
    WaveDecoder decoder = new WaveDecoder(new FileInputStream("samples/sample.wav"));
    float[] samples = new float[1024];
    int readSamples = 0;
    while ((readSamples = decoder.readSamples(samples)) > 0) {
      System.out.println("read " + readSamples + " samples");
    }
  }
}
