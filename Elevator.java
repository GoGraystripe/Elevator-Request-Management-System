/** This class defines the properties of an Elevator, including its currentFloor, its state (going to a source, destination
 * or neither), and the request its currently handling. In the optimization, it also makes use of a list of requests for
 * it to handle and its direction: up, down, or not moving.
 * 
 * @author Otto Halbhuber
 * ID: 116150792
 * Recitation: R30
*/

import java.util.*;

public class Elevator{
    private int currentFloor;
    private int elevatorState;
    private Request request;

    private RequestList requestList;

    public static final int IDLE = 0;
    public static final int TO_SOURCE = 1;
    public static final int TO_DESTINATION = 2;

    public static final int GOING_DOWN = -1;
    public static final int GOING_UP = 1;
    public static final int NOT_MOVING = 0;

    private int direction;

    /**This is the elevator constructor */
    public Elevator(){
        
        currentFloor = 1;
        elevatorState = IDLE;

        requestList = new RequestList();

        request = null;

    }

    /**current floor getter
     * @return
     *      returns the current floro
     */
    public int getCurrentFloor(){
        return currentFloor;
    }

    /**current floor setter
     * @param currentFloor
     *      the new currentFloor
     */
    public void setCurrentFloor(int currentFloor){
        this.currentFloor = currentFloor;
    }

    /**elevator state getter
     * @return
     *      returns the current elevator state TO_SOURCE, TO_DESTINATION, or IDLE
     */
    public int getElevatorState(){
        return elevatorState;
    }

    /**elevator state setter 
     * @param elevatorState
     *      the new elevator state
    */
    public void setElevatorState(int elevatorState){
        this.elevatorState = elevatorState;
    }

    /**request getter
     * @return
     *      the request the elevator is currently handling
     */
    public Request getRequest(){
        return request;
    }

    /**request setter
     * @param request
     *      the new request the elevator will be handling
     */
    public void setRequest(Request request){
        this.request = request;
    }

    /**requestList getter
     * @return
     *      the list of requests the elevator is handling
     * Used in the optimized version.
     */
    public RequestList getRequestList(){
        return requestList;
    }

    /**requestList setter
     * @param requestList
     *      the new list of requests the elevator is handling
     */
    public void setRequestList(RequestList requestList){
        this.requestList = requestList;
    }

    /** the method that changes the elevator's floor, if it isn't idle, depending on
     * whether it needs to go to a source or destination, and its position relative to
     * the source/destination
     */
    public void move(){

        if(elevatorState == IDLE || request == null) return;

        if(elevatorState == TO_SOURCE){

            if(request.getSourceFloor() - currentFloor > 0) currentFloor++;
            
            else if(request.getSourceFloor() - currentFloor < 0) currentFloor--;

        }

        if(elevatorState == TO_DESTINATION){
            
            if(request.getDestinationFloor() - currentFloor > 0) currentFloor++;

            else if(request.getDestinationFloor() - currentFloor < 0) currentFloor--;

        }

        if(currentFloor == request.getSourceFloor() && elevatorState == TO_SOURCE) {
            // System.out.println("An elevator has arrived at its source floor: " + request.getSourceFloor() + " at time " + Simulator.currentTime);
            // System.out.println("The request was made at time " + request.getTimeEntered() + ", meaning that the total wait time for this request was " + (Simulator.currentTime - request.getTimeEntered()));

            // Simulator.totalTime += (Simulator.currentTime - request.getTimeEntered());

            // System.out.println();

            elevatorState = TO_DESTINATION;
        }

            

        if(currentFloor == request.getDestinationFloor() && elevatorState == TO_DESTINATION){

            elevatorState = IDLE;

            request = null;
        }
        
    }


    /**The method that makes the elevator change floors, if it isn't idle, that is used in the optimized version.
     * Sets the elevators direction: GOING_UP, GOING_DOWN, or NOT_MOVING and adjusts its current floor accordingly.
     */
    public void optimizedMove(){

        if(elevatorState == IDLE || requestList.getFront() == null) return;

        if(elevatorState == TO_SOURCE){

            if(requestList.getFront().getRequest().getSourceFloor() - currentFloor > 0) direction = GOING_UP;
            
            else if(requestList.getFront().getRequest().getSourceFloor() - currentFloor < 0) direction = GOING_DOWN;

            else direction = NOT_MOVING;

        }

        else if(elevatorState == TO_DESTINATION){

            if(requestList.getFront().getRequest().getDestinationFloor() - currentFloor > 0) direction = GOING_UP;
            
            else if(requestList.getFront().getRequest().getDestinationFloor() - currentFloor < 0) direction = GOING_DOWN;

            else direction = NOT_MOVING;
            
        }
        
        if(direction == GOING_UP) currentFloor++;

        else if(direction == GOING_DOWN) currentFloor--;


    }

    /**direction getter
     * @return 
     *      the elevator's direction
     */
    public int getDirection(){
        return direction;
    }

    /**direction setter
     * @param direction
     *      the elevator's new direction
     */
    public void setDirection(int direction){
        this.direction = direction;
    }


}