/* Author: Shariq Mobin cs61b-eb */

package util;

import java.util.*;

/** A Set2D implemented with a QuadTree.  The type argument, Point,
 *  indicates the type of points contained in the set.  The rather
 *  involved type parameter structure here allows you to extend 
 *  QuadPoint, and thus add additional data and methods to the points
 *  you store. */
public class QuadTree<Point extends QuadTree.QuadPoint> extends Set2D<Point> {
	
    /** The supertype of all possible kinds of QuadTree member.
     *  Type arguments to QuadTree are subtypes of QuadPoint. */
    public static class QuadPoint extends Set2D.BasePoint {
    	
    	private double x, y;
        /** My current x coordinate. */
        public double x () {
            return x;
        }

        /** My current y coordinate. */
        public double y () {
            return y;
        }
        
        /** Move me to new position (X, Y). */
        public void move (double x, double y) {
            this.x = x;
            this.y = y;
        }

        public QuadPoint (double x, double y) {
            move(x,y);
        }

    }

    /** An empty set whose bounding rectangle has lower-left coordinates
     *  (LLX,LLY) and upper-right coordinates (URX,URY).  The argument
     *  DELTA has no externally visible effect, but may affect performance.
     *  It is intended to specify the smallest possible dimension of 
     *  any subtree's bounding rectangle.  No rectangle with a side smaller
     *  than DELTA is subdivided.  */
    public QuadTree (double llx, double lly, double urx, double ury,
                     double delta) {
        this.llx = llx;
        this.lly = lly;
        this.urx = urx;
        this.ury = ury;
        this.delta = delta;
        size = 0;
    }

    public int size () {
    	return size;
    }

    public void add (Point p) {
    	if (p.x > urx || p.x < llx || p.y > ury || p.y < lly) {
    		throw new IllegalArgumentException("Attempted to add a point outside of the boundaries"+p.x+" "+p.y);
    	}
    	if (myRoot == null)
        	myRoot = new QuadNode<Point>(this, null, llx, lly, urx, ury, p, delta);
        else if (size == 1) {
        	if (myRoot.getType().equals("Leaf")) {
        		QuadNode<Point> qnode = (QuadNode<Point>)myRoot;
        		ArrayList<Point> list = qnode.getList();
        		if (list != null) {
        			list.add(p);
        		}
        		else {
		        	Point point = qnode.getPoint();
		        	myRoot = new ParentQuadTreeNode<Point>(this, null, llx, lly, urx, ury, delta);
		        	myRoot.insert(point);
		        	myRoot.insert(p);
        		}
        	}
        	else
        		myRoot.insert(p);
        }
        else
        	myRoot.insert(p);
        
        size++;
    }

