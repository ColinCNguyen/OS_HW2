/**
 * Holds an array of Books and includes appropriate helper methods.
 *
 * @author	T.Sergeant
 * @version Student Version for OS Homework
 * @see Book
 * @see SearchRequest
 *
 * <p> There is a series of searchBy methods that will search the appropriate
 * field.	These will be executed by separate threads.	Communication with the
 * threads is handled by a shared instance of the SearchRequest class.</p>
 *
 */
import java.util.Scanner;
import java.io.File;
import java.io.PrintStream;

public class BookDatabase
{
	private Book [] book;  // list of books
	private int n;         // number of books in the list
	private String dbFile; // datafile from where books are read


	/**
	 * Given the name of a datafile of the proper form, load the array of
	 * Books.
	 *
	 * @param inputFile the name of the file to be loaded
	 */
	public BookDatabase(String inputFile) throws Exception
	{
		Scanner bookfile;
		String [] parts;	 // hold book info when split on |

		dbFile= inputFile;
		book= new Book[10000];	 // assume no more than 10000 books in data file
		bookfile= new Scanner(new File(inputFile));
		n= 0;
		while (bookfile.hasNextLine())
		{
			parts= bookfile.nextLine().split("\\|");
			book[n]= new Book(parts);
			n++;
		}
		bookfile.close();
	}


	/**
	 * Getter for size of the book list.
	 *
	 * @return the number of entries in the array
	 */
	public int size() { return n; }


	/**
	 * Returns a reference to the book at the specified position.
	 *
	 * @param pos position of element to be returned
	 * @return reference to the Book object at specified position; null if
	 * not a valid position
	 */
	public Book getBook(int pos)
	{
		if (pos>=0 && pos<n)
			return book[pos];
		else
			return null;
	}


	/**
	 * Display all books in the list.
	 */
	public void display()
	{
		int i;
		for (i=0; i<n; i++)
			System.out.printf("[%4d] %s\n",i,book[i]);
	}


	/**
	 * Searches list on ISBN field and returns (via the parameter)
	 * the position at which the entry was found.
	 *
	 * @param sr a SearchRequest object that specifies where to start and end
	 * the search as well as what value is to be searched for
	 *
	 * <p>The foundPos field of the SearchRequest parameter will be -1 if the
	 * search fails.	Otherwise it will be set to the position in the array
	 * at which the element was found.	This position value can be passed to
	 * the getBook method to obtain a reference to the book that matched.</p>
	 *
	 * <p>This search is case-sensitive but allows for a partial match.	Finding
	 * later matches can be accomplished by calling the method again with the
	 * startPos field of the SearchRequest object set to the position
	 * following the previous match.</p>
	 */
	public void searchByISBN(SearchRequest sr)
	{
		sr.foundPos= -1;
		for (int i= sr.startPos; i<sr.stopPos; i++)
			if (book[i].foundInISBN(sr.searchVal)) {
				sr.foundPos= i;
				return;
			}
	}


	/**
	 * Searches list on Title field and returns (via the parameter)
	 * the position at which the entry was found.
	 *
	 * @param sr a SearchRequest object that specifies where to start and end
	 * the search as well as what value is to be searched for
	 *
	 * <p>The foundPos field of the SearchRequest parameter will be -1 if the
	 * search fails.	Otherwise it will be set to the position in the array
	 * at which the element was found.	This position value can be passed to
	 * the getBook method to obtain a reference to the book that matched.</p>
	 *
	 * <p>This search is case-sensitive but allows for a partial match.	Finding
	 * later matches can be accomplished by calling the method again with the
	 * startPos field of the SearchRequest object set to the position
	 * following the previous match.</p>
	 */
	public void searchByTitle(SearchRequest sr)
	{
		sr.foundPos= -1;
		for (int i= sr.startPos; i<sr.stopPos; i++)
			if (book[i].foundInTitle(sr.searchVal)) {
				sr.foundPos= i;
				return;
			}
	}

	/**
	 * Searches list on Author field and returns (via the parameter)
	 * the position at which the entry was found.
	 *
	 * @param sr a SearchRequest object that specifies where to start and end
	 * the search as well as what value is to be searched for
	 *
	 * <p>The foundPos field of the SearchRequest parameter will be -1 if the
	 * search fails.	Otherwise it will be set to the position in the array
	 * at which the element was found.	This position value can be passed to
	 * the getBook method to obtain a reference to the book that matched.</p>
	 *
	 * <p>This search is case-sensitive but allows for a partial match.	Finding
	 * later matches can be accomplished by calling the method again with the
	 * startPos field of the SearchRequest object set to the position
	 * following the previous match.</p>
	 */
	public void searchByAuthor(SearchRequest sr)
	{
		sr.foundPos= -1;
		for (int i= sr.startPos; i<sr.stopPos; i++)
			if (book[i].foundInAuthor(sr.searchVal)) {
				sr.foundPos= i;
				return;
			}
	}


