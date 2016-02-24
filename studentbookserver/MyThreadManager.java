/**
 * Keeps track of which threads are busy and which ones can be assigned work.
 *
 * @author	T.Sergeant
 * @version Student Version for OS Homework
 *
 */

import java.net.Socket;
import java.util.concurrent.Semaphore;

public class MyThreadManager
{
	private boolean [] busy;   // which threads are busy
	private Thread [] workers; // array of threads
	private Semaphore readers = new Semaphore(1000);
	private Semaphore writers = new Semaphore(1);
	private int numReaders = 0;

	/**
	 * We default to 5 threads in server pool.
	 */
	public MyThreadManager() { this(5); }


	/**
	 * Initially none of the threads is busy.
	 *
	 * @param n number of threads allowed in server pool
	 */
	public MyThreadManager(int n)
	{
		workers= new Thread[n];
		busy= new boolean[n];
		for (int i=0; i<n; i++)
			busy[i]= false;
	}


	/**
	 * Find the first non-busy thread and return it.
	 *
	 * @return position in array of a non-busy thread
	 */
	private int nextOpen()
	{
		for (int i=0; i<workers.length; i++)
			if (!busy[i] || workers[i].getState()==Thread.State.TERMINATED)
				return i;
		return -1;
	}


	/**
	 * Create a new server thread to handle incoming request.
	 */
	public void assign(Socket con, BookDatabase db) throws Exception
	{
		int pos;

		pos= nextOpen();	// look for an unused slot
		if (pos<0){				// if all busy then
			con.close();		// close the connection
		}
		else {
			busy[pos]= true;
			workers[pos]= new Thread(new ServerThread(con,db, numReaders, readers, writers));
			workers[pos].start();
			System.out.println("Assigned connection in slot "+pos+" to "+workers[pos].getName());
		}
	}
}
