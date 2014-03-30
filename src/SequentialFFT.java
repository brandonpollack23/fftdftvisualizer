import java.util.Queue;

//worth noting ONLY POWERS OF TWO BIN SIZE PLZ
public class SequentialFFT extends Transformer
{

	public SequentialFFT(Queue<double[]> queue, Song song, int numBins)
	{
		super(queue, song, numBins);
	}

	@Override
	public void run()
	{
		for(int i = 0; i < datas.length; i += samplingFreq/REFRESH_RATE)
		{
			queue.add(transform(i));
			queue.notify();
		}
	}

	@Override
	public double[] transform(int startPos)
	{
		return transform(startPos,startPos+numBins);
	}
	
	public double[] transform(int startPos, int endPos)
	{
		//Xk = even computation plus odd computation		
		double[] result = new double[numBins];		
	}

}
