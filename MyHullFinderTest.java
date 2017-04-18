package convexhull;

import static org.junit.Assert.*;

import static org.hamcrest.CoreMatchers.is;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

import net.datastructures.Entry;
import support.convexhull.Angle;
import support.convexhull.AngleComparator;
import support.convexhull.CircularTree;
import support.convexhull.HullPoint;

/**
 * This class can be used to test the functionality of you convex hull
 * implementation. You will find s few examples to guide you through the syntax
 * of writing test cases. Each test case uses its own HullFinder instance, to
 * ensure that the test cases are independent of each other. All of these given
 * examples should also pass once you've implemented your hull finder. The
 * annotation @Test before each test case is JUnit syntax, it basically lets the
 * compiler know that this is a unit test method. Use this annotation for every
 * test method. This class is also like any other java class, so should you need
 * to add private helper methods to use in your tests, you can do so, simply
 * without the annotations as you usually would write a method. The general
 * framework of a test case is: - Name the test method descriptively, mentioning
 * what is being tested (it is ok to have slightly verbose method names here) -
 * Set-up the program state (ex: instantiate a heap and insert K,V pairs into
 * it) - Use assertions to validate that the program is in the state you expect
 * it to be
 *
 *
 * TODO: Give an overview of your tests and a brief explanation of why you
 * believe you have covered all possible cases.
 */

public class MyHullFinderTest {
	/*
	 * Change this if you are working from home (just like in the App class)!
	 */
	String _imagePath;

	public MyHullFinderTest() {
		// _imagePath = "/course/cs016/lib/convexhull-images/";
		//String imagePath = "/Users/Zorro/Pictures/";
		String imagePath = "/Users/Akhil/Desktop/CS_PICS";

	}

	/**
	 * An example of testing the hull when incremental add is used. Read the
	 * inline comments to understand the set-up. If you attempt
	 * angularGrahamScan, a similar method could be followed to test it.
	 */
	@Test
	public void testAddIncremental() {
		MyHullFinder hull = new MyHullFinder(_imagePath);
		// Create a list of coordinate pairs of points (100, 200), (300, 300) ,
		// (100, 100) and (138, 188)
		List<Integer> coordinates = Arrays.asList(100, 200, 300, 300, 100, 100, 138, 188);

		// Convert from coordinates to a list of HullPoints using a helper
		// method
		List<HullPoint> points = getPoints(coordinates);

		// Pass all points through your implementation of insertIncremental
		for (int i = 0; i < points.size(); i++) {
			hull.insertIncremental(points.get(i));
		}

		// Form a list of HullPoints you know would form the correct hull out of
		// your test points.
		List<HullPoint> expectedPoints = getPoints(Arrays.asList(100, 200, 300, 300, 100, 100));

		// Using the hull's iterator, create a list of the actual points in the
		// hull
		List<HullPoint> actualPoints = new ArrayList<HullPoint>();
		Iterator<Entry<Angle, HullPoint>> it = hull.hull();
		while (it.hasNext()) {
			actualPoints.add(it.next().getValue());
		}

		/*
		 * This assertion checks that the set of expected points contains all of
		 * the actual points and vice-versa. Checking this both ways ensures
		 * that the lists in fact contain the same set of points. Note that two
		 * HullPoints are equal if both coordinates of the points are equal.
		 */
		assertTrue(expectedPoints.containsAll(actualPoints) && actualPoints.containsAll(expectedPoints));

		// another way to check the same thing is to use two assertThat
		// statements
		assertThat(expectedPoints.containsAll(actualPoints), is(true));
		assertThat(actualPoints.containsAll(expectedPoints), is(true));

		assert (expectedPoints.containsAll(actualPoints) && actualPoints.containsAll(expectedPoints));
	}

	/**
	 * TODO: Write your own tests below! Don't forget to test any helper methods
	 * you write.
	 */

