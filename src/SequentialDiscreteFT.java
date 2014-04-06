import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

// TODO: Can be simplified due to symmetry from inputs having only a real component 

public class SequentialDiscreteFT extends Transformer
{
	protected int startPos; //this should be 0 for sequential always, however parallel utilizes it
	protected Song song;
	private int incSize; //this variable keeps track of the amout to increment for the next frame THIS thread renders (whether we are discrete or parallel)
	//in a purely sequential transform, this is equivalent to frame size
	//when we are operating in parallel, this is equivalent to lenght of data divided by number of threads
	
	public SequentialDiscreteFT(ConcurrentLinkedDeque<double[]> q, Song song, int startPos, int numBins, int incSize) throws Exception
	{
		super(q, song, numBins);
		this.startPos = startPos;
		this.song = song;
		this.incSize = incSize;
	}

	@Override
	public void run()
	{
		for (int i = startPos; i < datas.length; i += incSize)
		{
			double[] data;
			
			data = transform(i);			
			
			queue.add(data); //maybe have a length check here
		}
	}

	@Override
	public double[] transform(int startPos)
	{
		//http://www.analog.com/static/imported-files/tech_docs/dsp_book_Ch31.pdf
		//wikipedia has the complex formula that's why*/
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
			
			result[i] = Math.pow(Math.pow(real, (double)2) + Math.pow(imag, 2), (double).5);
		}
		
		return result;
	}

}
