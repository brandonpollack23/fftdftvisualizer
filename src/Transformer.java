import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;


public abstract class Transformer implements Runnable
{
	Queue<double[]> queue;
	int[] datas;
	int numBins;
	int samplingFreq;
	final int REFRESH_RATE = 60;
	
	public Transformer(Queue<double[]> queue, Song song, int numBins)
	{
		this.queue = queue;
		this.datas = song.getData();
		this.numBins = numBins;
		this.samplingFreq = song.getSamplingFreq();
	}
	
	public abstract double[] transform(int startPos); //to be implemeted by children
}
