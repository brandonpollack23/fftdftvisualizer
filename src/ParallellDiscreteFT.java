import java.util.concurrent.ConcurrentLinkedQueue;

public class ParallellDiscreteFT extends SequentialDiscreteFT
{
	public ParallellDiscreteFT(ConcurrentLinkedQueue<double[]> queue, Song song, int numBins) throws Exception
	{
		super(queue, song, numBins);
	}
	
	@Override
	public void run()
	{

	}
}
