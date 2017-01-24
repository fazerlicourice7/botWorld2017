/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package brain;

import actor.BotBrain;

/**
 *
 * @author 18balanagav
 */
public class testBot extends BotBrain {

    A_star algorithm;
    A_Star_Node dest;
    
    @Override
    public void initForRound() {
        dest = new A_Star_Node(12, 8);
        algorithm = new A_star(getColor());
    }

    @Override
    public int chooseAction() {
        return algorithm.aStar(getLocation(), dest, getArena());
    }
    
    private A_Star_Node getLocation() {
        return new A_Star_Node(getRow(), getCol());
    }
}
