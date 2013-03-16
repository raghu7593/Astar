//2 3 4 1 5 0 7 6 8
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package puzzle8;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author RAGHUTEJA
 */
public class puzzle8_main {
    int heu;
    node s = new node();
    node g = new node();
    int ppredirection_ol = 0;
    int ppredirection_cl = 0;
    
    Comparator<node> comparator = new node_comparator();
    PriorityQueue<node> ol = new PriorityQueue<node>(10,comparator);
    PriorityQueue<node> cl = new PriorityQueue<node>(10, comparator);
    
    puzzle8_main(String initial_pos,String final_pos,int h) {
        heu = h;
        String[] initial_temp;
        initial_temp = initial_pos.split(" ");
        for(int i=0; i<initial_temp.length; i++){
            int xc = i/3;
            int yc = i%3;
            s.puz[xc][yc] = Integer.parseInt(initial_temp[i]);
        }
        heuristic heur = new heuristic();
        
        String[] final_temp;
        final_temp = final_pos.split(" ");
        for(int i=0; i<final_temp.length; i++){
            int xc = i/3;
            int yc = i%3;
            g.puz[xc][yc] = Integer.parseInt(final_temp[i]);
        }
        g.h = 0;
        if(heu == 0) s.h = heur.displaced_tiles(s, g);
        else if (heu == 1) s.h = heur.manhattan(s, g);
        else if (heu == 3) s.h = heur.overestimated(s, g);
        else s.h = 0;
        s.g = 0;
        s.f = s.h;
    }
    
    boolean isequal(node n1, node n2) {
        for(int i=0; i<3; i++) {
            for(int j=0; j<3; j++) {
                if(n1.puz[i][j] != n2.puz[i][j]) return false;
            }
        }
        return true;
    }
    
    boolean inrange(int i, int j){
        if((i<3 && i>=0) && (j<3 && j>=0)) return true;
        else return false;
    }
    
    List <node> children (node n){
        List<node> child=new ArrayList<node>();
        int a=0, b=0;
        for(int i=0; i<3; i++) {
            for(int j=0; j<3; j++) {
                if(n.puz[i][j] == 0) {
                    a = i;
                    b = j;
                }
            }
        }
        if(inrange(a+1,b)) {
            node nnode = new node();
            for(int i=0; i<3; i++) {
                for(int j=0; j<3; j++) {
                    nnode.puz[i][j] = n.puz[i][j];
                }
            }
            nnode.puz[a][b] = nnode.puz[a+1][b];
            nnode.puz[a+1][b] = 0;
            heuristic heur = new heuristic();
            if(heu == 0) nnode.h = heur.displaced_tiles(nnode, g);
            else if (heu == 1) nnode.h = heur.manhattan(nnode, g);
            else if (heu == 3) nnode.h = heur.overestimated(nnode, g);
            else nnode.h = 0;
            nnode.g = n.g+1;
            nnode.f = nnode.g + nnode.h;
            nnode.parent = n;
            child.add(nnode);
        }
        if(inrange(a-1,b)) {
            node nnode = new node();
            for(int i=0; i<3; i++) {
                for(int j=0; j<3; j++) {
                    nnode.puz[i][j] = n.puz[i][j];
                }
            }
            nnode.puz[a][b] = nnode.puz[a-1][b];
            nnode.puz[a-1][b] = 0;
            heuristic heur = new heuristic();
            if(heu == 0) nnode.h = heur.displaced_tiles(nnode, g);
            else if (heu == 1) nnode.h = heur.manhattan(nnode, g);
            else if (heu == 3) nnode.h = heur.overestimated(nnode, g);
            else nnode.h = 0;
            nnode.g = n.g+1;
            nnode.f = nnode.g + nnode.h;
            nnode.parent = n;
            child.add(nnode);
        }
        if(inrange(a,b+1)) {
            node nnode = new node();
            for(int i=0; i<3; i++) {
                for(int j=0; j<3; j++) {
                    nnode.puz[i][j] = n.puz[i][j];
                }
            }
            nnode.puz[a][b] = nnode.puz[a][b+1];
            nnode.puz[a][b+1] = 0;
            heuristic heur = new heuristic();
            if(heu == 0) nnode.h = heur.displaced_tiles(nnode, g);
            else if (heu == 1) nnode.h = heur.manhattan(nnode, g);
            else if (heu == 3) nnode.h = heur.overestimated(nnode, g);
            else nnode.h = 0;
            nnode.g = n.g+1;
            nnode.f = nnode.g + nnode.h;
            nnode.parent = n;
            child.add(nnode);
        }
        if(inrange(a,b-1)) {
            node nnode = new node();
            for(int i=0; i<3; i++) {
                for(int j=0; j<3; j++) {
                    nnode.puz[i][j] = n.puz[i][j];
                }
            }
            nnode.puz[a][b] = nnode.puz[a][b-1];
            nnode.puz[a][b-1] = 0;
            heuristic heur = new heuristic();
            if(heu == 0) nnode.h = heur.displaced_tiles(nnode, g);
            else if (heu == 1) nnode.h = heur.manhattan(nnode, g);
            else if (heu == 3) nnode.h = heur.overestimated(nnode, g);
            else nnode.h = 0;
            nnode.g = n.g+1;
            nnode.f = nnode.g + nnode.h;
            nnode.parent = n;
            child.add(nnode);
        }
        return child;
    }
    
