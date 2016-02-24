
/**
 * A TCP client that interacts with a BookDatabaseServer.
 *
 * @author  T.Sergeant
 * @version Student Version for OS Homework
 *
 * <p> This version of the program attempts to connect to a server on port 6789
 * and then has the user enter a string.  The entered value is passed to
 * the server for processing.  The returned value is displayed and the user
 * reprompted to enter a string.  This continues until the string is blank.</p>
 */

import java.io.PrintStream;
import java.net.Socket;
import java.net.ServerSocket;
import java.util.Scanner;

public class BookDatabaseClient {
	public static Scanner kb = new Scanner(System.in);
	
	public static void main(String argv[]) throws Exception {
		int choice, pos;
		String searchVal, result;
		Socket clientSocket = new Socket("localhost", 6789); 				// all
		PrintStream out = new PrintStream(clientSocket.getOutputStream());	// communication
		Scanner in = new Scanner(clientSocket.getInputStream());			// on localhost,
																			// port 6789
		choice = showMenu();
		while (choice > 0) {
			out.println(choice); // tell server our type of request
			try {
				switch (choice) {
				case 1: // search
					
					System.out.print("\n\nEnter a search string: ");
					searchVal = kb.nextLine();
					out.println(searchVal);
					do {
						result = in.nextLine();
						System.out.println(result);
					} while (!result.equals("--- End of List ---"));
					
					break;

				case 2: // insert
					
					out.println(getBookString());
					result = in.nextLine();
					System.out.print("Sending book info to server: " + result);
					
					break;

				case 3: // remove book
					System.out.print("\n\nEnter position of book you want to delete: ");
					out.println(kb.nextInt());
					result = in.nextLine();
					System.out.print("Result of delete request: " + result);
					break;
				}
			} catch (Exception e) {
				System.out.println("Server is busy...too many connections. Please try again later.");
				System.exit(-1);
			}
			choice = showMenu();
		}

		clientSocket.close();
	}

	/**
	 * Display menu to user.
	 *
	 * @return user's menu selection
	 */
	public static int showMenu() {
		int choice;
		do {
			System.out.println("\n\n");
			System.out.println("[0] Exit Program");
			System.out.println("[1] Search for Book");
			System.out.println("[2] Insert a Book");
			System.out.println("[3] Delete a Book");
			System.out.print("Choice: ");
			choice = kb.nextInt();
		} while (choice < 0 || choice > 3);

		kb.nextLine();
		return choice;
	}

	/**
	 * Obtains information about a book from the user and packages it for a trip
	 * to the server.
	 *
	 * @return formatted book string fit for server
	 */
	public static String getBookString() {
		String isbn, title, author;
		int numPages, year;

		System.out.print("Enter ISBN   : ");
		isbn = kb.nextLine();
		System.out.print("Enter Title  : ");
		title = kb.nextLine();
		System.out.print("Enter Author : ");
		author = kb.nextLine();
		System.out.print("Enter # Pages: ");
		numPages = kb.nextInt();
		System.out.print("Enter Year   : ");
		year = kb.nextInt();
		return String.format("%s|%s|%s||%d|||%d", isbn, title, author, numPages, year);
	}
}
