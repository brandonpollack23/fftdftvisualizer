import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

public class ParallellDiscreteFT extends SequentialDiscreteFT
{
	public ParallellDiscreteFT(ConcurrentLinkedDeque<double[]> queue, Song song, int numBins, int startPos, boolean discreteParallel) throws Exception
	{
		super(queue, song, numBins);
		this.startPos = startPos;
		this.song = song;
	}
	
	// TODO: WIP, Patrick
	@Override
	public void run()
	{
		int NUM_PROCS = Runtime.getRuntime().availableProcessors();
		Thread[] thread = new Thread[NUM_PROCS];
		SequentialDiscreteFT[] sequentialDiscreteFT = new SequentialDiscreteFT[NUM_PROCS];
		Queue<double[]>[] threadQueues = new Queue[NUM_PROCS];
		
		for (int i = 0; i < NUM_PROCS; i++)
		{
			threadQueues[i] = new LinkedList<double[]>();
			sequentialDiscreteFT[i] = new SequentialDiscreteFT(threadQueues[i], song, numBins, i * something_for_incsize);
			thread[i] = new Thread(sequentialDiscreteFT[i]);
			for (int j = 0; i < NUM_PROCS; i++)
				thread[i].start();
			
			// TODO: Monitor the threadQueues and put the data into the main queue in order until all data is through
			
			for (int j = 0; i < NUM_PROCS; i++)
				thread[i].join();
		}
	}
}
