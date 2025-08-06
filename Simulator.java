/**This is the Simulator class, which runs the simulation
 * 
 * @author Otto Halbhuber
 * ID: 116150792
 * Recitation: R30
 */


import java.util.*;

public class Simulator{

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

        RequestQueue requestQueue = new RequestQueue();

        ArrayList<Elevator> elevators = new ArrayList<Elevator>();

        for(int i = 0; i < numElevators; i++){
            elevators.add(new Elevator());
        }

        for(int i = 0; i < simulationLength; i++){

            currentTime = i;

            BooleanSource booleanSource = new BooleanSource(requestProbability);

            if(booleanSource.requestArrived()){

                Request request = new Request(numFloors);

                requestQueue.enqueue(request);

                request.setTimeEntered(i);

                totalRequests++;
            
            }

            for(int j = 0; j < numElevators; j++){

                Elevator elevator = elevators.get(j);

                elevator.move();

                

                if(elevator.getElevatorState() != Elevator.IDLE){
                    if(elevator.getCurrentFloor() == elevator.getRequest().getSourceFloor()){

                        System.out.println("An elevator has arrived at its source floor: " + elevator.getRequest().getSourceFloor()
                            + " at time " + Simulator.currentTime);
                        System.out.println("The request was made at time " + elevator.getRequest().getTimeEntered()
                            + ", meaning that the total wait time for this request was " + (Simulator.currentTime - elevator.getRequest().getTimeEntered()));

                        handledRequests++;

                        Simulator.totalTime += (Simulator.currentTime - elevator.getRequest().getTimeEntered());

                        System.out.println();

                    }
                }

                if(elevator.getElevatorState() == Elevator.IDLE && !requestQueue.isEmpty()){

                    elevator.setRequest(requestQueue.dequeue());

                    elevator.setElevatorState(Elevator.TO_SOURCE);

                }

                
                
            }


        }

        System.out.println("Total Requests: " + totalRequests);
        System.out.println("Unassigned requests: "+requestQueue.size());
        System.out.println("Assigned requests: " + (totalRequests - requestQueue.size()));
        System.out.println("Handled requests: "+handledRequests);

        System.out.println("Total Time: " + totalTime);

        System.out.println();

        if(handledRequests != 0)
            System.out.println("Average wait time: " + Math.round((totalTime / (handledRequests + 0.0)) * 100) / 100.0 + " timesteps");
        else
            System.out.println("Average wait time: 0 timesteps");
        
        return totalTime / (handledRequests + 0.0);

    }
}