    public void remove (Point p) {
    	if (myRoot == null) {
    		;
    	}
    	else {
    		String type = myRoot.getType();
    		if (type.equals("Leaf")) {
    			QuadNode<Point> qnode = (QuadNode<Point>)myRoot;
        		ArrayList<Point> list = qnode.getList();
        		if (list != null) {
        			int pos = 0;
        			for (Point pt : list) {
        				if (isEqual(pt, p)) {
        					qnode.removeFromList(pos);
        					if (qnode.getList().isEmpty())
        						myRoot = null;
        					System.err.println("Hello world2");
        					System.err.flush();
        					size--;
        					break;
        				}
        				pos++;
        			}
        		}
        		else {
	    			Point nodepoint = ((QuadNode<Point>)myRoot).getPoint();
	    			if (isEqual(p, nodepoint)) {
	    				myRoot = null;
	    				size--;
	    			}
        		}
    		}
    		else {
    	        QuadTreeNode<Point> remove = get(p);
    	        if (remove != null) {
        			QuadNode<Point> qnode = (QuadNode<Point>)remove;
            		ArrayList<Point> list = qnode.getList();
            		if (list != null) {
            			int pos = 0;
            			for (Point pt : list) {
            				if (isEqual(pt, p)) {
            					qnode.removeFromList(pos);
            					size--;
            					break;
            				}
            				pos++;
            			}
            			if (qnode.getList().isEmpty()) {
            				ParentQuadTreeNode<Point> parent = qnode.parent;
            				parent.setDL(null);
            			}
            		}
            		else {
            			size--;
            			removeByParent(remove, p);
            		}     			
            	}
    		}
    	}
    }
    /** Removes the nodes parent) **/
    private void removeByParent(QuadTreeNode<Point> remove, Point p) {
			ParentQuadTreeNode<Point> parent = remove.parent;
			double pcentx = parent.centx();
			double pcenty = parent.centy();
			double ptx = p.x();
			double pty = p.y();
			if (pcenty <= pty) {
				if (ptx < pcentx) {
					parent.setTL(null);
				}
				else {
					parent.setTR(null);
				}
			}
			else {
				if (ptx < pcentx) {
					parent.setBL(null);
				}
				else {
					parent.setBR(null);
				}
			}
		
    }
    /** Gets the node of the point if it exists **/
    private QuadTreeNode<Point> get(Point p) {
    		return recurse(myRoot, p);
    }
    /** Gets the node of the point if it exists recursively **/
    private QuadTreeNode<Point> recurse(QuadTreeNode<Point> node, Point p) {
    	if (node == null) {
    		return null;
    	}
    	String type = node.getType();
    	if (type.equals("Leaf")) {
    		QuadNode<Point> qnode = (QuadNode<Point>)node;
    		ArrayList<Point> list = qnode.getList();
    		if (list != null) {
    			for (Point pt : list) {
    				if (isEqual(pt, p)) {
    					return node;
    				}
    			}
    			return null;
    		}
    		else {
	    		Point nodepoint = qnode.getPoint();
	    		if (isEqual(nodepoint, p)) {
	    			return node;
	    		}
	    		else
	    			return null;
    		}
    	}
    	else {
			ParentQuadTreeNode<Point> parent;
			parent = (ParentQuadTreeNode<Point>)node;
			if (parent.getDL() != null) {
				return recurse(parent.getDL(), p);
			}
			else {
    			double pcentx = parent.centx();
    			double pcenty = parent.centy();
    			double ptx = p.x();
    			double pty = p.y();
    			if (pcenty <= pty) {
    				if (ptx < pcentx) {
    					return recurse(parent.getTL(), p);
    				}
    				else {
    					return recurse(parent.getTR(), p);
    				}
    			}
    			else {
    				if (ptx < pcentx) {
    					return recurse(parent.getBL(), p);
    				}
    				else {
    					return recurse(parent.getBR(), p);
    				}
    			}
			}	
    	}	
    }
    /**
     * Determines if two points are equal
     * @param a is the first point
     * @param b is the second point
     * @return true if they are equal
     */
    private boolean isEqual(Point a, Point b) {
    	return a.x() == b.x() && a.y() == b.y();
    }
    public void moveTo (Set2D<Point> dest) {
        Iterator<Point> iter = iterator();
        while (iter.hasNext()) {
        	dest.add(iter.next());
        }
    }
    /** returns true if the quadtree contains the point **/
    public boolean contains (Point p) {
        return get(p) != null;
    }

    public double llx () {
        return llx;
    }

    public double lly () {
        return lly;
    }

    public double urx ()  {
        return urx;
    }

    public double ury ()  {
        return ury;
    }
    
    /** An iterator of all points **/
    public Iterator<Point> iterator () {
        return new QuadIterator<Point>();
    }
    /** An iterator of points within a box specified **/
    public Iterator<Point> iterator (double xl, double yl, 
                                     double xu, double yu) {
        return new QuadFilterIterator<Point>(xl, yl, xu, yu);
    }

    /** The root node, which contains (along with its subtrees) all points
     *  in THIS tree. */
    private double llx, lly, urx, ury, delta;
    private int size;
    private QuadTreeNode<Point> myRoot;

    @SuppressWarnings("hiding")
	class QuadIterator<QuadPoint> implements Iterator<Point> {
    	
    	/** The Iterator for the quadTree **/
    	private Stack<QuadTreeNode<Point>> fringe = new Stack<QuadTreeNode<Point>>();
    	private int mysize;
    	
    	QuadIterator() {
    		if (myRoot != null)
    			fringe.push(myRoot);
    		mysize = size;
    	}
    	
    	public boolean hasNext() {
    		return mysize > 0;
    	}
    	/** Finds the next point recursively **/
    	public Point next() {
    		if (!hasNext()) {
    			throw new NoSuchElementException("The QuadTree is empty");
    		}
    		QuadTreeNode<Point> node, TL, TR, BR, BL, DL;
    		node = fringe.pop();
    		String type = node.getType();
    		if (type.equals("Leaf")) {
    			QuadNode<Point> qnode = (QuadNode<Point>)node;
        		ArrayList<Point> list = qnode.getList();
        		if (list != null) {
        			if (!list.isEmpty()) {
        				for (Point other : list) {
        					fringe.push(new QuadNode<Point>(null, 
        							null,0,0,0,0, other, delta));
        				}
        				return next();
        			}
        			else {
        				return next();
        			}
        		}
        		else {
        			mysize--;
        			return ((QuadNode<Point>)node).getPoint();
        		}
    		}
    		else {
    			ParentQuadTreeNode<Point> parent = (ParentQuadTreeNode<Point>)node;
    			TL = parent.getTL();
    			TR = parent.getTR();
    			BR = parent.getBR();
    			BL = parent.getBL();
    			DL = parent.getDL();
    			if (DL != null) {
    				fringe.push(DL);
    			}
    			if (TL != null)
    				fringe.push(TL);
    			if (TR != null)
    				fringe.push(TR);
    			if (BR != null)
    				fringe.push(BR);
    			if (BL != null)
    				fringe.push(BL);
    			return next();
    		}
    	}

