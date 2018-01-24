/*
 * This class represents a Node in a BinarySearchTree
 * 
 * @author Vinny Lurgio
 * @version 1.0
 */
public class TreeNode<T> {
  protected TreeNode<T> left;
  protected TreeNode<T> right;
  protected TreeNode<T> parent;
  protected T data;
 
  /*
   * Creates and links the node to it's left, right, and parent nodes
   * also adds it's data 
   * 
   * @param data the info to be held by the node
   * @param left the node to the left
   * @param right the node to the right
   * @param parent the node above 
   */
  public TreeNode(T data,TreeNode<T> left,TreeNode<T> right,TreeNode<T> parent)
  {
	  this.data = data;
	  this.left = left;
	  this.right = right;
	  this.parent = parent;
  }
 
  /*
   * Returns a String representation of this node
   * (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  public String toString()
  {
	  return "[" + data.toString() + "]";
  }
} 
