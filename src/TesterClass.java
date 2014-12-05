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
		
        //This prints out all the states. Just for debugging purposes.
        for (Object current_state : mapData_States.keySet()) {									// Iterate through all states
            System.out.println(current_state);													// Prints out state
            
            HashMap state_value = (HashMap) mapData_States.get(current_state);							// Get internal state hashmap
            
            for (Object current_county : state_value.keySet()){									// Iterate through all counties in state
            	
                System.out.print("   - " + current_county);										// Prints out county belonging to [this] state
                ArrayList county_dimensions = (ArrayList) state_value.get(current_county);		// Get county's list of rectangular dimensions
                System.out.print(" " + county_dimensions + "\n");								// Print out county's dimensions
            }
        }
        
		
		// Load state neighbors
		//StateNeighbors stateNeighbors = LoadStateNeighborsList();
		
		// Test RTreeNode methods
		//TestRTreeNodeClass();
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
	
	public static StateNeighbors LoadStateNeighborsList() throws IOException{
		System.out.println("Loading State Neighbors data...");
		StateNeighbors stateNeighbors = new StateNeighbors();
		System.out.println("Loading State Neighbors completed...");
		return stateNeighbors;
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
