/*
 * @author Vinny Lurgio
 * @version 1.0
 * 
 * A class that uses a linked list idea in an ArrayList. Instead
 * of having pointers in the next and previous fields this classes
 * uses integers that refer to indexes in the linked list
 * 
 */
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;


public class ArrayLinkedList<T> {
 protected final static int NULL = -1;      
    protected ArrayList<Node<T>> array;
    protected NodePool<T> pool;
    protected int head; // position of dummy node in array
    protected int tail; // position of tail node in array
    protected int firstEmpty; // head of the list of empty nodes
    protected int numberEmpty; // number of empty nodes
    protected int size; // number of non-empty nodes
    protected int modCount; // number of modifications made
    protected final static int NODEPOOLSIZE = 8;
 
    /*
     * Constructor class to initialize data members, increment modCount,
     * create the head node, and add it to the array
     * 
     */
    public ArrayLinkedList()
    {
     this.head = 0;
     this.tail = 0;
     this.array = new ArrayList<Node<T>>();
     this.array.add(0, new Node<T>());
     this.modCount = 1;
     this.size = 0;
     this.numberEmpty = 0;
     this.firstEmpty = NULL;
     this.pool = new NodePool<T>(NODEPOOLSIZE);
     
    }
 
    /*
     * Returns the size of the array
     * 
     * @return size as an integer
     */
    public int size()
    {
     return size;
    }
 
    /*
     * Returns the first empty node in the array
     * 
     * @return firstEmpty
     */
    protected int getFirstEmpty()
    {
     return firstEmpty;
    }
    
    /*
     * Returns the position of the head node in the arrya
     * 
     * @return head
     */
    protected int getHead()
    {
     return head;
    }
    
    /*
     * Returns the position of the tail node in the array
     * 
     * @return tail
     */
    protected int getTail()
    {
     return tail;
    }
    /*
     * Convenience method to get the array representing
     * the list
     * 
     * @return array 
     */
    protected ArrayList<Node<T>> getArray()
    {
     return array;
    }
    
    /*
     * Adds the element to end of list and sets up a new tail node
     * 
     * @param e the element to be added to the list
     * @return true always returns true
     * @throws IllegalArgumentException if e is null
     */
    public boolean add(T e) 
    {
     if (e == null) throw new IllegalArgumentException();
   
        assert size >=0 && head==0 && numberEmpty >=0 && (numberEmpty==0  
         && firstEmpty==NULL) || (numberEmpty>0 && firstEmpty!=NULL)
          && (array.size() == size+numberEmpty+1);         
        
        Node<T> toAdd;
        Node<T> oldTail;
        
        if (numberEmpty == 0)
        {
	         toAdd = pool.allocate(); //add new node to end of array
	         array.add(toAdd); //add this new node
	         toAdd.previous = tail; //set the previous field to the former tail
	         toAdd.data = e; //set the data to null since this will be the new tail
	         toAdd.next = NULL; //set the next field to null 
         if (tail != head)
         {
	           oldTail = array.get(tail); //retrieve the node that was previously the tail
	           oldTail.next = array.size()-1; // set next field to the new tail
         }
         else
         {
        	 array.get(head).next = 1;
         }
         tail++; //increment tail to get the new tail position
         modCount++;
         size++;
         
        }
        else
        {
	         // set the new values to old tail node
	         oldTail = array.get(tail);
	         oldTail.next = firstEmpty;
	         // set the found empty node to the new tail node
	         toAdd = array.get(firstEmpty);
	         toAdd.data = e;
	         toAdd.previous = tail;
	         tail = firstEmpty;
	         firstEmpty = toAdd.next;
	         toAdd.next = NULL;
	         modCount++;
	         numberEmpty--;
	         size++;
	         
	         if (numberEmpty == 0)
	         {
	        	 firstEmpty = NULL;
	         }
	         
        }
        // check this assertion before each return statement
        assert size>0 && head==0 && numberEmpty >=0 
          && (numberEmpty==0 && firstEmpty==NULL) || (numberEmpty>0 
            && firstEmpty!=NULL)
            && (array.size() == size+numberEmpty+1);
        return true;
    }
    
