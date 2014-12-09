package CommandLine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import LoadData.MapData;
import RTree.RTreeNode_GlobalScale;
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
    			HashMap mapData_States = LoadMapData("src\\NationalFile_StateProvinceDecimalLatLong.txt");
    		
    		
    			// Create RTree(ish) data structure
    			RTreeNode_GlobalScale Root = CreateRTree(mapData_States);
    			ArrayList<RTreeNode_GlobalScale> nodesContainingPoint;
    			nodesContainingPoint = Root.findNodesContainingPoint(x, y);
    			String stateAbbrv = null;
    			for (int ii = 0; ii < nodesContainingPoint.size(); ii++) {
    				// Get current state node
    				RTreeNode_GlobalScale currentStateNode = nodesContainingPoint.get(ii); 
    				//if (stateAbbrv.equals("Root: United States")) continue;
    				stateAbbrv = currentStateNode.getName(); // Get name of state (abbreviation)	
    				//System.out.println(nodesContainingPoint.get(ii));
    			}
    			
    			// Display resulting nodes that contain the original coordinates or nearby
    			for (int ii = 0; ii < nodesContainingPoint.size(); ii++) {
    				System.out.println("From recursive function. Nodes containing test point: " + nodesContainingPoint.get(ii).getName());
    			}
    		
	    			// Try priority queue
	    			//pqDistances pq = new pqDistances(mapData_States.States, 100, "CO", -104.98, 39.7516667);
	    			pqDistances pq = new pqDistances(mapData_States, 100, stateAbbrv, x, y, k);
	    			pq.printQueue(k);
    		}
        }
        	
        	/***
        	 * Loads input map data for application
        	 * @throws IOException
        	 */
        	public static HashMap LoadMapData(String filename) throws IOException{
        		System.out.println("Loading Map Data...");
        		MapData mapData = new MapData(filename);
        		System.out.println("Loading Map Data completed...");
        		return mapData.getStates();
        	}
        	
        	public static RTreeNode_GlobalScale CreateRTree(HashMap<String, HashMap<String, ArrayList>> mapData){
        		
        		RTreeNode_GlobalScale rootNode = new RTreeNode_GlobalScale();							// Root of tree
        		
        		//This prints out all the states. Just for debugging purposes.
                for (Object current_state : mapData.keySet()) {											// Iterate through all states
                	
                	// Uncomment when selecting to print out a specific state (for testing purposes only)
                	//if (!current_state.toString().equals("MH")) continue;
                	
                	RTreeNode_GlobalScale stateNode = new RTreeNode_GlobalScale();						// Initialize current state's nodes
                    stateNode.setName(current_state.toString());										// Add state's name to node
                    
                    HashMap state_value = (HashMap) mapData.get(current_state);							// Get internal state hashmap (counties and their dimensions)
                    
                    for (Object current_county : state_value.keySet()){									// Iterate through all counties in state
                    	
                        ArrayList county_dimensions = (ArrayList) state_value.get(current_county);		// Get county's list of rectangular dimensions.
                        
                        // Store coordinates. Note ArrayList points order: [x1, y1, x2, y2]
                        Double x1, x2, y1, y2;
                        
                        if (county_dimensions.size() != 4) continue;	// If ArrayList does not have 4 points, skip county
                        
                        y2 = (Double) county_dimensions.get(0);			// Get y2 = max latitude
                        x1 = (Double) county_dimensions.get(1);			// Get x1 = min longitude 
                        y1 = (Double) county_dimensions.get(2);			// Get y1 = min latitude
                        x2 = (Double) county_dimensions.get(3);			// Get x2 = max longitude
                        
                        // Create county node: name, x1, x2, y1, y2
                        RTreeNode_GlobalScale countyNode = new RTreeNode_GlobalScale(current_county.toString(), x1, x2, y1, y2);
                        
                        // Add county to state
                        stateNode.addChild(countyNode);
                    }
                    
                    // Add state to root node
                    rootNode.addChild(stateNode);
                }
                
                rootNode.setName("Root: United States");
        		
        		return rootNode;
        	}
        	
        	
}