	/**
	 * Searches list on NumPages field and returns (via the parameter)
	 * the position at which the entry was found.
	 *
	 * @param sr a SearchRequest object that specifies where to start and end
	 * the search as well as what value is to be searched for
	 *
	 * <p>The foundPos field of the SearchRequest parameter will be -1 if the
	 * search fails.	Otherwise it will be set to the position in the array
	 * at which the element was found.	This position value can be passed to
	 * the getBook method to obtain a reference to the book that matched.</p>
	 *
	 * <p>Since the search field is an int, this search converts the search
	 * value to an int prior to the search.	Thus, it finds only exact
	 * matches.</p>
	 */
	public void searchByNumPages(SearchRequest sr)
	{
		sr.foundPos= -1;
		try {
			int sv= Integer.parseInt(sr.searchVal);
			if (sv <= 0) return;
			for (int i= sr.startPos; i<sr.stopPos; i++)
				if (book[i].matchesNumPages(sv)) {
					sr.foundPos= i;
					return;
				}
		} catch (Exception e) {} // if convert to int fails do nothing
	}

	/**
	 * Searches list on Year field and returns (via the parameter)
	 * the position at which the entry was found.
	 *
	 * @param sr a SearchRequest object that specifies where to start and end
	 * the search as well as what value is to be searched for
	 *
	 * <p>The foundPos field of the SearchRequest parameter will be -1 if the
	 * search fails.	Otherwise it will be set to the position in the array
	 * at which the element was found.	This position value can be passed to
	 * the getBook method to obtain a reference to the book that matched.</p>
	 *
	 * <p>Since the search field is an int, this search converts the search
	 * value to an int prior to the search.	Thus, it finds only exact
	 * matches.</p>
	 */
	public void searchByYear(SearchRequest sr)
	{
		sr.foundPos= -1;
		try {
			int sv= Integer.parseInt(sr.searchVal);
			sr.foundPos= -1;
			if (sv <= 0) return;
			for (int i= sr.startPos; i<sr.stopPos; i++)
				if (book[i].matchesYear(sv)) {
					sr.foundPos= i;
					return;
				}
		} catch (Exception e) {} // if convert to int fails do nothing
	}


	/**
	 * Insert a new Book into the list given individually specified * parameters.
	 *
	 * @param isbn the ISBN of the new book
	 * @param title the title of the new book
	 * @param author the author of the new book
	 * @param numPages the number of pages in the new book
	 * @param year the year of publication of the new book
	 * @return true if insert was successful; false otherwise
	 *
	 * <p> If there are already 10000 books in the list we ignore the request to
	 * add a new book.	Lazy? Yes!</p>
	 */
	public boolean insertBook(String isbn, String title, String author, int numPages, int year)
	{
		if (n>=10000) return false;
		book[n]= new Book(isbn,title,author,numPages,year);
		n++;
		return true;
	}


	/**
	 * Insert a new Book into the list given a book string.
	 *
	 * @param bookString book information having this form: isbn|title|author|numPages|year
	 * @return true if insert was successful; false otherwise
	 *
	 * <p>If there are already 10000 books in the list we ignore the request to add a new book.
	 * Lazy? Yes!</p>
	 */
	public boolean insertBook(String bookString)
	{
		if (n>=10000) return false;
		book[n]= new Book(bookString);
		n++;
		return true;
	}


	/**
	 * Remove the book found at the specified position.
	 *
	 * @param pos the position at which the book is found
	 * @return true if delete was successful; false otherwise
	 *
	 * <p>NOTE: We operate under the assumption that this is an unordered list
	 * of Books. Also, if the position specified is not within the range of
	 * existing books we ignore the request.</p>
	 *
	 */
	public boolean removeBook(int pos)
	{
		if (pos >= n) return false;
		book[pos]= book[n-1];
		n--;
		return true;
	}


	/**
	 * Save current in-memory version of the database back to disk.
	 */
	public void saveState() throws Exception
	{
		int i;
		PrintStream fout= new PrintStream(new File(dbFile));

		for (i=0; i<n; i++)
			fout.println(book[i].toBookString());

		fout.close();
	}
}
