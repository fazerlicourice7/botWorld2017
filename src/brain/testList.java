/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package brain;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author 18balanagav
 */
public class testList {
    
    List test = new ArrayList<>();

    public static void main(String[] args){
        DijkstraNode test2 = new DijkstraNode(0, 0);
        test2.setCostToArrive(20);
        List<DijkstraNode> unvisited = initUnvisited();
        unvisited.add(0, test2);        
        System.out.println(unvisited.get(0).toString());
        unvisited.sort(null);
        System.out.println("unvisited: " + unvisited);

    }
    
    private static List<DijkstraNode> initUnvisited() {
        List<DijkstraNode> unvisited = new ArrayList<>();
        for (int r = 0; r <= 20; r++) {
            for (int c = 0; c <= 20; c++) {
                DijkstraNode n = new DijkstraNode(r, c);
                n.setCostToArrive(1000000);
                unvisited.add(n);
            }
        }
        return unvisited;
    }
}

class object {
    int num;
    public int getNum(){
        return num;
    }
    
    public void setNum(int n){
        num = n;
    }
    
    public String toString(){
        return "object: NUM- " + num;
    }
}
