/**
 * Holds a single Book and provides comparison methods for each field.
 *
 * @author  T.Sergeant
 * @version Student Version for OS Homework
 *
 * @see BookDatabase
 *
 * <p>There is a series of searchBy methods that will search the appropriate
 * field.  These will be executed by separate threads.  Communication with
 * the threads is handled by a shared instance of the SearchRequest class.</p>
*/
public class Book
{
  private String isbn;        // These fields store information about
  private String title;       // a single (ficticious) book.
  private String author;
  private int    numPages;
  private int    year;

  /**
   * Creates a new Book given individual components.
   *
   * @param isbn the ISBN of the new book
   * @param title the title of the new book
   * @param author the author of the new book
   * @param numPages the number of pages in the new book
   * @param year the year of publication of the new book
   */
  public Book(String isbn, String title, String author, int numPages, int year)
  {
    this.isbn= isbn;
    this.title= title;
    this.author= author;
    this.numPages= numPages;
    this.year= year;
  }

  /**
   * Given book info separated by vertical bars (|), splits values and
   * creates a new Book.
   *
   * @param bookString book information in this form: isbn|title|author|numPages|year
   * @see BookDatabase
   */
  public Book(String bookString)
  {
    this(bookString.split("\\|"));
    //String[bookString.split("\\|"));
  }

  /**
   * Stores the array of strings into individual fields of the new object.
   *
   * @see BookDatabase
   * @param bookInfo an array containing info about a specific book
   * the search as well as what value is to be searched for
   *
   * <p>The positions are specific to the format of the data file and use of
   * the split method found in the BookDatabase class.</p>
   */
  public Book(String [] bookInfo)
  {
    isbn= bookInfo[0];      // we don't store publisher info
    title= bookInfo[1];     // or author id in our database
    author= bookInfo[2];
    numPages= Integer.parseInt(bookInfo[4]);  // pages & year
    year= Integer.parseInt(bookInfo[7]);      // are stored as ints
  }


  /** Returns true if val is found in the isbn field.
   * @return true if the parameter (val) is found in the specified field; false otherwise */
  public boolean foundInISBN(String val) { return isbn.indexOf(val.toUpperCase()) >= 0; }

  /** Returns true if val is found in the title field.
   * @return true if the parameter (val) is found in the specified field; false otherwise */
  //public boolean foundInTitle(String val) { return title.indexOf(val) >= 0; }
  public boolean foundInTitle(String val) { return title.toLowerCase().indexOf(val.toLowerCase()) >= 0; }

  /** Returns true if val is found in the author field.
   * @return true if the parameter (val) is found in the specified field; false otherwise */
  public boolean foundInAuthor(String val) { return author.toLowerCase().indexOf(val.toLowerCase()) >= 0; }

  /** Returns true if num matches the numPages field.
   * @return true if the parameter (val) is found in the specified field; false otherwise */
  public boolean matchesNumPages(int num) { return numPages==num; }

  /** Returns true if val is found in the year field.
   * @return true if the parameter (val) is found in the specified field; false otherwise */
  public boolean matchesYear(int num) { return year==num; }

  /**
   * Returns elements of book separated by vertical bars (as stored in the
   * file).
   * @return isbn|title|author||numPages|||year
  */
  public String toBookString()
  {
    return String.format("%s|%s|%s||%d|||%d",isbn,title,author,numPages,year);
  }

  /**
   * Returns string representation of the book.
   * @return isbn, then title, author, numPages, year (spaced out nicely)
  */
  public String toString()
  {
    return String.format("%s %-40s %-20s %4d %4d",isbn,title,author,numPages,year);
  }
}

