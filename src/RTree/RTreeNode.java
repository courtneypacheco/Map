package RTree;

import java.awt.Rectangle;
import java.util.ArrayList;
//import java.util.HashMap;

public class RTreeNode {
	
	//#region Private Variables -----------------------------------
	private String _name;
	private double _x1;
	private double _x2;
	private double _y1;
	private double _y2;
	private double _width;
	private double _height;
	
	//private HashMap Children = new HashMap<Object, Object>();
	private ArrayList<RTreeNode> _Children;
	//#endregion Private Variables --------------------------------
	
	//#region Properties ------------------------------------------
	// List of children nodes
	public ArrayList<RTreeNode> getChildren(){
		return this._Children;
	}
	
	// Name
	public String getName(){
		return this._name;
	}
	public void setName(String name){
		this._name = name;
	}
	
	// X1
	public double getX1(){
		return this._x1;
	}
	public void setX1(double x){
		this._x1 = x;
	}
	
	// X2
	public double getX2(){
		return this._x2;
	}
	public void setX2(double x){
		this._x2 = x;
	}
	
	// Y1
	public double getY1(){
		return this._y1;
	}
	public void setY1(double y){
		this._y1 = y;
	}
	
	// Y2
	public double getY2(){
		return this._y2;
	}
	public void setY2(double y){
		this._y2 = y;
	}
	
	// Width
	public double getWidth(){
		return this._width;
	}
	public void setWidth(double width){
		this._width = width;
	}
	
	// Height
	public double getHeight(){
		return this._height;
	}
	public void setHeight(double height){
		this._height = height;
	}
	
	//#endregion Properties ---------------------------------------
	
	//#region Methods ---------------------------------------------
	/**
	 * Constructor for the RTreeNode class with no arguments
	 */
	public RTreeNode(){
		this._name = "None";
		this._x1 = 0;
		this._x2 = 0;
		this._y1 = 0;
		this._y1 = 0;
		this._width = 0;
		this._height = 0;
		this._Children = new ArrayList<RTreeNode>();
	}
	
	/**
	 * Constructor for the RTreeNode class.
	 * @param x1	The left boundary
	 * @param x2	The right boundary
	 * @param y1	The upper boundary
	 * @param y2	The lower boundary
	 */
	public RTreeNode(String name, double x1, double x2, double y1, double y2){
		
		// Assign name
		this._name = name;
		
		// Check if x1 > x2, flip if they are
		if (x1 > x2){
			this._x1 = x2;
			this._x2 = x1;
		}
		else {
			this._x1 = x1;
			this._x2 = x2;
		}
		// Compute width
		this._width = this._x2 - this._x1;
		
		// Check if y1 > y2, flip if they are
		if (y1 > y2){
			this._y1 = y2;
			this._y2 = y1;
		}
		else {
			this._y1 = y1;
			this._y2 = y2;
		}
		// Compute height
		this._height = this._y2 - this._y1;
		
		// Initialize list of children nodes
		this._Children = new ArrayList<RTreeNode>();
	}
	
	/***
	 * Add child node to this node
	 * @param node	Child node that is to be added
	 */
	public void addChild(RTreeNode node){
		this._Children.add(node);
	}
	
	/**
	 * Returns this node's child that matches a name
	 * @param name	The name of child to be returned
	 * @return		The RTreeNode of the child with matching name
	 */
	public RTreeNode getChildByName(String name){
		for (int ii = 0; ii < this._Children.size(); ii++){
			if (this._Children.get(ii).getName().equals(name))
				return this._Children.get(ii);
		}
		return null;
	}
	
	/***
	 * Remove child node from this node
	 * @param node	Child node that is to be removed
	 */
	public void removeChild(RTreeNode node){
		if (this._Children != null)
			if (this._Children.size() > 0)
				this._Children.remove(node);
	}
	
	/***
	 * Remove child node from this node
	 * @param node	Child node (integer index) that is to be removed
	 */
	public void removeChild(int node){
		if (this._Children != null)
			if (this._Children.size() > 0)
				this._Children.remove(node);
	}
	
	/**
	 * Remove child node from this node if name matches
	 * @param name	Name of child node to be removed
	 */
	public void removeChildByName(String name){
		if (this._Children != null){
			if (this._Children.size() > 0){
				
				for (int ii = 0; ii < this._Children.size(); ii++){
					if (this._Children.get(ii).getName().equals(name))
					this._Children.remove(ii);
				}
				
			}
		}
	}
	
	/***
	 * Clears this node's list of children nodes
	 */
	public void clearChildren(){
		this._Children.clear();
	}
	
	/***
	 * Tests if a rectangle intersects with this one
	 * @param node	Another rectangle to check with this one
	 * @return		<code>true</code> if another rectangle intersects with this one
	 */
	public boolean intersects(RTreeNode node){
		Rectangle thisNode = new Rectangle();
		thisNode.setRect(_x1, _y1, _width, _height);
		
		Rectangle inputNode = new Rectangle();
		inputNode.setRect(node.getX1(), node.getY1(), node.getWidth(), node.getHeight());
		
		return thisNode.intersects(inputNode);
	}
	
	/***
	 * Returns if node has any children
	 * @return	<code>true</code> if node has at least one child
	 */
	public boolean hasChildren(){
		if (this._Children == null)
			return false;
		return this._Children.size() > 0;
	}
	
	/***
	 * Returns number of children this node has
	 * @return	Number of children this node has
	 */
	public int getNumberOfChildren(){
		if (this._Children == null)
			return 0;
		return this._Children.size();
	}
	
	/***
	 * Expands this node's rectangular region
	 * @param node	
	 */
	public void expandNode(RTreeNode node){
		
	}
	
	/***
	 * Expands this node's rectangular region
	 * @param node	
	 */
	public void expandNode(Rectangle node){
		
	}
	//#endregion Methods ------------------------------------------
}
