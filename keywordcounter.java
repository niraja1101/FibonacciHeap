/*
 * keywordcounter Class(contains main)  
 */

import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class keywordcounter {
    
    /**********VARIABLES**********/

    //Declare a Max Fibonacci Heap object for handling the nodes
    private MaxFibonacciHeap maxfibheap;

    // Declare the KeywordMap object to store (tag, tag-node) pairs in HashMap
    private KeywordMap keymap;
    
  
    
    
    
    /**********METHODS**********/
    /*
    Method: keywordcounter(constructor)
    Parameters: None
    Description: keywordcounter class constructor which initializes the class variables keymap and maxfibheap
    Return value: None
    */
    public keywordcounter() {
        
        // Initialize the object of KeywordMap class
        keymap = new KeywordMap();
        
        // Initialize the object of MaxfibonacciHeap class
        maxfibheap = new MaxFibonacciHeap();
    }
    
   
    /* 
    Method: main
    Description: Creates object of keywordcounter class , objects of BufferedReader, BufferedWriter  are used 
    for text file processing.
    It invokes its readf method to process the inputFile passed in the command line. 
    The output file is written to using the writef method
    Return value: None
    */
    public static void main(String[] args) throws IOException {
        
        // Create an object of MaxFibonacciHeap class
        keywordcounter ktc = new keywordcounter();
        
        try {
            // Create  BufferedWriter object to write output file
            BufferedWriter writer = new BufferedWriter(new FileWriter("output_file.txt"));
        
            // Create BufferedReader object to read the input file
            BufferedReader reader = new BufferedReader(new FileReader(args[0]));
        
            // Invoke readf to read input file passed as first argument on the command line
            ktc.readf(reader,writer);
        
            // Close the BufferedWriter and BufferedReader after processing
            try {
                
                reader.close();
                writer.close();
                
            } catch (IOException ex) {
                
                System.out.println(ex);
            }
        
        } catch (FileNotFoundException ex) {
            
            System.out.println(ex);
            
        }
      
    }
    


     
    /**********************************TEXT PROCESSING METHODS*******************************************/
    /*
    Method: writef
    Parameters: BufferedWriter writer, Node node and String newLine 
    Description: Writes the tag associated with the passed tagnode node in the Output file associated to BufferedWriter writer. 
    Return value: None 
    */
    
        public void writef(BufferedWriter writer,Node node, String newLine) {
        try {
            
            // Write the hashtag associated to passed node into Output file associated to BufferWriter writer
            if (newLine.equals("last")) {
                writer.write( node.tag.substring(node.tag.indexOf('$')+1, node.tag.length()));
                
                // Print new line if last tag of current query
                writer.newLine();
            }
            else
                // Every other tag should have a comma appended
                writer.write( node.tag.substring(node.tag.indexOf('$')+1, node.tag.length())+  ',');
        
        } catch (IOException e) {
            System.err.println("Error in writing to output_file.txt.");
        }
        
    }
    
    
    /*
    Method: readf
    Parameters: BufferedReader reader and BufferedWriter writer
    Description: Reads and processes the Input file passed associated with BufferedReader reader.
    Return value: None 
    */
    public void readf(BufferedReader reader, BufferedWriter writer) {
        
        // Process the input file associated with BufferedReader reader
        try {
            
            // A List of maxnode objects to be inserted again into the fibonacci heap structure after removemax for query handling
            List<Node> updatedlist = new ArrayList<Node>();
            
            // A String line is used to read the input file line by line
            String line = new String();
            
            // Read the input file line by line
            while ( !(line = reader.readLine()).equalsIgnoreCase("STOP") ) {
               
                // If line read is a query
                if ( !(line.startsWith("$")) ) {
                    
                    // Get the number of top words to be printed as int
                    int query = Integer.parseInt(line);
                  
                    // Process the query to remove <=query  maxnodes from MaxFibonacciHeap structure
                    for (int count=1; (count<= query && maxfibheap.findmax() != null) ; count++) {
                        
                        // One at a time, Remove the required number of max nodes  
                        Node maxnode = maxfibheap.removeMax();
                        
                        // Write the tag value associated with removed maxnode to Output file
                        if ( (count==query)|| (maxnode == null))
                            writef(writer,maxnode,"last");
                        else
                            writef(writer,maxnode,"reg");
                        
                        // Store removed maxnodes for re-adding into fiboacci heap structure after handling current query 
                        updatedlist.add(maxnode);
                    }
                        
                    // Add the removed maxnodes into the fibonnaciheap structure after handling query
                    if (!updatedlist.isEmpty())
                        maxfibheap.reinsertmaxnodes(updatedlist);
                }
                
                // Else, if line read is a new tag 
                else { 
                    
                    // Split the input line by space and obtain the tag and tagnode values 
                    String splitLine[] = line.split(" ");
                    
                    
                    // Retrieve the tag-related node from the hashmap
                    Node tagnode = keymap.getmap(splitLine[0]);
                    
                    // If tag  already exists in keymap, increaseKey for the tagnode
                    if (tagnode != null) {
                        maxfibheap.increaseKey(tagnode, (tagnode.key + Integer.parseInt(splitLine[1])) );
                    }
                    
                    //If tag doesn't exists,create a new node for it, insert into maxfibonnaciheap structure and also register it
                    //in the hashmap   
                    else {
                        tagnode = maxfibheap.insert( splitLine[0], Integer.parseInt(splitLine[1]) );
                        
                        // Insert (hashTag, tagnode node) pair into the Hashmap
                        keymap.putmap(splitLine[0], tagnode);
                        
                    }
                }
            }
            
        } catch (IOException ex) {
            System.out.println(ex);
        }
      
    }
}
    
    
    