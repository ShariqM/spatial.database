package tracker;


import static tracker.Main.ERR;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Simulate Command 'simulate t'
 * @author Shariq Mobin cs61b-eb
 *
 */
public class SimulateCommand extends Command{
	
	SimulateCommand() {
		super("simulate");
	}
	/**
	 * This method simulates realtime by the given input
	 * @param syst takes the trackingsystem so it can properly execute
	 * @param scan takes in the scanner to generate the desired args
	 * @exception InputMisMatchException thrown if not double arg
	 * @exception NumberFormatException  thrown if not double arg
	 * @exception NoSuchElementException thrown if not enough args
	 * @exception IllegalStateException thrown if scanner is closed
	 */
	void execute(TrackingSystem syst, Scanner scan) throws ProblemException {

		try {

			double t, delta_T, t_collision, v_max, D, dist;
			t = parse(scan);
			delta_T = 0;
			v_max = syst.maxVelocity();
			while (t > 0) {
				D = 2*syst.radius();
				dist = 2 * (D + syst.radius());
				delta_T = Math.min(dist/v_max, t);
				t_collision = syst.collision(dist);
				delta_T = Math.min(delta_T, t_collision);
				syst.UpdatePos(delta_T);
				syst.UpdateVel();
				t -= delta_T;
				
			}
			
		} catch (InputMismatchException e) {
			throw ERR ("Arguments of simulate must be of type double");
		} catch (NoSuchElementException e) {
			throw ERR ("Too few Args to simulate");
		} catch (IllegalStateException e) {
			throw ERR("Simulate Failed, Scanner is closed");
		} catch (NumberFormatException e) {
			throw ERR("Arguments of simulate must be of type double");
		}
		
	}

}
