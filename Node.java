/*
 * Node Class  
 */

public class Node {
    
    /**********VARIABLES**********/
    // Value of the key against the tag or frequency
    int key;
    // Degree of node that is the number of children the node has
    int degree;
    // The tag containing $ 
    String tag;
    // Parent of node
    Node parent;
    // Child of node
    Node child;
    // Previous node 
    Node previous;
    // nextptr node
    Node nextptr;
    // childcut value of node
    boolean childcut;
    
    
    /**********METHODS**********/
    /*
    Method: Node(constructor)
    Parameters: int key & String tag
    Description: Node class constructor which initializes the class variables with the passed parameters if any
    Return value: None
    */
    public Node(int key, String tag) {
            this.degree = 0;
            nextptr = this;
            this.key = key;
            previous = this;
            this.tag = tag;
            child = null;
            parent = null;
            
            
        }
        
        
 
    
}