	@Test
	public void testUpdateAnchor() {
		// TODO -- test with a lot of points being removed from early in hull
		MyHullFinder hull = new MyHullFinder(_imagePath);
		
		// test that the anchor becomes the first point with a hull of less than 3 points 
		HullPoint point1 = new HullPoint();
		point1.setX(30);
		point1.setY(60);
		hull.updateAnchor(point1);
		assertEquals(point1, hull.get_anchor());
		// because insertion happens in updateHull insert point 
		hull.get_hull().insert(hull.calcAngle(point1), point1);
		Angle point1ToAnchor = hull.calcAngle(point1);
		
		
		// insert 2nd point and check for changes
		HullPoint point2 = new HullPoint();
		point2.setX(40);
		point2.setY(70);
		hull.get_hull().insert(hull.calcAngle(point2), point2);
		Angle point2ToAnchor = hull.calcAngle(point2);
		
		
		// check again that anchor is expected when another point has been added
		assertEquals(point1,hull.get_anchor());
		
		// test that after 3 points the anchor becomes the average of the 3 points 
		HullPoint point3 = new HullPoint();
		point3.setX(50);
		point3.setY(80);
		
		hull.updateAnchor(point3);
		
		
		HullPoint avgAnchor = new HullPoint();
		avgAnchor.setX(40);
		avgAnchor.setY(70);
		
		assertEquals(avgAnchor, hull.get_anchor());

		// test that the angles of the points have been changed with relation to the anchor
		assertTrue(point1ToAnchor != hull.calcAngle(point1));
		// new point1 angle = Angle(-10,-10), original was (0,0)
		assertTrue(point1ToAnchor.getX()-10 == hull.calcAngle(point1).getX());
		assertTrue(point1ToAnchor.getY()-10 == hull.calcAngle(point1).getY());
		
		assertTrue(point2ToAnchor != hull.calcAngle(point2));
		
		
		
		
	}
	
	@Test
	public void testClockwiseOrCollinear(){
		// TODO test that this method actually produces what we want 
		MyHullFinder hull = new MyHullFinder(_imagePath);
		// these points are from the slides, should be CCW, left turn 
		HullPoint first = new HullPoint();
		first.setX(0);
		first.setY(0);
		
		HullPoint second = new HullPoint();
		second.setX(100);
		second.setY(50);
		
		HullPoint third = new HullPoint();
		third.setX(50);
		third.setY(100);
		
		assertEquals(false, hull.clockwiseOrCollinear(first, second, third));
		//TODO -- must test true case as well
	}
	
	

	/**
	 * Below are some helper methods you might find useful for testing. Feel
	 * free to add your own
	 */

	/**
	 * Given a list of coordinate pairs as (x1, y1, x2, y2, ...), creates and
	 * returns a list of HullPoints with those coordinates
	 * 
	 * @param List<Integer>
	 *            of coordinates to convert to points
	 * @return List<HullPoint>, a list of hull points.
	 */
	private List<HullPoint> getPoints(List<Integer> coordinates) {
		List<HullPoint> points = new ArrayList<>();
		if (coordinates == null || coordinates.isEmpty()) {
			System.out.println("Null or empty argument passed in. Returning empty list");
			return points;
		}

		if (coordinates.size() % 2 == 1) {
			System.out.println("Odd number of coordinates given in the argument, please give an even number "
					+ "to be able to form HullPoints. Returning empty list.");
			return points;
		}

		for (int i = 0; i < coordinates.size(); i += 2) {
			HullPoint point = new HullPoint();
			point.setX(coordinates.get(i));
			point.setY(coordinates.get(i + 1));
			points.add(point);
		}

		return points;
	}

	/**
	 * Helper method to print coordinates of a given HullPoint
	 * 
	 * @param HullPoint
	 *            p
	 */
	private void printPoint(HullPoint p) {
		System.out.println("(" + p.getX() + " , " + p.getY() + ")");
	}

}
