/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mc;

import java.util.Comparator;

/**
 *
 * @author RAGHUTEJA
 */
public class node_comparator implements Comparator<node>{

    @Override
    public int compare(node o1, node o2) {
        if(o1.f < o2.f) return -1;
        else if(o1.f > o2.f) return 1;
        else return 0;
    }
    
}
