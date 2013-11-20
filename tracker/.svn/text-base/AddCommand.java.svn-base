package tracker;


import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import static tracker.Main.*;

/** 
 * The command 'add ID X Y VX VY'. 
 * @author Shariq Mobin cs61b-eb
 * */
class AddCommand extends Command {
	
	AddCommand() {
		super("add");
	}
	/**
	 * This method effictively adds a point to the system.
	 * @param syst takes the trackingsystem so it can properly execute
	 * @param scan takes int he scanner to generate the desired args
	 * @exception InputMisMatchException thrown if not double arg
	 * @exception NumberFormatException  thrown if not double arg
	 * @exception NoSuchElementException thrown if not enough args
	 * @exception IllegalStateException thrown if scanner is closed
	 */
	void execute(TrackingSystem syst, Scanner scan) throws ProblemException {
		try {
			syst.addPoint(scan.nextInt(), scan.nextDouble(),
						scan.nextDouble(), scan.nextDouble(),
						parse(scan));
		} catch (InputMismatchException e) {
			throw ERR ("Arguments of Add must be of type double");
		} catch (NoSuchElementException e) {
			throw ERR ("Too few Args to Add");
		} catch (IllegalStateException e) {
			throw ERR("Add Failed, Scanner is closed");
		} catch (NumberFormatException e) {
			throw ERR("Arguments of Add must be of type double");
		}
	}
}
