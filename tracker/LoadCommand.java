package tracker;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import static tracker.Main.*;
/**
 * Load command 'load filename'
 * @author Shariq Mobin cs61b-eb
 */
class LoadCommand extends Command {
	LoadCommand () {
		super ("load");
	}
	/**
	 * This method prints all objects within distance d of eachother
	 * @param syst takes the trackingsystem so it can properly execute
	 * @param scan takes int he scanner to generate the desired args
	 * @exception NoSuchElementException thrown if not enough args
	 * @exception FileNodeFoundException thrown if file does not exist
	 */
	void execute(TrackingSystem syst, Scanner scan) throws ProblemException {
		try {
			Scanner load = new Scanner (new File (scan.next()));
			myScan = load;
			loading = true;
			inp.push(scan);
		} catch (NoSuchElementException e) {
			throw ERR("Load Command failed to find a file to read");
		}
		catch(FileNotFoundException e) {
			throw ERR("File not found in Load Command");
		} 
	}

}
