/*
 * This Class represents a doubly linked list of 
 * LinkedArrayNodes
 * 
 * @author Vinny Lurgio
 * @version 1.0
 */
public class LinkedArrays<T, K extends Comparable<K>> {
  protected int size;                 // number of elements
  protected int nodeCount;            // number of LinkedArrayNodes
  protected final int lengthOfArrays; // value initialized in constructor
  protected static final int DEFAULTLENGTHOFARRAYS = 16;
  protected LinkedArrayNode<T, K> head;        // dummy nodes head and tail
  protected LinkedArrayNode<T, K> tail;
 
  /*
   * Constructor
   * 
   * @param lengthOfArrays the length of the arrays in each node
   */
  LinkedArrays (int lengthOfArrays)
  {
	  head = new LinkedArrayNode<T, K>(null,  new LinkedArrayNode<T, K>(head, null));
	  tail = head.next;
	  this.lengthOfArrays = lengthOfArrays;
	  nodeCount = 0;
	  size = 0;
  }
 
  /*
   * Default constructor uses DEFAULTLENGTHOFARRAYS as
   * lengthOfArrays
   */
  LinkedArrays()
  {
	  head = new LinkedArrayNode<T, K>(null,  new LinkedArrayNode<T, K>(head, null));
	  tail = head.next;
	  this.lengthOfArrays = DEFAULTLENGTHOFARRAYS;
	  nodeCount = 0;
	  size = 0;
  }
 
  /*
   * Clears out the current LinkedArrays leaving
   * only the head and tail nodes
   */
  public void clear()
  {
	  LinkedArrayNode<T, K> current = head.next;
	  while (!current.next.equals(tail) && current != null)
	  {
		  current = null;
	  }
  }
 
  /*
   * Returns the size of the LinkedArrays
   */
  public int size()
  {
	  return size;
  }
 
  /*
   * Returns the number of LinkedArrayNodes in this
   * LinkedArrays
   */
  public int nodeCount()
  {
	  return nodeCount();
  }
 
  /*
   * Returns true if this object has only 
   * the head and tail nodes
   */
  public boolean isEmpty( )
  {
	  if (size == 0)
	  {
		  return true;
	  }
	  return false;
  }

  /*
   * Adds the item to the node's array associated with
   * it's key
   * 
   * @param key the key to add to
   * @param x the item to be added
   */
  public boolean add(K key, T x)
  {
	  if(head.next == tail)
	  {
		  LinkedArrayNode<T, K> newNode = new LinkedArrayNode<T, K>(head, tail);
		  head.next = newNode;
		  tail.prev = newNode;
		  newNode.key = key;
		  newNode.array[0] = x;
		  return true;
	  }
	  LinkedArrayNode<T, K> current = head.next;
	  while (current.next != null)
	  {
		  if (current.key != null && current.key.compareTo(key) == 0)
		  {
			  current.add(x);
		  }
		  else 
		  {
			  LinkedArrayNode<T, K> newNode = new LinkedArrayNode<T, K>(tail.prev, tail);
			  tail.prev.next = newNode;
			  tail.prev = newNode;
			  newNode.key = key;
			  newNode.array[0] = x;
			  return true;
		  }
		  current = current.next;
	  }
	  return false;
  }
    
 /*
  * Removes the first occurrance of x in the 
  * LinkedArray
  * 
  * @param x the item to be removed
  * @throws IllegalArgumentException if x is null
  */
  protected T remove(T x)
  {
	  if (x == null) throw new IllegalArgumentException();
	  LinkedArrayNode<T, K> current = head;
	  while(current.next != null)
	  {
		  for (int i = 0; i < current.arraySize; i++)
		  {
			  if(current.array[i].equals(x))  
			  {
				  T removed = (T)current.array[i];
				  current.array[i] = null;
				  if(i == 0) current = null;
				  return removed;
			  }
		  }
		  current = current.next;
	  }
	  return null;
  }

 /*
  * Returns a String representation of the linked list
  * (non-Javadoc)
  * @see java.lang.Object#toString()
  */
  public String toString()
  {
	  StringBuffer finalString = new StringBuffer();
	  LinkedArrayNode<T, K> current = head;
	  while (current.next != null)
	  {
		  finalString.append(" | ");
		  finalString.append(current.array.toString());
		  current = current.next;
	  }
	  finalString.append(" |");
	  return finalString.toString();
  }
}
