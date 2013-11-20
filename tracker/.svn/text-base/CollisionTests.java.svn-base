package tracker;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.junit.Test;

/**
 * Test the Collision command
 * @author Shariq Mobin cs61b-eb
 */
public class CollisionTests {
	
	private String output = "testing/output.txt";
	
	/** Test horizontal point on point collision **/
	@Test public void testCollision1() throws FileNotFoundException {
		String input = "testing/collisiontest1.txt";
		String[] args = new String[] {input,output};
		try {
			Main.main(args);
		}catch (ExitException e) {
			Scanner scan = new Scanner (new File (output));
			scan.nextLine();
			String x = scan.next();
			assertEquals("1:(22.00,25.00,-20.00,0.000)", x.trim());
			x  = scan.next();
			assertEquals("2:(68.00,25.00,20.00,0.000)", x.trim());
		}
	}
	/** Test point on  left wall collision **/
	@Test public void testCollision2() throws FileNotFoundException {
		String input = "testing/collisiontest2.txt";
		String[] args = new String[] {input,output};
		try {
			Main.main(args);
		}catch (ExitException e) {
			Scanner scan = new Scanner (new File (output));
			scan.nextLine();
			String x = scan.next();
			assertEquals("1:(23.00,25.00,20.00,0.000)", x.trim());
		}
	}
	/** Test vertical point on point collision **/
	@Test public void testCollision3() throws FileNotFoundException {
		String input = "testing/collisiontest3.txt";
		String[] args = new String[] {input,output};
		try {
			Main.main(args);
		}catch (ExitException e) {
			Scanner scan = new Scanner (new File (output));
			scan.nextLine();
			String x = scan.next();
			assertEquals("1:(22.00,22.00,0.000,-20.00)", x.trim());
			x  = scan.next();
			assertEquals("2:(22.00,68.00,0.000,20.00)", x.trim());
		}
	}
	/** Corner wall collision case **/
	@Test public void testCollision4() throws FileNotFoundException {
		String input = "testing/collisiontest4.txt";
		String[] args = new String[] {input,output};
		try {
			Main.main(args);
		}catch (ExitException e) {
			Scanner scan = new Scanner (new File (output));
			scan.nextLine();
			String x = scan.next();
			assertEquals("1:(4.000,4.000,1.000,1.000)", x.trim());
		}
	}
	/** Corner ball collision case **/
	@Test public void testCollision5() throws FileNotFoundException {
		String input = "testing/collisiontest5.txt";
		String[] args = new String[] {input,output};
		try {
			Main.main(args);
		}catch (ExitException e) {
			Scanner scan = new Scanner (new File (output));
			scan.nextLine();
			String x = scan.nextLine();
			assertEquals("1:(2.586,7.414,-1.000,1.000) 2:(5.414,4.586,1.000,-1.000)", x.trim());
		}
	}
	
	
	
}























