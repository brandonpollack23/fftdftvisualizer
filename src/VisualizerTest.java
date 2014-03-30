import java.util.LinkedList;
import java.util.Queue;

// Pseudo-thread.
public class VisualizerTest extends Thread
{
	Visualizer visualizer = null;
	
	public VisualizerTest(Queue<int[]> amplitudes, int N)
	{
		visualizer = new Visualizer(amplitudes, N);
	}
	
	public void run()
	{
		visualizer.VisualizerMain();
	}

	
	// Pseudo-main.
	public static void main(String[] args)
	{	
		// Main global variables.
		int N = 1024;
		Queue<int[]> amplitudes = new LinkedList<int[]>();
		
		// Start visualizer thread.
		VisualizerTest thread = new VisualizerTest(amplitudes, N);
		thread.start();
		
		// Pseudo-transformer.
		for(int i=0; i<200; i++)
		{
			int[] array = new int[N];
			for(int j=0; j<N; j++)
			{
				array[j] = (int)(Math.random()*401);
			}
			synchronized(amplitudes)
			{
				amplitudes.add(array);
				amplitudes.notifyAll();
			}
		}
		
		// Main cleaning code.
		try 
		{
			thread.join();
		} 
		catch(InterruptedException e) 
		{
		}
	}
}