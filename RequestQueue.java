/** This is is the RequestQueue class. It is a Queue of Requests that allows the Requests to be processed
 * using the FIFO principle. It uses the ArrayList class to construct a Queue of Requests.
 * 
 * @author Otto Halbhuber
 * ID: 116150792
 * Recitation: R30
*/

import java.util.*;

public class RequestQueue extends ArrayList<Request>{
    
    /**This is the RequestQueue constructor, which just calls the ArrayList constructor */
    public RequestQueue(){
        super();
    }

    /**Adds a new Request to the back of the ArrayList 
     * @param request
     *      the new Request to be added
    */
    public void enqueue(Request request){
        super.add(size(), request);
    }

    /**removes a Request from the front of the ArrayList
     * @return 
     *      returns the Request that was removed
     */
    public Request dequeue(){
        return super.remove(0);
    }

    /**returns the size of the Queue
     *@return 
     *      returns the size of the Queue      
     */
    // public int size(){
    //     return super.size();
    // }

    // /**specifies whether or not the Queue is empty
    //  *@return 
    //  *   returns true if size is 0, false otherwise
    //  */
    // public boolean isEmpty(){
    //     return size() == 0;
    // }

    /**Removes the first Request object in the Queue equal to the parameter. Used in the optimized version.
     * @param request
     *      the Request object to be removed
     * @return
     *      returns the Request object that was removed, if it exists. Returns null otherwise
     */
    public Request remove(Request request){

        for(int i = 0; i < size(); i++){
            if(request.equals(super.get(i))){
                return super.remove(i);
            }
        }

        return null;
    }


    /**Returns a String visualization of the RequestQueue
     * @return
     *      returns a String visualization of the RequestQueue
     */
    public String toString(){
        return super.toString();
    }

}