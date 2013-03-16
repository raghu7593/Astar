/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mc;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;

/**
 *
 * @author RAGHUTEJA
 */
public class mc_main {
    node s = new node();
    node g = new node();
    int boat_sz;
    int heu;
    
    Comparator<node> comparator = new node_comparator();
    PriorityQueue<node> ol = new PriorityQueue<node>(10,comparator);
    PriorityQueue<node> cl = new PriorityQueue<node>(10, comparator);
    
    mc_main(int m, int c,int sz,int h) {
        boat_sz = sz;
        heu = h;
        System.out.println(heu);
        s.m = m;
        s.c =c;
        s.g = 0;
        heuristic heur = new heuristic();
        if(heu == 0) s.h = 0;
        else s.h = heur.heuristic_mc(s);
        s.f = s.h;
        s.boat = 'L';
        
        g.m = 0;
        g.c = 0;
        g.h = 0;
        g.boat = 'R';
    }
    
    boolean isequal(node n1, node n2) {
        if((n1.m == n2.m) && (n1.c == n2.c) && (n1.boat == n2.boat)) return true;
        else return false;
    }
    
    boolean inrange(int a, int b){
        if(((a<=s.m) && (a>=0)) && ((b<=s.c) && (b>=0))) {
            if(a == 0) return true;
            else if(a == s.m) return true;
            else if((a >= b) && (s.m-a >= s.c-b)) return true;
            else return false;
        }
        else return false;
    }
    
    List <node> children (node n){
        List<node> child=new ArrayList<node>();
        int a = n.m;
        int b = n.c;
        
        if(n.boat == 'L') {
            for(int i=boat_sz; i>0; i--){
                for(int j=0; j<=i; j++){
                    if(inrange(a-j,b-i+j)){
                        node nnode = new node();
                        nnode.m = a-j;
                        nnode.c = b-i+j;
                        nnode.g = n.g+1;
                        heuristic heur = new heuristic();
                        if(heu == 0) nnode.h = 0;
                        else nnode.h = heur.heuristic_mc(nnode);
                        nnode.f = nnode.g + nnode.h;
                        nnode.boat = 'R';
                        nnode.parent = n;
                        child.add(nnode);
                    }
                }
            }
        }
        else {
            for(int i=boat_sz; i>0; i--){
                for(int j=0; j<=i; j++){
                    if(inrange(a+j,b+i-j)){
                        node nnode = new node();
                        nnode.m = a+j;
                        nnode.c = b+i-j;
                        nnode.g = n.g+1;
                        heuristic heur = new heuristic();
                        if(heu == 0) nnode.h = 0;
                        else nnode.h = heur.heuristic_mc(nnode);
                        nnode.f = nnode.g + nnode.h;
                        nnode.boat = 'L';
                        nnode.parent = n;
                        child.add(nnode);
                    }
                }
            }
        }
//        System.out.println("@@@@@@@@@@ " + child.size() + " @@@@@@@" + n.c + " ---- " + n.f);
        return child;
    }
    
    void printlist() {
        System.out.println("============================================================================================");
        for (node n : ol) {
            System.out.println("--" + n.m + "#" + n.c + "#" + n.boat + "#" + n.g + "#" + n.h + "#" + n.f + "#---");
        }
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
    }
    
    void printnode(node n) {
        System.out.println(n.m + "#" + n.c + "#" + n.boat + "#" + n.g + "#" + n.h + "#" + n.f + "#");
    }
    
    String parents(node n) {
//        System.out.println("###########################################################");
        String output = "";
        while(n.parent != null){
            output = output + n.m + "#" + n.c + "#" + n.boat + "#" + n.g + "#" + n.h + "#" + n.f + "#";
            n = n.parent;
        }
        return output;
    }
    
    boolean solvable() {
        if(s.m < s.c) return false;
        else return true;
    }
    
    void update_closedlist(node n){
        Iterator cl_it = cl.iterator();
        node next;
        while(cl_it.hasNext()) {
            next = (node) cl_it.next();
            if(next.parent != null) {
                if(isequal(next.parent,n)){
                    next.g = n.g + 1;
                    next.f = next.g + next.h;
                    update_closedlist(next);
                }
            }
        }
    }
    
    String run() {
        String output = "";
        if(solvable()) {
            ol.add(s);
            node n = null;
            while(!ol.isEmpty()) {
                n = ol.poll();
                printnode(n);
                cl.add(n);
                if(isequal(n,g)) break;

                node next,next_chk;
                List <node> child = children(n);
                Iterator child_it = child.iterator();
                boolean already = false;
                while(child_it.hasNext()) {
                    already = false;
                    next = (node) child_it.next();
                    //checking weather node is in open list
                    Iterator it = ol.iterator();
                    while (it.hasNext()){
                        next_chk = (node) it.next();
                        if(isequal(next_chk,next)) {
                            already = true;
                            if(next.g < next_chk.g){
                                next_chk.g = next.g;
                                next_chk.f = next.g + next_chk.h;
                                next_chk.parent = n;
                            }
                        }
                    }
                    Iterator it1 = cl.iterator();
                    while (it1.hasNext()){
                        next_chk = (node) it1.next();
                        if(isequal(next_chk,next)) {
                            already = true;
                            if(next.g < next_chk.g){
                                next_chk.g = next.g;
                                next_chk.f = next.g + next_chk.h;
                                next_chk.parent = n;
                                update_closedlist(next);
                            }
                        }
                    }
                    //adding it to open list
                    if(!already) {
                        ol.add(next);
                    }
                }
            }
            System.out.println("---------------------------Done----------------------------");
            if(isequal(n,g)){
                output = output + ol.size() + "#" + cl.size() + "#" + "Goal Reached" + "#" + parents(n);
            }
            else {
                output = output + "0" + "#" + "0" + "#" + "Goal cannot be Reached" + "#";
            }
        }
        else {
            output = output + "0" + "#" + "0" + "#" + "Goal cannot be Reached : Missionaries < cannibals" + "#";
        }
//        System.out.println(output);
        return output;
    }
}
