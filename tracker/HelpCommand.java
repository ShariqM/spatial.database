package tracker;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import static tracker.Main.*;

/** 
 * HelpCommand 'help'
 * Prints a helpful summary of commands
 * @author Shariq Mobin cs61b-eb
 *
 */
class HelpCommand extends Command {
	
	HelpCommand() {
		super("help");
	}
	
	void execute(TrackingSystem syst, Scanner scan) throws ProblemException {
		try {
			Scanner scanner = new Scanner (new File ("testing/help.txt"));
			while (scanner.hasNextLine())
				System.out.println(scanner.nextLine());
		} catch (FileNotFoundException e) {
			throw ERR("help.txt was removed from the testing folder");
		}
	}

}
