package convexhull;

import support.convexhull.*;
import net.datastructures.*;
import support.convexhull.Angle;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * An implementation of an incremental convex-hull implemented with a
 * CircularTree. Be certain that your running times match those specified in the
 * program documentation!
 *
 * Feel free to add additional comments.
 *
 */

public class MyHullFinder implements ConvexHullFinder {

	/**
	 * Set up and initialize the instance variables, adding new ones at the top
	 * of the file if necessary or desired.
	 */

	private CircularTree<Angle, HullPoint> _hull;

	private String _imagePath;
	private HullPoint _anchor;

	public MyHullFinder(String imagePath) {
		/*
		 * Note: Any time a CircularTree<Angle, HullPoint> is instantiated, it
		 * must use a new AngleComparator, or else it will use a default
		 * comparator that doesn't understand how our angles work.
		 */

		_hull = new CircularTree<Angle, HullPoint>(new AngleComparator());
		_imagePath = imagePath;

		// TODO initalize any other instance variables you use

	}

	/**
	 * This method will be called by the <code>HullVisualizer</code> when a user
	 * clicks on the screen to add a vertex. When the GUI calls this method, the
	 * <code>ConvexHull</code> should decide if the new vertex should be on the
	 * hull or not. If the new point is on the hull, the <code>ConvexHull</code>
	 * should add it to the hull and then adjust the hull so that it is now
	 * convex.
	 *
	 * This method must run in O(k*log(n)) time, where k is the number of points
	 * removed from the hull to accommodate the new point, and n is the number
	 * of points on the hull prior to the insertion.
	 *
	 * WARNING: This can be a tricky running time to implement, but it is an
	 * essential feature of the project. Do *not* consistently traverse the
	 * entire hull to insert a point or you will violate running time
	 * requirements and lose points! Examine the methods of CircularTree
	 * carefully to see what is available to you. The lecture slides and
	 * supplementary materials may offer suggestions as to how to approach this
	 * problem.
	 *
	 * ANOTHER WARNING: Note that (0,0) is the top left corner of the panel,
	 * meaning that lower points have a higher y value. Take this into account
	 * when making angles.
	 *
	 * <p>
	 * Do not call this method explicitly! The visualizer does this!
	 *
	 * @param vertex
	 *            the newly created vertex resulting from a user click
	 */
	public void insertIncremental(HullPoint vertex) {
		// TODO implement an Incremental Graham Scan

		// update anchor point (helper method)

		updateAnchor(vertex);

		// calculate angle from anchor
		Angle angle = calcAngle(vertex);

		// insert point into circular tree

		updateHull(vertex);
	}

	/**
	 * 
	 */
	private void updateHull(HullPoint vertex) {
		// determine if left or right turn, might have to recurse:

		// if right, remove 2nd to last point from hull, recurse

		// if left turn, we all good babie
	}

	/**
	 * 
	 */
	private Angle calcAngle(HullPoint vertex) {
		return new Angle(vertex.getX() - _anchor.getX(), vertex.getY() - _anchor.getY());
	}

	private Boolean isCollinear(HullPoint a, HullPoint b, HullPoint c){
		// all have same y, all have different x and y, use point with smallest x
		if ((a.getY() == b.getY() && b.getY() == c.getY()) ||
		(a.getX() == b.getX() && b.getX() == c.getX())){
			return true;
		}
		
	
		 Angle angle1 = new Angle()
		return false;
	}

	/**
	 * updates anchor points
	 * 
	 */
	private void updateAnchor(HullPoint vertex) {
		// if less than three points currently in hull, anchor is first point
		if (_hull.size() == 0) {
			_anchor = vertex;
		}

		// if more than two, use average of first three points in hull
		if (_hull.size() == 2) {
			// average the points using vertex
			HullPoint first = _hull.first().getValue();
			HullPoint second = _hull.after(_hull.first()).getValue();
			double x = (first.getX() + second.getX() + vertex.getX()) / 3;
			x = Math.round(x);
			double y = (first.getY() + second.getY() + vertex.getY()) / 3;
			y = Math.round(y);
			_anchor = new HullPoint();
			_anchor.setX((int) x);
			_anchor.setY((int) y);

			// first two points are in hull --> get root, root->next() of
			// CircularTree
			// third point is "vertex"
		}

	}

	/**
	 * When the user clicks on the "Clear" button, the
	 * <code>HullVisualizer</code> class will call this method.
	 * 
	 * <p>
	 * Do not call this method explicitly! The visualizer does this!
	 */
	public void clear() {
		// TODO clear your instance variables
	}

	/**
	 * @return an <code>Iterator</code> containing all the
	 *         <code>A_HullPoint</code>s in the upper chain of the hull
	 */
	public Iterator<Entry<Angle, HullPoint>> hull() {
		return _hull.iterator();
	}

	/**
	 * Gets the image path for the background. Please do not change this!
	 */
	public String getImagePath() {
		return _imagePath;
	}

	/**
	 * When the "Solve" button is clicked this method is called to invoke a
	 * static graham scan.
	 *
	 * Note: It is probably a good idea to 'sort' the points by angle using some
	 * sort of data structure that uses keys to keep track of the minimum.
	 *
	 */
	public void angularGrahamScan(ArrayList<HullPoint> vertices) {
		// TODO implement a static GrahamScan (optional, for extra credit)
	}

	/*
	 * You may find it useful to add some helper methods here. Think about
	 * actions that may be executed often in the rest of your code. Writing
	 * helper methods instead of copying and pasting helps "segment" your code,
	 * makes it easier to understand, and avoids problems in keeping each
	 * occurance "up-to-date."
	 */

}
