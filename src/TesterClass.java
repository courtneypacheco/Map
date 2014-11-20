import RTree.RTreeNode;

public class TesterClass {
	
	public static void main(String[] args) {
		
		// Test RTreeNodes class
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
	}

}
