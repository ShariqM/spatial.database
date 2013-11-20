
/* Author: Shariq Mobin cs61b-eb */

package util;

import util.QuadTree.QuadPoint;

import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import static org.junit.Assert.*;

import java.util.Iterator;

public class Testing {

    /** List of classes containing JUnit tests. */
    final static Class<?>[] testClasses = { Testing.class };

    /** Run all tests in the tracker package. */
    public static void main (String[] ignored) {
        Result junitResult = JUnitCore.runClasses (testClasses);
        System.err.printf ("Ran %d unit tests.%n",
                           junitResult.getRunCount ());
        if (junitResult.wasSuccessful ())
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
            System.exit (1);
        }
    }
    

    @Test public void testEmpty1 () {
        Set2D<QuadPoint> set = 
            new QuadTree<QuadPoint> (-1.0, -1.0, 1.0, 1.0, 0.0001);

        assertEquals ("size 0", 0, set.size (), 0.0);
        assertEquals ("llx", -1.0, set.llx (), 0.0);
        assertEquals ("lly", -1.0, set.lly (), 0.0);
        assertEquals ("urx", 1.0, set.urx (), 0.0);
        assertEquals ("ury", 1.0, set.ury (), 0.0);

        Iterator<QuadPoint> i = set.iterator ();
        assertTrue ("nothing in set", ! i.hasNext ());
    }

    @Test public void testAdd1 () {
    	Set2D<QuadPoint> set = new QuadTree<QuadPoint>(-10, -10, 10, 10, 3);
    	set.add(new QuadPoint(3 , 3));
    	Iterator<QuadPoint> i = set.iterator ();
    	assertTrue("something in set", i.hasNext());
    }
    @Test public void testRemove1 () {
    	Set2D<QuadPoint> set = new QuadTree<QuadPoint>(-10, -10, 10, 10, 3);
    	set.add(new QuadPoint(3 , 3));
    	set.remove(new QuadPoint(3, 3));
    	Iterator<QuadPoint> i = set.iterator ();
    	assertTrue("something in set", !i.hasNext());
    }
    @Test public void testDeltaAndContain () {
        	Set2D<QuadPoint> set = new QuadTree<QuadPoint>(-10, -10, 10, 10, 3);
        	set.add(new QuadPoint(3 , 3));
        	set.add(new QuadPoint(4 , 4));
        	set.add(new QuadPoint(2 , 2));
        	assertTrue("something in set", set.contains(new QuadPoint(3,3)));
    }

}

