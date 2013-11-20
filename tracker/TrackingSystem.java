package tracker;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import ucb.proj2.Physics;
import util.QuadTree;
import util.QuadTree.QuadPoint;
import static tracker.Main.*;


/** 
 * The TrackingSystem keeps track of the state of all needed variables
 * and appropriately updates them according to the users specifications.
 * @author Shariq Mobin cs61b-eb
 *
 */
	class TrackingSystem {
		
		private double radius;
		private QuadTree<QuadPoint> tree;
		ArrayList<double[]> locs;
		HashMap<Coordinate,double[]> map;
		private boolean boundset, radiusset;
		private final double DELTA_FACTOR = 2.0;
		
		TrackingSystem() {
			radius = Double.POSITIVE_INFINITY;
			tree = new QuadTree<QuadPoint>(0,0,0,0,1.0);
			locs = new ArrayList<double[]>();
			boundset = false;
			radiusset = false;
			map = new HashMap<Coordinate,double[]>();
		}
		/** Add a point to the System environment by adding ti to the HashMap, ArrayList, and QuadTree **/
		void addPoint(int id, double x, double y, double vx, double vy) 
				throws ProblemException {
			if (id < 0)
				throw ERR("Cannot add negative ID values");
			else if (id < locs.size() && locs.get(id) != null)
				throw ERR("Cannot add duplicate ID values");
			else if (outsideBox(x,y))
				throw ERR("Cannot add a point outside the boundary");
			else if (otherInterference(x,y)) {
				throw ERR("Cannot add a point intersecting another point");
			}
			
			while (locs.size() <= id)
				locs.add(null);
			locs.set(id, new double[] { id, x, y, vx, vy });
			map.put(new Coordinate(x,y), new double[] {id, vx, vy});
			tree.add(new QuadPoint(x,y));
		}
		/** Returns true if the point is outside of the bounds **/
		boolean outsideBox(double x, double y) {
			return (x-radius < tree.llx () || y-radius < tree.lly () 
					|| x+radius >= tree.urx () || y+radius >= tree.ury ());
		}
		/** Returns true if the point is interfering with another **/
		boolean otherInterference(double x, double y) {
			double limit = 4.0*radius*radius;
			Iterator<QuadPoint> iter = iterator(x - 2 * radius, y - 2 * radius,
								x + 2 * radius, y + 2 * radius);
			
			while (iter.hasNext()) {
				QuadPoint pt = iter.next();
				double[] ptdb = {pt.x(), pt.y()};
				double[] indb = {x, y};
				if (Physics.len2(ptdb, 0, indb, 0) < limit)
					return true;
			}
			return false;
		}
		/** Creates a boundary for the system **/
		void createBoundary(double llx, double lly, double urx, double ury) throws ProblemException {
			if (boundset && (llx > tree.llx() || lly > tree.lly() || urx < tree.urx() || ury < tree.ury()))
				throw Main.ERR("Attempted to create a boundary inside the current one");
			if (!boundset) {
				tree = new QuadTree<QuadPoint>(llx, lly, urx, ury, radius*DELTA_FACTOR); // Change delta
				boundset = true;
			}
			else {
				QuadTree<QuadPoint> newtree = new QuadTree<QuadPoint>(llx, lly, urx, ury, radius*DELTA_FACTOR);
				Iterator<QuadPoint> iter = iterator();
				while (iter.hasNext())
					newtree.add(iter.next());
				tree = newtree;
			}
		}
		/** Sets the radius of the System, can only be increased **/
		void setRadius(double rad) throws ProblemException {
			if (rad > radius)
				throw Main.ERR("May not increase radius");
			else if (rad < 0.0)
				throw Main.ERR("Cannot have a negative radius");
			radius = rad;
			if (!radiusset) {
				tree = new QuadTree<QuadPoint>(tree.llx(), tree.lly() ,
					tree.urx(), tree.ury(),radius*DELTA_FACTOR);
				radiusset = true;
			}
		}

		/** Determines the shortest time until a collision **/
		double collision(double dist) {
			double[] vals;
			double[] first = new double[4];
			double[] vels;
			double tc = Double.POSITIVE_INFINITY;
			double id1, id2, ptx, pty;
			double x, y, vx, vy;
			for (double[] elem : locs) {
				if (elem != null) {
						
					id1 = elem[0];
					for (int i = 0; i < 4; i++) {
						first[i] = elem[i+1];
					}
					
					x = first[0];
					y = first[1];
					vx = first[2];
					vy = first[3];
					if (vx < 0) 
						tc = Math.min (tc, - (x - radius () - llx ()) / vx);
					else if (vx > 0)
						tc = Math.min (tc, (urx () - x - radius ()) / vx);
					if (vy < 0)
						tc = Math.min (tc, - (y - radius () - lly ()) / vy);
					else if (vy > 0)
						tc = Math.min (tc, (ury () - y - radius ()) / vy);
					
					Iterator<QuadPoint> iter = iterator(x - dist, y - dist, x + dist, y + dist);
					while (iter.hasNext()) {
						
						QuadPoint pt = iter.next();
						vals = new double[4];
						ptx = pt.x();
						pty = pt.y();
						vels = map.get(new Coordinate(ptx,pty));
						id2 = vels[0];
						if (id2 <= id1)
							continue;
						vals[0] = ptx;
						vals[1] = pty;
						vals[2] = vels[1];
						vals[3] = vels[2];
						tc = Math.min(tc, Physics.collide(first, vals, radius()));
					}
				}
				
			}

			return tc;
		}
		/** Updates the position of all points based off the time **/
		void UpdatePos(double t) {
			Iterator<QuadPoint> iter = iterator();
			ArrayList<QuadPoint> moved = new ArrayList<QuadPoint>();
			ArrayList<Coordinate> delete = new ArrayList<Coordinate>();
			ArrayList<Coordinate> create = new ArrayList<Coordinate>();
			ArrayList<double[]> withvals = new ArrayList<double[]>();
			double vals[];
			while (iter.hasNext()) {
				QuadPoint pt = iter.next();
				double ptx = pt.x();
				double pty = pt.y();
				Coordinate pos = new Coordinate(ptx, pty);
				vals = map.get(pos);
				tree.remove(pt);
				delete.add(pos);
				pt.move(ptx+vals[1]*t, pty+vals[2]*t);
				moved.add(pt);
				Coordinate newpos = new Coordinate(pt.x(), pt.y());
				create.add(newpos);
				withvals.add(vals);
			}
			for (Coordinate del : delete) {
				map.put(del, null);
			}
			for (QuadPoint newpt : moved) {
				tree.add(newpt);
			}
			int i = 0;
			for (Coordinate add : create) {
				double newvals[] = withvals.get(i);
				map.put(add, newvals);
				i++;
				locs.set((int)newvals[0], new double[] {newvals[0], add.getX(), add.getY(), newvals[1], newvals[2] });
			}
			
		}
		/** Updates the new velocities of all particles taking into account collisions **/
		void UpdateVel() {
			double err_rad = radius()*2.0001;
			double radsq = Math.pow(err_rad, 2);
			Iterator<QuadPoint> iter = iterator();
			while (iter.hasNext()) {
				QuadPoint pt = iter.next();
				double ptx = pt.x();
				double pty = pt.y();
				Coordinate pos = new Coordinate(ptx,pty);
				double[] vals = map.get(pos);
				double id1 = vals[0];
				double ptvx = vals[1];
				double ptvy = vals[2];
				if ((ptx - llx() <= radius() && ptvx < 0) || 
						(urx() - ptx <= radius() && ptvx > 0)){
					ptvx *= -1;
				}
				if ((pty - lly() <= radius() && ptvy < 0) ||
						(ury() - pty <= radius() && ptvy > 0)) {
					ptvy *= -1;
				}
				map.put(pos, new double[] { id1, ptvx, ptvy });
				locs.set((int)id1, new double[] { id1, ptx, pty, ptvx, ptvy });
				
				Iterator<QuadPoint> other =  iterator (ptx - err_rad, pty - err_rad,
						   ptx + err_rad, pty + err_rad);
				 while (other.hasNext ()) {
					 QuadPoint other_pt = other.next();
					 double other_ptx = other_pt.x();
					 double other_pty = other_pt.y();
					 Coordinate other_pos = new Coordinate(other_ptx, other_pty);
					 double[] other_vals = map.get(other_pos);
					 double id2 = other_vals[0];
					 if (id2 == id1)
						 continue;
					 double other_ptvx = other_vals[1];
					 double other_ptvy = other_vals[2];
					 double[] pt1 = new double[] {ptx, pty, ptvx, ptvy};
					 double[] pt2 = new double[] {other_ptx, other_pty, other_ptvx, other_ptvy};
					 
					 if (Physics.len2(pt1, 0, pt2, 0) <= radsq) {
						 Physics.rebound(pt1, pt2);
						 map.put(pos, new double[] {id1, pt1[2], pt1[3]});
						 map.put(other_pos, new double[] {id2, pt2[2], pt2[3]});
						 locs.set((int)id1, new double[] { id1, ptx, pty, pt1[2], pt1[3] });
						 locs.set((int)id2, new double[] { id2, other_ptx, other_pty, pt2[2], pt2[3] });
					 }
				 }
			}
		}
		double radius() {
			return radius;
		}
		boolean contains(QuadPoint p) {
			return tree.contains(p);
		}
		double vx (int id) {
			return locs.get (id)[3];
		}
		double vy (int id) {
			return locs.get (id)[4];
		}
	    double llx () {
	        return tree.llx();
	    }

	    double lly () {
	        return tree.lly();
	    }

	    double urx ()  {
	        return tree.urx();
	    }

	    double ury ()  {
	        return tree.ury();
	    }
	    double maxVelocity() {
	    	double max = 0, vel;
	    	for (double[] elem : locs) {
	    		if (elem != null) {
		    		vel = Physics.len(elem, 3);
		    		if (vel > max)
		    			max = vel;
	    		}
	    	}
	    	return max;
	    }
	    Iterator<QuadPoint> iterator() {
	    	return tree.iterator();
	    }
	    Iterator<QuadPoint> iterator(double xl, double yl, double xu, double yu){
	    	return tree.iterator(xl,yl,xu,yu);
	    }
	    Iterator<double[]> locsIterator (double xl, double yl, 
                double xu, double yu) {
			ArrayList<double[]> filter = new ArrayList<double[]>();
			for(double[] point : locs) {
				if (point != null) {
					if (point[1] >= xl && point[2] >= yl && point[1] <= xu && point[2] <= yu)
						filter.add(point);
				}
			}
			
			return (Iterator<double[]>)filter.iterator();
		}
}	
