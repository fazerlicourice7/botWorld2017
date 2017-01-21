package actor;

import grid.Location;
import gui.RatBotsColorAssigner;
import java.awt.Color;
import java.util.ArrayList;
import world.BotWorld;
/**
 * Rats are the competitors in the RatBots game.  Every Bot has a BotBrain 
 that acts as its 'brain' by making decisions for its actions.  
 * @author Spock
 */
public class Bot extends GameObject
{       
    /**
     * Each Bot has a BotBrain as it's 'brain'.  The Bot 'tells' the BotBrain 
 what it 'sees' and the BotBrain makes the decision on how to act.  
     */
    private BotBrain botBrain; 
        
    private int score;
    private int roundsWon;
    private int matchesWon;
    private int matchesTied;
    private int matchesLost;
    private int totalScore = 0;
    
    private int mostRecentChoice = 0;
    
    /**
     * Constructs a red Rat with a generic BotBrain.  
     */
    public Bot()
    {
        botBrain = new BotBrain();
        setColor(Color.RED);
        initialize();
    }
    /**
     * Constructs a Rat with the given color and given BotBrain for its 'brain'.
     * All rats have a BotBrain that chooses their action each turn.  
     * @param rb the BotBrain that makes decisions for this Rat.
     */
    public Bot(BotBrain rb)
    {
        botBrain = rb;
        setColor(RatBotsColorAssigner.getAssignedColor());
//        if(rb.getPreferredColor() == null)
//        {
//            setColor(RatBotsColorAssigner.getAssignedColor());
//            rb.setPreferredColor(getColor());
//            
//        }
//        else    
//            setColor(rb.getPreferredColor());
        initialize();
    }
    /**
     * Constructs a copy of this Rat (that does not include the BotBrain.)
     * @param in the Rat being copied.
     */
    public Bot(Bot in)
    {
        super(in);
        botBrain = new BotBrain();
        setLocation(in.getLocation());
        setColor(in.getColor());
        score = in.getScore();
    }
    
    public void setColor(Color in)
    {
        super.setColor(in);
        botBrain.setColor(in);
    }


    
    public int getMostRecentChoice() { return mostRecentChoice; }
    
    
    /**
     * Overrides the <code>act</code> method in the <code>GameObject</code> 
 class to act based on what the BotBrain decides.
     */
    @Override
    public void act()
    {
        //Ask the BotBrain what to do. 
        giveDataToRatBot();
        int choice = botBrain.chooseAction();
        mostRecentChoice = choice;
        
        turn((choice%1000)); //Turn to face the direction of the choice.
        if( (choice%1000)%90 != 0) choice = -1; //An invalid choice.  
        
        if(choice < 0)
        {
            //REST
        }
        else if(0 <= choice && choice <= 999) 
        {
            //MOVE
            move();
        }
        else if(1000 <= choice && choice <= 1999) 
        {
            //DART
            run();
        }
        else if(2000 <= choice && choice <= 2999) 
        {
            //BUILD BLOCK
            buildBlock();
        }
        else if(3000 <= choice && choice <= 3999) 
        {
            //TRADE
             trade();
        }
        else if(4000 == choice) 
        {
            //ACQUIRE
             acquire();
        }
        else if (5000 == choice)
        {
            ageMarkers();
        }
        
//        //Color change...
//        if(!getColor().equals(ratBot.getPreferredColor())
//                && ratBot.getPreferredColor() != null)
//            setColor(ratBot.getPreferredColor());
        
    } //end of act() method
    
    /**
     * Turns the Bot
     * @param newDirection the direction to turn to.   
     */
    public void turn(int newDirection)
    {
        setDirection(newDirection);
    }
     public boolean canMove()
    {
        return canMove(getLocation(), getDirection());
    }
   
    /**
     * This method does not allow you to move onto other Rats or Blocks 
     */
    public boolean canMove(Location loc, int dir)
    {
        Location next = loc.getAdjacentLocation(dir);
        if(!getGrid().isValid(next)) 
            return false;
        //Make sure destination is not occupied by another Bot
        if(getGrid().get(next) instanceof Bot)
            return false;
        //Make sure destination is not occupied by a Block
        if(getGrid().get(next) instanceof Block)
            return false;
        
        return true;
    }
    
    /**
     * Moves the rat forward, putting a Tail into the location it previously
     * occupied.
     */
    public void move()
    {        
        if(canMove())
        {
            Location old = getLocation();
            Location next = old.getAdjacentLocation(getDirection());

//            processDestination(next);
            moveTo(next);
            placeMarker(old);
        }
    }
    
