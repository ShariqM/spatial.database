package tracker;

import java.util.Scanner;
import static tracker.Main.*;
/** 
 * SemiColonCommand ';'
 * @author Shariq Mobin cs61b-eb
 */
class SemiColonCommand extends Command {

	SemiColonCommand () {
		super (";");
	}
	/**
	 * This method simply halts the program until another input is given
	 * @param syst takes the trackingsystem because of Command
	 * @param scan takes in the scanner because of Command
	 */
	void execute(TrackingSystem syst, Scanner scan) {
		skip++;
		String s = scan.findInLine(".*");
		if (s != null) {
			skip+= 2;
			myScan = new Scanner(s);
			inp.push(scan);
			
		}
		
	}
}
