import java.util.concurrent.ConcurrentLinkedQueue;

public class ParallellDiscreteFT extends SequentialDiscreteFT
{
	int numThreadsSpawned = 0;

	public ParallellDiscreteFT(ConcurrentLinkedQueue<double[]> queue, Song song, int numBins)
	{
		super(queue, song, numBins);
	}
	
	@Override
	public void run()
	{
		if(numThreadsSpawned <= Runtime.getRuntime().availableProcessors())
		{
			++numThreadsSpawned;
		}
	}
	
	@Override
	public double[] transform(int startPos)
	{
		double[] result = new double[numBins];
		
		recursiveTransform(startPos,result);
		
		return result;
	}
	
	public void recursiveTransform(int startPos, double [] result)
	{
		
	}

}
