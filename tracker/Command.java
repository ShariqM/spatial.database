package tracker;


import static tracker.Main.inp;
import static tracker.Main.myScan;

import java.util.Scanner;

/**
 * The Parent of all commands
 * @author Shariq Mobin cs61b-eb
 *
 */
abstract class Command {
	
	/* Stores the name of my command */
	final String name;
	
	/* A List of all commands so that execution is clean */
	static final Command[] commands = {
		new AddCommand(),
		new BoundsCommand(),
		new CloserThanCommand(),
		new CommentCommand(),
		new HelpCommand(),
		new LoadCommand(),
		new NearCommand(),
		new PrintCommand(),
		new QuitCommand(),
		new RadCommand(),
		new SemiColonCommand(),
		new SimulateCommand(),
		new WriteCommand()
	};

	Command (String name) {
		this.name = name;
	}

	static Command get(String cmnd) throws ProblemException {
		if (cmnd.startsWith("#"))
			return new CommentCommand();
		for (Command command : commands) {
			if (command.name.startsWith(cmnd)) {
				return command;
			}
		}
		throw Main.ERR("Invalid Command: %s", cmnd);
	}
	
	abstract void execute (TrackingSystem syst, Scanner scan)
		throws ProblemException;
	
	/**
	 * A parser to eliminate semi colons for all commands
	 * @param scan the scanner to read the input
	 * @return the double value intended
	 */
	double parse(Scanner scan) {
		String next = scan.next();
		if (next.endsWith(";")) {
			
			/* Handles prompting the correct number of times */
			String s = scan.findInLine(".*");
			if (s != null) {
				Main.skip+= 2;
				myScan = new Scanner(s);
				inp.push(scan);
			}
			
			return Double.parseDouble(next.substring(0,next.length()-1));
		}
		else
			return Double.parseDouble(next);
	}


}
