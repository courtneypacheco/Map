import java.io.IOException;
import java.util.ArrayList;
import LoadData.MapData;
import RTree.RTreeNode;


public class TesterClass {
	
	public static void main(String[] args) throws IOException {
		
		// Load map data
		MapData mapData = LoadMapData("src\\NationalFile_StateProvinceDecimalLatLong.txt");
		
		// Test RTreeNode methods
		TestRTreeNodeClass();
	}
	
	/***
	 * Loads input map data for application
	 * @throws IOException
	 */
	public static MapData LoadMapData(String filename) throws IOException{
		System.out.println("Loading map data...");
		MapData mapData = new MapData(filename);
		System.out.println("Loading map data completed...");
		return mapData;
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
