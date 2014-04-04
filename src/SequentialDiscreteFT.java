import java.util.concurrent.*;
import java.util.concurrent.locks.*;

// TODO: Can be simplified due to symmetry from inputs having only a real component 


public class SequentialDiscreteFT extends Transformer
{
<<<<<<< HEAD
	private boolean discreteParallel;
	private int startPos, endPos;
	private Song song;
	
	public SequentialDiscreteFT(ConcurrentLinkedDeque<double[]> queue, Song song, int numBins, boolean discreteParallel) throws Exception
	{
		super(queue, song, numBins);
		this.discreteParallel = discreteParallel;
		this.startPos = 0;
		this.endPos = datas.length;
		this.song = song;
	}
	
	public SequentialDiscreteFT(ConcurrentLinkedDeque<double[]> queue, Song song, int numBins, int startPos, int endPos, boolean discreteParallel) throws Exception
	{
		super(queue, song, numBins);
		this.discreteParallel = discreteParallel; //false means do this discrete, true means do in parallel
		this.startPos = startPos;
		this.endPos = endPos;
		this.song = song;
=======
	public SequentialDiscreteFT(ConcurrentLinkedQueue<double[]> queue, Song song, int numBins) throws Exception
	{
		super(queue, song, numBins);
>>>>>>> parent of 73aeb3e... Depricated the ParallelDFT class
	}

	@Override
	public void run()
	{
		for (int i = 0; i < datas.length; i += samplingFreq / REFRESH_RATE)
		{
<<<<<<< HEAD
			for (int i = startPos; i < endPos; i += samplingFreq / REFRESH_RATE)
			{
				//TODO
				//find a way to wait if this is a child of a parallel operation
				//wait for the previous entry in queue's endPos to be my startPos
				
				ReentrantLock lock = new ReentrantLock();
				
				Condition c0 = lock.newCondition();
				
				
				
				try
				{
					//TODO
					//a few ideas are to have the queue contain the last group added's thread ID
					//or some variable to keep track of who it was, and devise a way to know who is next
					
					//simple, there are only a number of threads created 0 to NUMPROCS, so we just need to say,
					//hey was the last guy's i in that for loop generating below (which we can pass to the queue) 
					//one less than mine? if it is, stop awaiting
					//I dont want to change our queue without discussing it with you guys first though
					while() c0.await();
				}
				catch(Exception e)
				{
					
				}
				
				queue.add(transform(i));
				queue.notify();
				c0.signalAll();
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
=======
			queue.add(transform(i));
			queue.notify();
>>>>>>> parent of 73aeb3e... Depricated the ParallelDFT class
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
