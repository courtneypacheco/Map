import RTree.RTreeNode;



public class TesterClass {
	
	public static void main(String[] args) {
		RTreeNode UnitedStates = new RTreeNode("United States", 1, 25, 1, 25);
		
		RTreeNode MA = new RTreeNode("MA", 1, 10, 1, 10);
		RTreeNode CA = new RTreeNode("CA", 1, 3, 1, 3);
		RTreeNode TX = new RTreeNode("TX", 2, 20, 2, 20);
		
		UnitedStates.addChild(MA);
		UnitedStates.addChild(CA);
		UnitedStates.addChild(TX);
		
		System.out.println(UnitedStates.getName());
		
	}

}
