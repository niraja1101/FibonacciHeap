/*
 * KeywordMap Class  
 */

import java.util.HashMap;
import java.util.Map;

public class KeywordMap {
    
    /**********VARIABLES**********/
    // Declare the Map object to store (tag, tagnode) pairs
    private Map<String, Node> map;
    
    
    /**********METHODS**********/
    /*
    Method: KeywordMap(constructor)
    Parameters: None
    Description: KeywordMap class constructor which initializes the class variables
    Return value: None
    */
    KeywordMap(){
        this.map = new HashMap<String, Node>();
    }
    
    
    /*
    Method: putmap
    Parameters: String tag, Node tagnode 
    Description: Inserts the passed (tag,tagnode) entry in Hashmap. 
    Return value: None
    */
    public void putmap(String tag,Node tagnode) {
        map.put(tag, tagnode);
    }
    
    
    /*
    Method: getmap
    Parameters: String tag 
    Description: Retrieves tagnode associated with the tag entry from Hashmap. 
    Return value: tagnode  
    */
    public Node getmap(String tag) {
        return map.get(tag);
    }
    
}
