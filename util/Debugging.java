package util;

/** 
 * A utility class for handling debugging.
 * @author Shariq Mobin cs61b-eb
 */
public class Debugging {

    /** The current level of debugging information.  0 == no
     *  debugging. */
    public static int debuggingLevel = 0;

    /** If LEVEL is positive and less than the current debuggingLevel, 
     *  print FORMAT and ARGS, as for printf.  Otherwise, does nothing */
    public static void Debug (int level, String format, Object... args) {
        if (level > 0 && debuggingLevel >= level)
            System.err.printf (format, args);
    }

}

