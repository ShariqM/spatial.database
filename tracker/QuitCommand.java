package tracker;


import java.util.Scanner;
import static tracker.Main.*;
/**
 * Quit command 'quit'
 * @author Shariq Mobin cs61b-eb
 */
class QuitCommand extends Command{
	
	QuitCommand() {
		super("quit");
	}
	
	/**
	 * This method quits the program
	 * @param syst takes the trackingsystem because of Command
	 * @param scan takes in the scanner because of Command
	 */
	void execute(TrackingSystem syst, Scanner scan) {
		exit(exitval);
	}

}
