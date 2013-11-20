package tracker;

import java.util.Scanner;
/** 
 * The Command command '# <A comment> '
 * @author Shariq Mobin cs61b-eb
 */
class CommentCommand extends Command {

	CommentCommand () {
		super ("#");
	}
	/**
	 * Scans till the next ; or line which signal ends of single input.
	 * @param syst takes the trackingsystem so it can properly execute
	 * @param scan takes int he scanner to generate the desired args
	 */
	void execute(TrackingSystem syst, Scanner scan) {
		if ( scan.findInLine(";") ==  null)
			scan.nextLine();
	} 

}
