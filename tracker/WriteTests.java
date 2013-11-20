package tracker;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.junit.Test;


/**
 * Test the WriteCommand
 * @author Shariq Mobin
 */
public class WriteTests {
	

	/** Tests that it writes the new points correctly to file */
	@Test public void testWrite1() throws FileNotFoundException {
		String input = "testing/writetest1.txt";
		String[] args = new String[] {input};
		try {
			Main.main(args);
		}catch (ExitException e) {
			Scanner scan = new Scanner (new File ("testing/testwrite1.txt"));
			
			String x = scan.nextLine();
			assertEquals("bounds 0.0 0.0 256.0 256.0", x.trim());
			x  = scan.nextLine();
			assertEquals("rad 3.0", x.trim());
			x  = scan.nextLine();
			assertEquals("add 1 22.0 22.0 0.0 -20.0", x.trim());
			x  = scan.nextLine();
			assertEquals("add 2 22.0 68.0 0.0 20.0", x.trim());
		}
	}
}























