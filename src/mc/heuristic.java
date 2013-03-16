/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mc;

/**
 *
 * @author RAGHUTEJA
 */
public class heuristic {
    int heuristic_mc(node n) {
        int h = n.c + n.m;
        if(n.boat == 'L') {
            h = h/2;
        }
        else h = (h+1)/2;
        h = h/2;
        return h;
    }
}
