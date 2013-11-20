package tracker;


import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import static tracker.Main.*;
/**
 * RadCommand 'rad r'
 * @author Shariq Mobin cs61b-eb
 *
 */
class RadCommand extends Command{
	
	RadCommand() {
		super("rad");
	}
	
	/**
	 * This method sets the radius of the tracking system
	 * @param syst takes the trackingsystem so it can properly execute
	 * @param scan takes in the scanner to generate the desired args
	 * @exception InputMisMatchException thrown if not double arg
	 * @exception NumberFormatException  thrown if not double arg
	 * @exception NoSuchElementException thrown if not enough args
	 * @exception IllegalStateException thrown if scanner is closed
	 */
	void execute(TrackingSystem syst, Scanner scan) throws ProblemException {
		try {
			syst.setRadius(parse(scan));
		} catch (InputMismatchException e) {
			throw ERR ("Arguments of rad must be of type double");
		} catch (NoSuchElementException e) {
			throw ERR ("Too few Args to rad");
		} catch (IllegalStateException e) {
			throw ERR("Rad Failed, Scanner is closed");
		} catch (NumberFormatException e) {
			throw ERR("Arguments of rad must be of type double");
		}
	}
	
}
