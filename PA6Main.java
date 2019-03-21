
/** 
 * AUTHOR: Alex Dunn
 * FILE: PA6Main.java
 * ASSIGNMENT: Programming Assignment 6 - Crispr 
 * COURSE: CSc 210; Monday 3-4:50pm Section; Spring 2019
 * PURPOSE: This program implements an ecosystem that consists of a number of columns and rows.
 *  The animals will move around this ecosystem, and when they run into each another depending on 
 *  the animal type, they will either eat or reproduce. Animals with a Crispr gene will stop being 
 *  able to reproduce. To use this program, it accepts the name of an input file on the command 
 *  line. The input file will contain all of the simulation initialization settings and the command 
 *  lines to simulate.
 *  
 * USAGE:
 * java PA6Main infile from publicTestCases folder 
 *  
 * Where the infile is the name of an txt input file that contains all of the simulation 
 * initialization settings and the commands to simulate.
 * ----------- EXAMPLE INPUT -------------
 *  Input File:
 *  ------------------------------------------------
 *  |rows: 5                                       |
 *  |cols: 5                                       |
 *  |                                              |
    |CREATE (1,1) lion female right                |
 *  |PRINT                                         |
 *  |MOVE                                          |    
 *  |print                                         |
 *  ------------------------------------------------
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class PA6Main {
    /*
     * Purpose: Retrieves the integer value of a label:value line
     * 
     * @param reader, confirms that input file is valid.
     * 
     * @param label, the row and column prefix.
     * 
     * @return valStr, the integer value of the label.
     */
	private static int	getValue(Scanner reader, String label) {
		String	line = "";
				
		while (reader.hasNextLine()) {
			line = reader.nextLine().trim();

			if (line.length() > 0) {
				if (line.toLowerCase().startsWith(label)) {
					String	valStr = line.substring(label.length() + 1).trim();
					
					try {						
						return	Integer.parseInt(valStr);
					} catch(Exception e) {
						System.out.format("Error:  Invalid number '%s'.", valStr);
					}
				}

				break;
			}
		}
		
		return	-1;
	}

    /*
     * Purpose: Retrieves the next command line.
     * 
     * @param reader, confirms that input file is valid.
     * 
     * @return line, the next line in the file for getValue to process.
     */
	private static String[]	getCommand(Scanner reader) {
		String	line = "";
				
		while (reader.hasNextLine()) {
			line = reader.nextLine().trim();

			if (line.length() > 0) {
				return	line.split("\\s+");
			}
		}
		
		return	null;
	}

    /*
     * Purpose: Validate input file and return its scanner.
     * 
     * @param reader, confirms that input file is valid.
     * 
     * @return reader, processes input file.
     */
	private static Scanner	getCommands(String fileName) {
		try	{
			Scanner	reader = new Scanner(new File(fileName));

			if (reader.hasNextLine()) {
				return	reader;
			}
		} catch(FileNotFoundException e) {
			System.out.format("Error:  File '%s' does not exist.", fileName);
		}

		return	null;
	}

    /*
     * Main
     */
	public static void	main(String[] args) {
    	if (args.length >= 1) {
        	//	Get a valid scanner for the input command file
        	//
    		Scanner	cmds = getCommands(args[0]);
    		
    		if (cmds != null) {
    			//	Retrieve the grid dimensions
    			//
    			int	rows = getValue(cmds, "rows:");
    			int	cols = getValue(cmds, "cols:");
    			
    			if (rows > 0 && cols > 0) {
    				//	Process commands of the form:
    				//		action [ arguments... ]
    				//	where, available actions are:
    				//		CREATE (row,column) type gender [ arguments... ]
    				//		PRINT
    				//		MOVE - Move all animals
    				//		MOVE (row,column) -  Move only animals at this location
    				//		MOVE type -  Move only animals of the specified type
    				//		MOVE class -  Move only animals of the specified class
    				//		REPRODUCE -  Reproduce all animals
    				//		REPRODUCE (row,column) -  Reproduce only animals at this location
    				//		REPRODUCE type -  Reproduce only animals of the specified type
    				//
    				Ecosystem	ecoSys = new Ecosystem(rows, cols);
    				String[]	cmd = getCommand(cmds);

    				while (cmd != null) {
    					String	action = cmd[0];
    					
    					if (action.equalsIgnoreCase("CREATE")) {
    						ecoSys.create(cmd);
    					} else if (action.equalsIgnoreCase("MOVE")) {
    						ecoSys.move(cmd);
    					} else if (action.equalsIgnoreCase("PRINT")) {
    						ecoSys.print();
    					} else if (action.equalsIgnoreCase("REPRODUCE")) {
    						ecoSys.reproduce(cmd);
    					} else {
    						System.out.format("Error:  Invalid command, '%s'\n", action);
    					}
    					
    					cmd = getCommand(cmds);
    				}
    			}
    			
    			cmds.close();
    		}
    	} else {
			System.out.println("Not enough arguments");
    	}

	}
}
