package convexhull;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**
 * This class is used to test all of the test cases in the MyHullFinderTest
 * class. If any of the tests fail, a string indicating which test failed is
 * printed to the console. If all of the tests pass, a message will print to
 * indicate this.
 *
 * You do not need to touch this file!
 */
public class TestRunner {
   public static void main(String[] args) {
      Result result = JUnitCore.runClasses(MyHullFinderTest.class);
      for (Failure failure : result.getFailures()) {
         System.out.println("TEST FAILED: " + failure.toString());
      }
      if(result.wasSuccessful()) {
        System.out.println("all tests passed!");
      }
   }
}
