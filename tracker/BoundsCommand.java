package tracker;


import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import static tracker.Main.*;

/** 
 * The bounds command 'bound x_low y_low x_high y_high
 * @author Shariq Mobin cs61b-eb
 */
class BoundsCommand extends Command {

	BoundsCommand () {
		super ("bounds");
	}
	/**
	 * This method effictively creates bounds on the system.
	 * @param syst takes the trackingsystem so it can properly execute
	 * @param scan takes int he scanner to generate the desired args
	 * @exception InputMisMatchException  thrown if not double arg
	 * @exception NumberFormatException  thrown if not double arg
	 * @exception NoSuchElementException thrown if not enough args
	 * @exception IllegalStateException thrown if scanner is closed
	 */
	void execute(TrackingSystem syst, Scanner scan) throws ProblemException {
		try {
			syst.createBoundary(scan.nextDouble(), scan.nextDouble(),
						scan.nextDouble(), parse(scan));
		} catch (InputMismatchException e) {
			throw Main.ERR ("Arguments of Bounds must be of type double");
		} catch (NoSuchElementException e) {
			throw Main.ERR ("Too few Args to Bounds");
		} catch (IllegalStateException e) {
			throw ERR("Bounds Failed, Scanner is closed");
		} catch (NumberFormatException e) {
			throw Main.ERR ("Arguments of Bounds must be of type double");
		}
	}
}
