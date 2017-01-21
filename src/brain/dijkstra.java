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
import grid.Location;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.scene.Node;

/**
 *
 * @author 18balanagav
 */
public class dijkstra extends BotBrain {

    private static final double INFINITY = Double.POSITIVE_INFINITY;
    List<DijkstraNode> desti;

    DijkstraNode dest;

    @Override
    public void initForRound() {
        this.setName("Your Worst Nightmare-Dijkstra");
        desti = new ArrayList(Arrays.asList(new DijkstraNode(20, 20), new DijkstraNode(0, 20), new DijkstraNode(0, 0), new DijkstraNode(20, 0), new DijkstraNode(0, 0)));
        dest = new DijkstraNode(10,8);
    }

    @Override
    public int chooseAction() {
        return dijkstra(getLocation(), dest, getArena());
    }

    private int dijkstra(DijkstraNode start, DijkstraNode dest, GameObject[][] arena) {
        start.setCostToArrive(0);
        
        start = new DijkstraNode(1, 2);
        
        System.out.println("start: " + start.toString());
        //System.out.println(Arrays.toString(arena));
        List<DijkstraNode> unvisited = initUnvisited(arena, start);
        List<DijkstraNode> visited = new ArrayList<>();
        
        unvisited.sort(null);
        System.out.println("unvisited(0): " + unvisited.get(0).toString());
        System.out.println("unvisited: " + unvisited.toString());
        DijkstraNode current = new DijkstraNode(-1, -1);
        
        while (!visited.contains(dest) || !unvisited.isEmpty() || unvisited.get(0).getCostToArrive() == INFINITY) {
            current = unvisited.remove(0);
            System.out.println("current node: " + current.toString());

            
            for (int dir = 0; dir < 360; dir += 90) {
                DijkstraNode neighbor = getNodeFromUnvisited((DijkstraNode) current.getAdjacentLocation(dir), unvisited);
                System.out.println("currently checking: " + neighbor.toString());
                if (neighbor.isValidLocation()) {
                    double tentativeDistance = current.getCostToArrive() + 1;
                    System.out.println("tentativeDistance: " + tentativeDistance);
                    if (!canMove(neighbor, arena)) {
                        System.out.println("can't move to: " + neighbor.toString());
                        neighbor.setCostToArrive(INFINITY);
                    } else if (tentativeDistance < neighbor.getCostToArrive()) {
                        unvisited.remove(neighbor);
                        neighbor.setCostToArrive(tentativeDistance);
                        neighbor.setPrevious(current);
                        System.out.println(neighbor.toString() + "'s previous location is " + current.toString());
                        unvisited.add(neighbor);
                        System.out.println("updated " + neighbor.toString() + " in unvisited.");
                    }
                    //System.out.println("current node: " + neighbor.toString());
                }
            }
            //System.out.println("adding " + current.toString() + " to visited set.");
            visited.add(current);
            unvisited.sort(null);
            if(unvisited.isEmpty()){
                break;
            }
        }
        
        System.out.println("visited at end: " + visited.toString());
        System.out.println("unvisited at end: " + unvisited.toString());
        return start.getDirectionToward(reconstructPath(start, current).get(0));
    }

    public List<DijkstraNode> reconstructPath(DijkstraNode start, DijkstraNode current) {
        List<DijkstraNode> path = new ArrayList<>();
        DijkstraNode previous = current;
        System.out.println("1st previous: " + previous.toString());
        while (previous.distanceTo(start) != 1) {
            System.out.println("path: " + previous.toString());
            path.add(0, previous);
            previous = (DijkstraNode) previous.getPrevious();
        }
        //System.out.println("trying to move to: " + previous.toString());
        return path;
    }

    private boolean canMove(DijkstraNode target, GameObject[][] arena) {
        return !isBlock(target, arena) && !isBot(target, arena) && target.isValidLocation();
    }

    private boolean isBlock(DijkstraNode loc, GameObject[][] arena) {
        return arena[loc.getRow()][loc.getCol()] instanceof Block;
    }

    private boolean isBot(DijkstraNode loc, GameObject[][] arena) {
        return arena[loc.getRow()][loc.getCol()] instanceof Bot;
    }

    private DijkstraNode getLocation() {
        return new DijkstraNode(getRow(), getCol());
    }

    private DijkstraNode getNodeFromUnvisited(DijkstraNode adj, List<DijkstraNode> unvisited) {
        for (DijkstraNode n : unvisited) {
            if (n.equals(adj)) {
                return n;
            }
        }
        //Should never come to this.
        System.out.println("returned a -1, -1 node for " + adj.toString());
        return new DijkstraNode(-1, -1);
    }

    private List<DijkstraNode> initUnvisited(GameObject[][] arena, DijkstraNode start) {
        System.out.println("within initUnvisited, start: " + start.toString());
        List<DijkstraNode> unvisited = new ArrayList<>();
        for (int r = 0; r <= 4; r++) {
            for (int c = 0; c <= 4; c++) {
                if (r != start.getRow() || c != start.getCol()) {
                    //System.out.println("adding (" + r + ", " + c + ") to unvisited.");
                    DijkstraNode n = new DijkstraNode(r, c);
                    n.setCostToArrive(INFINITY);
                    unvisited.add(n);
                } else{
                    System.out.println("start achieved in initunvisited: " + start.toString());
                    start.setCostToArrive(0);
                    unvisited.add(start);
                }
            }
        }
        System.out.println("finish");
        return unvisited;
    }
}

class DijkstraNode extends brain.A_Star_Node {

    public DijkstraNode(int r, int c) {
        super(r, c);
    }

    @Override
    public int compareTo(Object other) {
        //System.out.println("compare to called.");
        A_Star_Node otherNode = (A_Star_Node) other;
        if (otherNode.getCostToArrive() > getCostToArrive()) {
            return -1;
        } else if (otherNode.getCostToArrive() < getCostToArrive()) {
            return 1;
        } else {
            return 0;
        }
    }
    
    @Override
    public String toString(){
        return "(r:" + getRow() + ", c:" + getCol() + "), distance to arrive: " + getCostToArrive();
    }

    @Override
    public DijkstraNode getAdjacentLocation(int direction) {
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
        return new DijkstraNode(getRow() + dr, getCol() + dc);
    }

}