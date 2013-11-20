

package util;

import java.util.Iterator;

/** A set of moveable objects in a rectangular region of 2D space, 
 *  retrievable by position.  Each object has a unique non-negative 
 *  integer identifier.  All objects in a Set2D reside within a 
 *  bounding rectangle, whose coordinates are retrievable.  
 *  It is legal to have two objects at the same location. */
public abstract class Set2D<Point extends Set2D.BasePoint> {

    /** A member of a Set2D. */
    public static abstract class BasePoint {

        /** My current x coordinate. */
        public abstract double x ();

        /** My current y coordinate. */
        public abstract double y ();
        
        /** Move me to new position (X, Y). */
        public abstract void move (double x, double y);

    }

    /** The current number of objects in the set. */
    public abstract int size ();

    /** Add P to THIS.  Causes an IllegalArgumentException if (X,Y) is not 
     *  in the bounding rectangle or P is already in THIS.
     *  Implementations of Set2D may also forbid Points belonging to
     *  multiple Set2Ds at once. */
    public abstract void add (Point p);

    /** Remove P from THIS, if it is currently a member, and
     *  otherwise do nothing. */
    public abstract void remove (Point p);

    /** Remove all points in THIS and place them in DEST. */
    public abstract void moveTo (Set2D<Point> dest);

    /** True iff THIS contains P. */
    public abstract boolean contains (Point p);

    /** The smallest x-coordinate allowed for objects in THIS.  All
     *  objects must have an x-coordinate that is >= llx (). */
    public abstract double llx ();

    /** The smallest y-coordinate allowed for objects in THIS.  All
     *  objects must have an y-coordinate that is >= lly (). */
    public abstract double lly ();

    /** The largest x-coordinate allowed for objects in THIS.  All
     *  objects must have an x-coordinate that is < urx (). */
    public abstract double urx ();

    /** The largest y-coordinate allowed for objects in THIS.  All
     *  objects must have an y-coordinate that is < ury (). */
    public abstract double ury ();

    /** An Iterator that returns all objects in THIS. */
    public abstract Iterator<Point> iterator ();

    /** An Iterator that returns all objects in THIS whose coordinates
     *  lie on or within a rectangle whose lower-left coordinate is 
     *  (XL, YL) and whose upper-right coordinate is (XU, YU). */
    public abstract Iterator<Point> iterator (double xl, double yl, 
                                              double xu, double yu);

}
  
