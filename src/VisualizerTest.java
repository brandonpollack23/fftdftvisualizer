import java.util.LinkedList;
import java.util.Queue;

// Pseudo-thread.
public class VisualizerTest
{
	
	// Pseudo-main.
	public static void main(String[] args)
	{	
		// Main global variables.
		int N = 1024;
		Queue<double[]> amplitudes = new LinkedList<double[]>();
		
		// Start visualizer thread.
		Visualizer visualizer = new Visualizer(amplitudes, N);
		Thread thread = new Thread(visualizer); 
		thread.start();
		
		// Pseudo-transformer.
		for(int i=0; i<200; i++)
		{
			double[] array = new double[N];
			for(int j=0; j<N; j++)
			{
				array[j] = Math.random()*401;
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