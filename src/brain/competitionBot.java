/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package brain;

import actor.BotBrain;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author 18balanagav
 */
public class competitionBot extends BotBrain {

    List<A_Star_Node> checked = new ArrayList<>();
    A_star algorithm;
    List<A_Star_Node> destinations = new ArrayList<>();

    @Override
    public void initForRound() {
        algorithm = new A_star(this.getColor());
        algorithm.aStar(getLocation(), new A_Star_Node(0, 0), getArena());
        checked = algorithm.getChecked();
        checked.sort(null);
        destinations = removeWalkingDests(getLocation(), checked);
    }

    @Override
    public int chooseAction() {
        return algorithm.aStar(getLocation(), destinations.remove(0), getArena());
    }

    private List<A_Star_Node> removeWalkingDests(A_Star_Node start, List<A_Star_Node> checked) {
        List<A_Star_Node> result = new ArrayList<>();
        for (A_Star_Node dest : checked) {
            if (dest.distanceTo(start) == 3) {
                result.add(dest);
            }
        }
        return result;
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
