/* Author: Shariq Mobin cs61b-eb */

package tracker;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.Stack;

import util.Debugging;
/** 
 * The Main Class handles the user input and runs the program
 * accordingly
 * @author Shariq Mobin cs61b-eb
 */
public class Main {

	static boolean standardinput;
	static Stack<Scanner> inp;
	static Scanner myScan;
	static int exitval;
	static boolean loading;
	static int skip; // The number of times prompting must be skipped
    public static void main (String... args) {
    	Scanner SI = null;
        String inputFileName, outputFileName;
        inp = new Stack<Scanner> ();
        myScan = null;
        inputFileName = outputFileName = null;
        TrackingSystem syst = new TrackingSystem();
        exitval = 0;
        skip = 0;
        
        loading  = false;
        
        int argslength = args.length;
        if (argslength > 0) { // Check for debugging and modify linput if there
        	if (args[0].indexOf("--debug=") != -1) {
        		String level = args[0].substring(8);
        		int x = Integer.parseInt(level);
        		Debugging.debuggingLevel = x;
        		args = refactor(args);
        	}
        }
        try {
	        switch (args.length) {
	        case 0:
	        	break;
	        case 1: {
	        	inputFileName = args[0];
	        	break;
	        } 
	        case 2: {
	        	inputFileName = args[0];
	        	outputFileName = args[1];
	            break;
	        }
	        default:
	        	Usage();
	        }
        } catch (Throwable t) {
        	exitval = 1;
        }
        
        if (inputFileName == null) {
        	myScan = new Scanner(System.in);
        	SI = myScan;
        	inp.push (myScan);
        	standardinput = true;
        }
        else {
            try {
            	myScan = new Scanner (new File (inputFileName));
                inp.push (myScan);
                loading = true;
            } catch (FileNotFoundException e) {
                System.err.printf ("Error: could not open %s%n", inputFileName);
                exitval = 1;
            }
        
            if (outputFileName != null) {
	            try {
	                System.setOut (new PrintStream (outputFileName));
	            } catch (FileNotFoundException e) {
	                System.err.printf ("Error: could not open %s%n", 
	                                   outputFileName);
	                exitval = 1;
	            }
            }
        }
        System.out.println ("Particle tracker, v4.0.");
        System.out.flush();
        
        while (! inp.empty ()) {
        	if (standardinput && !loading && skip == 0) {
        		System.out.print("> ");
        		System.out.flush();
        	}
        	if (skip != 0 )
        		skip--;
        	
            if (!myScan.hasNext ()) {
            	myScan = inp.pop();
            	if (loading && myScan == SI)
            		loading = false;
            	continue;
            }
            try {
            	Command.get(myScan.next()).execute(syst, myScan);
            } catch (ProblemException e) {
                System.err.printf ("Error: %s%n", e.getMessage ());
                exitval = 1;
                recoverFromError (inp);
            }
        }
        exit(exitval);
    }
    static void handleCommand(String p) {
    	
    }
    /** Exit from the program.  When the testing state is true, throws 
     *  ExitException with the given CODE as parameter.  Otherwise,
     *  behaves just like System.exit.  For this all to work, other
     *  parts of the program should not call System.exit directly. */
    static void exit (int code) {
        throw new ExitException (code);
    }

    /** A convenience function for generating exceptions.  Example:
     *     throw ERR ("unknown command: %s", token);
     */
    static ProblemException ERR (String msgTemplate, String ... args) {
        return new ProblemException (String.format (msgTemplate, (Object)args));
    }

    /** Crude error recovery: throw away the rest of this line. */
    static void recoverFromError (Stack<Scanner> s) {
        if (! s.empty ())
            s.peek ().skip (".*");
    }

    static void Usage () {
    	System.err.printf("Invalid Command Input, <Insert an informative message>");
        exit (1);
    }
    /** Creates a new array with the debug argument taken out to simplify input*/
	public static String[] refactor(String[] inp) {
		String[] copy = new String[inp.length-1];
		for (int i = 0; i < inp.length-1; i++) {
			copy[i] = inp[i+1];
		}
		return copy;
	}
}

