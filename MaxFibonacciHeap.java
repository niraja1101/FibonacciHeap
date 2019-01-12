/*
 * MaxFibonacciHeap Class
 */

import java.util.ArrayList;
import java.util.List;

public class MaxFibonacciHeap {
    
    /**********VARIABLES**********/
    // Declare a node to store the maximum key value of MaxFibonacciHeap 
    Node maxnode;
    
    // Declare the size of MaxFibonacciHeap
    int sizeofheap;
    
    
    /**********METHODS**********/
    /*
    Method: MaxFibonacciHeap(constructor)
    Parameters: None
    Description: MaxFibonacciHeap class constructor which initializes the class variables
    Return value: None
    */
    public MaxFibonacciHeap() {
        
        // Initialize the maxnode and size of MaxFibonacciHeap
        maxnode = null;
        sizeofheap = 0;
    }


    /*
    Method: returnmax
    Parameters: Node first and Node second
    Description: Returns the node with the bigger key among first and second
    Return value: Node with larger key
    */
    public Node returnmax (Node first, Node second)
    {
        if (first.key > second.key)
            return first;
        else if (first.key < second.key)
            return second;
        else return first;

    }
    
    /*
    Method: findmax
    Parameters: None
    Description: Returns the maxnode of MaxFibonacciHeap. 
    Return value: The maxnode of MaxFibonacciHeap.
    */
    public Node findmax() {
     
            return maxnode;
     
    }
    
    
    /*
    Method: insert
    Parameters: String tag & int key
    Description: Inserts new node with passed tag and frequency key values and melds it with existing list of roots.
    Return value: The newly created node.
    */
    public Node insert(String tag, int key){
        
        // Create new node with passed tag and frequency key values
        Node newnode = new Node(key, tag);
        
        //Meld the node into the existing list of roots and update the maxnode
        maxnode = meld(maxnode, newnode);
        
        // Increment the size of MaxFibonacciHeap
        sizeofheap++;
        
        // Return the newly created node
        return newnode;
        
    }
    
    
    /*
    Method: removeMax
    Parameters: None
    Description: Remove the maxnod,melds its children with existing list of roots. Follow this operation by pairwisecombine of trees with equal degree. 
    Return value: The removed maxnode.
    */
    public Node removeMax(){
       
        // Save the maxnode to be removed 
        Node oldmaxnode = maxnode;
            
        // Proceed if maxnode is non null
        if (maxnode != null) {
            
            // Detach the maxnode from the existing list of roots by setting pointers from its previous to its nextptr.
            maxnode.previous.nextptr = maxnode.nextptr;
            maxnode.nextptr.previous = maxnode.previous;
            
        
        

            Node maxnext = maxnode.nextptr;
            if(maxnext == maxnode)
                maxnext = null;

            //Set the previous and nextptr of maxnode to refer itself
            maxnode.previous = maxnode.nextptr = maxnode;
            
            // Decrement the size of MaxFibonacciHeap
            sizeofheap--;     
            
            // Update the parent node of all maxnode children to null
            if(maxnode.child != null){
                Node iterator = maxnode.child;
                do
                {
                    iterator.parent = null;
                    iterator = iterator.nextptr;
    
                }while(iterator!=maxnode.child);
            }
        
            // Meld the child of the removed maxnode with its previously saved nextptr node and update maxnode
            maxnode = meld(maxnext, maxnode.child);
            
            // Pairwise combine node trees of equal degree 
            if (maxnode != null) {
                pairwisecombine();
            }
        }    
        
        // Return the saved removed maxnode
        return oldmaxnode;
    }
    
    
    /*
    Method: increaseKey
    Parameters: Node node & int newkey
    Description: Increases the key value of the passed node to value of newkey . If this disturbs the maxheap property, cut the
    node from the parent and follow up with a cascadingcut  operation on heap.
    Return value: None
    Throws: New key is smaller than old key if newkey < node.key.
    */
    public void increaseKey(Node node, int newkey) {
      
        // Throw exception if newkey < node.key
        if (newkey < node.key) {
            throw new IllegalArgumentException("New key is smaller than old key. Not a valid increase key operation");
        }
        
        // Set the key value of the node to the newkey value passed.
        node.key = newkey;
       
       // Cut the node from its parent and siblings if it disturbs max heap property, follow up by cascadingcut

        if (node.parent != null && node.parent.key < node.key)
        
        {
            
            // Obtain the parent of node
            Node parentnode = node.parent;
            
            // Cut the node
            cutnode(node,parentnode);
            
            // Perform cascadingcut on the path from node's parent till root
            cascadingcut(parentnode);
        } 
        
        // Update the maxnode  
        if (node.key > maxnode.key)
            maxnode = node;
        
    }
    
    
    /*
    Method: meld
    Parameters: Node first and Node second   
    Description: Melds the first and second nodes into the MaxFibonacciHeap. 
    Return value: The maximum node of the two passed nodes.
    */
    public Node meld(Node first, Node second){
        
        // If both nodes are non null
        if (first != null && second != null) {
            
            // Meld the nodes
            Node temp = first.nextptr;
            first.nextptr = second.nextptr;
            second.nextptr.previous = first;
            
            second.nextptr = temp;
            
            temp.previous = second;
            
            return returnmax(first, second);
           
        }
        
        // Else if first node is null, return second
        else if (first == null) {
            return second; 
        }    
            
        // Else if second is null, return first
        else if (second == null) {
            return first;
        }
        
        
         return null;
        
    }
    
    
    /*
    Method: cascadingcut
    Parameters: None
    Description: cascadingcut operation peformed on MaxFibonacciHeap beginning from parent of node cut away til the root
    where if childcut value of node is true it is cut away from its parent else its childcut value is changed to true.
    Return value: None
    */
    public void cascadingcut(Node node){
        
        // Save the node's parent
        Node parentnode = node.parent;
        
        // If node's parent is not null
        if (parentnode != null) {
            
            // If the childcut value of node is false, set it to true
            if ( node.childcut == false ) 
                node.childcut = true;
            
            // Else, cut the node 
            else {
                cutnode(node,parentnode);
                cascadingcut(parentnode);
            }    
        }
        
    }
    
    
    /*
    Method: cutnode
    Parameters: Node node & Node parent
    Description: Cut the passed node from its parent and meld it with existing list of roots.
    Return value: None
    */
    public void cutnode(Node node, Node parent){
        
        // Detach the node by setting pointers between its previous node and its nextptr node
        node.nextptr.previous = node.previous;
        node.previous.nextptr = node.nextptr;
        
        
        // Save its nextptr node
        Node nextnode = node.nextptr;
        
        // Make nextptr and previous of the node to point to itself
        node.nextptr = node;
        node.previous = node;
        
        // Decrement the detached node's parent degree
        parent.degree--;
        
        // Set the child of detached node's parent to its sibling or null
        if (parent.child == node){
          
            parent.child = nextnode;
            if(parent.child == node)
                parent.child=null;
        }
        
        // Set the node's parent to null
        node.parent = null;
        
        // Meld the node with the existing list of roots
        maxnode = meld(maxnode,node);
        
        // Set the childcut value of detached node to false 
        node.childcut = false;
        
    }
    
    
    /*
    Method: pairwisecombine
    Parameters: None
    Description: Combines trees of equal degree considering two at a time and updates the maxnode at the end
    by examining the trees from the degreetable.
    Return value: None
    */
    public void pairwisecombine() {
        
        // ArrayList to keep track of already encountered nodes and their degree
        List<Node> degreetable = new ArrayList<Node>();
        
        // ArrayList to store the list of roots of MaxFibonacciHeap for traversal
        List<Node> rootlist = new ArrayList<Node>();
        
        // Add the current list of roots to rootlist for traversal
        rootlist.add(maxnode);
        for( Node current = maxnode.nextptr ; current!= maxnode ; current = current.nextptr){
            rootlist.add(current);
        }
        


        // Traverse rootlist and perform the pairwisecombine of trees of equal degree
        for (Node iter : rootlist) {
            
            // Resize and expand the degreetable to accomodate new entry for iter.degree
            while (degreetable.size() <= iter.degree ) {
                    degreetable.add(null);
            }
            
            // Keep merging until a tree of equal degree exists
            while (degreetable.get(iter.degree) != null) {
                
                // If an equal degree tree exists for encountered node, retrieve its entry from degreetable 
                Node other = degreetable.get(iter.degree);
                
                // Clear the degreetable entry after retrieval
                degreetable.set(iter.degree, null); 
                
                // Determine the maximum and minimum list root nodes 
                Node max,min;
               
               
                if(iter.key > other.key)
                {
                    max = iter;
                    min = other;
                }
                else {
                    max = other;
                    min = iter;
                }    
                
                // Detach the min out of list of roots and meld it into max's children list
                min.previous.nextptr = min.nextptr;
                min.nextptr.previous = min.previous;
               
                               
                // Make min node point to itself 
              min.previous = min;
              min.nextptr  = min;
                
                // Meld the max's child and min and set the maximum of the two as max's child node
                max.child = meld(max.child, min);
                
                // Set max as parent of min
                min.parent = max;
                
                // Reset the childcut value of min to false after it got its new parent 
                min.childcut = false;

                // Increment degree of maxnode
                max.degree++;                
                
                // Continue combining the max tree  
                iter = max;
                
                // Expand the degreetable to accomodate the entry for iter.degree 
                
                while (degreetable.size() <= iter.degree )
                {
                    degreetable.add(null);
                }
            }
            
            // If an equal degree match is not found, make an entry for iter in degreetable at iter.degree slot
            if (degreetable.get(iter.degree) == null) 
                degreetable.set(iter.degree, iter);
        }
        
        // Update maxnode by melding and examining the remaining list of roots in degreetable
        maxnode = null;
        for (Node nodeiter: degreetable) {
            if (nodeiter!=null) {
            // Make the previous and nextptr of node to refer itself before melding with current maxnode
            nodeiter.nextptr = nodeiter;
            nodeiter.previous = nodeiter;
            maxnode = meld(maxnode, nodeiter);
            }
        }
    }
    
    
    /*
    Method: reinsertmaxnodes
    Parameters: List<Node> list
    Description: Reinserts the nodes in the passed list into the existing list of roots of MaxFibonacciHeap. 
    Return value: None
    */
    public void reinsertmaxnodes(List<Node> nodelist) {
       
       // Iterate through the passed list and meld each node into the existing list of roots
       for(Node iterator: nodelist) {
           
           // Set the degree to zero and child, parent to null for encountered node
           iterator.child = null;
           iterator.parent = null;
           iterator.degree = 0;
           
           // Meld the encountered node with existing list of roots and update maxnode
           maxnode = meld(maxnode, iterator);
       } 
       
       // Clear the list after processing
       nodelist.clear();      
    }
    
}
