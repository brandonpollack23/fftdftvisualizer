import java.util.concurrent.ConcurrentLinkedQueue;


public class SequentialDiscreteFT extends Transformer
{
	public SequentialDiscreteFT(ConcurrentLinkedQueue<double[]> queue, Song song, int numBins)
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
		double[] result = new double[datas.length];
		double[] imag = new double[datas.length];
		double[] real = new double[datas.length];
		
		for(int i = startPos; i < numBins; i++)
		{
			for(int j = startPos; j < numBins; j++)
			{
				real[i] += datas[j]*Math.cos(2*Math.PI*i*j/numBins);
				imag[i] += datas[j]*Math.sin(2*Math.PI*i*j/numBins);
			}
			real[i] *= (2/numBins);
			imag[i] *= -(2/numBins);
			result[i] = Math.pow(Math.pow(real[i],(double)2) + Math.pow(imag[i],2),(double).5);
		}
		
		return result;
	}

}
