/*
 * This class sets up the nodes used to store numbers in a linked list
 * in the Number class
 * 
 * @author Vinny Lurgio
 * @version 1.0
 */
public class Node {
  protected Node prev;
  protected Node next;
  protected int data;
 
 /*
  * Constructor to initialize values
  * 
  * @param data the data to be in the node
  * @param prev the pointer to the last node
  * @param next the pointer to the next node
  */
  public Node(int data, Node prev, Node next)
  {
	  this.data = data;
	  this.prev = prev;
	  this.next = next;
  }
 
/*
 * Returns a string representation of the node
 * 
 * @returns String
 * @see java.lang.Object#toString()
 */
  public String toString()
  {
	  return "[" + Integer.toString(data) + "]";
  }
} 
