/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package brain;

import actor.Block;
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

    int WAITFORCORNER = 0; // number of moves I wait for a bot to move from one of 
    // the corner locations, if it doesn't move after 10 
    //moves, I move on.

    static int CORNER = 0;
    static boolean SINGLEPLAYER = false, ONEVSONE = false, BATTLEROYALE = false;

    static A_Star_Node topRightBlock = new A_Star_Node(5, 15);
    static A_Star_Node topLeftBlock = new A_Star_Node(5, 5);
    static A_Star_Node bottomRightBlock = new A_Star_Node(15, 15);
    static A_Star_Node bottomLeftBlock = new A_Star_Node(15, 5);

    static A_Star_Node topRightSpot = new A_Star_Node(6, 15);
    static A_Star_Node topLeftSpot = new A_Star_Node(6, 5);
    static A_Star_Node bottomLeftSpot = new A_Star_Node(14, 5);
    static A_Star_Node bottomRightSpot = new A_Star_Node(14, 15);

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

        /*if (numberOfBots(arena) == 1) {
         SINGLEPLAYER = true;
         ONEVSONE = false;
         BATTLEROYALE = false;
         initSinglePlayer();
         } else if (numberOfBots(arena) == 2) {
         SINGLEPLAYER = false;
         ONEVSONE = true;
         BATTLEROYALE = false;
         initOneVOne();
         } else {
         SINGLEPLAYER = false;
         ONEVSONE = false;
         BATTLEROYALE = true;
         initBattleRoyale();
         }
         SINGLEPLAYER = true;
         initSinglePlayer();*/
        algorithm = new A_star(getColor());
        //dest = destinations.remove(0);
    }

    public void initSinglePlayer() {
        destinations.addAll(centerSquare);
        destinations.addAll(topLeft);
        destinations.addAll(topCenter);
        destinations.addAll(topRight);
        destinations.addAll(centerRight);
        destinations.addAll(bottomRight);
        destinations.addAll(bottomCenter);
        destinations.addAll(bottomLeft);
        destinations.addAll(centerLeft);
    }

    public void initOneVOne() {
        destinations.addAll(centerSquare);
        destinations.addAll(centerLeft);
        destinations.addAll(bottomCenter);
        destinations.addAll(centerRight);
        destinations.addAll(topCenter);
        destinations.addAll(topLeft);
        destinations.addAll(bottomLeft);
        destinations.addAll(bottomRight);
        destinations.addAll(topRight);

    }

    public void initBattleRoyale() {

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
        if (getMoveNumber() == 1) {
            if (numberOfBots(arena) == 1) {
                SINGLEPLAYER = true;
                ONEVSONE = false;
                BATTLEROYALE = false;
                initSinglePlayer();
            } else if (numberOfBots(arena) > 1) { //========FIX THIS LATER!!!!
                SINGLEPLAYER = false;
                ONEVSONE = true;
                BATTLEROYALE = false;
                initOneVOne();
            } else {
                SINGLEPLAYER = false;
                ONEVSONE = false;
                BATTLEROYALE = true;
                initBattleRoyale();
            }
            dest = destinations.remove(0);
        }

        arena = getArena();

        if (SINGLEPLAYER) {
            return chooseActionSinglePlayer();
        } else if (ONEVSONE) {
            return chooseActionOneVOne();
        } else if (BATTLEROYALE) {
            return chooseActionBattleRoyale();
        } else {
            return -1;
        }
    }

    public int chooseActionSinglePlayer() {
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
                    return AGE;
                } else {
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

    public int chooseActionOneVOne() {
        CORNER = isBotInCorner(arena);
        RESTART: // All the code relating to blocking a bot in is inside this block
        {        // if it gets run again, with a CORNER value that is not 1,2,3 or 4
            // none of this code will be executed, and the program will continue
            // to find a new destination.
            if ((getLocation().equals(topRightSpot) && CORNER == 1)
                    || (getLocation().equals(topLeftSpot) && CORNER == 2)) {
                System.out.println("putting a block north");
                if (algorithm.isBot(topRightBlock, arena) || algorithm.isBot(topLeftBlock, arena)) {
                    if (WAITFORCORNER == 10) {
                        WAITFORCORNER = 0;
                        // set corner = 0 and restart this if/ else if block.
                        // will skip all the if statements about corner trapping.
                        CORNER = 0;
                        break RESTART;
                    } else {
                        WAITFORCORNER++;
                    }
                }
                return BLOCK_NORTH;
            } else if ((getLocation().equals(bottomRightSpot) && CORNER == 4)
                    || (getLocation().equals(bottomLeftSpot) && CORNER == 3)) {
                System.out.println("putting a block south");
                if (algorithm.isBot(bottomRightBlock, arena)
                        || algorithm.isBot(bottomLeftBlock, arena)) {
                    if (WAITFORCORNER == 10) {
                        WAITFORCORNER = 0;
                        // set corner = 0 and restart this if/ else if block.
                        // will skip all the if statements about corner trapping.
                        CORNER = 0;
                        break RESTART;
                    } else {
                        WAITFORCORNER++;
                    }
                }
                return BLOCK_SOUTH;
            }
            if (CORNER == 1) {
                dest = topRightSpot;
            } else if (CORNER == 2) {
                dest = topLeftSpot;
            } else if (CORNER == 3) {
                dest = bottomLeftSpot;
            } else if (CORNER == 4) {
                dest = bottomRightSpot;
            }
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
        }

        if (algorithm.isBot(dest, arena)) {
            A_Star_Node temp = dest;
            dest = destinations.remove(0);
            destinations.add(0, temp);
        }

        int returnValue = algorithm.aStar(getLocation(), dest, arena);
        if(returnValue == -1){
            //===FIGURE OUT HOW TO FIND A NEW DESTINATION
        }
        return returnValue;
    }

    public int chooseActionBattleRoyale() {
        return 0;
    }

    public boolean acquiredSurrounding(GameObject[][] arena, A_Star_Node loc) {
        for (int dir = 0; dir < 360; dir += 45) {
            if (!algorithm.myMarker(loc.getAdjacentLocation(dir), arena)
                    && !algorithm.isBlock(loc.getAdjacentLocation(dir), arena)) {
                return false;
            }
        }
        return true;
    }

    public boolean surroundingMaxAged(A_Star_Node loc, GameObject[][] arena) {
        for (int dir = 0; dir < 360; dir += 45) {
            A_Star_Node adj = loc.getAdjacentLocation(dir);
            if (algorithm.myMarker(adj, arena)) {
                Marker m = (Marker) arena[adj.getRow()][adj.getCol()];
                if (m.getAge() < 1000) {
                    return false;
                }
            }
        }
        return true;
    }

    public int isBotInCorner(GameObject[][] arena) {
        List<Bot> bots = getBots(arena);
        for (Bot b : bots) {
            if (b.getRow() < 6 && b.getCol() > 14
                    && !algorithm.isBlock(new A_Star_Node(5, 15), arena)) {
                return 1;
            } else if (b.getRow() < 6 && b.getCol() < 6
                    && !algorithm.isBlock(new A_Star_Node(5, 5), arena)) {
                return 2;
            } else if (b.getRow() > 14 && b.getCol() < 6
                    && !algorithm.isBlock(new A_Star_Node(15, 5), arena)) {
                return 3;
            } else if (b.getRow() > 14 && b.getCol() > 14
                    && !algorithm.isBlock(new A_Star_Node(15, 15), arena)) {
                return 4;
            }
        }
        return 0;
    }

    public ArrayList<Bot> getBots(GameObject[][] arena) {
        ArrayList<Bot> bots = new ArrayList<>();
        for (int r = 0; r < 21; r++) {
            for (int c = 0; c < 21; c++) {
                if (arena[r][c] instanceof Bot) {
                    bots.add((Bot) arena[r][c]);
                }
            }
        }
        return bots;
    }

    public int numberOfBots(GameObject[][] arena) {
        int counter = 0;
        return getBots(arena).size();
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
