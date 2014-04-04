/*import java.util.concurrent.ConcurrentLinkedQueue;

public class ParallellDiscreteFT extends SequentialDiscreteFT
{
	public ParallellDiscreteFT(ConcurrentLinkedQueue<double[]> queue, Song song, int numBins) throws Exception
	{
		super(queue, song, numBins);
	}
	
	@Override
	public void run()
	{
		final int NUM_PROCS = Runtime.getRuntime().availableProcessors();
		double[] transformedData = new double[numBins];
		
		for(int i = 0; i < NUM_PROCS; i++)
		{
			try
			{
				Thread thread = new Thread(super())
		}
	}
}

/*class ParallelDiscreteFTHelper implements Runnable
{
	private int startPos, endPos;
	private double[] resultArray;
	
	public ParallelDiscreteFTHelper(int startPos, int endPos, double[] resultArray)
	{
		this.startPos = startPos;
		this.endPos = endPos;
		this.resultArray = resultArray;
	}
	
	@Override
	public void run()
	{
		
	}	
}*/

//this class is now depricated