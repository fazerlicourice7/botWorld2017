package brain;

import actor.Block;
import actor.Bot;
import actor.BotBrain;
import grid.Location;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

/**
 * @author Jannis JannBot is an A* pathfinder
 */
public class JannBot extends BotBrain {

    int step;
    Location goal;
    ArrayList<Integer> destinations;

    public JannBot() {
        setName("TheLegend27");
    }

    @Override
    public void initForRound() {
        step = 0;
        setUpLocs();
    }

    @Override
    public int chooseAction() {
        if (playersInArena() == 1) {
            return singlePlayerMode();
        } else if (playersInArena() == 2) {
            return oneVsOne();
        } else {
            return battleRoyal();
        }
    }

    private int oneVsOne() {
        int move = 4000;
        {
            for (int row = 0; row < 21; row++) {
                for (int col = 0; col < 21; col++) {
                    //System.out.println("here");
                    if (getArena()[row][col] instanceof Bot) {

                        //System.out.println(row + ", " + col);
                        if (getArena()[row][col].getColor() != this.getColor()) {
                            //System.out.println(this.getLocation().distanceTo(new Location(row, col)));
                            move = AStar(getLocation(), new Location(row, col));
                        }
                    }
                }
            }
            int x = 5;
            int y = 10;
            if (move != 4000) {
                x = getLocation().getAdjacentLocation(move).getRow();
                y = getLocation().getAdjacentLocation(move).getCol();
            }

            if (getArena()[x][y] instanceof Bot) {
                move = 4000;
            }
        }
        return move;
    }

    private int battleRoyal() {
        return 4000;
    }

    private int singlePlayerMode() {
        if (step > 80) {
            return 5000;
        }

        goal = new Location(destinations.get(step), destinations.get(step + 1));

        if (AStar(getLocation(), goal) == 4000) {
            step += 2;
            return 4000;
        }

        return AStar(getLocation(), goal);
    }

    /**
     * This method checks if there are any other players in the arena
     *
     * @return number of bots in the arena
     */
    private int playersInArena() {
        int count = 0;
        for (int row = 0; row < 21; row++) {
            for (int col = 0; col < 21; col++) {
                if (getArena()[row][col] instanceof Bot) {
                    count++;
                }
            }
        }
        return count;
    }

    private void setUpLocs() {
        destinations = new ArrayList(Arrays.asList(4, 1, 1, 1, 1, 4, 4, 4, 4, 8, 1, 8, 1, 10, 1, 12, 4, 12, 1, 16, 1, 19, 4, 19, 4, 16, 8, 19,
                8, 16, 8, 13, 8, 10, 8, 7, 8, 4, 8, 1, 12, 1, 12, 4, 12, 7, 12, 10, 12, 13, 12, 16, 12, 19,
                13, 19, 16, 19, 19, 19, 19, 16, 16, 16, 16, 12, 19, 12, 19, 10, 19, 8, 16, 8, 19, 4, 19, 1,
                16, 1, 16, 4));
    }

    private int AStar(Location start, Location goal) {
        ArrayList<Node> open = new ArrayList<>();
        ArrayList<Node> closed = new ArrayList<>();

        Node goalNode = new Node(goal.getRow(), goal.getCol());
        Node current = new Node(start.getRow(), start.getCol());
        Node startNode = new Node(start.getRow(), start.getCol());

        current.setG(0);
        closed.add(current);

        for (int i = 0; i < 360; i += 90) {
            Node neighbor = current.getAdjacentNode(i);
            neighbor.setGoal(goal);
            neighbor.setParent(current);
            open.add(neighbor);
        }

        while (!open.isEmpty()) {
            current = open.get(lowestNodeIndex(open));

            if (current.equals(goalNode)) {
                return start.getDirectionToward(reconstructPath(current, startNode).get(0));
            }

            if (isWall(current)) {
                open.remove(current);
                current.setG(999);
                continue;
            }

            open.remove(current);
            closed.add(current);

            for (int i = 0; i < 360; i += 90) {
                Node neighbor = current.getAdjacentNode(i);

                if (closed.contains(neighbor) || !current.getAdjacentLocation(i).isValidLocation() || isWall(neighbor)) {
                    neighbor.setG(999);
                    continue;
                }

                int G = current.getG() + 1;

                if (!open.contains(neighbor)) {
                    open.add(neighbor);
                } else if (G >= neighbor.getG()) {
                    continue;
                }

                neighbor.setParent(current);
                neighbor.setGoal(goal);
            }
        }
        return 4000;
    }

    private ArrayList<Node> reconstructPath(Node current, Node start) {
        ArrayList<Node> path = new ArrayList<>();
        while (!current.equals(start)) {
            path.add(0, current);
            current = current.getParent();
        }
        return path;
    }

    private int lowestNodeIndex(ArrayList<Node> open) {
        int lowestIndex = 0;
        for (Node x : open) {
            if (x.getF() < open.get(lowestIndex).getF()) {
                lowestIndex = open.indexOf(x);
            }
        }
        return lowestIndex;
    }

    public Location getLocation() {
        return new Location(getRow(), getCol());
    }

    private boolean isWall(int direction) {
        Location next = getLocation().getAdjacentLocation(direction);
        if (next.isValidLocation()) {
            if (getArena()[next.getRow()][next.getCol()] instanceof Block) {
                return true;
            }
        }
        return false;
    }

    private boolean isWall(Location x) {
        if (x.isValidLocation()) {
            if (getArena()[x.getRow()][x.getCol()] instanceof Block) {
                return true;
            }
        }
        return false;
    }
}

class Node extends Location {

    private int G; //distance from start (parent g + 1)
    private int H; //distance to end (static)
    public Node parent; //previous node

    public Node(int r, int c) {
        super(r, c);
        parent = null;
        H = 0;
        G = 0;
    }

    public int getF() {
        return G + H;
    }  //total cost

    public int getG() {
        return G;
    } //cost to start

    public int getH() {
        return H;
    }   //cost to end

    public Node getParent() {
        return parent;
    }

    public void setG(int g) {
        G += g;
    }

    public void setGoal(Location goal) {
        H += this.distanceTo(goal);
    }

    public void setParent(Node x) {
        parent = x;
        G += x.getG() + 1;
    }

    public Node getAdjacentNode(int dir) {
        Location temp = new Location(this.getRow(), this.getCol());
        temp = temp.getAdjacentLocation(dir);
        Node next = new Node(temp.getRow(), temp.getCol());
        return next;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Node)) {
            return false;
        }

        Node otherLoc = (Node) other;
        return getRow() == otherLoc.getRow() && getCol() == otherLoc.getCol();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.parent);
        return hash;
    }
}
