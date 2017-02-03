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
import grid.Location;
import static grid.Location.EAST;
import static grid.Location.FULL_CIRCLE;
import static grid.Location.HALF_RIGHT;
import static grid.Location.NORTH;
import static grid.Location.NORTHEAST;
import static grid.Location.NORTHWEST;
import static grid.Location.SOUTH;
import static grid.Location.SOUTHEAST;
import static grid.Location.SOUTHWEST;
import static grid.Location.WEST;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * This file contains the logic for my bot for all 3 game modes.
 *
 * @author 18balanagav
 */
public class VamshiBot extends BotBrain {

    A_Star_Node dest;

    A_star algorithm;
    Random ran;

    boolean initializedSinglePlayer = false;

    int notGoingToUse;

    A_Star_Node temp;
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

    A_Star_Node centerTest;

    ArrayList<A_Star_Node> destinations;

    ArrayList<A_Star_Node> topLeft = new ArrayList<>(Arrays.asList(new A_Star_Node(1, 4), new A_Star_Node(1, 1), new A_Star_Node(4, 1), new A_Star_Node(4, 4)));
    ArrayList<A_Star_Node> topCenter = new ArrayList<>(Arrays.asList(new A_Star_Node(4, 8), new A_Star_Node(1, 8), new A_Star_Node(1, 12), new A_Star_Node(4, 12)));
    ArrayList<A_Star_Node> topRight = new ArrayList<>(Arrays.asList(new A_Star_Node(1, 16), new A_Star_Node(1, 19), new A_Star_Node(4, 19), new A_Star_Node(4, 16)));
    ArrayList<A_Star_Node> centerRight = new ArrayList<>(Arrays.asList(new A_Star_Node(8, 16), new A_Star_Node(8, 19), new A_Star_Node(12, 19), new A_Star_Node(12, 16)));
    ArrayList<A_Star_Node> bottomRight = new ArrayList<>(Arrays.asList(new A_Star_Node(19, 16), new A_Star_Node(19, 19), new A_Star_Node(16, 19), new A_Star_Node(16, 16)));
    ArrayList<A_Star_Node> bottomCenter = new ArrayList<>(Arrays.asList(new A_Star_Node(16, 12), new A_Star_Node(19, 12), new A_Star_Node(19, 8), new A_Star_Node(16, 8)));
    ArrayList<A_Star_Node> bottomLeft = new ArrayList<>(Arrays.asList(new A_Star_Node(16, 1), new A_Star_Node(19, 1), new A_Star_Node(19, 4), new A_Star_Node(16, 4)));
    ArrayList<A_Star_Node> centerLeft = new ArrayList<>(Arrays.asList(new A_Star_Node(12, 4), new A_Star_Node(12, 1), new A_Star_Node(8, 1), new A_Star_Node(8, 4)));
    ArrayList<A_Star_Node> centerSquare = new ArrayList<>(Arrays.asList(new A_Star_Node(7, 10), new A_Star_Node(7, 13), new A_Star_Node(10, 13), new A_Star_Node(13, 13), new A_Star_Node(13, 10), new A_Star_Node(13, 7), new A_Star_Node(10, 7), new A_Star_Node(7, 7)));

    GameObject[][] arena;

    /**
     * A method that initializes all the necessary instance variables at the
     * start of each round. It functions as a constructor.
     */
    @Override
    public void initForRound() {
        arena = getArena();

        setName("Your Worst Nightmare");

        ran = new Random();
        destinations = new ArrayList<>();

        centerTest = new A_Star_Node(ran.nextInt(8) + 6, ran.nextInt(8) + 6);
        algorithm = new A_star(getColor());
    }

    /**
     * Initializes the destinations list for single player.
     */
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

    /**
     * Initializes the list of destinations for multiple bots.
     */
    public void initOneVOne() {
        initializedSinglePlayer = false;
        destinations.addAll(centerSquare);
        destinations.addAll(topCenter);
        destinations.addAll(centerRight);
        destinations.addAll(bottomCenter);
        destinations.addAll(centerLeft);
        destinations.addAll(topLeft);
        destinations.addAll(bottomLeft);
        destinations.addAll(bottomRight);
        destinations.addAll(topRight);
    }