    void printlist() {
        for (node n : cl) {
            for(int i=0; i<3; i++){
                for(int j=0; j<3; j++){
                    System.out.print(n.puz[i][j] + " ");
                }
            }
            System.out.println();
        }
    }
    
    void printnode(node n) {
        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                System.out.print(n.puz[i][j] + " ");
            }
        }
        System.out.print(" -- " + n.g + " -- " + n.h + " -- " + n.f);
        System.out.println();
    }
    
    String parents(node n) {
        System.out.println("###########################################################");
        String output = "";
        while(n.parent != null){
            for(int i=0; i<3; i++){
                for(int j=0; j<3; j++){
                    output = output + n.puz[i][j] + " ";
                }
            }
            output = output + "#" + n.g + "#" + n.h + "#" + n.f + "#";
            n = n.parent;
        }
        return output;
    }
    
    boolean solvable() {
        int start[] = new int[9];
        int k = 0;
        for(int i=0; i<3; i++) {
            for(int j=0; j<3; j++){
                start[k] = s.puz[i][j];
                k++;
            }
        }
        int inversions = 0;
        for(int i=0; i<9; i++) {
            if(start[i] != 0){
                for(int j=i; j<9; j++){
                    if((start[j] < start[i]) && (start[j] != 0)) inversions++;
                }
            }
        }
        if(inversions % 2 == 0) return true;
        else return false;
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
                                ppredirection_ol++;
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
                                ppredirection_cl++;
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
            System.out.println(ol.size() + "--" + cl.size());
    //        printlist();
            String output = "";
            if(isequal(n,g)) {
                output = output + ppredirection_cl + "#" +  ppredirection_ol + "#" + ol.size() + "#" + cl.size() + "#" + "Goal Reached" + "#" + parents(n);
            }
            else {
                output = output + ppredirection_cl + "#" +  ppredirection_ol + "#" + ol.size() + "#" + cl.size() + "#" + "Goal cannot be Reached" + "#" + parents(n);
            }
            for(int i=0; i<3; i++){
                for(int j=0; j<3; j++){
                    output = output + s.puz[i][j] + " ";
                }
            }
            output = output + "#" + s.g + "#" + s.h + "#" + s.f + "#";
            return output;
        }
        else {
            String output = "";
            output = output + "0" + "#" +  "0" + "#" + "0" + "#" + "0" + "#" + "Goal cannot be Reached" + "#";
            return output;
        }
    }
}
