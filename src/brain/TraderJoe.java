package brain;

import actor.BotBrain;

/**
 * @author Spock
 * TraderJoe chooses a random move each turn or else chooses to TRADE.
 */
public class TraderJoe extends BotBrain
{
    public TraderJoe()
    {
        setName("TraderJoe");
    }
    
    @Override
    public int chooseAction()
    {        
        //This line chooses from 0,90,180,270 randomly :-)
        int choice = (int)(Math.random()*4)*90;
        
        int extra = (int)(Math.random()*8);
        if(extra==0) 
            choice += 3000; //TRADE in the chosen direction
        
        return choice;
    }
    
}
