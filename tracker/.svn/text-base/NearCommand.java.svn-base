package tracker;


import static tracker.Main.ERR;

import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

import ucb.proj2.Physics;
import util.QuadTree.QuadPoint;

/**
 * Near Command corresponds to 'near x y d', 'near x * d', 'near * y d'
 * @author Shariq Mobin cs61b-eb
 *
 */
class NearCommand extends Command {
	
	NearCommand() {
		super("near");
	}
	/**
	 * This method prints all objects who are near eachother according
	 * to input type requested
	 * @param syst takes the trackingsystem so it can properly execute
	 * @param scan takes int he scanner to generate the desired args
	 * @exception InputMisMatchException thrown if not double arg
	 * @exception NumberFormatException  thrown if not double arg
	 * @exception NoSuchElementException thrown if not enough args
	 * @exception IllegalStateException thrown if scanner is closed
	 */
	void execute(TrackingSystem syst, Scanner scan) throws ProblemException {
		try {
			int i;
			double x, y, d, llx, lly, urx, ury;
			x = y = Double.NEGATIVE_INFINITY;
			if (scan.hasNext("\\*")) {
				scan.next();
				y = scan.nextDouble();
				d = parse(scan);
				llx = Double.NEGATIVE_INFINITY;
				lly = y-d;
				urx = Double.POSITIVE_INFINITY;
				ury = y+d;
			}
			else {
				x = scan.nextDouble();
				if (scan.hasNext("\\*")) {
					scan.next();
					d = parse(scan);
					llx = x-d;
					lly = Double.NEGATIVE_INFINITY;
					urx = x+d;
					ury = Double.POSITIVE_INFINITY;
				}
				else {
					y = scan.nextDouble();
					d = parse(scan);
					llx = x-d;
					lly = y-d;
					urx = x+d;
					ury = y+d;
				}
			}
			Iterator<QuadPoint> iter = syst.iterator(llx, lly, urx, ury);
			i = 0;
			ArrayList<Integer> ids = new ArrayList<Integer>();
			while (iter.hasNext()) {
				QuadPoint curr = iter.next();
				double[] val = syst.map.get(new Coordinate(curr.x(),curr.y()));
				if (withinD(d, x, y, curr.x(), curr.y())) {
					ids.add((int)val[0]);
				}
			}
			Collections.sort(ids);
			for (int id : ids) {
				double[] value = syst.locs.get(id);
				if (i != 0 && i % 2 == 0)
					System.out.println ();
				else if(i % 2 == 1)
					System.out.print(" ");
				System.out.printf ("%d:(%.4g,%.4g,%.4g,%.4g)", 
						   id, value[1], value[2],
						   value[3], value[4]);
				i += 1;
			}
			System.out.println();
			
		} catch (InputMismatchException e) {
			throw ERR ("Arguments of near must be of type double");
		} catch (NoSuchElementException e) {
			throw ERR ("Too few Args to near");
		} catch (IllegalStateException e) {
			throw ERR("near Failed, Scanner is closed");
		} catch (NumberFormatException e) {
			throw ERR("Arguments of near must be of type double");
		}
		
	}
	/**
	 * Determines whether the two points satisfy the near requirement 
	 * @param d the largest distance apart they can be
	 * @param x the x parameter of the first point
	 * @param y the y parameter of the first point
	 * @param x2 the x parameter of the second point
	 * @param y2 the y parameter of the second point
	 * @return true if satisfied
	 */
	boolean withinD(double d, double x, double y, double x2, double y2) {
		if (x == Double.NEGATIVE_INFINITY)
			x = x2;
		if (y == Double.NEGATIVE_INFINITY)
			y = y2;
		double[] origin = {x,y};
		double[] point = {x2, y2};
		double dist = Physics.len( origin, 0 , point, 0);
		return dist <= d;
	}

}
