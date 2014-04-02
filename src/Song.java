// TODO: Actually test this code
import java.io.*;
import java.util.*;
import WavFile.*;

public class Song
{
	private final int[][] DATA;
	private final int N_CHANNELS;
	private final long SAMPLING_FREQUENCY;

	private int EST_LEN = 1024 * 1024;
	private int DEF_FRAME_CHUNK_SZ = 1024;
	
	public Song(File file) throws Exception
	{
		try
		{
			int[] buffer;
			int framesRead;
			ArrayList<Integer> list = new ArrayList<Integer>(EST_LEN);
			WavFile wav = WavFile.openWavFile(file);

			wav.display();

			N_CHANNELS = wav.getNumChannels();
			SAMPLING_FREQUENCY = wav.getSampleRate();

			buffer = new int[DEF_FRAME_CHUNK_SZ * N_CHANNELS];

			// Put WAV data into 'list'
			do
			{
				framesRead = wav.readFrames(buffer, DEF_FRAME_CHUNK_SZ);

				for (int i = 0; i < framesRead * N_CHANNELS; i++)
					list.add((Integer)buffer[i]);
			} while (framesRead != 0);

			// Initialize 'DATA'
			DATA = new int[N_CHANNELS][list.size() / N_CHANNELS];
			for (int i = 0; i < N_CHANNELS; i++)
				for (int j = 0, k = 0; j < list.size(); j += N_CHANNELS, k++)
					DATA[i][k] = (int)list.get(j + i);

			wav.close();
		}
		catch (IOException|WavFileException e)
		{
			throw new Exception("Exception when importing WAV file\n");
		}
	}
	
	public int[] getData()
	{
		// For now, only mono data returned
		return DATA[0];
	}

	public int getSamplingFreq() throws Exception
	{
		if (SAMPLING_FREQUENCY > Integer.MAX_VALUE)
			throw new Exception("SAMPLING_FREQUENCY out of range for int\n");
		return (int)SAMPLING_FREQUENCY;
	}
}

