/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ggs;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;

/**
 *
 * @author RAGHUTEJA
 */
public class ggs_main {
    int num_nodes;
    int[] heuristic;
    int[][] graph;
    node s = new node();
    node g = new node();
    
    Comparator<node> comparator = new node_comparator();
    PriorityQueue<node> ol = new PriorityQueue<node>(10,comparator);
    PriorityQueue<node> cl = new PriorityQueue<node>(10, comparator);
    
    ggs_main(int n,int[][] gr, int[] heu){
        num_nodes = n;
        heuristic = new int[num_nodes];
        graph = new int[num_nodes][num_nodes];
        for(int i=0; i<num_nodes; i++){
            heuristic[i] = heu[i];
            for(int j=0; j<n; j++){
                graph[i][j] = gr[i][j];
            }
        }
        
        s.node_id = 1;
        s.h = heuristic[0];
        s.g = 0;
        s.f = s.h;
        
        g.node_id = num_nodes;
        g.h = 0;
    }
    
    boolean isequal(node n1, node n2) {
        if(n1.node_id == n2.node_id) return true;
        else return false;
    }
    
    List <node> children (node n){
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        List<node> child=new ArrayList<node>();
        for(int i=0; i<num_nodes; i++){
            if((graph[n.node_id-1][i] != -1) && (graph[n.node_id-1][i] != 0)) {
                node nnode = new node();
                nnode.node_id = i+1;
                nnode.parent = n;
                nnode.g = n.g + graph[n.node_id-1][i];
                nnode.h = heuristic[i];
                nnode.f = nnode.g + nnode.h;
                child.add(nnode);
                printnode(nnode);
            }
        }
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        return child;
    }
    
    void update_closedlist(node n){
        Iterator cl_it = cl.iterator();
        node next;
        while(cl_it.hasNext()) {
            next = (node) cl_it.next();
            if(next.parent != null) {
                if(isequal(next.parent,n)){
                    next.g = n.g + graph[n.node_id-1][next.node_id-1];
                    next.f = next.g + next.h;
                    update_closedlist(next);
                }
            }
        }
    }
    
    void printnode(node n) {
        System.out.println(n.node_id + "#" + n.g + "#" + n.h + "#" + n.f + "#");
    }
    
    void printol(){
        node next;
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        Iterator it1 = ol.iterator();
        while (it1.hasNext()){
            next = (node) it1.next();
            printnode(next);
        }
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
    }
    
    String parents(node n) {
        System.out.println("###########################################################");
        String output = "";
        while(n.parent != null){
            output = output + n.node_id + "#" + n.g + "#" + n.h + "#" + n.f + "#";
            n = n.parent;
        }
        return output;
    }
    
    String run() {
        String output = "";
        ol.add(s);
        node n = null;
        while(!ol.isEmpty()) {
            printol();
            n = ol.poll();
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
                    System.out.println("------------------------");
                    if(isequal(next_chk,next)) {
                        already = true;
                        if(next.g < next_chk.g){
                            next_chk.g = next.g;
                            next_chk.f = next.g + next_chk.h;
                            next_chk.parent = n;
                        }
                    }
                }
                if(already) continue;
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
                if(already) continue;
                //adding it to open list
                if(!already) {
                    ol.add(next);
                }
            }
        }
        System.out.println("---------------------------Done----------------------------");
        System.out.println(ol.size() + "--" + cl.size());
        if(isequal(n,g)) {
            output = output + ol.size() + "#" + cl.size() + "#" + "Goal Reached" + "#" + parents(n);
        }
        else {
            output = output + ol.size() + "#" + cl.size() + "#" + "Goal cannot be Reached" + "#" + parents(n);
        }
        output = output + s.node_id + "#" + s.g + "#" + s.h + "#" + s.f + "#";
        System.out.println(output);
        return output;
    }
}