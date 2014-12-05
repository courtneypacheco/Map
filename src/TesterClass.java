import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import LoadData.MapData;
import RTree.RTreeNode;
import StateNeighbors.StateNeighbors;


public class TesterClass {
	
	public static void main(String[] args) throws IOException {
		
		// Load map data
		HashMap mapData_States = LoadMapData("src\\NationalFile_StateProvinceDecimalLatLong.txt");
		
		//TEST
		//testcode(mapData_States);
		
		// Create RTree
		RTreeNode Root = CreateRTree(mapData_States);
		
		Root.printStats();
		
		// Load state neighbors
		//StateNeighbors stateNeighbors = LoadStateNeighborsList();
		
		// Test RTreeNode methods
		//TestRTreeNodeClass();
	}
	
	public static void testcode(HashMap mapData){
		//This prints out all the states. Just for debugging purposes.
        for (Object current_state : mapData.keySet()) {											// Iterate through all states
        	
        	// IMPORTANT: Uncomment when selecting to print out a specific state (for testing purposes only)
        	if (!current_state.toString().equals("MH")) continue;
        	
        	RTreeNode stateNode = new RTreeNode();												// Initialize current state's nodes
            System.out.println(current_state);													// Prints out state
            
            HashMap state_value = (HashMap) mapData.get(current_state);							// Get internal state hashmap (counties and their dimensions)
            
            for (Object current_county : state_value.keySet()){									// Iterate through all counties in state
            	
                System.out.print("   - " + current_county);										// Prints out county belonging to [this] state
                ArrayList county_dimensions = (ArrayList) state_value.get(current_county);		// Get county's list of rectangular dimensions.
                System.out.print(" " + county_dimensions + "\n");								// Print out county's dimensions (points form)
                
                // Store coordinates. Note ArrayList points order: [x1, y1, x2, y2]
                /*Double x1, x2, y1, y2;
                x1 = (Double) county_dimensions.get(0);			// Get x1
                y1 = (Double) county_dimensions.get(1);			// Get y1
                x2 = (Double) county_dimensions.get(2);			// Get x2
                y2 = (Double) county_dimensions.get(3);			// Get y2
                
                // Create county node: name, x1, x2, y1, y2
                RTreeNode countyNode = new RTreeNode(current_county.toString(), x1, x2, y1, y2);
                
                // Add county to state
                stateNode.addChild(countyNode);*/
            }
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
	
	/**
	 * Loads states' neighbors
	 * @return
	 * @throws IOException
	 */
	public static StateNeighbors LoadStateNeighborsList() throws IOException{
		System.out.println("Loading State Neighbors data...");
		StateNeighbors stateNeighbors = new StateNeighbors();
		System.out.println("Loading State Neighbors completed...");
		return stateNeighbors;
	}
	
	/**
	 * Creates a specific RTree: 
	 * Level 1 (Root): The United States
	 * Level 2: States
	 * Level 3: Counties
	 * @param mapData	Type: HashMap
	 * @return
	 */
	public static RTreeNode CreateRTree(HashMap<String, HashMap<String, ArrayList>> mapData){
		
		RTreeNode rootNode = new RTreeNode();			// Root of tree
		
		//This prints out all the states. Just for debugging purposes.
        for (Object current_state : mapData.keySet()) {											// Iterate through all states
        	
        	RTreeNode stateNode = new RTreeNode();												// Initialize current state's nodes
            System.out.println(current_state);													// Prints out state
            
            HashMap state_value = (HashMap) mapData.get(current_state);							// Get internal state hashmap (counties and their dimensions)
            
            for (Object current_county : state_value.keySet()){									// Iterate through all counties in state
            	
                System.out.print("   - " + current_county);										// Prints out county belonging to [this] state
                ArrayList county_dimensions = (ArrayList) state_value.get(current_county);		// Get county's list of rectangular dimensions.
                System.out.print(" " + county_dimensions + "\n");								// Print out county's dimensions (points form)
                
                // Store coordinates. Note ArrayList points order: [x1, y1, x2, y2]
                Double x1, x2, y1, y2;
                
                if (county_dimensions.size() < 4) continue;		// If ArrayList does not have 4 points, skip county
                
                x1 = (Double) county_dimensions.get(0);			// Get x1
                y1 = (Double) county_dimensions.get(1);			// Get y1
                x2 = (Double) county_dimensions.get(2);			// Get x2
                y2 = (Double) county_dimensions.get(3);			// Get y2
                
                // Create county node: name, x1, x2, y1, y2
                RTreeNode countyNode = new RTreeNode(current_county.toString(), x1, x2, y1, y2);
                
                // Add county to state
                stateNode.addChild(countyNode);
            }
            
            // Add state to root node
            rootNode.addChild(stateNode);
        }
        
        rootNode.setName("Root: United States");
		
		return rootNode;
	}
	
	public static void TestRTreeNodeClass(){
		// Test RTreeNodes class
		System.out.println("----------------------------------");
		RTreeNode UnitedStates = new RTreeNode("United States", 1, 25, 1, 25);
		
		RTreeNode MA = new RTreeNode("MA", 1, 10, 1, 10);
		RTreeNode CA = new RTreeNode("CA", 1, 3, 1, 3);
		RTreeNode TX = new RTreeNode("TX", 2, 20, 2, 20);
		RTreeNode blah = new RTreeNode();
		
		UnitedStates.addChild(MA);
		UnitedStates.addChild(CA);
		UnitedStates.addChild(TX);
		UnitedStates.addChild(blah);
		
		System.out.println(UnitedStates.getName());
		System.out.println(UnitedStates.getChildByName("MA").getName());
		System.out.println(UnitedStates.getChildByName("TX").getName());
		System.out.println(UnitedStates.getChildByName("CA").getName());
		System.out.println(UnitedStates.getChildByName("None").getName());
		
		// Test the expand region method
		System.out.println("----------------------------------");
		
		RTreeNode Basic = new RTreeNode("Basic", 0, 10, 0, 10);
		Basic.printStats();
		
		RTreeNode Larger = new RTreeNode("Larger", -5, 20, -5, 20);
		Larger.printStats();
		
		RTreeNode Smaller = new RTreeNode("Smaller", 2, 4, 2, 4);
		Smaller.printStats();
		
		RTreeNode Both = new RTreeNode("Both", -5, 9, 1, 20);
		Both.printStats();
		
		System.out.println("----------------- Now expand Basic-----------------");
		System.out.println("----------------- Basic + Larger -----------------");
		Basic = new RTreeNode("Basic", 0, 10, 0, 10);	// Reset
		Basic.expandRegion(Larger);
		Basic.printStats();
		System.out.println("----------------- Basic + Smaller -----------------");
		Basic = new RTreeNode("Basic", 0, 10, 0, 10);	// Reset
		Basic.expandRegion(Smaller);
		Basic.printStats();
		System.out.println("----------------- Basic + Both -----------------");
		Basic = new RTreeNode("Basic", 0, 10, 0, 10);	// Reset
		Basic.expandRegion(Both);
		Basic.printStats();
		
		// Test recursive find
		Basic = new RTreeNode("Basic", 0, 10, 0, 10);	// Reset
		
		RTreeNode hello1 = new RTreeNode("hello1", 0, 7, 0, 7);
		RTreeNode hello2 = new RTreeNode("hello2", 0, 11, 0, 11);
		RTreeNode hellothere1 = new RTreeNode("hellothere1", -5, 7, 0, 7);
		hello1.addChild(hellothere1);
		
		Basic.addChild(hello1);
		Basic.addChild(hello2);
		
		double x = 1;
		double y = 10;
		
		System.out.println("Basic contains point (" + x + ", " + y + ")?: " + Basic.containsPoint(x, y));
		System.out.println("hello1 contains point (" + x + ", " + y + ")?: " + hello1.containsPoint(x, y));
		System.out.println("hello2 contains point (" + x + ", " + y + ")?: " + hello2.containsPoint(x, y));
		System.out.println("hellothere1 contains point (" + x + ", " + y + ")?: " + hellothere1.containsPoint(x, y));
		
		ArrayList<RTreeNode> nodesContainingPointTest = Basic.findNodesContainingPoint(x, y);
		for (int ii = 0; ii < nodesContainingPointTest.size(); ii++){
			System.out.println(nodesContainingPointTest.get(ii).getName());
		}
	}
}
