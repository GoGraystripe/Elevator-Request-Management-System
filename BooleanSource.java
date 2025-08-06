/** This class determines whether or not a Request arrived based on a given probability.
 * 
 * @author Otto Halbhuber
 * ID: 116150792
 * Recitation: R30
 */

import java.util.*;

public class BooleanSource{

    private double probability;

    /**This is the BooleanSource constructor */
    public BooleanSource(double probability){
        this.probability = probability;
    }

    /**Determines whether or not a Request arrived
     * @return
     *      returns true if Math.random() is less than the given probability, false otherwise
     */
    public boolean requestArrived(){
        return Math.random() < probability;
    }
}