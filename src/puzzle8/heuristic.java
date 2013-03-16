/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package puzzle8;

/**
 *
 * @author RAGHUTEJA
 */
public class heuristic {
    
    int displaced_tiles(node n, node g){
        int dt = 0;
        for(int i=0; i<9; i++) {
            int xc = i/3;
            int yc = i%3;
            if((n.puz[xc][yc] != g.puz[xc][yc]) && (n.puz[xc][yc] != 0)) {
                dt++;
            }
        }
        return dt;
    }
    
    int manhattan(node n, node g){
        int md = 0;
        for(int i=0; i<3; i++) {
            for(int j=0; j<3; j++){
                if(n.puz[i][j] != 0){
                    int i1=0,j1=0;
                    for(i1=0; i1<3; i1++) {
                        for(j1=0; j1<3; j1++){
                            if(g.puz[i1][j1] == n.puz[i][j]){
                                md = md + Math.abs(i-i1) + Math.abs(j-j1);
                            }
                        }
                    }
                }
            }
        }
        return md;
    }
    
    int overestimated(node n, node g){
        return displaced_tiles(n,g)*2;
    }
}