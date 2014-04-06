import java.util.ArrayList;

// TODO: Can be simplified due to symmetry from inputs having only a real component 

public class SequentialDiscreteFT extends Transformer
{
	private boolean discreteParallel;
	private int startPos, endPos;
	private Song song;
	
	public SequentialDiscreteFT(ArrayList<double[]> queue, Song song, int numBins, boolean discreteParallel) throws Exception
	{
		super(queue, song, numBins);
		this.discreteParallel = discreteParallel;
		this.startPos = 0;
		this.endPos = datas.length;
		this.song = song;
	}
	
	public SequentialDiscreteFT(ArrayList<double[]> queue, Song song, int numBins, int startPos, int endPos, boolean discreteParallel) throws Exception
	{
		super(queue, song, numBins);
		this.discreteParallel = discreteParallel; //false means do this discrete, true means do in parallel
		this.startPos = startPos;
		this.endPos = endPos;
		this.song = song;
	}

	@Override
	public void run()
	{
		if(!discreteParallel)
		{
			for (int i = startPos; i < endPos; i += samplingFreq / REFRESH_RATE)
			{		
				queue.add(i,transform(i));
				queue.notify();
			}
		}
		else
		{
			final int NUM_PROCS = Runtime.getRuntime().availableProcessors();
			
			for(int i = 0; i < NUM_PROCS; i++)
			{
				Thread thread = null;
				
				int dividedSize = datas.length / NUM_PROCS;
				int startPos = i * dividedSize;
				
				try
				{
					thread = new Thread(new SequentialDiscreteFT(queue, song, numBins, startPos, startPos + dividedSize, false));
				} 
				catch (Exception e)
				{
					//UH OH!!!
				}
				
				thread.start();
			}
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
			/*real *= (2/numBins);
			imag *= -(2/numBins);
			
			//http://www.analog.com/static/imported-files/tech_docs/dsp_book_Ch31.pdf
			//wikipedia has the complex formula that's why*/
			
			//actually not needed
			result[i] = Math.pow(Math.pow(real, (double)2) + Math.pow(imag, 2), (double).5);
		}
		
		return result;
	}

}
