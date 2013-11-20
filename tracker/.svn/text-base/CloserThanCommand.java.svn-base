package tracker;


import static tracker.Main.ERR;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

import ucb.proj2.Physics;
import util.QuadTree.QuadPoint;

/** The closerThanCommand 'closer-than d'
 * @author Shariq Mobin cs61b-eb
 */
class CloserThanCommand extends Command {
	
	/* idOrder tells Collections.sort how to compare items */
	Comparator<int[]> idOrder = new Comparator<int[]>() {
		public int compare(int[] id, int[]id2) {
			int difference = id[0] - id2[0];
			if (difference == 0)
				return id[1] - id2[1];
			else
				return difference;
		}
	};
	
	CloserThanCommand () {
		super ("closer-than");
	}	
	/**
	 * This method prints all objects within distance d of eachother
	 * @param syst takes the trackingsystem so it can properly execute
	 * @param scan takes in the scanner to generate the desired args
	 * @exception InputMisMatchException thrown if not double arg
	 * @exception NumberFormatException  thrown if not double arg
	 * @exception NoSuchElementException thrown if not enough args
	 * @exception IllegalStateException thrown if scanner is closed
	 */
	void execute(TrackingSystem syst, Scanner scan) throws ProblemException {
		try {
			double d = parse(scan);
			double ptx, pty, exc_ptx, exc_pty;
			double[] val;
			double[] exc_val;
			QuadPoint pt, exc_pt;
			Iterator<QuadPoint> exc_pts;
			Iterator<QuadPoint> allpts = syst.iterator();
			ArrayList<int[]> idList = new ArrayList<int[]>();
			while (allpts.hasNext()) {
				pt = allpts.next();
				ptx = pt.x();
				pty = pt.y();
				val = syst.map.get(new Coordinate(ptx, pty));
				exc_pts = syst.iterator( ptx - d , pty - d, ptx + d, pty + d);
				
				while (exc_pts.hasNext()) {
					exc_pt = exc_pts.next();
					exc_ptx = exc_pt.x();
					exc_pty = exc_pt.y();
					exc_val = syst.map.get(new Coordinate(exc_ptx, exc_pty));
					if (withinD(d, ptx, pty, exc_ptx, exc_pty) && val[0] < exc_val[0]) {
						idList.add(new int[] { (int)val[0], (int)exc_val[0]});
					}
				}
			}
			
			Collections.sort(idList, idOrder);
			for (int[] point : idList) {
				int id = point[0];
				int id2 = point[1];
				double[] vars = syst.locs.get(id);
				double[] vars2 = syst.locs.get(id2);
				System.out.printf("%s:(%.4g,%.4g,%.4g,%.4g) %s:(%.4g,%.4g,%.4g,%.4g)\n",
						id, vars[1], vars[2], vars[3], vars[4], id2, vars2[1], vars2[2],
						vars2[3], vars2[4]);
			}
			
		} catch (InputMismatchException e) {
			throw ERR ("Arguments of closer-than must be of type double");
		} catch (NoSuchElementException e) {
			throw ERR ("Too few Args to Bounds");
		} catch (IllegalStateException e) {
			throw ERR("Add Failed, Scanner is closed");
		} catch (NumberFormatException e) {
			throw ERR("Arguments of closer-tahn must be of type double");
		}
	}
	/**
	 * Determines whether two points are within distance d of eachother
	 * @param d is the greatest distance allowed
	 * @param x is the x coordinate of the first point
	 * @param y is the y coordinate of the first point
	 * @param x2 is the x coordinate of the second point
	 * @param y2 is the y coordinate of the second point
	 * @return true if within
	 */
	boolean withinD(double d, double x, double y, double x2, double y2) {
		double[] origin = {x,y};
		double[] point = {x2, y2};
		double dist = Physics.len( origin, 0 , point, 0);
		return dist <= d;
	}
}