    /*
     * Inserts element at the index of the ArrayList given also
     * moves each following element in the list accordingly
     * 
     * @param index the index to insert the element at
     * @param e the element to be inserted
     * @throws IndexOutOfBoundsException if index < 0 or index > size
     * @throws IllegalArgumentException if e is null
     * @see add(T e)
     */
    public void add(int index, T e) 
    {
	     //checks that both the index and the element to add are valid
	     if (index < 0 || index > size()) throw new IndexOutOfBoundsException();
	     if (e == null) throw new IllegalArgumentException();
	     
	     assert size>=0 && head==0 && numberEmpty >=0 
	       && (numberEmpty==0 && firstEmpty==NULL) || (numberEmpty>0 
	         && firstEmpty!=NULL) && (array.size() == size+numberEmpty+1);
	       
	       //when the index is the size of the array this is the same as the add(T e) function
	       if(index == size)
	       {
		         add(e);
		         assert size>0 && head==0 && numberEmpty >=0 
		    	         && (numberEmpty==0 && firstEmpty==NULL) || (numberEmpty>0 
		    	             && firstEmpty!=NULL) && (array.size() == 
		    	                 size+numberEmpty+1);
		         return;
	        }
	       
	       Node<T> oldNode = array.get(array.get(head).next);
	       Node<T> newNode;
	       int count = 0;
	       
	       //check to see whether there's an empty node
	       //or you have to get one from the pool
	       if(numberEmpty == 0) newNode = pool.allocate();
	       else newNode = array.get(firstEmpty);
	       
	       
	       newNode.data = e;
	       array.add(newNode);
	       size++;
	   
	       
	       // if the index is 0 this will always be the node after head
	       if (index == 0)
	       {
	    	   array.get(head).next = size;
	       }
	       // if the index is not zero traverse the list until you're at the required position
	       else
	       {
		        while (count != index)
		        {
			         oldNode = array.get(oldNode.next);
			         count++;
		        }
	       }
    
    
	    // field setup of newNode
	    
	    array.get(size).previous = oldNode.previous;
	    
	    array.get(size).next = positionOf(oldNode.data);
	    
	    array.get(array.get(size).previous).next = size;
	    
	    oldNode.previous = size;
	    
	    
	    tail++;
	       
	        
	       
	 
	       // check this assertion before each return statement
	       assert size>0 && head==0 && numberEmpty >=0 
	         && (numberEmpty==0 && firstEmpty==NULL) || (numberEmpty>0 
	             && firstEmpty!=NULL) && (array.size() == 
	                 size+numberEmpty+1);
    }
 
    /*
     * Adds an element at beginning of list (right after head node)
     * 
     * @param e the element to be added
     * @throws IllegalArgumentException
     */
    public void addFirst(T e)
    {
    	add(0, e);
    }
 
    /*
     * Adds an element to the end of the list
     * 
     * @param e the element to be added
     * @throws IllegalArgumentException if e is null
     * @see add(T e)
     */
    public void addLast(T e)
    {
    	add(e);
    }
 

    /*
     * Adds all elements in a given collection to the end of the list
     * 
     * @param c a Collection of elements to be added
     * @return boolean true if elements are added false if not
     * @throws NullPointerException if the Collection is null
     */
    @SuppressWarnings("unchecked")
 public boolean addAll(Collection<? extends T> c)
    {
	     
	     if(c == null) throw new NullPointerException();
	     
	     //check to see that there is at least one element in Collection c
	     if(c.size() == 0) return false;
	     
	     // create an array containing the elements in the Collection c
	     T[] elements = (T[])c.toArray();
	  
	     //iterate through array adding all elements to end of list.
	     for (int i = 0; i < elements.length; i++)
	     {
		      add(elements[i]);
		      size++;
	     }
	     return true;
    }
 
