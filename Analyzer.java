/** This is the Analyzer class, which allows the user to run the program interaction with the terminal.
 * 
 * @author Otto Halbhuber
 * ID: 116150792
 * Recitation: R30
 */

import java.util.*;

public class Analyzer{

    public static void main(String[] args) throws IllegalArgumentException{

        double requestProbability;
        int numFloors;
        int numElevators;
        int simulationLength;
        boolean isOptimized = false;


        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Building Elevator Simulation!");
        System.out.println("Please answer the following prompts so we can run your specified simulation.");
        
        System.out.println();

        System.out.println("At any given timestep, what is the probability (0.0-1.0) that a new passenger will make a new request?");

        requestProbability = scanner.nextDouble();
        scanner.nextLine();

        

        if(requestProbability < 0 || requestProbability > 1)
            throw new IllegalArgumentException("The probability value must be between 0.0 and 1.0");
        
        System.out.println();

        System.out.println("How many floors does the building have?");
        
        numFloors = scanner.nextInt();
        scanner.nextLine();

        if(numFloors <= 0)
            throw new IllegalArgumentException("The number of floors must be greater than or equal to 1");

        System.out.println();

        System.out.println("How many elevators does the building have?");

        numElevators = scanner.nextInt();
        scanner.nextLine();

        if(numElevators <= 0)
            throw new IllegalArgumentException("The number of elevators must be greater than or equal 1");

        System.out.println();
        
        System.out.println("For how many timesteps should the simulation run? Please choose a relatively high number to ensure that the elevators can satisfy at least 1 request.");

        simulationLength = scanner.nextInt();
        scanner.nextLine();

        if(simulationLength <= 0)
            throw new IllegalArgumentException("The duration of the simulation must be greater than 0 timesteps");

        System.out.println();

        System.out.println("Do you want the simulation to use the optimized program? y/n");

        if(scanner.nextLine().equals("y"))
            isOptimized = true;

        if(isOptimized)
            OptimizedSimulator.simulate(requestProbability, numFloors, numElevators, simulationLength);
        else
            Simulator.simulate(requestProbability, numFloors, numElevators, simulationLength);



        

        //request probability, number of floors, number of elevators, number of timesteps
        
        




        // double optimizedSimulatorSum = 0;
        // double simulatorSum = 0;

        // long optimizedRealTime = -1;
        // long regularRealTime = -1;

        // long start = System.nanoTime();
        // for(int i = 0; i < 1000; i++){

        //     double average = OptimizedSimulator.simulate(1, 100, 10, 500 );

        //     optimizedSimulatorSum += average;

        // }

        // // long end = System.nanoTime();
        // // optimizedRealTime = end - start;

        // // start = System.nanoTime();
        // for(int i = 0; i < 1000; i++){
            
        //     simulatorSum += Simulator.simulate(1, 100, 10, 500);

        // }

        // // end = System.nanoTime();
        // // regularRealTime = end - start;

        // System.out.println("Average Simulator average wait time: " + simulatorSum / 1000.0);
        // System.out.println("Average Optimized Simulator average wait time: " + optimizedSimulatorSum / 1000.0);
        // //System.out.println("% improvement: " + ((-1 * (optimizedSimulatorSum - simulatorSum) / simulatorSum) * 100));

        // //System.out.println("Simulator real time: " + regularRealTime);
        // System.out.println("Optimized simulator real time: " + optimizedRealTime);
    }

}