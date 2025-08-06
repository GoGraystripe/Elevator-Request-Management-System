/**This is the OptimizedSimulator class, which runs the optimized version of the simulation
 * 
 * @author Otto Halbhuber
 * ID: 116150792
 * Recitation: R30
 */

import java.util.*;

public class OptimizedSimulator{

    public static int totalTime;

    public static int currentTime;

    public static int totalRequests;

    public static int handledRequests;
    
    /** runs the simulation
     * @param requestProbability
     *      the probability of a new request being made at any given timestep, between 0.0 and 1.0.
     * 
     * @param numFloors
     *      the number of floors in the building
     * 
     * @param numElevators
     *      the number of elevators in the building
     * 
     * @param simulationLength
     *      how many timesteps the simulation will run for
     * 
     * @return 
     *      the average wait time; non-void for debugging and testing purposes
     */
    public static double simulate(double requestProbability, int numFloors, int numElevators, int simulationLength){

        totalTime = 0;
        currentTime = 0;
        totalRequests = 0;
        handledRequests = 0;

        ArrayList<ArrayList<Request>> requestsByFloor = new ArrayList<ArrayList<Request>>();

        for(int i = 0; i < numFloors; i++) requestsByFloor.add(new ArrayList<Request>());
        
        RequestQueue requestQueue = new RequestQueue();

        ArrayList<Elevator> elevators = new ArrayList<Elevator>();

        for(int i = 0; i < numElevators; i++) elevators.add(new Elevator());
        

        for(int i = 0; i < simulationLength; i++){

            currentTime = i;

            System.out.println("Timestep: " + currentTime);

            BooleanSource booleanSource = new BooleanSource(requestProbability);

            if(booleanSource.requestArrived()){

                Request request = new Request(numFloors);

                requestQueue.enqueue(request);

                request.setTimeEntered(i);

                requestsByFloor.get(request.getSourceFloor() - 1).add(request);

                System.out.println("A request was made on floor #" + request.getSourceFloor()
                + " to go to floor #"+request.getDestinationFloor());

                totalRequests++;
            
                System.out.println("The total number of requests in the system is now: "+totalRequests);

            }

            for(int j = 0; j < numElevators; j++){

                Elevator elevator = elevators.get(j);

                System.out.println("Elevator #" + j + " state: " + elevator.getElevatorState());

                elevator.optimizedMove();                

                System.out.println("Elevator #" + j + " has just been called for system analysis.");

                System.out.println("Elevator #" + j + " is currently at floor #" + elevator.getCurrentFloor());

                System.out.println("Elevator #" + j + " currently has " + elevator.getRequestList().size() + " requests.");

                


                if(elevator.getElevatorState() == Elevator.TO_SOURCE){

                    System.out.println("Elevator #" + j + " is on its way to a source");

                    System.out.println("Elevator #" + j  + " Floor #:" + elevator.getCurrentFloor());
                    System.out.println("Source floor: " + elevator.getRequestList().getFront().getRequest().getSourceFloor());

                    if(elevator.getCurrentFloor() == elevator.getRequestList().getFront().getRequest().getSourceFloor()){
    

                        System.out.println("An elevator has arrived at its source floor: " + elevator.getRequestList().getFront().getRequest().getSourceFloor()
                            + " at time " + OptimizedSimulator.currentTime);
                        System.out.println("The request was made at time " + elevator.getRequestList().getFront().getRequest().getTimeEntered()
                            + ", meaning that the total wait time for this request was "
                            + (OptimizedSimulator.currentTime - elevator.getRequestList().getFront().getRequest().getTimeEntered()));

                        elevator.setElevatorState(Elevator.TO_DESTINATION);

                        ArrayList<Request> requestsForCurrentFloor = requestsByFloor.get(elevator.getCurrentFloor() - 1);
                        
                        for(int k = requestsForCurrentFloor.size() - 1; k >= 0; k--){

                            Request request = requestsForCurrentFloor.get(k);

                            if(elevator.getDirection() == request.getDirection()){

                                requestsForCurrentFloor.remove(k);

                                elevator.getRequestList().add(request);

                                requestQueue.remove(request);

                                System.out.println("Elevator #" + j + " just picked up an extra request while making a pickup on floor #"
                                    + elevator.getCurrentFloor());

                                     System.out.println("The request was made at time " + request.getTimeEntered()
                                    + ", meaning that the total wait time for this request was "
                                    + (OptimizedSimulator.currentTime - request.getTimeEntered()));

                                totalTime += (OptimizedSimulator.currentTime - request.getTimeEntered());

                                handledRequests++;

                            }

                            else if(request.getDirection() == 0){

                                requestsForCurrentFloor.remove(k);

                                requestQueue.remove(request);

                                System.out.println("A passenger requested to go to the floor they were already on. Their request was processed.");
                                System.out.println("The request was made at time " + request.getTimeEntered()
                                    + ", meaning that the total wait time for this request was "
                                    + (OptimizedSimulator.currentTime - request.getTimeEntered()));

                            }

                        }

                        handledRequests++;

                       

                        OptimizedSimulator.totalTime += (OptimizedSimulator.currentTime - elevator.getRequestList().getFront().getRequest().getTimeEntered());

                        System.out.println();

                    }
                }

                if(elevator.getElevatorState() == Elevator.TO_DESTINATION){

                    System.out.println("Elevator #" + j + " is on its way to a destination.");

                    int target = elevator.getRequestList().getFront().getRequest().getDestinationFloor();

                    int saveTarget = target;

                    /* Unload the passengers who want to get off at that floor */
                    int dischargedPassengers = 0;
                    while (elevator.getCurrentFloor() == target){

                        elevator.getRequestList().removeFront();

                        dischargedPassengers++;

                        System.out.println("Elevator #" + j + " just discharged a passenger on Floor #" +  target);

                        if(elevator.getRequestList().getFront() != null){
                            target = elevator.getRequestList().getFront().getRequest().getDestinationFloor();
                        }
                        else{
                            break;
                        }

                        

                    }

                    System.out.println("Elevator #" +  j + " just discharged " + dischargedPassengers + " passengers");

                    if(elevator.getCurrentFloor() == saveTarget){ /* Load new passengers */
 
                        int newPassengers = 0;

                        ArrayList<Request> requestsForCurrentFloor = requestsByFloor.get(saveTarget - 1);

                        if(!requestsForCurrentFloor.isEmpty()){

                            System.out.println("Floor #" + saveTarget + " has " + requestsForCurrentFloor.size() + " requests");

                            for(int k = requestsForCurrentFloor.size() - 1; k >= 0; k--){

                                Request request = requestsForCurrentFloor.get(k);

                                System.out.println("Request direction: " + request.getDirection());
                                System.out.println("Elevator direction: " + elevator.getDirection());
                                
                                if(request.getDirection() == elevator.getDirection()){



                                    elevator.getRequestList().add(request);

                                    requestsForCurrentFloor.remove(k); /*remove them from the requests on that floor */

                                    Request removed = requestQueue.remove(request); /* remove them from the requestQueue */


                                    if(removed == null) System.out.println("Failed to remove request from requestQueue");

                                    System.out.println("Elevator #" + j + " just picked up an extra request while making a drop on floor #"
                                        + saveTarget);

                                     System.out.println("The request was made at time " + request.getTimeEntered()
                                        + ", meaning that the total wait time for this request was "
                                        + (OptimizedSimulator.currentTime - request.getTimeEntered()));

                                    OptimizedSimulator.totalTime += (OptimizedSimulator.currentTime - request.getTimeEntered());

                                    handledRequests++;

                                    newPassengers++;

                                }
                                else if(request.getDirection() == 0){

                                    requestsForCurrentFloor.remove(k);

                                    requestQueue.remove(request);

                                    System.out.println("A passenger requested to go to the floor they were already on. Their request was processed.");
                                    System.out.println("The request was made at time " + request.getTimeEntered()
                                        + ", meaning that the total wait time for this request was "
                                        + (OptimizedSimulator.currentTime - request.getTimeEntered()));

                                }

                            }

                            System.out.println("Elevator #" + j + " just accepted " + newPassengers + " new passengers");

                        }

                    }
                    
                    saveTarget = -1;

                }

                

                if(elevator.getElevatorState() == Elevator.IDLE && !requestQueue.isEmpty()){

                    Request request = requestQueue.dequeue();

                    elevator.getRequestList().add(request);

                    elevator.setElevatorState(Elevator.TO_SOURCE);

                    System.out.println("Elevator #" + j + " has just been assigned a new source call to go from "
                        + request.getSourceFloor() + " to " + request.getDestinationFloor());

                }

                if(elevator.getRequestList().isEmpty()){
                
                    System.out.println("Elevator #" +  j +" currently has no requests in its list.");

                    elevator.setElevatorState(Elevator.IDLE);

                }
            }

            System.out.println();
        }

        System.out.println("Total Requests: " + totalRequests);
        System.out.println("Unassigned requests: "+requestQueue.size());
        System.out.println("Assigned requests: " + (totalRequests - requestQueue.size()));
        System.out.println("Handled requests: "+handledRequests);

        System.out.println("Total Time: " + totalTime);
        
        System.out.println();

        if(handledRequests != 0)
            System.out.println("Average wait time: " + Math.round((totalTime / (handledRequests + 0.0)) * 100) / 100.0  + " timesteps");
        else
            System.out.println("Average wait time: 0 timesteps");

        return totalTime / (handledRequests + 0.0);

        // System.out.println();
        // System.out.println("---------------------------------------------");
        // System.out.println();

        

    }

    

}