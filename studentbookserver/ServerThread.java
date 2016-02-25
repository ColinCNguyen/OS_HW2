/**
 * Provides the runnable interface to allow multiple server threads to interact
 * with clients.
 *
 * @author	T.Sergeant
 * @version Student Version for OS Homework
 *
 */

import java.util.Scanner;
import java.util.concurrent.Semaphore;
import java.io.PrintStream;
import java.net.Socket;

public class ServerThread implements Runnable
{
	private Socket con;       // socket on which connections are accepted
	private BookDatabase db;  // database object shared by server threads
	private static Semaphore reader;
	private static Semaphore dbw;
	private static int numReaders;

	/**
	 * We require a socket and database object.
	 */
	public ServerThread(Socket con, BookDatabase db, int numR, Semaphore read,  Semaphore write)
	{
		this.con= con;
		this.db= db;
		numReaders = numR;
		reader = read;
		dbw = write;
	}


	/**
	 * Listen for client requests and respond.
	 */
	public void run()
	{
		int code,pos;
		boolean madeChanges= false;
		String bookStr;

		try {
		Scanner in= new Scanner(con.getInputStream());
		PrintStream out= new PrintStream(con.getOutputStream());
		SearchRequest request= new SearchRequest(db,"",0,db.size());
	
		while (in.hasNextInt()) {
			code= in.nextInt();
			in.nextLine();
			switch (code) {
				case 1: //------- search request
					request.startPos= 0;
					request.stopPos= db.size();			// just in case size has changed since last search
					request.searchVal= in.nextLine();
					reader.acquire();
					numReaders++;
					if(numReaders == 1){
						dbw.acquire();
					}
					reader.release();
					db.searchByTitle(request);
					while (request.foundPos >= 0) {
						out.printf("[%4d] %s\n",request.foundPos,db.getBook(request.foundPos));
						request.findNext();
						db.searchByTitle(request);
					}
					out.println("--- End of List ---");
					reader.acquire();
					numReaders--;
					if(numReaders == 0)
						dbw.release();
					reader.release();
					break;
				case 2: //------- insert request
					bookStr= in.nextLine();
					dbw.acquire();
					System.out.println("Book str: "+bookStr);
					if (db.insertBook(bookStr)) {
						out.println("success");
						madeChanges= true;
					}
					else
						out.println("failure");
					System.out.println("done with insert");
					dbw.release();
					
					break;
				case 3: //------- delete request
					dbw.acquire();
					if (db.removeBook(Integer.parseInt(in.nextLine()))) {
						madeChanges= true;
						out.println("success");
					}
					else
						out.println("failure");
					dbw.release();
					break;
			}
		}
			con.close();
			dbw.acquire();
			if (madeChanges) db.saveState();
			dbw.release();

		} catch (Exception e) {}
	}
}
