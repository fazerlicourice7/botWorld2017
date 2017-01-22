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
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

/**
 *
 * @author 18balanagav
 */
public class A_star {

    private static final double INFINITY = Double.POSITIVE_INFINITY;

    int blockCounter;
    Color color;

    public A_star(Color color) {
        this.color = color;
    }

    public int aStar(A_Star_Node start, A_Star_Node dest, GameObject[][] arena) {
        List<A_Star_Node> queue = new ArrayList<>();
        List<A_Star_Node> checked = new ArrayList<>();

        blockCounter = 0;

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
                return reconstructPath(start, current);
            }

            for (int dir = 0; dir < 360; dir += 90) {
                A_Star_Node temp;
                if (current.getAdjacentLocation(dir).isValidLocation()) {
                    temp = current.getAdjacentLocation(dir);
                } else {
                    continue;
                }
                temp.setPrevious(current);
                temp.setCostToArrive(getPathCost(temp, current, arena));

                if (canMove(temp, arena)) {
                    temp.setCostToDest(temp.distanceTo(dest));
                } else {
                    temp.setCostToDest(INFINITY);
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
            blockCounter++;
        }
        return -1;
    }

    public int reconstructPath(A_Star_Node start, A_Star_Node current) {
        A_Star_Node previous = current;
        //System.out.println("dest: " + current.toString());
        try {
            while (!previous.getPrevious().equals(start)) {
                //System.out.println("path: " + previous.toString());
                previous = previous.getPrevious();
            }
        } catch (NullPointerException n) {
            System.out.println("start: " + start.toString());
            System.out.println("previous: " + previous.toString());
        }
        //System.out.println("trying to move to: " + previous.toString());
        if (start.distanceTo(previous) > 1) {
            return start.getDirectionToward(previous) + 1000; // runs in the specified direction
        }
        return start.getDirectionToward(previous);
    }

    public A_Star_Node run(A_Star_Node start, int dir, GameObject[][] arena) {
        for (int i = 0; canMoveAndisValidLocation(start.getAdjacentLocation(dir), arena) && i < 3; i++) {
            start = start.getAdjacentLocation(dir);
        }
        return start;
    }

    public double getPathCost(A_Star_Node current, A_Star_Node previous, GameObject[][] arena) {
        double distance = previous.getCostToArrive() + 1;
        double markerValue = calculateMarkerValue(current, arena);
        return distance + markerValue;
    }
    
    public double getRunningPathCost(A_Star_Node current, A_Star_Node previous, GameObject[][] arena) {
        A_Star_Node[] locations = getLocsInBetween(current, previous);
        double distance = previous.getCostToArrive() + 1;
        double markerValue = 0;
        
        int size = locations.length;
        for(int locationCounter = 0; locationCounter < size; locationCounter++){
            markerValue += calculateMarkerValue(locations[locationCounter], arena);
        }
        return distance + markerValue;
    }
    
    public double calculateMarkerValue(A_Star_Node current, GameObject[][] arena){ /////===ADD LARGER VALUE TO MARKERS, MARKERS ARE MORE COSTLY/VALUABLE THAN PHYSICAL MOVES
        if (!isMarker(current, arena)) {
            return 0;
        } else if (myMarker(current, arena)) {
            return +2;
        } else {
            return -2;
        }
    }

    public boolean isMarker(A_Star_Node loc, GameObject[][] arena) {
        return arena[loc.getRow()][loc.getCol()] instanceof Marker;
    }

    public boolean myMarker(A_Star_Node loc, GameObject[][] arena) {
        if (isMarker(loc, arena)) {
            Marker marker = (Marker) arena[loc.getRow()][loc.getCol()];
            if (marker.getColor().equals(this.color)) {
                return true;
            }
        }
        return false;
    }

    public A_Star_Node[] getLocsInBetween(A_Star_Node start, A_Star_Node end) {
        A_Star_Node[] locations = null;
        if (start.getRow() == end.getRow() || start.getCol() == end.getCol()) {
            locations = new A_Star_Node[start.distanceTo(end)];
            int dir = start.getDirectionToward(end);
            int counter = 0;
            locations[counter] = start.getAdjacentLocation(dir);
            while(counter  < start.distanceTo(end) - 1){
                counter++;
                locations[counter] = locations[counter - 1].getAdjacentLocation(dir);
            }
        }
        return locations;
    }

    public boolean alreadyChecked(List<A_Star_Node> checked, A_Star_Node target) {
        for (A_Star_Node n : checked) {
            if (n.equals(target)) {
                return true;
            }
        }
        return false;
    }

    public double basicHeuristic(A_Star_Node current, A_Star_Node dest) {
        int deltaRow = Math.abs(current.getRow() - dest.getRow());
        int deltaCol = Math.abs(current.getCol() - dest.getCol());
        return pythagorean(deltaRow, deltaCol);
    }

    public double pythagorean(int arm1, int arm2) {
        return Math.sqrt(Math.pow(arm1, 2) + Math.pow(arm2, 2));
    }

    public double invPythagorean(int hypotenuse, int arm) {
        return Math.sqrt(Math.pow(hypotenuse, 2) - Math.pow(arm, 2));
    }

    public boolean canMoveAndisValidLocation(A_Star_Node target, GameObject[][] arena) {
        if (target.isValidLocation()) { // can't call canMove with an invalid location.
            if (canMove(target, arena)) {
                return true;
            }
        }
        return false;
    }

    public boolean canMove(A_Star_Node target, GameObject[][] arena) {
        return !isBlock(target, arena) && !isBot(target, arena) && target.isValidLocation();
    }

    public boolean isBlock(A_Star_Node loc, GameObject[][] arena) {
        //System.out.println("loc: " + loc.toString());
        //System.out.println("arena: " + arena.toString());
        return arena[loc.getRow()][loc.getCol()] instanceof Block;
    }

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

    public double getCostToArrive() {
        return costToArrive;
    }

    public void setCostToArrive(double s) {
        costToArrive = s;
    }

    public void setPrevious(A_Star_Node p) {
        previous = p;
    }

    public A_Star_Node getPrevious() {
        return previous;
    }

    public void setCostToDest(double c) {
        heuristicToDest = c;
    }

    public double getCostToDest() {
        return heuristicToDest;
    }

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
        if (adjustedDirection == EAST) {
            dc = 1;
        } else if (adjustedDirection == SOUTHEAST) {
            dc = 1;
            dr = 1;
        } else if (adjustedDirection == SOUTH) {
            dr = 1;
        } else if (adjustedDirection == SOUTHWEST) {
            dc = -1;
            dr = 1;
        } else if (adjustedDirection == WEST) {
            dc = -1;
        } else if (adjustedDirection == NORTHWEST) {
            dc = -1;
            dr = -1;
        } else if (adjustedDirection == NORTH) {
            dr = -1;
        } else if (adjustedDirection == NORTHEAST) {
            dc = 1;
            dr = -1;
        }
        return new A_Star_Node(getRow() + dr, getCol() + dc);
    }
}
