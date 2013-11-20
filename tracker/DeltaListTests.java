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
public class DeltaListTests {
	
	private String output = "testing/output.txt";

	@Test public void testCloser1() throws FileNotFoundException {
		String input = "testing/deltatest1.txt";
		String[] args = new String[] {input,output};
		try {
			Main.main(args);
		}catch (ExitException e) {
			Scanner scan = new Scanner (new File (output));
			scan.nextLine();
			String x = scan.nextLine();
			assertEquals("1:(4.781,3.325,2.538,5.308) " +
					"2:(3.457,9.481,1.921,6.956)", x.trim());
		}
	}
}

