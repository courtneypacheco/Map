package CommandLine;

import java.io.IOException;
import java.util.HashMap;

import LoadData.MapData;
import RTree.pqDistances;
import Rectangle.RegionRectangle;

/* Command-line Interface
 * To test on Eclipse:
 * 		Run ---> Run Configurations ---> In the Arguments tab, enter arguments
 * 
 * Argument description:
 * 		x - latitude
 *		y - longitude
 *		k - number of nearest counties to dislay, must be between 1-10 inclusive
 * 
 */

public class CommandLine {
	/**
	 * Gets called when user provides incorrect commandline args
	 * @param errorCode: depends on the type of error from user-input
	 */
	static void errorCheck(int errorCode) {
		switch (errorCode) {
			// Incorrect number of arguments given
			case 1: System.out.println("Error:\tincorrect number of arguments entered");
					System.out.println("\tThis program requires three arguments in the following order:\n\t<latitude>   <longitude>   <number of results to display>\n\tExiting now.");
					System.exit(1);
			case 2: // out-of-bound points (ex. if user inputs 100 for the value for k)
			case 3: // incorrect input format (ex. if user inputs "abc", "17.3zre" as latitude, longitude)
		}
	}
	
	public static void main(String[] args) throws IOException {
		int argsLen = args.length;
		
        	if (argsLen != 3) {
        		errorCheck(1);
        	} else {
        		Double x = Double.parseDouble(args[0]);
        		Double y = Double.parseDouble(args[1]);
    			int k = Integer.parseInt(args[2]);

    			// Load map data
    			MapData mapData_States = new MapData("src\\NationalFile_StateProvinceDecimalLatLong.txt");
    		
    		
    		ArrayList<RTreeNode_GlobalScale> nodesContainingPoint;
		nodesContainingPoint = Root.findNodesContainingPoint(y, x);
		String stateAbbrv = null;
		for (int ii = 0; ii < nodesContainingPoint.size(); ii++){
			
			RTreeNode_GlobalScale currentStateNode = nodesContainingPoint.get(ii);				// Get current state node
			stateAbbrv = currentStateNode.getName();										// Get name of state (abbreviation)
			
			if (!stateAbbrv.equals("Root: United States")) continue;	
		}
		
		// Display resulting nodes that point is in or nearby
		for (int ii = 0; ii < nodesContainingPoint.size(); ii++){
			System.out.println("From recursive function. Nodes containing test point: " + nodesContainingPoint.get(ii).getName());
		}
    		
    			// Try priority queue
    			//pqDistances pq = new pqDistances(mapData_States.States, 100, "CO", -104.98, 39.7516667);
    			pqDistances pq = new pqDistances(mapData_States.States, 100, stateAbbrv, y, x, k); // longitude (y), latitude (x)
    			pq.printQueue(k);
        	}
	}
}
