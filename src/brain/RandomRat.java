package brain;

import actor.BotBrain;
/**
 * @author Spock
 * RandomRat chooses a random move each turn.
 */
public class RandomRat extends BotBrain
{    
    public RandomRat()
    {
        setName("RandomRat");
    }
    
    @Override
    public int chooseAction()
    {        
        int direction = (int)(Math.random()*4);
        
        if(direction == 0) return MOVE_NORTH;        
        if(direction == 1) return MOVE_EAST;        
        if(direction == 2) return MOVE_SOUTH;        
        if(direction == 3) return MOVE_WEST;        
        
        return REST;
    }
    
}
