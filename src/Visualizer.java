import java.awt.Color;
import java.awt.Component;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.util.LinkedList;
import java.util.Queue;

@SuppressWarnings("serial")
public class Visualizer extends Frame {
	
	// Constants.
	public final short DISPLAY_START_X = 12;
	public final short DISPLAY_START_Y = 550;
	public final short WINDOW_WIDTH = 1048;
	public final short WINDOW_HEIGHT = 600;
	
	// States.
	private final int N;
	private Queue<int[]> amplitudes = null;
	int[] presentamplitudes = null;
	
	
	// Component used to paint the amplitude bars.
	private Component painter = new Component() 
	{
		// Override the paint method to draw the amplitude bars.
		@Override
		public void paint(Graphics graphics)
		{	
			// Paint the background onto the buffered image.
			Image image = createImage(WINDOW_WIDTH, WINDOW_HEIGHT);
			Graphics imagegraphics = image.getGraphics();
			imagegraphics.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
			
			// Paint the amplitude bars onto the buffered image.
			if(presentamplitudes != null)
			{
				imagegraphics.setColor(Color.ORANGE);
				for(int i=0; i<presentamplitudes.length; i++)
				{
					imagegraphics.drawLine(i + DISPLAY_START_X, DISPLAY_START_Y - presentamplitudes[i], i + DISPLAY_START_X, DISPLAY_START_Y);
				}	
			}
			
			// Paint the buffered image onto the window.
			graphics.drawImage(image, 0, 0, WINDOW_WIDTH, WINDOW_HEIGHT, null);	
		}
	};
	
	
	// Override the default update method to stop flickering.
	@Override
	public void update(Graphics graphics)
	{
		painter.paint(graphics);
	}
	
	
	// Constructor.
	public Visualizer(Queue<int[]> amplitudes ,int N)
	{
		super("Visualizer");
		
		this.amplitudes = amplitudes;
		this.N = N;
		
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		setVisible(true);
		setBackground(Color.BLACK);
		painter.setVisible(true);
		add(painter);
	}
	
	
	// Pseudo-main.
	public static void main(String[] args)
	{	
		int N = 1024;
		Queue<int[]> amplitudes = new LinkedList<int[]>();
		
		// Fill up the queue with sets of random amplitudes.
		for(int i=0; i<200; i++)
		{
			int[] array = new int[N];
			for(int j=0; j<N; j++)
			{
				array[j] = (int)(Math.random()*401);
			}
			amplitudes.add(array);
		}
		
		// Test the visualizers.
		Visualizer visualizer = new Visualizer(amplitudes, N);

		while(!amplitudes.isEmpty())
		{
			visualizer.presentamplitudes = amplitudes.poll();
			visualizer.repaint();
			try {
				Thread.sleep(17);
			} 
			catch (InterruptedException e) {}
		}
	
		visualizer.dispose();	
	}	
}