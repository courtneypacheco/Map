import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import LoadData.MapData;
import RTree.RTreeNode;
import RTree.RTreeNode_GlobalScale;
import StateNeighbors.StateNeighbors;


public class TesterClass {
	
	public static void main(String[] args) throws IOException {
		
		// Load map data
		HashMap mapData_States = LoadMapData("src\\NationalFile_StateProvinceDecimalLatLong.txt");
		
		// Create RTree
		RTreeNode_GlobalScale Root = CreateRTree(mapData_States);
		//Root.printStats();	//test
		
		ArrayList<RTreeNode_GlobalScale> nodesContainingPointTest;
		//nodesContainingPointTest = Root.findNodesContainingPoint(-118.809997, 46.694205); // WA
		//nodesContainingPointTest = Root.findNodesContainingPoint(-170.7583333, -13);		// AS
		//nodesContainingPointTest = Root.findNodesContainingPoint(-104.98, 39.7516667);	// CO
		nodesContainingPointTest = Root.findNodesContainingPoint(-99.948225, 46.939944);	// ND --> include but also end at WA
		//nodesContainingPointTest = Root.findNodesContainingPoint(-83.600569, 37.706635);	// KY --> bigger list, includes KY
		//nodesContainingPointTest = Root.findNodesContainingPoint(-121.657211, 40.569105);	// CA --> bigger list, includes CA
		//nodesContainingPointTest = Root.findNodesContainingPoint(-71.433562, 42.551768);	// MA
		for (int ii = 0; ii < nodesContainingPointTest.size(); ii++){
			System.out.println(nodesContainingPointTest.get(ii).getName());
		}
		
		
		
		for (Object stateChild: Root.getChildren()){
			stateChild = (RTreeNode_GlobalScale) stateChild;
			if (!((RTreeNode_GlobalScale) stateChild).getName().equals("NY")) continue;
			((RTreeNode_GlobalScale) stateChild).printStats();
			for (Object countyChild: ((RTreeNode_GlobalScale) stateChild).getChildren()){
				countyChild = (RTreeNode_GlobalScale) countyChild;
				((RTreeNode_GlobalScale) countyChild).printStats();
			}
		}
	
		// Load state neighbors
		//StateNeighbors stateNeighbors = LoadStateNeighborsList();
		
		// Test RTreeNode_GlobalScale methods
		//TestRTreeNode_GlobalScaleClass();
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
	 * Creates a specific Rectangle Tree: 
	 * Level 1 (Root): The United States
	 * Level 2: States
	 * Level 3: Counties
	 * @param mapData	Type: HashMap
	 * @return
	 */
	public static RTreeNode_GlobalScale CreateRTree(HashMap<String, HashMap<String, ArrayList>> mapData){
		
		RTreeNode_GlobalScale rootNode = new RTreeNode_GlobalScale();			// Root of tree
		
		//This prints out all the states. Just for debugging purposes.
        for (Object current_state : mapData.keySet()) {											// Iterate through all states
        	
        	// IMPORTANT: Uncomment when selecting to print out a specific state (for testing purposes only)
        	//if (!current_state.toString().equals("MH")) continue;
        	
        	RTreeNode_GlobalScale stateNode = new RTreeNode_GlobalScale();						// Initialize current state's nodes
            System.out.println(current_state);													// Prints out state
            stateNode.setName(current_state.toString());										// Add state's name to node
            
            HashMap state_value = (HashMap) mapData.get(current_state);							// Get internal state hashmap (counties and their dimensions)
            
            for (Object current_county : state_value.keySet()){									// Iterate through all counties in state
            	
                System.out.print("   - " + current_county);										// Prints out county belonging to [this] state
                ArrayList county_dimensions = (ArrayList) state_value.get(current_county);		// Get county's list of rectangular dimensions.
                System.out.print(" " + county_dimensions + "\n");								// Print out county's dimensions (points form)
                
                // Store coordinates. Note ArrayList points order: [x1, y1, x2, y2]
                Double x1, x2, y1, y2;
                
                if (county_dimensions.size() != 4) continue;		// If ArrayList does not have 4 points, skip county
                
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
	
	public static void TestRTreeNode_GlobalScaleClass(){
		// Test RTreeNode_GlobalScales class
		System.out.println("----------------------------------");
		RTreeNode_GlobalScale UnitedStates = new RTreeNode_GlobalScale("United States", 1, 25, 1, 25);
		
		RTreeNode_GlobalScale MA = new RTreeNode_GlobalScale("MA", 1, 10, 1, 10);
		RTreeNode_GlobalScale CA = new RTreeNode_GlobalScale("CA", 1, 3, 1, 3);
		RTreeNode_GlobalScale TX = new RTreeNode_GlobalScale("TX", 2, 20, 2, 20);
		RTreeNode_GlobalScale blah = new RTreeNode_GlobalScale();
		
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
		
		RTreeNode_GlobalScale Basic = new RTreeNode_GlobalScale("Basic", 0, 10, 0, 10);
		Basic.printStats();
		
		RTreeNode_GlobalScale Larger = new RTreeNode_GlobalScale("Larger", -5, 20, -5, 20);
		Larger.printStats();
		
		RTreeNode_GlobalScale Smaller = new RTreeNode_GlobalScale("Smaller", 2, 4, 2, 4);
		Smaller.printStats();
		
		RTreeNode_GlobalScale Both = new RTreeNode_GlobalScale("Both", -5, 9, 1, 20);
		Both.printStats();
		
		System.out.println("----------------- Now expand Basic-----------------");
		System.out.println("----------------- Basic + Larger -----------------");
		Basic = new RTreeNode_GlobalScale("Basic", 0, 10, 0, 10);	// Reset
		Basic.expandRegion(Larger);
		Basic.printStats();
		System.out.println("----------------- Basic + Smaller -----------------");
		Basic = new RTreeNode_GlobalScale("Basic", 0, 10, 0, 10);	// Reset
		Basic.expandRegion(Smaller);
		Basic.printStats();
		System.out.println("----------------- Basic + Both -----------------");
		Basic = new RTreeNode_GlobalScale("Basic", 0, 10, 0, 10);	// Reset
		Basic.expandRegion(Both);
		Basic.printStats();
		
		// Test recursive find
		Basic = new RTreeNode_GlobalScale("Basic", 0, 10, 0, 10);	// Reset
		
		RTreeNode_GlobalScale hello1 = new RTreeNode_GlobalScale("hello1", 0, 7, 0, 7);
		RTreeNode_GlobalScale hello2 = new RTreeNode_GlobalScale("hello2", 0, 11, 0, 11);
		RTreeNode_GlobalScale hello2baby = new RTreeNode_GlobalScale("hello2baby", 0, 11, 0, 11);
		RTreeNode_GlobalScale hellothere1 = new RTreeNode_GlobalScale("hellothere1", -5, 7, 0, 7);
		hello1.addChild(hellothere1);
		hello2.addChild(hello2baby);
		
		Basic.addChild(hello1);
		Basic.addChild(hello2);
		
		double x = 1;
		double y = 10;
		
		System.out.println("Basic contains point (" + x + ", " + y + ")?: " + Basic.containsPoint(x, y));
		System.out.println("hello1 contains point (" + x + ", " + y + ")?: " + hello1.containsPoint(x, y));
		System.out.println("hello2 contains point (" + x + ", " + y + ")?: " + hello2.containsPoint(x, y));
		System.out.println("hellothere1 contains point (" + x + ", " + y + ")?: " + hellothere1.containsPoint(x, y));
		
		ArrayList<RTreeNode_GlobalScale> nodesContainingPointTest = Basic.findNodesContainingPoint(x, y);
		for (int ii = 0; ii < nodesContainingPointTest.size(); ii++){
			System.out.println(nodesContainingPointTest.get(ii).getName());
		}
	}
}
