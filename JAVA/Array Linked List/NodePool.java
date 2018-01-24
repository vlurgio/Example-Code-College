/*
 * 
 * @author Vinny Lurgio
 * @version 1.0
 * 
 * The basic class implementing the Stack to pool nodes
 */
public class NodePool<T> extends ObjectPool<Node<T>> {
 /*
  * Constructor invoking constructor of the parent class
  * 
  * @throws IllegalArgmuentException if maxSize < 1
  */
   NodePool(int maxSize)
   {
	   super(maxSize);
   }
  
   /*
    * Returns a new node when the pool is empty
    * 
    * @return Node<T>
    * @see ObjectPool#create()
    */
   protected Node<T> create()
   {
	   return new Node<T>();
   }
  
   /*
    * Resets values of x to initial values. Will be called when
    * an empty node is released to teh NodePool
    * 
    * @see clear()
    * @see compress
    */
   protected Node<T> reset(Node<T> x)
   {
	    x.init();
	    return x;
    
   }
}
