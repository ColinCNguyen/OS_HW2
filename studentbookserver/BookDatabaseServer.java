/**
 * This implements a thread farm/pool to create a multi-threaded database
 * server.
 *
 * @author	T.Sergeant
 * @version Student Version for OS Homework
 *
 * <pre>
 * Here are some facts about this version:
 *	 1. Allows up to a fixed number of connections
 *	 2. Allows more connections (welcome connections) than it
 *	    can deal with simultaneously ... causing clients to crash
 *     (but only when they try to send)
 *	 3. Concurrent inserts or deletions could wreak havoc
 *	 4. Concurrent "db.saveState()" commands could wreak havoc
 *
 * Possible Improvements:
 *	1. Search by other fields
 *	2. Set up a protocol so that if connections are full clients will be
 *    notified and then exit gracefully.
 *    (more complicated/advanced: allow client the option to wait or quit
 *    if connections are all in use)
 *	OR remove arbitrary upper limit on # of connections	(NOTE: it is
 *    useful to limit max number of connections because it can overload
 *    the server machine)
 *	3. fix critical section problems noted in #3 and #4 above.
 * </pre>
 */

import java.net.ServerSocket;
import java.util.concurrent.Semaphore;

public class BookDatabaseServer
{
	public static void main(String [] args) throws Exception
	{
		
		BookDatabase db= new BookDatabase("books.txt");	// be sure this file exists!
		ServerSocket welcome= new ServerSocket(6789);   // we'll use port 6789
		MyThreadManager tm= new MyThreadManager(3);     // for testing we'll allow up to 3 clients at a time

		do {
			tm.assign(welcome.accept(), db);
		} while (true);
	}
}