    /*
     * Returns true if the given element is in the list
     * 
     * @param e the element to search for
     * @returns boolean true if element is in list false if not
     * @throws IllegalArgumentException if e is null
     */
    public boolean contains(T e)
    {
	     if (e == null) throw new IllegalArgumentException();
	     
	     ArrayLinkedListIterator it = new ArrayLinkedListIterator();
	     
	     while (it.hasNext())
	     {
	    	 if (it.next().equals(e)) return true;
	     }
	     return false;
    }
 
 
    /*
     * Returns index of the given element in the list. Will 
     * always returns the first occurrence of the element.
     * 
     * @param e the element to be searched for
     * @returns index the index of the element in the list or NULL if the element is not in the list
     * @throws IllegalArgumentException if e is null
     * 
     */
    public int indexOf(T e)
    {
    	if (e == null) throw new IllegalArgumentException();
    	
	    ArrayLinkedListIterator it = new ArrayLinkedListIterator();
	    
	    while(it.hasNext())
	    {
	    	if (it.next().equals(e)) return it.current();
	    }
	    return NULL;
      
     
    }
    
    
    /*
     * Returns position of the element in the array if it exists
     * otherwise returns NULL(-1)
     * 
     * @param e the element you are looking for
     * @returns int position if found NULL otherwise
     * @throws IllegalArgumentException if e is null
     */
   protected int positionOf(T e)
   {
		if ( e == null) throw new IllegalArgumentException();
		
	    Node<T> node = array.get(1);
	    
	    for(int i = 1; i < array.size(); i++)
	    {
		     if (node.data != null && node.data.equals(e)) return i;
		     if (i+1 <= array.size()-1) node = array.get(i+1);
	    }
	    return NULL;
   }
 
   /*
    * Returns the element at the given index in the list
    * 
    * @returns data the data of the node at index in list
    * @throws IndexOutOfBoundsException if index is out of range of the list
    */
    public T get(int index)
    {
	     if (index < 0 || index + 1 > size) throw new IndexOutOfBoundsException();
	     Node<T> node = array.get(array.get(head).next);
	     
	     for(int i = 0; i < index; i++)
	     {
	    	 if (node.next != NULL)
	         if (i != index)
	    	 node = array.get(node.next);
	     }
	     return node.data;
    }
 
    
    /*
     * Returns first element in the list
     * 
     * @return T the first element in the list
     * @throws NoSuchElementException if the list is empty
     */
    public T getFirst()
    {
	     if (size == 0) throw new NoSuchElementException();
	     return array.get(array.get(head).next).data;
    }
 
    /*
     * Returns the last item in the list
     * 
     * @return T the last item in the list
     * @throws NoSuchElementException if the list is empty
     */
    public T getLast()
    {
	     if (size == 0) throw new NoSuchElementException();
	     return array.get(tail).data;
    }
    
    /*
     * Removes the node at given position in ArrayList
     * called by remove(T e) and remove(int index)
     * 
     * @param current the position of the node to be removed
     */
    protected void removeNode(int current) 
    {
       assert current > 0 && current <= size;
       
       //get the node to be removed
       Node<T> rNode = array.get(current);
       
       if (rNode.next != NULL)
       {
    	   Node<T> nNode = array.get(array.get(current).next);
    	   nNode.previous = rNode.previous;
    	   Node<T> pNode = array.get(array.get(current).previous);
           pNode.next = rNode.next;
       }
       
       else 
       {
    	   array.get(array.get(current).previous).next = rNode.next;
       }
       
        //set up the removed node for the empty list
       array.get(current).data = null;
       array.get(current).previous = NULL;
       array.get(current).next = firstEmpty;
       
       firstEmpty = current;
       
       
       
       
       size--;
       tail--;
       if (size == 0) array.get(head).next = NULL;
       numberEmpty++;
       if (numberEmpty == 0) firstEmpty = NULL;
       
    }
 
