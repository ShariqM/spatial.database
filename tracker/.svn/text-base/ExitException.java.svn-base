package tracker;

/** 
 *  An exception class that indicates the program should exit.  Used as
 *  substitute for System.exit for the benefit of JUnit tests.  See also
 *  tracker.Main.exit. 
 *  @author Shariq Mobin cs61b-eb
 *  */
public class ExitException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ExitException (int code) {
        this.code = code;
        System.exit(code);
    }

    public int getCode () {
        return code;
    }

    final private int code;

}
