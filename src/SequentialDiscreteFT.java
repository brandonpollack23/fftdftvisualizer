// TODO: Can be simplified due to symmetry from inputs having only a real component 

import java.util.concurrent.ConcurrentLinkedQueue;


public class SequentialDiscreteFT extends Transformer
{
	public SequentialDiscreteFT(ConcurrentLinkedQueue<double[]> queue, Song song, int numBins) throws Exception
	{
		super(queue, song, numBins);
	}

	@Override
	public void run()
	{
		for (int i = 0; i < datas.length; i += samplingFreq / REFRESH_RATE)
		{
			queue.add(transform(i));
			queue.notify();
		}
	}

	@Override
	public double[] transform(int startPos)
	{
		double[] result = new double[numBins];
		
		for (int i = 0; i < numBins; i++)
		{
			double real = 0;
			double imag = 0;

			for (int j = 0; j < numBins; j++)
			{
				real += datas[startPos + j] * Math.cos(2 * Math.PI * i * j / numBins);
				imag += -datas[startPos + j] * Math.sin(2 * Math.PI * i * j / numBins);
			}
			//TODO: What was this for again? It's not part of the DFT on Wikipedia
			//real[i] *= (2/numBins);
			//imag[i] *= -(2/numBins);
			result[i] = Math.pow(Math.pow(real, (double)2) + Math.pow(imag, 2), (double).5);
		}
		
		return result;
	}

}
