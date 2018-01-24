/*
 * This class represents a BinarySearchTree
 * 
 * @author Vinny Lurgio
 * @version 1.0
 */
import java.util.List;

public class BinarySearchTree<T extends Comparable<? super T>> {
 protected TreeNode<T> root;        // root of tree
 protected int size;                // size of tree
 
 /*
  * Initializes and constructs a tree from a given sorted List<T> object
  */
 public BinarySearchTree(List<T> L)
 {
	 size = L.size();
	 root = buildTree(0, size-1, L);
 }
    
 /*
  * Builds a balanced tree from a given List and returns the root.
  * This method is recursive
  * 
  * @param start where to start in the List<T>
  * @param end where to end in the List<T>
  * @param L the list to build from
  */
protected TreeNode<T> buildTree(int start, int end, List<T> L)
{
	if (start > end) return null;
	
	int middle = (start + end) / 2;
	TreeNode<T> root = new TreeNode<T>(L.get(middle), buildTree(start, middle -1, L), buildTree(middle+1, end, L), this.root);
	this.root = root;

	return root;
	
}

/*
 * Displays the tree to the command window
 * 
 * @param root the root of the tree to display
 */
public void display(TreeNode<T> root){ 
	this.root.toString();	
	if(root!=null)
		{ 
			display(root.left); 
 			System.out.print(root.toString()); 
 			display(root.right); 
		}
} 

}