    /**
     *
     */
    public void initBattleRoyale() {
        destinations.addAll(centerSquare);
        destinations.addAll(topCenter);
        destinations.addAll(centerRight);
        destinations.addAll(bottomCenter);
        destinations.addAll(centerLeft);
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
            switch (numberOfBots(arena)) {
                case 1: // Single Player
                    SINGLEPLAYER = true;
                    ONEVSONE = false;
                    BATTLEROYALE = false;
                    initSinglePlayer();
                    break;
                case 2: // One V One
                    SINGLEPLAYER = false;
                    ONEVSONE = true;
                    BATTLEROYALE = false;
                    initOneVOne();
                    break;
                default: // Battle Royale
                    SINGLEPLAYER = false;
                    ONEVSONE = false;
                    BATTLEROYALE = true;
                    initBattleRoyale();
                    break;
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

    /**
     * Calculates the best move to make in the situation when I am the only bot
     * in the arena
     *
     * @return
     */
    public int chooseActionSinglePlayer() {

        if (getLocation().equals(dest) && !destinations.isEmpty()) {
            dest = getDestination();
            return ACQUIRE;
        } else if (getLocation().equals(dest) && destinations.isEmpty()) {
            if (acquiredSurrounding(arena, getLocation())) {
                if (!surroundingMaxAged(getLocation(), arena)) {
                    return AGE;
                } else {
                    dest = algorithm.run(getLocation(), ran.nextInt(3) * 90, arena);
                    while (getLocation().distanceTo(dest) < 3) {
                        dest = algorithm.run(getLocation(), ran.nextInt(3) * 90, arena);
                    }
                }
            } else {
                return ACQUIRE;
            }
        }

        int returnValue = algorithm.aStar(getLocation(), dest, arena);
        int counter = 0;
        while (returnValue == -1 && counter < 5) {
            dest = getDestination();
            returnValue = algorithm.aStar(getLocation(), dest, arena);
            counter++;
        }
        if (counter == 5) {
            if (!acquiredSurrounding(arena, getLocation())) {
                return ACQUIRE;
            }
            return AGE;
        }
        return returnValue;
    }

    /**
     * Calculates the best move to make in the situation. With more than one bot
     * in the arena.
     *
     * @return
     */
    public int chooseActionOneVOne() {

        // ======== IF ALL CORNERS BLOCKED ===========
        if (algorithm.isBlock(topRightBlock, arena) && algorithm.isBlock(topLeftBlock, arena)
                && algorithm.isBlock(bottomRightBlock, arena) && algorithm.isBlock(bottomLeftBlock, arena)) {
            BATTLEROYALE = true;
            ONEVSONE = false;
        }

        //========= IF BLOCKED IN ========
        if (algorithm.aStar(getLocation(), centerTest, arena) == -1) {
            setName("Single Player - blocked in");

            if (!acquiredSurrounding(arena, getLocation())) {
                return ACQUIRE;
            } else {
                return AGE;
            }

            /*int sect = getSector(arena, getLocation());
             switch (sect) {
             case 1:
             destinations.addAll(0, topRight);
             break;
             case 2:
             destinations.addAll(0, topLeft);
             break;
             case 3:
             destinations.addAll(0, bottomLeft);
             break;
             case 4:
             destinations.addAll(0, bottomRight);
             break;
             case 5:
             destinations.addAll(0, centerRight);
             break;
             case 6:
             destinations.addAll(0, topCenter);
             break;
             case 7:
             destinations.addAll(0, centerLeft);
             break;
             case 8:
             destinations.addAll(0, bottomCenter);
             break;
             default:
             break;
             }
             dest = getDestination();
             return chooseActionSinglePlayer(); */
        }

        setName("not blocked in");
        //======= BLOCK OTHER BOT IN CORNER ==========
        CORNER = isBotInCorner(arena);

        setName("corner: " + CORNER);
        if ((getLocation().equals(topRightSpot) && dest.equals(topRightSpot) && CORNER == 1)
                || (getLocation().equals(topLeftSpot) && dest.equals(topLeftSpot) && CORNER == 2)) {
            return BLOCK_NORTH;
        } else if ((getLocation().equals(bottomLeftSpot) && dest.equals(bottomLeftSpot) && CORNER == 3)
                || (getLocation().equals(bottomRightSpot) && dest.equals(bottomRightSpot) && CORNER == 4)) {
            return BLOCK_SOUTH;
        }

        temp = dest;
        switch (CORNER) {
            case 1:
                dest = topRightSpot;
                break;
            case 2:
                dest = topLeftSpot;
                break;
            case 3:
                dest = bottomLeftSpot;
                break;
            case 4:
                dest = bottomRightSpot;
                break;
            default:
                break;
        }
        if (!temp.equals(dest)) {
            destinations.add(0, temp);
        }

        //======== GET THE NEXT DESTINATION ============
        if (getLocation().equals(dest) && !destinations.isEmpty()) {
            dest = getDestination();

            if (!acquiredSurrounding(arena, getLocation())) {
                return ACQUIRE;
            }

        } else if (getLocation().equals(dest) && destinations.isEmpty()) {
            if (!acquiredSurrounding(arena, getLocation())) {
                return ACQUIRE;
            }

            temp = dest;
            if (CORNER != 1464) {
                ArrayList<Bot> bots = getBots(arena);
                for (Bot b : bots) {
                    if (b.getColor() != getColor()) {
                        A_Star_Node botLoc = new A_Star_Node(b.getRow(), b.getCol());
                        do {
                            dest = botLoc.getAdjacentLocation(ran.nextInt(3) * 90);
                        } while (!dest.isValidLocation());
                    }
                }
            }

            if (dest.equals(temp) || dest.equals(getLocation())
                    || dest.distanceTo(getLocation()) < 2) {
                return AGE;
            }
        }

        while (algorithm.isBot(dest, arena)) {
            temp = dest;
            dest = getDestination();
            destinations.add(0, temp);
        }

        //======= RUN A*============
        int returnValue = algorithm.aStar(getLocation(), dest, arena);
        int counter = 0;
        while (returnValue == -1 && counter < 5) {
            dest = getDestination();
            returnValue = algorithm.aStar(getLocation(), dest, arena);
            counter++;
        }
        if (counter == 5) {
            if (!acquiredSurrounding(arena, getLocation())) {
                return ACQUIRE;
            }
            return AGE;
        }
        return returnValue;
    }

    /**
     *
     * @return
     */
    public int chooseActionBattleRoyale() {

        // =========== IF BLOCKED IN =========
        if (algorithm.aStar(getLocation(), centerTest, arena) == -1) {
            setName("Single Player - blocked in");
            if (!initializedSinglePlayer) {
                initSinglePlayer();
                initializedSinglePlayer = true;
            }
            dest = getDestination();
            return chooseActionSinglePlayer();
        }

        // ========= BLOCK OTHER BOTS IN =============
        CORNER = isBotInCorner(arena);

        if ((getLocation().equals(topRightSpot) && dest.equals(topRightSpot) && CORNER == 1)
                || (getLocation().equals(topLeftSpot) && dest.equals(topLeftSpot) && CORNER == 2)) {
            return BLOCK_NORTH;
        } else if ((getLocation().equals(bottomLeftSpot) && dest.equals(bottomLeftSpot) && CORNER == 3)
                || (getLocation().equals(bottomRightSpot) && dest.equals(bottomRightSpot) && CORNER == 4)) {
            return BLOCK_SOUTH;
        }
        setName("corner: " + CORNER);
        temp = dest;
        switch (CORNER) {
            case 1:
                dest = topRightSpot;
                break;
            case 2:
                dest = topLeftSpot;
                break;
            case 3:
                dest = bottomLeftSpot;
                break;
            case 4:
                dest = bottomRightSpot;
                break;
            default:
                break;
        }
        if (!temp.equals(dest)) {
            destinations.add(0, temp);
        }
        //======GO TO DESTINATIONS======

        if (getLocation().equals(dest) && !destinations.isEmpty()) {
            dest = getDestination();
            return ACQUIRE;
        } else if (getLocation().equals(dest) && destinations.isEmpty()) {
            initBattleRoyale();
            if (!acquiredSurrounding(arena, getLocation())) {
                return ACQUIRE;
            }
        }

        //===== RUN A* ===========
        int returnValue = algorithm.aStar(getLocation(), dest, arena);
        int counter = 0;
        while (returnValue == -1 && counter < 5) {
            dest = getDestination();
            returnValue = algorithm.aStar(getLocation(), dest, arena);
            counter++;
        }
        if (counter == 5) {
            if (!acquiredSurrounding(arena, getLocation())) {
                return ACQUIRE;
            }
            return AGE;
        }
        return returnValue;
    }

    /**
     * Gets the next value in the destinations list.
     *
     * @return
     */
    public A_Star_Node getDestination() {
        A_Star_Node d = new A_Star_Node(0, 0);
        do {
            try {
                d = destinations.remove(0);
            } catch (IndexOutOfBoundsException ex) {
                d = new A_Star_Node(ran.nextInt(8) + 6, ran.nextInt(8) + 6);
            }
        } while (algorithm.isBlock(d, arena));
        return d;
    }

    public int getSector(GameObject[][] arena, A_Star_Node myLoc) {
        if (myLoc.getRow() < 6 && myLoc.getCol() > 14) {
            return 1;
        } else if (myLoc.getRow() < 6 && myLoc.getCol() < 6) {
            return 2;
        } else if (myLoc.getRow() > 14 && myLoc.getCol() < 6) {
            return 3;
        } else if (myLoc.getRow() > 14 && myLoc.getCol() > 14) {
            return 4;
        } else if (myLoc.getRow() < 14 && myLoc.getRow() > 6 && myLoc.getCol() > 15) {
            return 5;
        } else if (myLoc.getRow() < 5 && myLoc.getCol() > 6 && myLoc.getCol() < 14) {
            return 6;
        } else if (myLoc.getRow() < 14 && myLoc.getRow() > 6 && myLoc.getCol() < 5) {
            return 7;
        } else if (myLoc.getRow() > 15 && myLoc.getCol() > 6 && myLoc.getCol() < 14) {
            return 8;
        } else {
            return 0;
        }
    }

    /**
     *
     * @param arena
     * @param loc
     * @return true if all the surrounding markers are this bot's.
     */
    public boolean acquiredSurrounding(GameObject[][] arena, A_Star_Node loc) {
        for (int dir = 0; dir < 360; dir += 45) {
            if (loc.getAdjacentLocation(dir).isValidLocation()) {
                if (!algorithm.myMarker(loc.getAdjacentLocation(dir), arena)
                        && !algorithm.isBlock(loc.getAdjacentLocation(dir), arena)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Compares the ages of all the markers around this bot and figures out if
     * all the surrounding markers are max Aged.
     *
     * @param loc
     * @param arena
     * @return true if all the surrounding markers are max aged.
     */
    public boolean surroundingMaxAged(A_Star_Node loc, GameObject[][] arena) {
        for (int dir = 0; dir < 360; dir += 45) {
            A_Star_Node adj = loc.getAdjacentLocation(dir);
            if (adj.isValidLocation()) {
                if (algorithm.myMarker(adj, arena)) {
                    Marker m = (Marker) arena[adj.getRow()][adj.getCol()];
                    if (m.getAge() < 1000) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Figures out if a bot is one of the 4 corner quadrants.
     *
     * @param arena
     * @return true if a bot is in one of the corner quadrants.
     */
    public int isBotInCorner(GameObject[][] arena) {
        List<Bot> bots = getBots(arena);
        boolean blockInCorner = false;
        for (Bot b : bots) {
            if (b.getLocation().equals(getLocation())) {
                continue;
            }
            if (b.getRow() < 6 && b.getCol() > 14 && !b.getLocation().equals(topRightBlock)) {
                if (!algorithm.isBlock(topRightBlock, arena)) {
                    return 1;
                } else {
                    blockInCorner = true;
                }
            } else if (b.getRow() < 6 && b.getCol() < 6 && !b.getLocation().equals(topLeftBlock)) {
                if (!algorithm.isBlock(topLeftBlock, arena)) {
                    return 2;
                } else {
                    blockInCorner = true;
                }
            } else if (b.getRow() > 14 && b.getCol() < 6 && !b.getLocation().equals(bottomLeftBlock)) {
                if (!algorithm.isBlock(bottomLeftBlock, arena)) {
                    return 3;
                } else {
                    blockInCorner = true;
                }
            } else if (b.getRow() > 14 && b.getCol() > 14 && !b.getLocation().equals(bottomRightBlock)) {
                if (!algorithm.isBlock(bottomRightBlock, arena)) {
                    return 4;
                } else {
                    blockInCorner = true;
                }
            }
        }
        if (blockInCorner) {
            return 1464;
        }
        return 0;
    }

    /**
     *
     * @param arena
     * @return a list of all the bots in the arena.
     */
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

    /**
     * Looks through the arena and counts the number of bots present (including
     * itself).
     *
     * @param arena
     * @return the number of bots in the arena.
     */
    public int numberOfBots(GameObject[][] arena) {
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

/**
 *
 * @author 18balanagav
 */
class A_star {

    private static final double INFINITY = Double.POSITIVE_INFINITY;
    private static final double DISTANCECOST = 3;

    Color color;

    List<A_Star_Node> checked = new ArrayList<>();

    public A_star(Color color) {
        this.color = color;
    }

    /**
     * An implementation of the a* algorithm that finds the most valuable path
     * from the start node to the destination node. The algorithm takes into
     * account markers and number of steps.
     *
     * @param start
     * @param dest
     * @param arena
     * @return the direction the bot should move this turn to arrive at the
     * destination.
     */
    public int aStar(A_Star_Node start, A_Star_Node dest, GameObject[][] arena) {
        List<A_Star_Node> queue = new ArrayList<>();
        checked = new ArrayList<>();

        start.setCostToDest(start.distanceTo(dest));
        queue.add(start);
        while (!queue.isEmpty()) {
            queue.sort(null);

            A_Star_Node current = queue.remove(0);
            if (alreadyChecked(checked, current)) {
                continue;
            } else {
                checked.add(current);
            }

            if (current.equals(dest)) {
                if (current.getCostToArrive() == INFINITY) {
                    return -1;
                }
                return reconstructPath(start, current);
            }

            for (int dir = 0; dir < 360; dir += 90) {
                A_Star_Node temp = current.getAdjacentLocation(dir);
                if (!temp.isValidLocation()) {
                    continue;
                }
                temp.setPrevious(current);

                if (canMove(temp, arena)) {
                    temp.setCostToDest(temp.distanceTo(dest));
                    temp.setCostToArrive(getPathCost(temp, current, arena));
                } else {
                    temp.setCostToDest(INFINITY);
                    temp.setCostToArrive(INFINITY);
                }

                queue.add(temp);

                //===========Run in direction 'dir'===========
                temp = run(current, dir, arena);
                if (checked.contains(temp)) {
                    continue;
                }
                temp.setPrevious(current);
                temp.setCostToArrive(getRunningPathCost(temp, current, arena));
                temp.setCostToDest(temp.distanceTo(dest));

                queue.add(temp);

            }
        }
        return -1;
    }

    /**
     *
     * @return the list of checked nodes.
     */
    public List<A_Star_Node> getChecked() {
        return checked;
    }

    /**
     * Reconstructs the path from the destination to the start node following
     * the parent node all the way back to the start node.
     *
     * @param start
     * @param current
     * @return
     */
    public int reconstructPath(A_Star_Node start, A_Star_Node current) {
        A_Star_Node previous = current;
        try {
            while (!previous.getPrevious().equals(start)) {
                previous = previous.getPrevious();
            }
        } catch (NullPointerException n) {

        }

        if (start.distanceTo(previous) > 1) {
            return start.getDirectionToward(previous) + 1000; // runs in the specified direction
        }
        return start.getDirectionToward(previous);
    }

    /**
     *
     * @param start
     * @param dir
     * @param arena
     * @return the location that the bot would end up at if it ran in the given
     * direction.
     */
    public A_Star_Node run(A_Star_Node start, int dir, GameObject[][] arena) {
        for (int i = 0; canMoveAndisValidLocation(start.getAdjacentLocation(dir), arena) && i < 3; i++) {
            start = start.getAdjacentLocation(dir);
        }
        return start;
    }

    /**
     * Calculate the cost of moving to the given Node from the previous node.
     *
     * @param current
     * @param previous
     * @param arena
     * @return
     */
    public double getPathCost(A_Star_Node current, A_Star_Node previous, GameObject[][] arena) {
        double distance = previous.getCostToArrive() + DISTANCECOST;
        double markerValue = calculateMarkerValue(current, arena, -3);
        return distance + markerValue;
    }

    /**
     * Calculate the marker value of running from current to previous.
     *
     * @param current
     * @param previous
     * @param arena
     * @return
     */
    public double getRunningPathCost(A_Star_Node current, A_Star_Node previous, GameObject[][] arena) {
        A_Star_Node[] locations = getLocsInBetween(current, previous);
        double distance = previous.getCostToArrive() + DISTANCECOST;
        double markerValue = 0;

        int size = locations.length;
        int locCounter = 0;
        while (locCounter < size - 1) {
            //Don't reduce cost if the 2 markers that get run over are empty.
            markerValue += calculateMarkerValue(locations[locCounter], arena, 0);
            locCounter++;
        }
        //Reduce cost if the marker we land on is empty.
        markerValue += calculateMarkerValue(locations[locCounter], arena, -3);
        return distance + markerValue;
    }

    /**
     * Calculates the value of walking over a given location. Uses a custom
     * algorithm.
     *
     * @param current
     * @param arena
     * @param emptyLocValue
     * @return
     */
    public double calculateMarkerValue(A_Star_Node current, GameObject[][] arena, double emptyLocValue) {
        if (!isMarker(current, arena)) {
            return emptyLocValue;
        } else {
            Marker m = (Marker) arena[current.getRow()][current.getCol()];

            if (myMarker(current, arena)) {
                return +4 + (m.getAge() / 250);
            } else {
                return -4 - (m.getAge() / 250);
            }
        }
    }

    /**
     *
     * @param loc
     * @param arena
     * @return true if the given node is a marker.
     */
    public boolean isMarker(A_Star_Node loc, GameObject[][] arena) {
        return arena[loc.getRow()][loc.getCol()] instanceof Marker;
    }

    /**
     *
     * @param loc
     * @param arena
     * @return true if the specified node is a marker of the same color as my
     * bot.
     */
    public boolean myMarker(A_Star_Node loc, GameObject[][] arena) {
        if (isMarker(loc, arena)) {
            Marker marker = (Marker) arena[loc.getRow()][loc.getCol()];
            if (marker.getColor().equals(this.color)) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param start
     * @param end
     * @return an array of all the locations between start and end.
     */
    public A_Star_Node[] getLocsInBetween(A_Star_Node start, A_Star_Node end) {
        A_Star_Node[] locations = null;
        if (start.getRow() == end.getRow() || start.getCol() == end.getCol()) {
            locations = new A_Star_Node[start.distanceTo(end)];
            int dir = start.getDirectionToward(end);
            int counter = 0;
            locations[counter] = start.getAdjacentLocation(dir);
            while (counter < start.distanceTo(end) - 1) {
                counter++;
                locations[counter] = locations[counter - 1].getAdjacentLocation(dir);
            }
        }
        return locations;
    }

    /**
     * Finds out if the target node has been checked already.
     *
     * @param checked
     * @param target
     * @return true if the target node is in the given list, false otherwise
     */
    public boolean alreadyChecked(List<A_Star_Node> checked, A_Star_Node target) {
        for (A_Star_Node n : checked) {
            if (n.equals(target)) {
                return true;
            }
        }
        return false;
    }

    /**
     * This calls the canMove() function only after checking if the node is on
     * the grid.
     *
     * @param target
     * @param arena
     * @return whether or not the target node is a node on which I can be ie. it
     * is not a block or a bot and it is within the grid.
     */
    public boolean canMoveAndisValidLocation(A_Star_Node target, GameObject[][] arena) {
        if (target.isValidLocation()) { // can't call canMove with an invalid location.
            if (canMove(target, arena)) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param target
     * @param arena
     * @return whether or not the target node is a node on which I can be ie. it
     * is not a block or a bot and it is within the grid.
     */
    public boolean canMove(A_Star_Node target, GameObject[][] arena) {
        return !isBlock(target, arena) && !isBot(target, arena) && target.isValidLocation();
    }

    /**
     *
     * @param loc
     * @param arena
     * @return whether or not the given node is a block.
     */
    public boolean isBlock(A_Star_Node loc, GameObject[][] arena) {
        return arena[loc.getRow()][loc.getCol()] instanceof Block;
    }

    /**
     *
     * @param loc
     * @param arena
     * @return whether or not the given node is a bot.
     */
    public boolean isBot(A_Star_Node loc, GameObject[][] arena) {
        return arena[loc.getRow()][loc.getCol()] instanceof Bot;
    }
}

class A_Star_Node extends Location {

    double costToArrive;
    double heuristicToDest;
    private A_Star_Node previous;

    public A_Star_Node(int r, int c) {
        super(r, c);
    }

    /**
     *
     * @return the cost to arrive at this node.
     */
    public double getCostToArrive() {
        return costToArrive;
    }

    /**
     * Sets the cost to arrive at this node to s.
     *
     * @param s
     */
    public void setCostToArrive(double s) {
        costToArrive = s;
    }

    /**
     * Sets the previous/ parent node to the node p.
     *
     * @param p
     */
    public void setPrevious(A_Star_Node p) {
        previous = p;
    }

    /**
     *
     * @return the parent Node, which is the node that the bot was at before it
     * came to this node.
     */
    public A_Star_Node getPrevious() {
        return previous;
    }

    /**
     * Sets the cost to the destination as the value c.
     *
     * @param c
     */
    public void setCostToDest(double c) {
        heuristicToDest = c;
    }

    /**
     *
     * @return The heuristic cost to the destination
     */
    public double getCostToDest() {
        return heuristicToDest;
    }

    /**
     * Compares this Node to <code>other</code> for ordering. Returns a negative
     * integer, zero, or a positive integer as this location is less than, equal
     * to, or greater than <code>other</code>. Nodes are ordered in ascending
     * order of totalCost. <br />
     * (Precondition: <code>other</code> is a <code>Location</code> object.)
     *
     * @param other the other location to test
     * @return a negative integer if this location is less than
     * <code>other</code>, zero if the two locations are equal, or a positive
     * integer if this location is greater than <code>other</code>
     */
    @Override
    public int compareTo(Object other) {
        A_Star_Node otherNode = (A_Star_Node) other;
        if (otherNode.getCostToArrive() + otherNode.getCostToDest() > costToArrive + heuristicToDest) {
            return -1;
        } else if (otherNode.getCostToArrive() + otherNode.getCostToDest() < costToArrive + heuristicToDest) {
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * Gets the adjacent location in any one of the eight compass directions.
     *
     * @param direction the direction in which to find a neighbor location
     * @return the adjacent location in the direction that is closest to
     * <tt>direction</tt>
     */
    @Override
    public A_Star_Node getAdjacentLocation(int direction) {
        // reduce mod 360 and round to closest multiple of 45
        int adjustedDirection = (direction + HALF_RIGHT / 2) % FULL_CIRCLE;
        if (adjustedDirection < 0) {
            adjustedDirection += FULL_CIRCLE;
        }

        adjustedDirection = (adjustedDirection / HALF_RIGHT) * HALF_RIGHT;
        int dc = 0;
        int dr = 0;
        switch (adjustedDirection) {
            case EAST:
                dc = 1;
                break;
            case SOUTHEAST:
                dc = 1;
                dr = 1;
                break;
            case SOUTH:
                dr = 1;
                break;
            case SOUTHWEST:
                dc = -1;
                dr = 1;
                break;
            case WEST:
                dc = -1;
                break;
            case NORTHWEST:
                dc = -1;
                dr = -1;
                break;
            case NORTH:
                dr = -1;
                break;
            case NORTHEAST:
                dc = 1;
                dr = -1;
                break;
            default:
                break;
        }
        return new A_Star_Node(getRow() + dr, getCol() + dc);
    }
}
