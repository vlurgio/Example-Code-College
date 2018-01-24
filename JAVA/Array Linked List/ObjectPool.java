/*
 * @author Vinny Lurgio
 * @version 1.0
 * 
 * An abstract class extended by NodePool
 */

import java.util.*;
abstract class ObjectPool<T> {
   protected Stack<T> pool;// Stack of pooled objects

   protected int maxSize; // max number of pooled objects (i.e., stack size)

   protected static final int DEFAULTMAXSIZE = 8;

  

   /*
    * Constructor for the class sets up maxSize and creates the Stack
    * 
    * @param maxSize the maximum size of the Stack
    * @throws IllegalArgumentException if maxSize < 1
    * 
    */

   ObjectPool(int maxSize)
   {
	    if (maxSize >= 1) this.maxSize = maxSize;
	    else throw new IllegalArgumentException();
	    pool = new Stack<T>();
   }
   
      
  

 /*
  * 
  * Convenience constructor giving a default value of 8 to maximum stack size
  * 
  */

   ObjectPool()
   {
	    maxSize = DEFAULTMAXSIZE;
	    pool = new Stack<T>();
   }

  

   /*
    * Reinitializes data in node then adds node to the pool as long
    * as the pool is not full
    * 
    * @param x the node to be added to the pool
    */

   public void release(T x)
   {
	    if (pool.size() < maxSize)
	    {
		     reset(x);
		     pool.push(x);
	    }
   }

  

  /*
   * Returns a String representation of the Stack
   * 
   * @return finalString.toString() a String representation of the Stack
   * @see java.lang.Object#toString()
   */

   public String toString()
   {
	    StringBuilder finalString = new StringBuilder();
	    Object[] stringRep = new Object[pool.size()];
	    pool.copyInto(stringRep);
	    for (int i = pool.size()-1; i >= 0; i--)
	    {
		     
		     finalString.append((Object)stringRep[i].toString());
		     if (i != 0) finalString.append(", ");
	    }
	    return finalString.toString();
   }

  

   /*
    * Returns the current size of the Stack
    * 
    * @return size an integer value representing number of items in Stack
    */

   public int size()
   {
	   return pool.size();
   }

  

  /*
   * Returns newly constructed object of type T
   * 
   * @return newObject a newly created object of type T
   * @see NodePool
   */

   protected abstract T create();

  

   /*
    * Reinitializes the values of x
    * 
    * @return x after it has been reinitialized by init()
    * @see release
    * @see init()
    */

   protected abstract T reset(T x);

  

   /*
    * Returns a new node using create if the pool is empty or 
    * returns the top node in the stack if one exists
    * 
    * @return allocated the node to be created
    * @see create()
    */

   protected T allocate()
   {
	    T allocated;
	    if (pool.size() == 0)
	    {
	    	allocated = create();
	    }
	    else 
	    {
	    	allocated = pool.pop();
	    }
	    return allocated;
   }

}
