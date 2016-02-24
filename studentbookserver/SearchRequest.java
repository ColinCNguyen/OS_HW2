/**
 * Keeps information to be shared between the search threads and their
 * various constituents.
 *
 * @author  T.Sergeant
 * @version Student Version for OS Homework
 *
 * @see Book
 * @see SearchRequest
 *
 * <p>The fields are public because the sole purpose of this class is to
 * provide a vehicle for sharing information.</p>
 */
public class SearchRequest
{
  /** the database object we are searching */
  public BookDatabase db;

  /** the value we are searching for */
  public String searchVal;

  /** the position in db where we want to start searching */
  public int startPos;

  /** the position in db where we want to stop searching */
  public int stopPos;

  /** the position at which searchVal was found in the db */
  public int foundPos;

  /**
   * Initializes the object with parameters as appopriate.
   *
   * @param db the BookDatabase we will search in
   * @param sv the value to be searched for
   * @param start the position to start searching
   * @param stop the position to stop searching
   *
   * <p>The value of foundPos get managed by the BookDatabase search methods,
   * but we initialize it anyway for good measure.</p>
   */
  public SearchRequest(BookDatabase db, String sv, int start, int stop)
  {
    this.db= db;
    searchVal= sv;
    startPos= start;
    stopPos= stop;
    foundPos= -1;
  }

  /**
   * After a successful search we set startPos to be poised for finding the
   * next matching entry.
   */
  public void findNext()
  {
    startPos= foundPos+1;
    foundPos= -1;
  }
}

