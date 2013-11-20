/**
 * @author Shariq Mobin cs61b-eb
 * The QuadTreeNode abstract class.
 * Specifies all methods QuadTreeNodes should ahve
 */

package util;


/** Represents a single node in a quad tree.  This is abstract  
 *  to have different kinds of QuadTreeNode corresponding to
 *  empty trees, leaf trees (no children, just objects), and internal
 *  trees (those with children). */
abstract class QuadTreeNode<Point extends QuadTree.QuadPoint> {

    /** The QuadTree I am part of. */
    final QuadTree<Point> myTree;

    /** The QuadTreeNode, if any, that contains me. */
    ParentQuadTreeNode<Point> parent;

    /** A new QuadTreeNode that is part of WHOLETREE. */ 
    QuadTreeNode (QuadTree<Point> wholeTree) {
        myTree = wholeTree;
    }

    /** The type of Node I am */
    abstract String getType();
    
    /** My smallest X coordinate. */
    abstract double llx ();

    /** My smallest Y coordinate. */
    abstract double lly ();

    /** My largest X coordinate */
    abstract double urx (); 

    /** My largest Y coordinate */
    abstract double ury ();

    /** True iff P is within my bounding box. */
    boolean isWithin (Point p) {
        return 
            p.x () >= llx () && p.x () < urx () &&
            p.y () >= lly () && p.y () < ury (); 
    }

    /** Insert P into THIS, returning the resulting tree.  It is an error
     *  if P is not within my bounding box. */
    abstract QuadTreeNode<Point> insert (Point p);
  
}



