package actor;

/**
 * @author Spock
 */
import java.awt.Color;

/**
 * A BotBrain is the 'brain' for a Bot in the BotWorld.  Each time that 
 a Bot is given its turn to act, it asks its BotBrain how it should act.  The
 Bot also provides the BotBrain with all of the sensor information it has.  
 </br>
 It is important to note that even though a BotBrain has a Location, 
 changing that location does not actually move the Rat (or the BotBrain.)  
 It is only changing where you think you are, but really you're only fooling 
 yourself.  The same is true about the other parameters a BotBrain has.  
 </br>
 * While this structure is intended to keep you from 'cheating' the game.  
 * I'm sure some of you will find a way around it.  Therefore, it is a rule 
 * that any attempts to work around the rules will eliminate you from all 
 * competitions.  (Although I would like to get feedback so that I can 
 * improve how the game works.)  
 */
public class BotBrain 
{   
    //CONSTANTS for possible moves.

    /**
     * The value to return to not move during a turn. 
     */
    public static final int REST = -1;
    /**
     * The values to return to move one space in the desired direction. 
     */
    public static final int MOVE_NORTH = 0;
    public static final int MOVE_EAST = 90;
    public static final int MOVE_SOUTH = 180;
    public static final int MOVE_WEST = 270;
    public static final int NORTH = 0;
    public static final int EAST = 90;
    public static final int SOUTH = 180;
    public static final int WEST = 270;
    /**
     * The values to return to 'run' three spaces in the desired direction. 
     */
    public static final int RUN_NORTH = 1000;
    public static final int RUN_EAST = 1090;
    public static final int RUN_SOUTH = 1180;
    public static final int RUN_WEST = 1270;
    /**
     * The values to return to build a single Block in the desired direction.  
     */
    public static final int BLOCK_NORTH = 2000;
    public static final int BLOCK_EAST = 2090;
    public static final int BLOCK_SOUTH = 2180;
    public static final int BLOCK_WEST = 2270;
    /**
     * The values to return to trade Markers in the desired direction.  
     */
    public static final int TRADE_NORTH = 3000;
    public static final int TRADE_EAST = 3090;
    public static final int TRADE_SOUTH = 3180;
    public static final int TRADE_WEST = 3270;
    /**
     * The value to return to place Markers in all unblocked adjacent spaces.
     */
    public static final int ACQUIRE = 4000;        
    /**
     * The value to return to age Markers in all adjacent spaces.
     */
    public static final int AGE = 5000;        
    
    //Instance Variables-----------------------------
    
    //What you know...
    private int row;
    private int col;  
    private String name;  
    private Color color;

    //What you see...
    private GameObject[][] theArena;    
    
    //Match information...
    private int moveNumber; 
    private int roundNumber; 
    private int score;
    private int bestScore;
    private int roundsWon;   
    
    //Constructor=============================================================
    /**
     * Constructs a new BotBrain.
     * The name of the BotBrain will be 'default' until it is changed.
     */
    public BotBrain()
    {
        name = "default";
    }
    /**
     * Chooses the action for this BotBrain. </br>
     * @return the selected action (as an integer) 
     */
    public int chooseAction()
    {
        // Every BotBrain that extends this class should override this method.
        return REST;   
    }
    
    /**
     * This method should include code that will initialize this BotBrain 
 at the start of a new round.  
     */
    public void initForRound()
    {
        // Every BotBrain that extends this class should override this method.
        /* empty */
    }
    
    //Accessors ----------------------------------------------
    //What you know...
    public int getCol() { return col; }
    public int getRow() { return row; }
    public String getName() { return name; } 
    public Color getColor() { return color; }
    
    //What you see...
    public GameObject[][] getArena() { return theArena; }
    
    //Match information...
    public int getMoveNumber() { return moveNumber; }
    public int getRoundNumber() { return roundNumber; }
    public int getScore() { return score; }
    public int getBestScore() { return bestScore; }
    public int getRoundsWon() { return roundsWon; }


    public void setName(String in) { name = in; }
    
    /*
    The modifier methods listed below are used by the RatBots engine to 
    give 'vision' to your BotBrain each turn.  If you change their values, 
    you are not really changing the game, you're just lying to yourself.  
    */
    public void setLocation(int c, int r) { col = c; row = r; }
    public void setArena(GameObject[][] arena) 
    {
        theArena = arena;
    }
    
    public void setColor(Color c) { color = c; }
    public void setMoveNumber(int in) { moveNumber = in; }
    public void setRoundNumber(int in) { roundNumber = in; }
    public void setScore(int in) { score = in; }
    public void setBestScore(int in) { bestScore = in; }
    public void setRoundsWon(int in) { roundsWon = in; }           
        
    
    
}