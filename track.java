/* Particle Tracker
 * Author: Shariq Mobin cs61b-eb
 */


public class track {

    /** Run the tracking interpreter.  ARGS.length <= 2: 
     *       --debug=N [ INPUTFILE [ OUTPUTFILE ] ]
     *  where N is an integer whose meaning, if non-zero, is 
     *  implementation-dependent, INPUTFILE defaults to the standard
     *  input, and OUTPUTFILE defaults to the standard output. */
    public static void main(String[] args) {
        try {
            tracker.Main.main (args);
        } catch (tracker.ExitException e) {
            System.exit (e.getCode ());
        }
    }

}
