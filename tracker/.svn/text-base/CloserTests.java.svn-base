package tracker;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.junit.Test;



/**
 * Test closer-than method
 * @author Shariq Mobin
 */
public class CloserTests {
	
	private String output = "testing/output.txt";

	@Test public void testCloser1() throws FileNotFoundException {
		String input = "testing/closertest1.txt";
		String[] args = new String[] {input,output};
		try {
			Main.main(args);
		}catch (ExitException e) {
			Scanner scan = new Scanner (new File (output));
			scan.nextLine();
			String x = scan.nextLine();
			assertEquals("1:(22.00,22.00,0.000,20.00) " +
					"3:(22.00,29.00,0.000,0.000)", x.trim());
			x  = scan.nextLine();
			assertEquals("2:(22.00,68.00,0.000,-20.00) " +
					"5:(29.00,72.00,1.000,2.000)", x.trim());
		}
	}
}

