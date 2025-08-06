/** This the Request class. It stores information on a request, specifically the
 * floor the request was made at, the floor the passenger making the request
 * wants to go to, the distance between those floors, and the time that request was made
 * 
 * @author Otto Halbhuber
 * ID: 116150792
 * Recitation: R30
 */

import java.util.*;

public class Request{
    private int sourceFloor;
    private int destinationFloor;

    private int distanceToFloor;

    private int timeEntered;
    
    
    /** This is the constructor for a Request object*/
    public Request(int numFloors){
        
        sourceFloor = (int) (Math.random() * numFloors + 1);

        destinationFloor = (int) (Math.random() * numFloors + 1);

        distanceToFloor = Math.abs(destinationFloor - sourceFloor);
    }


    /** source floor getter
     * @return
     *  returns the source floor
    */
    public int getSourceFloor(){
        return sourceFloor;
    }
    
    /** source floor setter
     *  @param sourceFloor
     *     the new source floor
     */
    public void setSourceFloor(int sourceFloor){
        this.sourceFloor = sourceFloor;
    }

    /**destination floor getter
     * @return
     *      returns the destination floor
     */
    public int getDestinationFloor(){
        return destinationFloor;
    }

    /**destination floor setter
     * @param destinationFloor
     *      the new destination floor
     */
    public void setDestinationFloor(int destinationFloor){
        this.destinationFloor = destinationFloor;
    }

    /**distance to floor getter
     *@return
     *  the distance between the source and destination floors
     */
    public int getDistanceToFloor(){
        distanceToFloor = Math.abs(destinationFloor - sourceFloor);
        return distanceToFloor;
    }

    /**distance to floor setter
     *@param distancetToFloor
     *  the new distance between the source and destination floors
     */
    public void setDistanceToFloor(int distanceToFloor){
        //this.distanceToFloor = distanceToFloor;
    }

    /** time entered getter
     * @return
     *      the time the request was entered into the system
     */
    public int getTimeEntered(){
        return timeEntered;
    }

    /** time entered setter
     * @param timeEntered
     *      the new time the request was entered into the system
     */
    public void setTimeEntered(int timeEntered){
        this.timeEntered = timeEntered;
    }

    /** direction getter
     * @return
     *      the direction the passenger wants to go (1 means up, -1 means down, 
     *      and 0 means destination floor equals the source floor)
     */
    public int getDirection(){
        
        if(destinationFloor - sourceFloor > 0) return 1;

        if(destinationFloor - sourceFloor < 0) return -1;

        return 0;

    }

    /**defines what it means for two Requests to be equal to each other. Used in the optimized version.
     * @param obj
     *      the object that the Request is being compared to
     * @return
     *      true if Request and the object are equal, false otherwise
     * 
     */
    @Override
    public boolean equals(Object obj){

        Request request = null;

        if(this == obj) return true;

        if(obj == null) return false;

        if(obj.getClass() == getClass()){
            request = (Request) obj;
        }
        else return false;

        return request.sourceFloor == sourceFloor
            && request.destinationFloor == destinationFloor && request.timeEntered == timeEntered
            && request.getDirection() == getDirection();

    }
    
    


}