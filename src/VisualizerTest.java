import java.util.concurrent.ConcurrentLinkedQueue;

// Pseudo-thread.
public class VisualizerTest
{
	
	// Pseudo-main.
	public static void main(String[] args)
	{	
		// Main global variables.
		int N = 1024;
		ConcurrentLinkedQueue<double[]> amplitudes = new ConcurrentLinkedQueue<double[]>();
		
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
				array[j] = Math.random()*1201;
			}
			
			amplitudes.add(array);
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