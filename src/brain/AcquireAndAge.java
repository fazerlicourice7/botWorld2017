package brain;

import actor.BotBrain;
/**
 * @author Spock
 * AcquireAndAge chooses a random move each turn (similar to RandomRat).
 * However, this Bot will ACQUIRE or AGE about 5% of the time for each.  
 */
public class AcquireAndAge extends BotBrain
{
    public AcquireAndAge()
    {
        setName("AcquireAndAge");
    }
    
    @Override
    public int chooseAction()
    {       
        //This line chooses from 0,90,180,270 randomly :-)
        int choice = (int)(Math.random()*4)*90;
        
        if(Math.random() < .05)
            choice = ACQUIRE;
        if(Math.random() < .05)
            choice = AGE;

        return choice;
    }
    
}
