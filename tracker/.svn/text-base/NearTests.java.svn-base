package tracker;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.junit.Test;


/**
 * Test the near command
 * @author Shariq Mobin
 */
public class NearTests {
	
	private String output = "testing/output.txt";

	/** tests basic near x y d **/
	@Test public void testNear1() throws FileNotFoundException {
		String input = "testing/neartest1.txt";
		String[] args = new String[] {input,output};
		try {
			Main.main(args);
		}catch (ExitException e) {
			Scanner scan = new Scanner (new File (output));
			scan.nextLine();
			String x = scan.next();
			assertEquals("1:(10.00,10.00,20.00,20.00)", x.trim());
			x  = scan.next();
			assertEquals("2:(15.00,15.00,20.00,20.00)", x.trim());
			x = scan.next();
			assertEquals("3:(20.00,20.00,20.00,20.00)", x.trim());
		}
	}
	/** tests basic near x * d **/
	@Test public void testNear2() throws FileNotFoundException {
		String input = "testing/neartest2.txt";
		String[] args = new String[] {input,output};
		try {
			Main.main(args);
		}catch (ExitException e) {
			Scanner scan = new Scanner (new File (output));
			scan.nextLine();
			String x = scan.next();
			assertEquals("1:(20.00,10.00,20.00,20.00)", x.trim());
			x  = scan.next();
			assertEquals("2:(40.00,50.00,20.00,20.00)", x.trim());

		}
	}
	/** tests basic near * y d **/
	@Test public void testNear3() throws FileNotFoundException {
		String input = "testing/neartest3.txt";
		String[] args = new String[] {input,output};
		try {
			Main.main(args);
		}catch (ExitException e) {
			Scanner scan = new Scanner (new File (output));
			scan.nextLine();
			String x = scan.next();
			assertEquals("1:(20.00,30.00,20.00,20.00)", x.trim());
		}
	}
}























