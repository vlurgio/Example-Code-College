/*
 * This class represents a Node in a LinkedArrays
 * the node is doubly linked and holds an array of
 * elements and a key
 * 
 * @author Vinny Lurgio
 * @version 1.0
 */

public class LinkedArrayNode<T, K extends Comparable<K>> {
  protected LinkedArrayNode<T, K> prev;
  protected LinkedArrayNode<T, K> next;
  protected K key;
  protected Object[] array;          // array holds T objects
  protected static final int DEFAULTLENGTHOFARRAY = 16;
  protected int arraySize;    // number of elements in the array.
 
  
  /*
   * Workhorse constructor. Initializes the next and prev fields and also
   * creates the array of length lengthOfArray as given.
   * 
   * @param prev the previous node
   * @param next the next node
   * @param lengthOfArray the length of the array to be held by this node
   * @throws IllegalArgumentException if lengthOfArray is less than 0
   */
  public LinkedArrayNode(LinkedArrayNode<T, K> prev, LinkedArrayNode<T, K> next, int lengthOfArray)
  {
	  if (lengthOfArray < 0) throw new IllegalArgumentException();
	  this.prev = prev;
	  this.next = next;
	  array = new Object[lengthOfArray];
  }
 
  /*
   * Convenience constructor using DEFAULTLENGTHOFARRAY as the 
   * size for the array to be held by this node.
   * 
   * @param prev the previous node
   * @param next the next node
   */
  public LinkedArrayNode(LinkedArrayNode<T, K> prev, 
     LinkedArrayNode<T, K> next)
  {
	  this.prev = prev;
	  this.next = next;
	  array = new Object[DEFAULTLENGTHOFARRAY];
  }


  /*
   * Adds element x to the end of the array
   * 
   * @param x the element to be added to the array
   * @throws IllegalArgumentException if x is null
   */
  public void add(T x)
  {
	if (x == null) throw new IllegalArgumentException();
	int i = 0;
	while(array[i] != null)
		{
			i++;
		}
	array[i] = x;
	arraySize++;
  }
 
  // Locate and remove the first element that equals x. This may require 
  // elements to be shifted (left). Returns a reference to the removed 
  // element, or null if the element was not removed.
  // Throws IllegalArgumentException if x is null.
  // Target Complexity: O(n)
  /*
   * Locates and removes the first element that is equal to x. Then 
   * shifts the elements after it left. Returns null if x is not in
   * the array.
   * 
   * @param x the item to be removed
   * @throws IllegalArgumentException if x is null
   */
  @SuppressWarnings("unchecked")
public T remove(T x)
  {
	  if (x == null) throw new IllegalArgumentException();
	  int i = 0;
	  while (!array[i].equals(x))
	  {
		  if (i == array.length-1) return null;
		  i++;
	  }
	  T  returnVal = (T)array[i];
	  for (; i<array.length-1; i++)
	  {
		  array[i] = array[i+1];
	  }
	  return returnVal;
  }
 
  /*
   * Returns a reference to the first element in the array that equals x
   * or null if no such element is in the array
   * 
   * @param x the item to be located
   * @throws IllegalArgumentException if x is null
   */
  public T getMatch(T x)
  {
	  if (x == null) throw new IllegalArgumentException();
	  int i = 0;
	  while (!array[i].equals(x))
	  {
		  if (i == array.length-1) return null;
		  i++;
	  }
	  T  returnVal = (T)array[i];
	  return returnVal;
  }
 
  /*
   * Creates a nice representation of the array inside the node.
   * 
   * (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  public String toString()
  {
	  StringBuffer finalString = new StringBuffer();
	  for (int i = 0; i < array.length; i++)
	  {
		  finalString.append(array[i].toString());
		  if (i == array.length - 1) return finalString.toString();
		  finalString.append(", ");
	  }
	  return "";
  }
}

