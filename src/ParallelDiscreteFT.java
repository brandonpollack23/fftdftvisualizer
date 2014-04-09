import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;

public class ParallelDiscreteFT extends SequentialDiscreteFT
{
	public ParallelDiscreteFT(Queue<double[]> queue, Song song, int numBins, int startPos,
			int incSize) throws Exception
	{
		super(queue, song, numBins, startPos, incSize);
	}
	
	// TODO: WIP, Patrick
	@Override
	public void run()
	{
		int NUM_PROCS = Runtime.getRuntime().availableProcessors();
		Thread[] thread = new Thread[NUM_PROCS];
		SequentialDiscreteFT[] sequentialDiscreteFT = new SequentialDiscreteFT[NUM_PROCS];
		List<Queue<double[]>> threadQueues = new LinkedList<Queue<double[]>>();
		
		try
		{
			for (int i = 0, s = startPos; i < NUM_PROCS; i++, s += incSize)
			{
				threadQueues.add(new LinkedList<double[]>());
				sequentialDiscreteFT[i] = new SequentialDiscreteFT(threadQueues.get(i), song, numBins, s,
						incSize * NUM_PROCS);
				thread[i] = new Thread(sequentialDiscreteFT[i]);
				thread[i].start();
			}
			// TODO: Monitor the threadQueues and put the data into the main queue in order until all data is through
			
			for (int i = 0; i < NUM_PROCS; i++)
				thread[i].join();
		}
		catch (Exception e)
		{
			System.out.println("ParalleleDiscreteFT caught exception, exiting");
		}
	}
}
