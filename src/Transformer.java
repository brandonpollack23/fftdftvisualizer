import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;


public abstract class Transformer implements Runnable
{
	ArrayList<double[]> queue;
	int[] datas;
	int numBins;
	int samplingFreq;
	final int REFRESH_RATE = 60;
	
	public Transformer(ArrayList<double[]> queue2, Song song, int numBins) throws Exception
	{
		this.queue = queue2;
		this.datas = song.getData();
		this.numBins = numBins;
		this.samplingFreq = song.getSamplingFreq();
	}
	
	public abstract double[] transform(int startPos); //to be implemeted by children
}