    /*
     * Remove the first occurrence of element from the list
     * if present 
     * 
     * @param e the element to be removed
     * @returns true if element was in the list
     * @throws IllegalArgumentException if e is null
     */
    public boolean remove(T e) 
    {
     
	     if (e == null) throw new IllegalArgumentException();
	 
	       assert size>=0 && head==0 && numberEmpty >=0
	        && (numberEmpty==0 && firstEmpty==NULL) || (numberEmpty>0 
	          && firstEmpty!=NULL) && (array.size() == size+numberEmpty+1);
	       
	       ArrayLinkedListIterator it = new ArrayLinkedListIterator();
	  
	       while (it.hasNext())
	       {
	    	   if (it.next().equals(e))
	    	   {
	    		   removeNode(positionOf(e));
	    		   return true;
	    	   }
	       }
 
       // check this assertion before each return statement
       assert size>=0 && head==0 && numberEmpty >=0 
         && (numberEmpty==0 && firstEmpty==NULL) || (numberEmpty>0 
          && firstEmpty!=NULL) && (array.size() == size+numberEmpty+1);
       return true;
    }
 
    
    /*
     * Removes element at given index in list shifts any
     * following elements to the left
     * 
     * @param index the index in the list to remove
     * @returns T the element that was removed
     * @throws IndexOutOfBoundsException if index is greater than size or less than 0
     */
    public T remove(int index) 
    {
	     if (index < 0 || index > size-1) throw new IndexOutOfBoundsException();
	     assert size>=0 && head==0 && numberEmpty >=0 
	        && (numberEmpty==0 && firstEmpty==NULL) || (numberEmpty>0 
	          && firstEmpty!=NULL) && (array.size() == size+numberEmpty+1);
	      
	      int position;
	      ArrayLinkedListIterator it = new ArrayLinkedListIterator();
	      
	      while(it.hasNext())
	      {
	    	   T data = it.next();
		       if (it.current() == index)
		        {
		    	   	 
			         position = positionOf(data);
			         removeNode(position);
			         assert size>=0 && head==0 && numberEmpty >=0 && (numberEmpty==0 
			        	        && firstEmpty==NULL) || (numberEmpty>0 && firstEmpty!=NULL) 
			        	        && (array.size() == size+numberEmpty+1);
			         return data;
		        }  
	      }
 
        // check this assertion before each return statement
       assert size>=0 && head==0 && numberEmpty >=0 && (numberEmpty==0 
        && firstEmpty==NULL) || (numberEmpty>0 && firstEmpty!=NULL) 
        && (array.size() == size+numberEmpty+1);
       return null;
       
    }
 
   
    /*
     * Returns and removes the first element in the list
     * 
     * @return T the element removed
     * @throws NoSuchElementException if the list is empty
     */
    public T removeFirst()
    {
	     if (size == 0) throw new NoSuchElementException();
	     return remove(0);
    }
    
    /*
     * Returns and removes the last element in the list
     * 
     * @returns T the element removed
     * @throws NoSuchElementException if the list is empty 
     */
    public T removeLast()
    {
	     if(size == 0) throw new NoSuchElementException();
	     return remove(size-1);
    }
 
 
    /*
     * Returns true if the Node at the specified position(in array) is empty
     * 
     * @returns boolean true if empty false otherwise
     */
    protected boolean positionIsEmpty(int position) 
    {
	      assert position >= 0 && position < array.size();
	      if (array.get(position).data == null) return true;
	      else return false;
    }
 
    
    /*
     * Returns the number of empty Nodes in the array
     * 
     * @returns numberEmpty
     */
    protected int numEmpty()
    {
     return numberEmpty;
    }
 
  
    /*
     * Replaces element at specified index of list with the given element
     * 
     * @return T the element that was previously at the index given
     * @throws IllegalArgumentException if e is null
     * @throws IndexOutOfBoundsException if index is out of range
     */
    public T set(int index, T e)
    {
	     if (e == null) throw new IllegalArgumentException();
	     
	     if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
	     
	     Node<T> oNode = array.get(array.get(head).next);
	     
	     for(int i =0; i<size; i++)
	     {
		      if (i == index)
		      {
			       oNode.data = e;
			       return oNode.data;
		      }
		      oNode = array.get(oNode.next);
	     }
	     return null;
    }
 
    
    /*
     * Removes all elements of list except the head node
     * also releases all nodes to the NodePool
     * 
     */
    public void clear() 
    {
       assert size>=0 && head==0 && numberEmpty >=0 
        && (numberEmpty==0 && firstEmpty==NULL) || (numberEmpty>0 
        && firstEmpty!=NULL) && (array.size() == size+numberEmpty+1);
 
       for(int i = 1; i < array.size(); i++)
       {
    	   pool.release(array.get(i));
       }
       size = 0;
       tail = 0;
       firstEmpty = NULL;
       numberEmpty = 0;
       array.get(head).next = NULL;
 
       // check this assertion before each return statement
       assert size==0 && head==0 && numberEmpty==0 && firstEmpty==NULL
       && (array.size() == size+numberEmpty+1);
       return;
    }
 