		public void remove() {
			throw new UnsupportedOperationException();
		}
    }
    @SuppressWarnings("hiding")
	class QuadFilterIterator<QuadPoint> implements Iterator<Point> {
    	
    	private Stack<QuadTreeNode<Point>> fringe = new Stack<QuadTreeNode<Point>>();
    	private double LX, LY, UX, UY;
    	private boolean hasNext;
    	private Point next;
    	
    	QuadFilterIterator(double LX, double LY, double UX, double UY) {
            this.LX = LX;
            this.LY = LY;
            this.UX = UX;
            this.UY = UY;
            hasNext = false;
            next = null;
    		if (myRoot != null)
    			build(myRoot);
    	}
    	/** Builds the list of points first so hasNext works **/
    	private void build(QuadTreeNode<Point> root) {
    		fringe.push(root);
    		next = add();
    		if (next != null)
    			hasNext = true;
    	}
    	public boolean hasNext() {
    		return hasNext;
    	}
    	public Point next() {
    		if (!hasNext())
    			throw new NoSuchElementException("The QuadTree is empty");
    		Point new_next = add();
    		if (new_next == null) {
    			hasNext = false;
    			return next;
    		}
    		else {
    			Point temp = next;
    			next = new_next;
    			return temp;
    		}
    	}
    	/** Find and create the list of points */
    	private Point add() {
    		if (fringe.empty()) {
    			return null;
    		}
    		QuadTreeNode<Point> node, TL, TR, BR, BL, DL;
    		node = fringe.pop();
    		String type = node.getType();
    		if (type.equals("Leaf")) {
    			QuadNode<Point> qnode = (QuadNode<Point>)node;
        		ArrayList<Point> list = qnode.getList();
        		if (list != null) {
        			if (!list.isEmpty()) {
        				Point returnthis = null;
        				for (Point other : list) {
        					if (within(other) && returnthis == null)
        						returnthis = other;
        					else if (returnthis != null) {
        						fringe.push(new QuadNode<Point>(null, 
        							null,0,0,0,0, other, delta));
        					}
        				}
        				if (returnthis == null)
        					return add();
        				else
        					return returnthis;
        			}
        			else {
        				return add();
        			}
        		}
    			Point p = ((QuadNode<Point>)node).getPoint();
    			if (within(p)) {
    				return p;
    			}
    			else
    				return add();
    		}
    		else {
    			ParentQuadTreeNode<Point> parent = (ParentQuadTreeNode<Point>)node;
    			TL = parent.getTL();
    			TR = parent.getTR();
    			BR = parent.getBR();
    			BL = parent.getBL();
    			DL = parent.getDL();
    			if (DL != null && within(DL)) {
    	   			fringe.push(DL);
    	    	}
    			if (TL != null && within(TL)) {
    				fringe.push(TL);
    			}
    			if (TR != null && within(TR)) {
    				fringe.push(TR);
    			}
    			if (BR != null && within(BR)) {
    				fringe.push(BR);
    			}
    			if (BL != null && within(BL)) {
    				fringe.push(BL);
    			}
    			return add();
    		}

    			
    	}
    	/** returns true if the point is within the bounds **/
    	private boolean within(Point p) {
    		 return 
             p.x () >= LX  && p.x () <= UX &&
             p.y () >= LY && p.y () <= UY; 
    	}
    	private boolean altWithin(double nLX, double nLY, double nUX, double nUY, double x, double y) {
   		 return 
         x >= nLX  && x <= nUX &&
         y >= nLY && y <= nUY; 
    	}
    	/** Returns true if one of the 4 corners of the boundary requested
    	 * is contained in the boundaries of QuadTreeNode;
    	 * @param node is the treenode being checked
    	 */
    	private boolean within(QuadTreeNode<Point> node) {
    		double nLX, nLY, nUX, nUY;
    		nLX = node.llx();
    		nLY = node.lly();
    		nUX = node.urx();
    		nUY = node.ury();
    		boolean b = altWithin(nLX, nLY, nUX, nUY, LX, LY) || altWithin(nLX, nLY, nUX, nUY, LX, UY) ||
    				altWithin(nLX, nLY, nUX, nUY, UX, LY) || altWithin(nLX, nLY, nUX, nUY, UX, UY);
    		boolean c = altWithin(LX, LY, UX, UY, nLX, nLY) || altWithin(LX, LY, UX, UY, nLX, nUY) ||
    			altWithin(LX, LY, UX, UY, nUX, nLY) || altWithin(LX, LY, UX, UY, nUX, nUY);
    		return (b || c); 
    	}

		public void remove() {
			throw new UnsupportedOperationException();
		}
    }

}

