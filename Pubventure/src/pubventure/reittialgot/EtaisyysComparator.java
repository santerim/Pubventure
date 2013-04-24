
package pubventure.reittialgot;

import Pubventure.ymparisto.Pubiobjekti;
import java.util.Comparator;

/**
 *
 * @author Santeri
 * 
 * Tämä luokka toimii PriorityQueuen comparatorina
 */


public class EtaisyysComparator implements Comparator<Pubiobjekti> {
    
    @Override
    public int compare(Pubiobjekti eka, Pubiobjekti toka) {
        if (eka.getF() < toka.getF()) {
            return -1;
        }
        if (eka.getF() > toka.getF()) {
            return 1;
        }
        return 0;
    }
}
