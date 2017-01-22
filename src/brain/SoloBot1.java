/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package brain;

import actor.BotBrain;
import actor.GameObject;
import java.util.Random;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * This algorithm completes the objective for the single player challenge:
 * to get more than 1.5 million points.
 * @author 18balanagav
 */
public class SoloBot1 extends BotBrain {

    A_Star_Node dest;

    A_star algorithm;
    Random ran;

    ArrayList<A_Star_Node> destinations;

    GameObject[][] arena;

    /**
     * A method that initializes all the necessary instance variables at the start of each round.
     * It functions as a constructor.
     */
    @Override
    public void initForRound() {
        arena = getArena();

        ran = new Random();
        destinations = new ArrayList<>(Arrays.asList(new A_Star_Node(1, 4), new A_Star_Node(1, 1), new A_Star_Node(4, 1), new A_Star_Node(4, 4),
                new A_Star_Node(1, 16), new A_Star_Node(1, 19), new A_Star_Node(4, 19), new A_Star_Node(4, 16),
                new A_Star_Node(19, 16), new A_Star_Node(19, 19), new A_Star_Node(16, 19), new A_Star_Node(16, 16),
                new A_Star_Node(16, 1), new A_Star_Node(19, 1), new A_Star_Node(19, 4), new A_Star_Node(16, 4)));

        algorithm = new A_star();
        dest = destinations.remove(0);
    }

    /**
     * A method that figures out what to do based on the current situation in
     * the game. It uses the A* algorithm to calculate the shortest path.
     * @return 
     */
    @Override
    public int chooseAction() {
        arena = getArena();
        if (getLocation().equals(dest) && !destinations.isEmpty()) {
            do {
                try {
                    dest = destinations.remove(0);
                } catch (IndexOutOfBoundsException ex) {
                    dest = new A_Star_Node(ran.nextInt(6) + 7, ran.nextInt(21));
                }
            } while (algorithm.isBlock(dest, arena));
            return ACQUIRE;
        } else if (getLocation().equals(dest) && destinations.isEmpty()) {
            do {
                dest = new A_Star_Node(ran.nextInt(6) + 7, ran.nextInt(21));
            } while (!dest.isValidLocation() || algorithm.isBlock(dest, arena) || dest.equals(getLocation()));
            return ACQUIRE;
        }
        return algorithm.aStar(getLocation(), dest, arena);
    }

    /**
     * This method returns this bot's location as an A_Star_Node.
     * @return 
     */
    private A_Star_Node getLocation() {
        return new A_Star_Node(getRow(), getCol());
    }
}
