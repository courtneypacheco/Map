package RTree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Stack;

public class RTree implements Skeleton{
    
    public RNode root_pointer;
    
    
    //Root node id
    private int root_id = 0;
    private int highestUsedNodeId = root_id;
    
    
    //Current size of the tree
    private int size = 0;
    
    
    //Current height of the tree
    private int tree_height = 0;
    

    //Tree parameters
    final protected int default_max_entries = 80; //max # of children
    final protected int default_min_entries = 1;  //min # of children
    
    
    /** 
     * Stacks which store each node's index and ID from the root down to the leaf.
    */
    private Stack parents = new Stack();
    private Stack parents_idx = new Stack();
    
    //Maps node IDs to nodes
    private HashMap<Integer,RNode> Node_IDs = new HashMap<Integer,RNode>();
    
    /**
    * Default constructor.
    * Creates a new tree.
    */
    public RTree(HashMap Data){
        //Create pointer to the root of the tree
        this.root_pointer = new RNode(this.root_id,this.tree_height);
        Node_IDs.put(root_id, root_pointer);

        HashMap Counties;    //hashmap of counties for a given state
        String county_name;  //holds the current county name
        ArrayList MBR;       //minimum bounding rectangle
        RNode c, s;          //c=County;  s=state
        
        size++;
        
        //Iterate through all the states
        for (Object state: Data.keySet()){ 
            String state_name = (String)state;
            Counties = (HashMap)Data.get(state_name);
            
            //RNode temp = new RNode();
            
            //Iterate through all the counties
            for (Object county: Counties.keySet()){
                
                //Next ~10 lines create a node based on the MBR for the county
                county_name = (String)county;
                MBR = (ArrayList)Counties.get(county_name);
                
                if (MBR.size()>2){
                    c = new RNode(state_name,county_name,(double)MBR.get(0),(double)MBR.get(1),(double)MBR.get(2),(double)MBR.get(3),this.tree_height,size);
                }
                else{ //some counties only have 1 coordinate point, so this 'else' statement is meant to handle those counties
                    c = new RNode(state_name,county_name,(double)MBR.get(0),(double)MBR.get(1),(double)MBR.get(0),(double)MBR.get(1),this.tree_height,size);
                }
                    
                add(c,size,this.tree_height); //problem w/ adding...
            }
        }
     
        System.out.println("\nRTree completed.");
        System.out.println("   -> Number of LEVELS in the tree: " + this.tree_height);
        System.out.println("   -> Number of NODES in the tree: " + this.size);
    }

    @Override
    public void add(RNode node, int node_id, int level) {
        
        RNode n = chooseLeaf(node,level);
        RNode leaf = null;
        
        
        //Make sure the node's number of children does not exceed the max # of children allowed
        if (n.nChildren < default_max_entries){
            n.addChild(node);
        }
        
        //If the number of children exceeds max #, then split the node
        else{
            leaf = split(n,node,size);
        }
        
        //Now fix the tree
        RNode new_node = fixTree(n, leaf);
        
        if (new_node != null){
            int orig_root_id = root_id;
            RNode orig_root = getNode(orig_root_id);
            
            this.root_id = getNextNode();
            this.tree_height++;
            
            RNode root = new RNode(root_id,tree_height);
            root.addChild(new_node);
            root.addChild(orig_root);
            Node_IDs.put(root_id, root);
        }
        
        
        size++;
    }