    @SuppressWarnings("unchecked")
    /*
     * Returns a new ArrayLinkedListIterator starting at 
     * the beginning of the list
     * 
     * @returns ArrayLinkedListIterator
     */
    Iterator<T> iterator()
    {
    	return new ArrayLinkedListIterator();
    }
 
  
    /*
     * 
     * Debugging method to show a string representation of the values
     * in the list
     * 
     */
    protected void dump() 
    {
      System.out.println();
      System.out.println("**** dump start ****");
      System.out.println("size:" + size);
      System.out.println("number empty:" + numberEmpty);
      System.out.println("first empty:"+firstEmpty);
      System.out.println("head:" + head);
      System.out.println("tail:" + tail); 
      System.out.println("list:");
      System.out.println("array:");
      for (int i=0; i<array.size(); i++) // show all elements of array
         System.out.println(i + ": " + array.get(i));
      System.out.println("**** dump end ****");
      System.out.println();
    }
 

    /*
     * Returns a String representation of the values in the list
     * 
     * @returns String a String representation of the values in the list
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
	     if (size == 0) return "";
	     Node<T> cNode = array.get(array.get(head).next);
	     StringBuffer endString = new StringBuffer();
	     for(int i =0; i<size; i++)
	     {
		      endString.append(cNode.data.toString()+" ");
		      if (cNode.next != NULL)
		      cNode = array.get(cNode.next);
	     }
	     return endString.toString();
    }
 
    
    @SuppressWarnings("unchecked")
    /*
     * Compresses the array by releasing empty nodes in the array to the pool
     * also sorts the array so that the position of an element in the array
     * is the same as it's index in the linked list
     *
     */
    public void compress()
    {
    	ArrayLinkedListIterator it = new ArrayLinkedListIterator();
    	ArrayLinkedList<T> nArray = new ArrayLinkedList<T>();
    	while (it.hasNext())
			{
				nArray.add(it.next());
			}
    	if (firstEmpty != NULL)
    	{
    		Node<T> eNode = array.get(firstEmpty);
        	for (int i = 0; i< numberEmpty; i++)
        	{
        		pool.release(eNode);
        		if (eNode.next != NULL)
        		eNode = array.get(eNode.next);
        	}
    	}
		array = nArray.array;
		tail = nArray.tail;
		head = nArray.head;
		size = nArray.size;
    	
    	 numberEmpty = 0;
    	 firstEmpty = NULL;
	     if (size == 0)
	     {
	    	 array.get(head).next = NULL;
	     }
    }
 
    /*
     * @author Vinny Lurgio
     * @version 1.0
     * 
     * An iterator that moves through the ArrayLinkedList moves through
     * the linked list not the array
     * 
     */
    @SuppressWarnings("unused")
    private class ArrayLinkedListIterator implements Iterator<T> {
    	Node<T> node;
    	int current;
      /*
       * Constructor for the iterator
       */
      public ArrayLinkedListIterator ()
      {
    	  current = 0;
    	  node = array.get(head);
      }
      
      public ArrayLinkedListIterator(int position)
      {
    	  node = array.get(position);
    	  current = 0;
      }
 
      
      /*
       * Checks to see if the list has another element
       * after the current one
       * 
       * @returns boolean true if the list contains another element false otherwise
       * @see java.util.Iterator#hasNext()
       */
      public boolean hasNext()
      {
       if (node.next != NULL) return true;
       else return false;
      }
 
      
      /*
       * Moves iterator forward one index in the list and 
       * returns the passed over element
       * 
       * @returns data the data in the passed over node
       * @see java.util.Iterator#next()
       */
      public T next()
      {
    	  current++;
    	  node = array.get(node.next);
       return node.data;
      }
 
     
      /*
       * (non-Javadoc)
       * Unsupported Function
       * 
       * @throws UnsupportedOperationException
       * @see java.util.Iterator#remove()
       */
      public void remove()
      {
    	  throw new UnsupportedOperationException();
      }
      
      /*
       * Keeps track of the index the iterator has
       * reached in the list
       * 
       * @returns current the current index
       */
      public int current()
      {
    	  return current-1;
      }
   }
    
}
