package util;

import java.util.ArrayList;

/**
 * A ParentQuadTreeNode, I have other QuadTreeNode's
 * as children
 * @author Shariq Mobin cs61b-eb
 */
class ParentQuadTreeNode<Point extends QuadTree.QuadPoint> extends QuadTreeNode<Point> {
	
	/* My five children Top Right, Top Left, Bottom Right, Bottom Left, and Delta List */
	private QuadTreeNode<Point> TL, TR, BR, BL, DL;
	
	private double llx, lly, urx, ury, centx, centy, delta;

	/**
	 * Construct a new ParentQuadTreeNode
	 * @param wholeTree the tree I am apart of
	 */
	ParentQuadTreeNode(QuadTree<Point> wholeTree) {
		super(wholeTree);
		this.parent = null;
		TL = TR = BR = BL = null;
	}
	ParentQuadTreeNode(QuadTree<Point> wholeTree, ParentQuadTreeNode<Point> parent, double llx,
			double lly, double urx, double ury, double delta) {
		super(wholeTree);
		this.parent = parent;
		TL = TR = BR = BL = DL = null;
        this.llx = llx;
        this.lly = lly;
        this.urx = urx;
        this.ury = ury;
        centx = (llx + urx) / 2;
        centy = (lly + ury) / 2;
        this.delta = delta;
        
		
	}
	public String getType() {
		return "Parent";
	}
	public double llx() {
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
	public double centx() {
		return centx;
	}
	public double centy() {
		return centy;
	}
	/**
	 * Takes in a point and enters it into the quadtree via leaves or a Delta List (DL)
	 * @param p the point to be inserted
	 * @return the Node it is resides in
	 */
	QuadTreeNode<Point> insert(Point p) {
		double ptx = p.x();
		double pty = p.y();
		if ((urx - llx) <= delta || (ury - lly) <= delta) {
			if (DL == null) {
				ArrayList<Point> list = new ArrayList<Point>();
				list.add(p);
				DL = new QuadNode<Point>(myTree, this, llx, lly, urx, ury, list, delta);
				return DL;
			}
			else
				return DL.insert(p);
		}
		else if (pty >= centy) {
			if (ptx < centx) {
				if (TL == null) {
					if ((ury - centy) <= delta || (centx - llx) <= delta) {
						ArrayList<Point> list = new ArrayList<Point>();
						list.add(p);
						TL = new QuadNode<Point>(myTree, this, llx, centy, centx, ury, list, delta);
					}
					else {
						TL = new QuadNode<Point>(myTree, this, llx, centy, centx, ury, p, delta);
					}
					return TL;
				}
				else {
					return TL.insert(p);
				}	
			}
			else {
				if (TR == null) {
					if ((ury - centy) <= delta || (urx - centx) <= delta) {
						ArrayList<Point> list = new ArrayList<Point>();
						list.add(p);
						TR = new QuadNode<Point>(myTree, this, centx, centy, urx, ury, list, delta);
					}
					else {
						TR = new QuadNode<Point>(myTree, this, centx, centy, urx, ury, p, delta);
					}
					return TR;
				}
				else {
					return TR.insert(p);
				}
			}
		}
		else {
			if (ptx < centx) {
				if (BL == null) { 
					if ((centy - lly) <= delta || (centx - llx) <= delta) {
						ArrayList<Point> list = new ArrayList<Point>();
						list.add(p);
						BL = new QuadNode<Point>(myTree, this, llx, lly, centx, centy, list, delta);
					}
					else {
						BL = new QuadNode<Point>(myTree, this, llx, lly, centx, centy, p, delta);
					}
					return BL;
				}
				else {
					return BL.insert(p);
				}
			}
			else {
				if (BR == null) {
					if ((centy - lly) <= delta || (urx - centx) <= delta) {
						ArrayList<Point> list = new ArrayList<Point>();
						list.add(p);
						BR = new QuadNode<Point>(myTree, this, centx, lly, urx, centy, list, delta);
					}
					else {
						BR = new QuadNode<Point>(myTree, this, centx, lly, urx, centy, p, delta);
					}
					return BR;
				}
				else {
					return BR.insert(p);
				}
			}
		}
		
	}
	public QuadTreeNode<Point> getTR() {
		return TR;
	}
	public void setTR(QuadTreeNode<Point> TR) {
		this.TR = TR;
	}
	public QuadTreeNode<Point> getTL() {
		return TL;
	}
	public void setTL(QuadTreeNode<Point> TL) {
		this.TL = TL;
	}
	public QuadTreeNode<Point> getBL() {
		return BL;
	}
	public void setBL(QuadTreeNode<Point> BL) {
		this.BL = BL;
	}
	public QuadTreeNode<Point> getBR() {
		return BR;
	}
	public void setBR(QuadTreeNode<Point> BR) {
		this.BR = BR;
	}
	public QuadTreeNode<Point> getDL() {
		return DL;
	}
	public void setDL(QuadTreeNode<Point> DL) {
		this.DL = DL;
	}
	

}