    public void acquire()
    {
        for(int dir = 0; dir<360; dir+=45)
        {
            if(canMove(getLocation(),dir))
            {
                Location loc = getLocation().getAdjacentLocation(dir);
//                System.out.println("Acquiring at "+loc);
                placeMarker(loc);
            }
//            else
//                System.out.println(" NOT Acquiring in direction "+dir);
                
        }
    }

    public void ageMarkers()
    {
        final int AGE_INCREASE = 4; //really five because it will increase one as well.
        for(int dir = 0; dir<360; dir+=45)
        {
            if(canMove(getLocation(),dir))
            {
                Location loc = getLocation().getAdjacentLocation(dir);
//                System.out.println("Acquiring at "+loc);
                if(getGrid().get(loc) instanceof Marker)
                {
                    Marker m = (Marker)getGrid().get(loc);
                    m.increaseAge(AGE_INCREASE);
                }
            }
//            else
//                System.out.println(" NOT Acquiring in direction "+dir);
                
        }
    }
    
    public void trade()
    {
        Location space = getLocation();
        while(canMove(space,getDirection()))
        {
            space = space.getAdjacentLocation(getDirection()); 
            if(getGrid().get(space) instanceof Marker)
            {
                Marker m = (Marker)getGrid().get(space);
                if(m.getColor().equals(getColor()))
                    placeOpponentMarker(space);
                else
                    placeMarker(space);
            }
        }
    }
    
    private void run()
    {
        Location next = getLocation();
        Location old = getLocation();
        int numSteps = 3;
        while(canMove(next,getDirection()) && numSteps > 0)
        {
            numSteps--;
            next = next.getAdjacentLocation(getDirection()); 
            if(getGrid().get(next) == null || getGrid().get(next) instanceof Marker)
            {
                moveTo(next);
                if(numSteps == 2)
                    placeMarker(old);
                old = new Location(next.getRow(), next.getCol());
            }
        }
//        processDestination(next);
        moveTo(next);
    }
    
//    private void processDestination(Location next)
//    {
//        GameObject nextObj = getGrid().get(next);
//
//        if(nextObj instanceof Prize)
//        {
//            Prize m = (Prize)nextObj;
//            score+=m.getValue();  
//        }
//    }
    
    private void placeMarker(Location loc)
    {
        Marker t = new Marker(getColor());
        t.putSelfInGrid(getGrid(), loc);
    }
    
    private void placeOpponentMarker(Location loc)
    {
        ArrayList<Location> objLocs = getGrid().getOccupiedLocations();
        ArrayList<Color> otherColors = new ArrayList<Color>();
        for(Location place : objLocs)
        {
            GameObject obj = getGrid().get(place);
            if(obj instanceof Marker)
            {
                Marker m = (Marker)obj;
                if(!m.getColor().equals(getColor()))
                    otherColors.add(m.getColor());
            }
        }
        if(otherColors.size() > 0)
        {
            int sel = (int)(Math.random()*otherColors.size());
            Marker t = new Marker(otherColors.get(sel));
            t.putSelfInGrid(getGrid(), loc);            
        }
        else
        {
            getGrid().remove(loc);
        }
    }
    
    private void buildBlock()
    {
        Location next = getLocation().getAdjacentLocation(getDirection());
        if(getGrid().isValid(next) && 
                !(getGrid().get(next) instanceof Bot) &&
                !(getGrid().get(next) instanceof Block))
        {
            new Block().putSelfInGrid(getGrid(), next);            
        }
    }
    
//    private void buildWall()
//    {
//        ArrayList<Location> places = new ArrayList<Location>();
//        Location centerOfWall = getLocation().getAdjacentLocation(getDirection());
//        places.add(centerOfWall);
//        Location left = new Location(centerOfWall.getRow(), centerOfWall.getCol());
//        Location right = new Location(centerOfWall.getRow(), centerOfWall.getCol());
//        
//        int WALL_SIZE_FROM_CENTER = 3;
//        for(int q=0; q<WALL_SIZE_FROM_CENTER; q++)
//        {
//            left = left.getAdjacentLocation(getDirection()-90);
//            places.add(left);
//            right = right.getAdjacentLocation(getDirection()+90);
//            places.add(right);
//        }
//        
//        for(Location loc : places)
//        {
//            if(getGrid().isValid(loc) && 
//                    !(getGrid().get(loc) instanceof Bot) &&
//                    !(getGrid().get(loc) instanceof Block) )
//            {
//                new Block(Block.TEMPORARY_DURATION).putSelfInGrid(getGrid(), loc);
//            }            
//        }
//    }
    
    private void buildGarden()
    {
        
    }

    @Override
    public String toString()
    {
        return "Rat: "+botBrain.getName();
    }

