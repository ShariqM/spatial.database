package tracker;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.junit.Test;

/**
 * Test the loading of files 
 * @author Shariq Mobin
 */
public class LoadTests {
	
	private String output = "testing/output.txt";

	@Test public void testLoad1() throws FileNotFoundException {
		String input = "testing/loadtest1.txt";
		String[] args = new String[] {input,output};
		try {
			Main.main(args);
		}catch (ExitException e) {
			Scanner scan = new Scanner (new File (output));
			scan.nextLine();
			String x = scan.nextLine();
			assertEquals("10:(20.00,20.00,20.00,20.00) " +
					"12:(40.00,40.00,40.00,40.00)", x.trim());
			x  = scan.nextLine();
			assertEquals("24:(101.0,101.0,101.0,101.0) " +
					"94:(95.00,95.00,12.00,12.00)", x.trim());
		}
	}
}























