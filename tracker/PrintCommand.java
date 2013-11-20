package tracker;


import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

import util.QuadTree.QuadPoint;
/**
 * prints all points
 * @author Shariq Mobin cs61b-eb
 */
class PrintCommand extends Command {

	PrintCommand () {
		super ("print");
	}
	
	/**
	 * This method prints all objects 
	 * @param syst takes the trackingsystem so it can properly execute
	 * @param scan takes int he scanner to generate the desired args
	 * @exception InputMisMatchException thrown if not double arg
	 * @exception NoSuchElementException thrown if not enough args
	 * @exception IllegalStateException thrown if scanner is closed
	 */
	void execute(TrackingSystem syst, Scanner scan) throws ProblemException {
	
			Iterator<QuadPoint> iter = syst.iterator();
			while (iter.hasNext()) {
				QuadPoint pt = iter.next();
				double[] vals = syst.map.get(new Coordinate(pt.x(),pt.y()));
				System.out.printf("%s:(%.4g,%.4g) (%.4g,%.4g)\n",vals[0],pt.x(),pt.y(), vals[1], vals[2]);
				System.out.flush();
			}
			System.out.println("");
	
	
	}

}