    /**
     * Updates the most recent data (location, grid and status)
 information to the BotBrain.  This allows the BotBrain to make a decision
 based on current data every turn.  
     */
    public final void giveDataToRatBot()
    {
        //score, energy, col, row, myStuff ================
        botBrain.setScore(score);
        botBrain.setLocation(getLocation().getCol(), getLocation().getRow());
        //match stuff: bestScore, roundsWon ===================
        botBrain.setBestScore(this.calculateBestScore());
        botBrain.setRoundsWon(this.getRoundsWon());
        //world stuff: moveNumber, roundNumber ================
        botBrain.setMoveNumber(BotWorld.getMoveNum());
        botBrain.setRoundNumber(BotWorld.getRoundNum());

        //theArena!============================================
        int numRows = getGrid().getNumRows();
        int numCols = getGrid().getNumCols();
        GameObject[][] theArena = new GameObject[numRows][numCols];
        for(int row=0; row<numRows; row++)
        {
            for(int col=0; col<numCols; col++)
            {
                GameObject a = getGrid().get(new Location(row, col));
                if(a != null)
                    theArena[row][col] = a.getClone();
                //Might need to do each with instanceof here...
                
            }
        }
        botBrain.setArena(theArena);
            
//                if(a instanceof Prize)
//                {
//                    mSeen.add(new Prize((Prize)a));
//    //                System.out.print(ratBot.getName()+" sees "+a+" in compass "+dir);
//                }
//                else if(a instanceof Bot)
//                {
//                    rSeen.add(new Bot((Bot)a));
//                    done = true;
//    //                System.out.print(ratBot.getName()+" sees "+a+" in compass "+dir);
//                }
        
    } //end of giveDataToRatBot() -----------------------------
    
    public int myScore()
    {
        int score =0;
        
        return score;
    }
    
    public int calculateBestScore()
    {
        int bestScore = getScore();
        ArrayList<Bot> rats = getGrid().getAllRats();
        for(Bot r : rats)
        {
            if(r.getScore() > bestScore)
            {
                bestScore = r.getScore();
            }
        }  
        return bestScore;
    }
    
//    public void copyGridLocation(RatBotsGrid from, RatBotsGrid to, Location loc)
//    {
//        //Copy the Actor
//        GameObject actor = (GameObject)from.get(loc);
//        if(actor != null && !(actor instanceof Tail))
//        {
//            GameObject clone = actor.getClone();
//            clone.putSelfInGrid(to, loc);
//        }  
//        else
//        {
//            to.remove(loc); //remove the fog.
//        }
//        //Copy the walls.
//        for(int d = 0; d < 360; d+=90)
//        {
//            if(from.isWall(loc, d))
//                to.setWall(loc, d);
//        }
//        
//    }
    
    /**
     * Accessor method to get the BotBrain from a Bot.
     * @return the BotBrain 'brain' of this BotBrain.
     */
    public BotBrain getRatBot()
    {
        return botBrain;
    }

    /**
     * Gets the current score (from this round).  
     * @return the score
     */
    public int getScore() { return score; }
    /**
     * Sets the current score of this Bot.
     * @param in the score
     */
    public void setScore(int in) { score = in; }
    /**
     * Adds the given amount to score of this Bot.  
     * @param add the amount to add
     */
    public void addToScore(int add) { score += add; }
    /**
     * Gets the total points scored over all rounds in this match for this Bot.
     * @return the total score
     */
    public int getTotalScore() { return totalScore; }

    /**
     * Gets the number of rounds won by this Bot in the match.
     * @return the rounds won
     */
    public int getRoundsWon() { return roundsWon; }
    
    
    /**
     * Sets the number of rounds won by this Bot in the match.
     * @param in the rounds won
     */
    public void setRoundsWon(int in) { roundsWon = in; }
    /**
     * Increases the number of rounds won in this match by this Bot by one.
     */
    public void increaseRoundsWon() { roundsWon++; }

    // These methods are used for the RoundRobin tourney.
    public int getMatchesWon() { return matchesWon; }
    public int getMatchesTied() { return matchesTied; }
    public int getMatchesLost() { return matchesLost; }
    public void increaseMatchesWon() { matchesWon++; }
    public void increaseMatchesTied() { matchesTied++; }
    public void increaseMatchesLost() { matchesLost++; }
     /**
     * Initializes this Bot for a new round.  
     */
    public final void initialize()
    {
        totalScore += score;
        score = 0;
        botBrain.initForRound();
    }
    
    public void clearScores()
    {
        score = 0;
        roundsWon = 0;
        matchesWon = 0;
        matchesTied = 0;
        matchesLost = 0;
        totalScore = 0;
    }

    @Override
    public GameObject getClone()
    {
        GameObject clone = new Bot(this);
        return clone;
    }
    
}