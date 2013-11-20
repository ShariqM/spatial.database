// Remove all comments that begin with //, and replace appropriately.
// Feel free to modify ANYTHING in this file, or to not use 
// it at all.

/* Author: *YOUR NAME AND LOGIN HERE* */

package tracker;

import java.io.IOException;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.PrintStream;
import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;


/** Central dispatching point for all testing.   */
public class Testing {

    /** List of classes containing JUnit tests. 
     * */
    final static Class<?>[] testClasses = { Testing.class , BasicTests1.class , CloserTests.class, 
    	CollisionTests.class, DeltaListTests.class, LoadTests.class, NearTests.class, WriteTests.class};

    // In this case, you'll have to add your own JUnit tests.  For 
    // end-to-end tests, just add tests to the testing directory.  The
    // functions runFullTests and runErrorTests will look in this
    // directory for tests; the ones that start with "bad" are error 
    // tests.  

    final static File 
        /** The directory in which this program is run. */
        workingDir = new File (System.getProperty ("user.dir")),
        /** The directory containing tests. */
        testFileDir = new File (workingDir, "testing");

    /** Run all tests in the tracker package. */
    public static void main (String[] ignored) {
        boolean ok;
        Result junitResult = JUnitCore.runClasses (testClasses);
        ok = junitResult.wasSuccessful ();
        System.err.printf ("Ran %d unit tests.%n",
                           junitResult.getRunCount ());
        if (ok) 
            System.err.printf ("All unit tests passed.%n");
        else {
            if (junitResult.getFailureCount () == 1)
                System.err.printf ("There was 1 JUnit failure:%n");
            else
                System.err.printf ("There were %d JUnit failures:%n", 
                                   junitResult.getFailureCount ());
            for (Failure f : junitResult.getFailures ())
                System.err.printf ("%s: %s%n", 
                                   f.getDescription (),
                                   f.getException ());
        }
        try {
            System.in.close ();
            System.out.close ();
        } catch (IOException e) {
        }
        ok &= runFullTests ();
        ok &= runErrorTests ();
        if (ok)
            System.exit (0);
        else
            System.exit (1);
    }

    final static FilenameFilter 
        goodFiles = new FilenameFilter () {
            public boolean accept (File dir, String name) {
                return name.endsWith (".trk") && ! name.startsWith ("bad");
            }
        },
        badFiles = new FilenameFilter () {
            public boolean accept (File dir, String name) {
                return name.endsWith (".trk") && name.startsWith ("bad");
            }
        };

    /** Run tests that are supposed to succeed from the test directory. */
    static boolean runFullTests () {
        int ok;
        File[] inputs = testFileDir.listFiles (goodFiles);
        Arrays.sort (inputs);
        ok = 0;
        for (File inp : inputs) {
            String name = inp.getName ();
            File outFile;
            
            outFile = null;
            try {
                try {
                    outFile = File.createTempFile ("trk", ".out", workingDir);
                    Main.main (new String[] { inp.getAbsolutePath (), 
                                              outFile.getAbsolutePath () });
                } catch (ExitException e) {
                    if (e.getCode () != 0) {
                        System.err.printf ("%s: FAILED (non-zero exit)%n", name);
                        continue;
                    }
                } catch (Exception e) {
                    System.err.printf ("%s: FAILED (exception)%n", name);
                    continue;
                }

                String stdName = 
                    name.substring (0, name.length () - 3) + "std";
                if (fileContents (new File (testFileDir, stdName))
                    .equals (fileContents (outFile))) {
                    System.err.printf ("%s: OK%n", name);
                    ok += 1;
                } else 
                    System.err.printf ("%s: FAILED (wrong output)%n", name);
            } finally {
                if (outFile != null)
                    outFile.delete ();
            }
    
        }
        System.err.printf ("Passed %d/%d normal tests.%n", 
                           ok, inputs.length);
        return ok == inputs.length;
    }
        
    /** Run tests that are supposed to produce errors from 
     *  the test directory. */
    static boolean runErrorTests () {
        int ok;
        PrintStream err = System.err;
        File outFile, errFile;
        File[] inputs = testFileDir.listFiles (badFiles);
        Arrays.sort (inputs);
        ok = 0;
        outFile = errFile = null;
        for (File inp : inputs) {
            String name = inp.getName ();
            
            try {
                try {
                    outFile = File.createTempFile ("trk", ".out", workingDir);
                    errFile = File.createTempFile ("trk", ".err", workingDir);
                    System.setErr (new PrintStream (errFile));
                    try {
                        Main.main (new String[] { inp.getAbsolutePath (), 
                                                  outFile.getAbsolutePath () });
                    } finally {
                        System.err.close ();
                        System.setErr (err);
                    }
                } catch (ExitException e) {
                    if (e.getCode () == 0) {
                        System.err.printf ("%s: FAILED (zero exit)%n", name);
                        continue;
                    }
                } catch (Exception e) {
                    System.err.printf ("%s: FAILED (exception)%n", name);
                    continue;
                }
            
                if (fileContents (errFile).toLowerCase ().indexOf ("error")
                    == -1)
                    System.err.printf ("%s: FAILED (no error indication)%n", 
                                       name);
                else {
                    System.err.printf ("%s: OK%n", name);
                    ok += 1;
                }
            } finally {
                if (outFile != null)
                    outFile.delete ();
                if (errFile != null)
                    errFile.delete ();
            }
        }
        System.err.printf ("Passed %d/%d error tests.%n", 
                           ok, inputs.length);
        return ok == inputs.length;
    }
                
    /** A handy utility function for testing: converts all end-of-line 
     *  sequences to "\n", whether on Unix or Windows, removes all
     *  empty lines, and makes sure the last line of the file ends in a 
     *  newline sequence. */
    static String canonicalize (String s) {
        s = s.replace ("\r\n", "\n");
        if (s.length () > 0 && ! s.endsWith ("\n"))
            s += "\n";
        return s.replaceAll ("\\n\\n+(>\\s*)*", "\n");
    }

    /** The canonicalized contents of the file named NAME.  We assume 
     *  that the current directory is the one containing the drawing package.
     *  The argument NAME is a File as opposed to a String in order to 
     *  help in finessing the differences between Unix and Windows 
     *  file names. */
    static String fileContents (File name) {
        try {
            StringBuilder result = new StringBuilder ();
            FileReader inp = new FileReader (name);
            while (true) {
                char[] buffer = new char[4096];
                int n = inp.read (buffer);
                if (n == -1)
                    break;
                result.append (buffer, 0, n);
            }
            return canonicalize (result.toString ());
        } catch (IOException e) {
            throw new IllegalArgumentException ("could not read file: " 
                                                + e.getMessage ());
        }
    }

    /** The canonicalized contents of the file named NAME in our testing
     *  directory. */
    static String fileContents (String name) {
        return fileContents (new File (testFileDir, name));
    }
    
    @Test public void testTrack() {
    	assert(true);
    }

}
