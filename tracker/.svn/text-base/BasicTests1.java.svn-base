package tracker;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.junit.Test;


/**
 * Test the basic elements of input
 * @author Shariq Mobin cs61b-eb
 */
public class BasicTests1 {
	
	private String output = "testing/output.txt";
	
	/** Tests whether the program exits when 'quit' is entered' 
	 * @throws FileNotFoundException **/
	@Test public void testQuit() throws FileNotFoundException {
		String input = "testing/quit.txt";
		String[] args = new String[] {input,output};
		try {
			Main.main(args);
		}catch (ExitException e) {
			Scanner scan = new Scanner (new File (output));
			String x = scan.nextLine();
			assertEquals("Particle tracker, v4.0.", x.trim());
		}
	}
	/** Tests whether the program can handle multiple commands per line 
	 * @throws FileNotFoundException **/
	@Test public void testSemiColon() throws FileNotFoundException {
		String input = "testing/semicolontest.txt";
		String[] args = new String[] {input,output};
		try {
			Main.main(args);
		}catch (ExitException e) {
			Scanner scan = new Scanner (new File (output));
			String x = scan.nextLine();
			assertEquals("Particle tracker, v4.0.", x.trim());
		}
	}
	
	/** Tests the restructuring of trees 
	 * @throws FileNotFoundException */
	@Test public void testStruct() throws FileNotFoundException {
		String input = "testing/pretest.txt";
		String[] args = new String[] {input,output};
		try {
			Main.main(args);
		}catch (ExitException e) {
			Scanner scan = new Scanner (new File (output));
			scan.nextLine();
			String x = scan.nextLine();
			assertEquals("1:(0.1000,0.1000,1.000,0.000)", x.trim());
		}
	}
	@Test public void testCollision() throws FileNotFoundException {
		String input = "testing/collisionball.txt";
		String[] args = new String[] {input,output};
		try {
			Main.main(args);
		}catch (ExitException e) {
			Scanner scan = new Scanner (new File (output));
			scan.nextLine();
			String x = scan.next();
			assertEquals("5:(22.00,25.00,-20.00,0.000)", x.trim());
			x  = scan.next();
			assertEquals("6:(68.00,25.00,20.00,0.000)", x.trim());
		}
	}
	/** Tests 900 point input **/
	@Test public void testLargeData() throws FileNotFoundException {
		String input = "testing/largedata.txt";
		String[] args = new String[] {input,output};
		try {
			Main.main(args);
		}catch (ExitException e) {
			Scanner scan = new Scanner (new File (output));
			Scanner calcoutput = new Scanner(new File("testing/largedataoutput.txt"));
			while (calcoutput.hasNextLine()) {
				String out = scan.nextLine();
				String cout = calcoutput.nextLine();
				if (!out.equals(cout))
					assertTrue(false);
			}
			assertTrue(true);
		}
	}
}