    @Override
    public void delete(String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public RNode split(RNode node, RNode extra, int ID) { //linear split
        
        //When we split the node, we will store ~1/2 the values here
        RNode new_node = new RNode(size,tree_height+1);
        Node_IDs.put(new_node.ID, new_node);
        
        //Now we want to find the best MBRs
        //(1) Pick seeds - find the distance between the 2 rectangles that are furthest apart from each other.
        double longitude1, latitude1, longitude2, latitude2, x, y, temp_distance;
        RNode temp_child;
        String R1 = null, R2 = null; //holds the names of the 2 rectangles furthest apart from each other.
        double largest_distance = 0;
        for (Object child1 : node.children.keySet()){
            
            temp_child = (RNode)node.children.get((String)child1);
            longitude1 = temp_child.center[0];
            latitude1 = temp_child.center[0];
            
            for (Object child2 : node.children.keySet()){
                
                temp_child = (RNode)node.children.get((String)child2);
                longitude2 = temp_child.center[0];
                latitude2 = temp_child.center[1];
                
                temp_distance = calculateDistance(latitude1,longitude1,latitude2,longitude2);
                
                if (temp_distance > largest_distance){
                    largest_distance = temp_distance;
                    R1 = (String)child1;
                    R2 = (String)child2;
                }
            }
        }
        
        //Compare R1 & R2 to the new node
        RNode Seed1, Seed2;

        //R1
        temp_child = (RNode)node.children.get(R1); //<---- error
        longitude1 = temp_child.center[0];
        latitude1  = temp_child.center[0];
        
        longitude2 = extra.center[0];
        latitude2  = extra.center[1];
        temp_distance = calculateDistance(latitude1,longitude1,latitude2,longitude2);
        
        //If distance btwn R1 & 'extra' is greater than the distance btwn R1 & R2...
        if (temp_distance > largest_distance){
            largest_distance = temp_distance;
            
            Seed1 = (RNode)node.children.get(R1);
            Seed2 = extra;
        }
        
        //R2
        temp_child = (RNode)node.children.get(R2);
        longitude1 = temp_child.center[0];
        latitude1  = temp_child.center[0];
        
        longitude2 = extra.center[0];
        latitude2  = extra.center[1];
        temp_distance = calculateDistance(latitude1,longitude1,latitude2,longitude2);
        if (temp_distance > largest_distance){
            largest_distance = temp_distance;
            Seed1 = extra;
            Seed2 = (RNode)node.children.get(R2);
        }
        else{
            Seed1 = (RNode)node.children.get(R1);
            Seed2 = (RNode)node.children.get(R2);
        }
        
        
        //(2) Now add nodes, one by one, to each MBR based on which seed grows the least
        RNode MBR1 = new RNode(tree_height,size++);
        RNode MBR2 = new RNode(tree_height,size++);
        
        MBR1.addChild(Seed1);
        MBR2.addChild(Seed2);
        
        double expand1=0, expand2=0;
        RNode current_child;
        
        //For each MBR, we need to keep track of running min and max
        double MBR1_min_x = Seed1.min_x;
        double MBR1_min_y = Seed1.min_y;
        double MBR1_max_x = Seed1.max_x;
        double MBR1_max_y = Seed1.max_y;
        double MBR2_min_x = Seed2.min_x;
        double MBR2_min_y = Seed2.min_y;
        double MBR2_max_x = Seed2.max_x;
        double MBR2_max_y = Seed2.max_y;
        
        for (Object child : node.children.keySet()){
            current_child = (RNode)node.children.get((String)child);
            expand1 = Seed1.calculateExpansion(current_child);
            expand2 = Seed2.calculateExpansion(current_child);
            
            //If seed1 expands more than seed2, then add to seed2. We want a minimum bounding rectangle!
            if (expand1 > expand2){
                MBR2.addChild(current_child);
                
                if (MBR2_min_x > current_child.min_x){
                    MBR2_min_x = current_child.min_x;
                }
                if (MBR2_max_x < current_child.max_x){
                    MBR2_max_x = current_child.max_x;
                }
                if (MBR2_min_y > current_child.min_y){
                    MBR2_min_y = current_child.min_y;
                }
                if (MBR2_max_y < current_child.max_y){
                    MBR2_max_y = current_child.max_y;
                }
            }
            else{
                MBR1.addChild(current_child);
                
                if (MBR1_min_x > current_child.min_x){
                    MBR1_min_x = current_child.min_x;
                }
                if (MBR1_max_x < current_child.max_x){
                    MBR1_max_x = current_child.max_x;
                }
                if (MBR1_min_y > current_child.min_y){
                    MBR1_min_y = current_child.min_y;
                }
                if (MBR1_max_y < current_child.max_y){
                    MBR1_max_y = current_child.max_y;
                }
            }
        }
        
        //Update MBRs
        MBR1.updateMBR(MBR1_min_x, MBR1_min_y, MBR1_max_x, MBR1_max_y);
        MBR2.updateMBR(MBR2_min_x, MBR2_min_y, MBR2_max_x, MBR2_max_y);
        
        //Add them as children to the new node
        new_node.addChild(MBR1);
        new_node.addChild(MBR2);
        
        
        return new_node;
    }
    
    public double calculateDistance(double longitude1, double latitude1, double longitude2, double latitude2){
        double x = (longitude2 - longitude1) * Math.cos((latitude1+latitude2)/2);
        double y = (latitude2 - latitude1);
        return Math.sqrt(x*x + y*y);
    }

    @Override
    public void pickSeeds(RNode node, RNode new_node, double new_min_x, double new_min_y, double new_max_x, double new_max_y) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getNextNode() {
        return 1 + this.highestUsedNodeId++;
    }


    @Override
    public RNode chooseLeaf(RNode node, int level) {
        
        
        //Get root node
        RNode N = getNode(this.root_id);
        
        //Clear stacks
        parents.clear();
        parents_idx.clear();
        
        //Check if 'node' is already a leaf:
        while (true){
            if (N.tree_level == level){
                return N;
            }

            //Choose subtree method: choose subtree where the MBR has to expand the least
            double smallest_expansion = N.calculateExpansion(node);
            double tmp_expansion = -1;
            
            //Index of the subtree node
            int SN_idx = 0;
            String child_name = null;
            
            for (Object child  : N.children.keySet()){
                
                //Note to self: possible errors here!
                child_name = (String)child;
                tmp_expansion = N.calculateExpansion(node.getChild(child_name));
                
                System.out.println(child_name);
                
                if (smallest_expansion > tmp_expansion){
                    smallest_expansion = tmp_expansion;
                    SN_idx++;
                }
                else if ((smallest_expansion == tmp_expansion) && (N.getArea() > node.getArea())){
                    smallest_expansion = tmp_expansion;
                    SN_idx++;
                }
                
                
            }
            
            parents.push(node.ID);
            parents_idx.push(SN_idx);
            
            N = getNode(SN_idx);
            //N = (RNode)N.children.get(child_name);
        }
        
        
    }

    @Override
    public RNode getNode(int ID) {
        return this.Node_IDs.get(ID);
    }
    
    private RNode fixTree(RNode n1, RNode n2){
       
        //System.out.println("TREE HEIGHT: " + this.tree_height);
        //System.out.println("N1 TREE HEIGHT: " + n1.tree_level);
        
        while (n1.tree_level != this.tree_height){
            RNode parent = (RNode)parents.pop();
            int idx = (int)parents_idx.pop();
            
            RNode node = getNode(idx);
            RNode nodes_child = parent.getChild(node.County); //child of 'node'
            if ((nodes_child.max_x != n1.max_x) ||
                (nodes_child.min_x != n1.min_x) ||
                (nodes_child.min_y != n1.min_y) ||
                (nodes_child.max_y != n1.max_y)){
                
                parent.getChild(node.County).updateMBR(n1.min_x, n1.min_y, n1.max_x, n1.max_y); //possible problem here
                
            }
            
            RNode new_node = null;
            if (n2 != null){

                //Make sure the node's number of children does not exceed the max # of children allowed
                if (parent.nChildren < default_max_entries){
                    parent.addChild(n2);
                }

                //If the number of children exceeds max #, then split the node
                else{
                    new_node = split(n2,node,size);
                }

                n1 = parent;
                n2 = new_node;

                //Reset
                parent = null;
                new_node = null;
            }
        }
        
       return n2;
    }
    
    //Which county did you click?
    public void whichCounty(){
        
    }
    
}
