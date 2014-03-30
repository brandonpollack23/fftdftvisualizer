import java.awt.Color;
import java.awt.Component;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Queue;

@SuppressWarnings("serial")
public class Visualizer extends Frame implements Runnable {
	
	// Graphics constants.
	public final short DISPLAY_START_X = 12;
	public final short DISPLAY_START_Y = 550;
	public final short WINDOW_WIDTH;
	public final short WINDOW_HEIGHT = 600;
	
	// Time constants.
	public final short ONE_SIXTIETH_SECOND = 17;
	public final short TIME_OUT = 30;
	
	// States and attributes.
	private final int N;
	private Queue<double[]> amplitudes = null;
	double[] presentamplitudes = null;
	
	
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
					imagegraphics.drawLine(i + DISPLAY_START_X, DISPLAY_START_Y - (int)presentamplitudes[i], i + DISPLAY_START_X, DISPLAY_START_Y);
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
	public Visualizer(Queue<double[]> amplitudes ,int N)
	{
		super("Visualizer");
		
		this.amplitudes = amplitudes;
		this.N = N;
		
		WINDOW_WIDTH = (short) (2*DISPLAY_START_X + this.N);
		
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		setVisible(true);
		setBackground(Color.BLACK);
		painter.setVisible(true);
		add(painter);
	}
	
	
	// Visualizer Main.
	@Override
	public void run()
	{
		while(true)
		{
			// Lock access to queue.
			synchronized(amplitudes)
			{
				while(amplitudes.isEmpty())
				{
					try 
					{
						long start = System.currentTimeMillis();
						amplitudes.wait(2*TIME_OUT);
						// Self-terminate mechanism.
						if(System.currentTimeMillis() - start > TIME_OUT)
						{
							this.dispose();
							return;
						}
					} 
					catch(InterruptedException e)
					{
					}
				}
				// Obtain the preset array of amplitudes.
				presentamplitudes = amplitudes.poll();					
			}
			
			// Repaint the amplitude bars.
			repaint();
			
			// Sleep.
			try 
			{
				Thread.sleep(ONE_SIXTIETH_SECOND);
			} 
			catch (InterruptedException e) 
			{
			}
		}	
	}
}