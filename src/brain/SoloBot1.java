/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package brain;

import actor.Bot;
import actor.BotBrain;
import actor.GameObject;
import actor.Marker;
import java.util.Random;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This algorithm completes the objective for the single player challenge: to
 * get more than 1.5 million points.
 *
 * @author 18balanagav
 */
public class SoloBot1 extends BotBrain {

    A_Star_Node dest;

    A_star algorithm;
    Random ran;

    ArrayList<A_Star_Node> destinations;

    ArrayList<A_Star_Node> topLeft;
    ArrayList<A_Star_Node> topCenter;
    ArrayList<A_Star_Node> topRight;
    ArrayList<A_Star_Node> centerRight;
    ArrayList<A_Star_Node> bottomRight;
    ArrayList<A_Star_Node> bottomCenter;
    ArrayList<A_Star_Node> bottomLeft;
    ArrayList<A_Star_Node> centerLeft;
    ArrayList<A_Star_Node> centerSquare;

    GameObject[][] arena;

    List<Integer> ACTIONS = new ArrayList<>(Arrays.asList(ACQUIRE, AGE, AGE));
    List<Integer> actions = new ArrayList<>();

    /**
     * A method that initializes all the necessary instance variables at the
     * start of each round. It functions as a constructor.
     */
    @Override
    public void initForRound() {
        arena = getArena();

        ran = new Random();
        destinations = new ArrayList<>();

        topLeft = new ArrayList<>(Arrays.asList(new A_Star_Node(1, 4), new A_Star_Node(1, 1), new A_Star_Node(4, 1), new A_Star_Node(4, 4)));
        topCenter = new ArrayList<>(Arrays.asList(new A_Star_Node(4, 8), new A_Star_Node(1, 8), new A_Star_Node(1, 12), new A_Star_Node(4, 12)));
        topRight = new ArrayList<>(Arrays.asList(new A_Star_Node(1, 16), new A_Star_Node(1, 19), new A_Star_Node(4, 19), new A_Star_Node(4, 16)));
        centerRight = new ArrayList<>(Arrays.asList(new A_Star_Node(8, 16), new A_Star_Node(8, 19), new A_Star_Node(12, 19), new A_Star_Node(12, 16)));
        bottomRight = new ArrayList<>(Arrays.asList(new A_Star_Node(19, 16), new A_Star_Node(19, 19), new A_Star_Node(16, 19), new A_Star_Node(16, 16)));
        bottomCenter = new ArrayList<>(Arrays.asList(new A_Star_Node(16, 12), new A_Star_Node(19, 12), new A_Star_Node(19, 8), new A_Star_Node(16, 8)));
        bottomLeft = new ArrayList<>(Arrays.asList(new A_Star_Node(16, 1), new A_Star_Node(19, 1), new A_Star_Node(19, 4), new A_Star_Node(16, 4)));
        centerLeft = new ArrayList<>(Arrays.asList(new A_Star_Node(12, 4), new A_Star_Node(12, 1), new A_Star_Node(8, 1), new A_Star_Node(8, 4)));
        centerSquare = new ArrayList<>(Arrays.asList(new A_Star_Node(7, 10), new A_Star_Node(7, 13), new A_Star_Node(10, 13), new A_Star_Node(13, 13), new A_Star_Node(13, 10), new A_Star_Node(13, 7), new A_Star_Node(10, 7), new A_Star_Node(7, 7)));

        destinations.addAll(centerSquare);
        destinations.addAll(topLeft);
        destinations.addAll(topCenter);
        destinations.addAll(topRight);
        destinations.addAll(centerRight);
        destinations.addAll(bottomRight);
        destinations.addAll(bottomCenter);
        destinations.addAll(bottomLeft);
        destinations.addAll(centerLeft);

        algorithm = new A_star(getColor());
        dest = destinations.remove(0);
    }

    /**
     * A method that figures out what to do based on the current situation in
     * the game. It uses the A* algorithm to calculate the shortest path.
     *
     * @return
     */
    @Override
    public int chooseAction() {
        arena = getArena();

        if (numberOfBots(arena) == 1) {
            return singlePlayerChooseAction();
        } else {
            return -1;
        }
    }

    public int singlePlayerChooseAction() {
        if (actions.size() > 0) {
            return actions.remove(0);
        }
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
            if (acquiredSurrounding(arena, getLocation())) {
                if (!surroundingMaxAged(getLocation(), arena)) {
                    //System.out.println("aging");
                    return AGE;
                } else {
                    System.out.println("max aged");
                    A_Star_Node possibleDest = algorithm.run(getLocation(), 90, arena);
                    if (possibleDest.distanceTo(getLocation()) == 3) {
                        dest = possibleDest;
                        return 1090;
                    } else {
                        dest = algorithm.run(getLocation(), 0, arena);
                        return 1270;
                    }
                }
            } else {
                return ACQUIRE;
            }
        }

        return algorithm.aStar(getLocation(), dest, arena);
    }

    public boolean acquiredSurrounding(GameObject[][] arena, A_Star_Node loc) {
        for (int dir = 0; dir < 360; dir += 45) {
            if (!algorithm.myMarker(loc.getAdjacentLocation(dir), arena) && !algorithm.isBlock(loc.getAdjacentLocation(dir), arena)) {
                return false;
            }
        }
        return true;
    }

    public boolean surroundingMaxAged(A_Star_Node loc, GameObject[][] arena) {
        for (int dir = 0; dir < 360; dir += 45) {
            if (algorithm.myMarker(loc.getAdjacentLocation(dir), arena)) {
                Marker m = (Marker) arena[loc.getAdjacentLocation(dir).getRow()][loc.getAdjacentLocation(dir).getCol()];
                if (m.getAge() < 1000) {
                    return false;
                }
            }
        }
        return true;
    }

    public int numberOfBots(GameObject[][] arena) {
        int counter = 0;
        for (int r = 0; r < 21; r++) {
            for (int c = 0; c < 21; c++) {
                if (arena[r][c] instanceof Bot) {
                    counter++;
                }
            }
        }
        return counter;
    }

    /**
     * This method returns this bot's location as an A_Star_Node.
     *
     * @return
     */
    private A_Star_Node getLocation() {
        return new A_Star_Node(getRow(), getCol());
    }
}
