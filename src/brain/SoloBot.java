/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package brain;

import actor.BotBrain;
import java.util.Random;
import brain.A_star;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author 18balanagav
 */
public class SoloBot extends BotBrain {

    A_Star_Node dest;

    A_star algorithm;
    Random ran;

    ArrayList<A_Star_Node> destinations;
    
    @Override
    public void initForRound() {
        ran = new Random();
        destinations =  new ArrayList<>(Arrays.asList(new A_Star_Node(1, 4), new A_Star_Node(1, 1), new A_Star_Node(4, 1), new A_Star_Node(4, 4),
            new A_Star_Node(1, 16), new A_Star_Node(1, 19), new A_Star_Node(4, 19), new A_Star_Node(4, 16),
            new A_Star_Node(19, 16), new A_Star_Node(19, 19), new A_Star_Node(16, 19), new A_Star_Node(16, 16),
            new A_Star_Node(16, 1), new A_Star_Node(19, 1), new A_Star_Node(19, 4), new A_Star_Node(16, 4)));

        algorithm = new A_star();
        dest = destinations.remove(0);
    }

    @Override
    public int chooseAction() {
        if (getLocation().equals(dest) && !destinations.isEmpty()) {
            dest = destinations.remove(0);
            return ACQUIRE;
        } else if (getLocation().equals(dest) && destinations.isEmpty()){
            dest = new A_Star_Node(ran.nextInt(6) + 7, ran.nextInt(21));
            while(!dest.isValidLocation() && !algorithm.canMove(dest, getArena()) && !dest.equals(getLocation())){
                dest = new A_Star_Node(ran.nextInt(6) + 7, ran.nextInt(21));
            }
        }
        return algorithm.aStar(getLocation(), dest, getArena());
    }

    private A_Star_Node getLocation() {
        return new A_Star_Node(getRow(), getCol());
    }
}