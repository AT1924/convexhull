package convexhull;

import support.convexhull.*;
import net.datastructures.*;

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
		// update anchor point (helper method)
		updateAnchor(vertex);

		// insert point into circular tree, update hull
		updateHull(vertex);
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
		_hull = null;
		_anchor = null;
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

	/**
	 * calculates the angle between a point and the anchor point
	 * 
	 * @param vertex
	 * @return
	 */
	public Angle calcAngle(HullPoint vertex) {
		return new Angle(vertex.getX() - _anchor.getX(), vertex.getY() - _anchor.getY());
	}

	/**
	 * updates anchor point. If the hull has 0 points, the anchor is the passed
	 * in point. If the hull has 3 or more points in it, then the anchor is
	 * found by the average of the first three points
	 * 
	 * @param vertex
	 */
	public void updateAnchor(HullPoint vertex) {
		// if less than three points currently in hull, anchor is first point
		if (_hull.size() == 0) {
			_anchor = vertex;
			
		}

		// if more than two, use average of first three points in hull
		// make sure to reinsert the values with new angles into hull
		if (_hull.size() == 2) {
			// average the points using vertex
			HullPoint first = _hull.first().getValue();
			HullPoint second = _hull.after(_hull.first()).getValue();
			double x = (first.getX() + second.getX() + vertex.getX()) / 3;
			x = Math.round(x);
			double y = (first.getY() + second.getY() + vertex.getY()) / 3;
			y = Math.round(y);

			// set anchor
			_anchor = new HullPoint();
			_anchor.setX((int) x);
			_anchor.setY((int) y);

			// recalculates and reinserts the angle of the points with respect to new anchor
			Entry<Angle, HullPoint> firstEntry = _hull.first();
			Entry<Angle, HullPoint> secondEntry = _hull.after(firstEntry);
			
			// should I just remove the angle here instead?
			_hull.remove(firstEntry);
			_hull.remove(secondEntry);
			
			if (calcAngle(firstEntry.getValue()).compareTo(calcAngle(secondEntry.getValue())) <= 0) {
				_hull.insert(calcAngle(firstEntry.getValue()), firstEntry.getValue());
				_hull.insert(calcAngle(secondEntry.getValue()), secondEntry.getValue());
			} else {
				_hull.insert(calcAngle(secondEntry.getValue()), secondEntry.getValue());
				_hull.insert(calcAngle(firstEntry.getValue()), firstEntry.getValue());
			}

		}

	}

	public void updateHull(HullPoint vertex) {
		// edge case: if < 4 points in hull, just add vertex to hull
		Angle angleToAnchor = calcAngle(vertex);
		if (_hull.size() < 4) {
			_hull.insert(calcAngle(vertex), vertex);
		} else {
			// insert point with key angle and value of point
			// angle can be calculated from angle method
			Entry<Angle, HullPoint> latest = _hull.insert(angleToAnchor, vertex);

			// TODO -- get before and after points, and possibly delete point
			// if deleted, hull does not change, so return
			Entry<Angle, HullPoint> beforeLatest = _hull.before(latest);
			Entry<Angle, HullPoint> afterLatest = _hull.after(latest);
			if (clockwiseOrCollinear(beforeLatest.getValue(), latest.getValue(), afterLatest.getValue())) {
				_hull.remove(latest);
				return;
			}

			// get previous two entries
			Entry<Angle, HullPoint> prev1 = _hull.before(latest);
			Entry<Angle, HullPoint> prev2 = _hull.before(prev1);

			// while last point is collinear or clockwise from penultimate point
			while ((clockwiseOrCollinear(prev2.getValue(), prev1.getValue(), latest.getValue())) && _hull.size() > 3) {
				// remove middle point
				_hull.remove(prev1);
				// keep removing middle point while clockwiseOrCollinear
				prev1 = prev2;
				prev2 = _hull.before(prev2);
			}

			// TODO
			// need to make latest point 1st of 3 points to consider, and update
			// looking forward
			// make sure you actually have the points to consider (there are
			// points left)
			prev1 = latest;
			prev2 = _hull.after(latest);
			latest = _hull.after(prev2);
			while (clockwiseOrCollinear(prev1.getValue(), prev2.getValue(), latest.getValue()) && _hull.size() > 3
					&& prev2 != null) {
				// remove middle point
				_hull.remove(prev2);
				// keep removing middle point while clockwiseOrCollinear
				prev2 = latest;
				latest = _hull.after(latest);
			}
		}
	}

	public boolean clockwiseOrCollinear(HullPoint first, HullPoint second, HullPoint third) {
		// orientation test, if counterclockwise x1-y1 == 1, -1 for clockwise
		// and 0 for collinear
		
		int x1 = (second.getX() - first.getX()) * (third.getY() - first.getY());
		int y1 = (second.getY() - first.getY()) * (third.getX() - first.getX());
		if ((x1 - y1) > 0) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * calculates the orientation of a set of 3 points, which are the
	 * parameters. If the orientation is counter-clockwise returns 1, clockwise
	 * returns -1, and collinear returns 0.
	 * 
	 * @param first
	 * @param second
	 * @param third
	 * @return
	 */
	public int orientation(HullPoint first, HullPoint second, HullPoint third) {
		// orientation test, if counterclockwise return 1, -1 for clockwise and
		// 0 for collinear
		int x1 = (second.getX() - first.getX()) * (third.getY() - first.getY());
		int y1 = (second.getY() - first.getY()) * (third.getX() - first.getX());
		if ((x1 - y1) > 0) {
			return 1;
		}

		else if ((x1 - y1) < 0) {
			return -1;
		}

		else {
			return 0;
		}

	}

	/**
	 * 
	 * @param a
	 * @param b
	 * @param c
	 * @return
	 */
	public Boolean isCollinear(HullPoint a, HullPoint b, HullPoint c) {
		// all have same y, all have different x and y, use point with smallest
		// x
		// if ((a.getY() == b.getY() && b.getY() == c.getY()) ||
		// (a.getX() == b.getX() && b.getX() == c.getX())){
		// return true;
		// }
		//
		//
		// Angle angle1 = new Angle()
		return true;
	}
	
	// method to get _anchor for testing purposes
	public HullPoint get_anchor() {
		return _anchor;
	}
	// method to get _hull for testing purposes
	public CircularTree<Angle, HullPoint> get_hull() {
		return _hull;
	}

}
