package util;

import java.util.ArrayList;

/**
 * A QuadeNode I hold points, or lists of points but not both
 * @author Shariq Mobin cs61b-eb
 */
public class QuadNode<Point extends QuadTree.QuadPoint> extends QuadTreeNode<Point> {
	
	private double llx, lly, urx, ury, delta;
	private Point point;
	private ArrayList<Point> list;
	QuadNode(QuadTree<Point> myTree, ParentQuadTreeNode<Point> parent, double llx,
			double lly, double urx, double ury, Point point, double delta) {
		super(myTree);
		this.parent = parent;
        this.llx = llx;
        this.lly = lly;
        this.urx = urx;
        this.ury = ury;
        this.point = point;
        this.delta = delta;
        list = null;
	}
	QuadNode(QuadTree<Point> myTree, ParentQuadTreeNode<Point> parent, double llx,
			double lly, double urx, double ury, ArrayList<Point> list, double delta) {
		super(myTree);
		this.parent = parent;
        this.llx = llx;
        this.lly = lly;
        this.urx = urx;
        this.ury = ury;
        this.point = null;
        this.delta = delta;
        this.list = list;
	}
	QuadTreeNode<Point> insert(Point p) {
		if (list != null ) {
			list.add(p);
			return this;
		}
		else {
			
			double pllx = parent.llx();
			double plly = parent.lly();
			double pcenty = parent.centy();
			if (pcenty == lly) {
				if (pllx == llx) {
					parent.setTL(new ParentQuadTreeNode<Point>(myTree, parent, 
							llx, lly, urx, ury, delta));
					parent.getTL().insert(point);
					return parent.getTL().insert(p);
				}
				else {
					parent.setTR(new ParentQuadTreeNode<Point>(myTree, parent, 
							llx, lly, urx, ury, delta));
					parent.getTR().insert(point);
					return parent.getTR().insert(p);
				}
			}
			else if (plly == lly) {
				if (pllx == llx) {
					parent.setBL(new ParentQuadTreeNode<Point>(myTree, parent, 
							llx, lly, urx, ury, delta));
					parent.getBL().insert(point);
					return parent.getBL().insert(p);
				}
				else {
					parent.setBR(new ParentQuadTreeNode<Point>(myTree, parent, 
							llx, lly, urx, ury, delta));
					parent.getBR().insert(point);
					return parent.getBR().insert(p);
				}
			}
			System.err.println("This should not happen");
			return null;
		}
	}
	Point getPoint() {
		return point;
	}
	ArrayList<Point> getList() {
		return list;
	}
	Point removeFromList(int k) {
		return list.remove(k);
	}
	void setList(ArrayList<Point> newlist) {
		list = newlist;
	}
	String getType() {
		return "Leaf";
	}
	double llx() {
		return llx;
	}

	double lly() {
		return lly;
	}
	
	double urx() {
		return urx;
	}
	
	double ury() {
		return ury;
	}

}
