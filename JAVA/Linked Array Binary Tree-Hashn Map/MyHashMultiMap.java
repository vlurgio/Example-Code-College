/*
 * This class represents a HashMap of LinkedArrays
 * in which the keys are stored in LinkedArrayNodes
 * 
 * @author Vinny Lurgio
 * @version 1.0
 */
public class MyHashMultiMap<T, K extends Comparable<K>> {  
	
	protected Object[] table;
	protected int tableSize;
 
  /*
   * A constructor for the HashMap
   */
  public MyHashMultiMap()
  {
	table = new Object[101];  
	tableSize = 101;
  }
 
 /*
  * Associates the given key with the given value in 
  * the HashTable. If the key already exists adds the 
  * item to this key's array
  * 
  * @param key the key to be associated with
  * @param x the item to add to this key
  */
  public void put(K key, T x)
  {
	  if(key == null || x == null)
	  {
		  throw new IllegalArgumentException();
	  }
	  
	  
	  int index = myHash(key); 
	  
	  if(table[index] == null)
	  {
		  table[index] = new LinkedArrays<T, K>();
		  ((LinkedArrays<T, K>) table[index]).add(key, x);
	  }
	  else
	  {
		  LinkedArrays<T, K> theArray = (LinkedArrays<T, K>) table[index];
		  theArray.add(key, x);
	  }
  }
 
  /*
   * Returns the array associted with a certain key or
   * null if this key is not in the HashTable
   * 
   * @param key the key to search for
   */
  public Object[] get(K key)
  {
	  int index = myHash(key);
	  
	  if (table[index] == null)
	  {
		  return null;
	  }
	  LinkedArrays<T, K> theArray = (LinkedArrays<T, K>) table[index];
	  LinkedArrayNode<T, K> current = theArray.head;
	  
	  while(current.next != null)
	  {
		  if (current.key != null && current.key.compareTo(key) == 0)
		  {
			  return current.array;
		  }
		  current = current.next;
	  }
	  return null;
  }
 
  /*
   * Returns the size of the current table
   */
  public int size()
  {
	  return tableSize;
  }
 
  /*
   * Computes an appropriate HashCode for the specified key
   * 
   * @key the key to get the HashCode for
   */
  protected int myHash(K key) 
  {
    int hashVal = key.hashCode( );
    hashVal %= tableSize;
    if( hashVal < 0 )
      hashVal += tableSize;
    return hashVal;
  }
  
  /*
   * clears out the HashTable back to an array of nulls
   */
  @SuppressWarnings("unchecked")
protected void clear()
  {
	  for(int i = 0; i < tableSize; i++)
	  {
		  if(table[i] != null)
		  {
			  ((LinkedArrays<T, K>) table[i]).clear();
		  }
	  }
  }
  /*
   * Rehashes the current table effectively doubling it's size
   */
  @SuppressWarnings("unchecked")
protected void rehash()
  {
	 MyHashMultiMap<T, K> newTable = new MyHashMultiMap<T, K>(); 
	 newTable.tableSize = tableSize*2+1;
	 newTable.table = new Object[newTable.tableSize];
	 for (int i = 0; i<tableSize; i++)
	 {
		 if(table[i] != null)
		 {
			 @SuppressWarnings("unchecked")
			LinkedArrays<T, K> theArray = (LinkedArrays<T, K>) table[i];
			 LinkedArrayNode<T, K> current = theArray.head;
			 while(current.next != null)
			 {
				 for(int j = 0; j < current.array.length; j++)
				 {
					 if (current.array[j] != null)
					 {
						 newTable.put(current.key, (T)current.array[j]);
					 }
				 }
				 current = current.next;
			 }
		 }
	 }
	 table = newTable.table;
	 tableSize = newTable.tableSize;
  }

}
