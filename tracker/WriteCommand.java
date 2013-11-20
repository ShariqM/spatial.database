package tracker;


import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;
import static tracker.Main.*;
import util.QuadTree.QuadPoint;
/**
 * Write Command 'write filename'
 * @author Shariq Mobin cs61b-eb
 *
 */
public class WriteCommand extends Command  {
	
	Comparator<double[]> idOrder = new Comparator<double[]>() {
		public int compare(double[] id, double[]id2) {
			double difference = id[0] - id2[0];
			return (int)difference;
		}
	};
	
	WriteCommand() {
		super("write");
	}
	
	/**
	 * This method recreates in the input in a file 
	 * @param syst takes the trackingsystem so it can properly execute
	 * @param scan takes in the scanner to generate the desired args
	 * @exception NoSuchElementException thrown if not enough args
	 * @exception IOException if it cannot write the output
	 */
	void execute(TrackingSystem syst, Scanner scan) throws ProblemException {
		try {

			PrintWriter write = new PrintWriter(new File(scan.next()));
			write.printf("bounds %s %s %s %s%nrad %s%n", syst.llx(), syst.lly(),
					syst.urx(), syst.ury(), syst.radius());
			Iterator<QuadPoint> pts = syst.iterator();
			ArrayList<double[]> idList = new ArrayList<double[]>();
			while (pts.hasNext()) {
				QuadPoint pt = pts.next();
				double ptx = pt.x();
				double pty = pt.y();
				double[] temp = syst.map.get(new Coordinate(ptx,pty));
				idList.add(new double [] { temp[0], ptx, pty, temp[1], temp[2] });
			}
			Collections.sort(idList, idOrder);	
			for (double[] point : idList) {
				write.printf("add %d %s %s %s %s%n", (int)point[0],
						point[1], point[2], point[3], point[4]);
			}
			write.close();
			
		} catch (NoSuchElementException e) {
			throw ERR("Writer filename was not provided");
		} catch (IOException e) {
			throw ERR("Failed to write the output");
		}
		
		
	}

}
