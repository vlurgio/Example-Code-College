/*
 * @author Vinny Lurgio
 * @version 1.0
 * 
 *  A class that represents a node as it would exist
 *  in a linked list. The pointers for next and 
 *  previous, however, are integers in these nodes
 *  as they will refer to indices of an ArrayList
 *  
 */

public class Node<T> {
		  protected static final int NULL = -1;
		  protected int previous;
		  protected int next;
		  protected T data;
		 
		  /*
		   * Constructor for the Node class
		   * 
		   * @see init()
		   */
		  protected Node()
		  {
			  init();
		  }
		 
		  /*
		   * Create a string representation of the node
		   * 
		   * @return the node as a string
		   * @see java.lang.Object#toString()
		   */
		  public String toString()
		  {
			  if (data != null)
			return "[" + Integer.toString(previous) + ","  + 
			Integer.toString(next) + "," + data.toString() + "]";
			  
			  else 
				  return "[" + Integer.toString(previous) + ","  + 
					Integer.toString(next) + "," + "null" + "]";
		  }
		 
		  /*
		   * Initializes the data members and is called by the constructor
		   * 
		   * @see reset()
		   * 
		   */
		  protected void init()
		  {
			  this.data = null;
			  this.next = NULL;
			  this.previous = NULL;
		  }
		} 