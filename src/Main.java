import java.util.concurrent.*;

public class Main
{
	public static void usage()
	{
		System.out.println("Usage:");
		System.out.println("\tMainSequentialDFT <wavefile> <method> <parallel>\n");
		System.out.println("Where method is 0 or 1 (DFT or FFT),");
		System.out.println("parallel is 0 or 1 (Sequential or Parallel),");
		System.out.println("and wavefile is a .wav file for input.");
	}

	public static void main(String[] args)
	{
		final int NBINS = 32;

		Transformer transformer;
		int method;
		boolean parallel = false;
		Song song;
		Visualizer visualizer;
		ConcurrentLinkedQueue<double[]> q;
		Thread visualizerThread;
		Thread transformerThread;

		if (args.length < 3 || args.length > 3)
		{
			usage();
			return;
		}

		// Parse arguments
		song = new Song(new File(args[0]));
		method = Integer.parseInt(args[1]);
		if (Integer.parseInt(args[2]) != 0)
			parallel = true;

		if (method == 0)
		{
			if (parallel)
				transformer = new ParallelDiscreteFT(q, song, NBINS);
			else
				transformer = new SequentialDiscreteFT(q, song, NBINS);
		}
		else if (method == 1)
		{
			if (parallel)
				transformer = new SequentialFFT(q, song, NBINS);
			else
				transformer = new ParallelFFT(q, song, NBINS);
		}
		else
		{
			System.out.println("Bad method, quitting");
			return;
		}

		// Initialize and start the visualizer
		visualizer = new Visualizer(q, NBINS);
		visualizerThread = new Thread(visualizer);
		visualizerThread.start();

		// Transformerz
		transformerThread = new Thread(transformer);
		transformerThread.start();

		try
		{
			transformerThread.join();
			visualizerThread.join();
		}
		catch (InterruptedException e)
		{
			System.out.println("Exception during join, quitting");
			return;
		}
	}
}

