package brain;

import actor.Block;
import actor.Bot;
import actor.BotBrain;
import static actor.BotBrain.ACQUIRE;
import static actor.BotBrain.AGE;
import static actor.BotBrain.MOVE_EAST;
import static actor.BotBrain.MOVE_NORTH;
import static actor.BotBrain.MOVE_SOUTH;
import static actor.BotBrain.MOVE_WEST;
import static actor.BotBrain.REST;
import actor.Marker;
import grid.Location;
import static java.lang.Math.abs;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
/**
 * @author Spock
 * RandomRat chooses a random move each turn.
 */
public class Test_Opponent extends BotBrain
{    
    
    public Test_Opponent()
    {
        opponentLocs = new ArrayList();
        setName("MaximumTroll");
        checkpoint = false;
    }
    ArrayList<Location> opponentLocs;
    boolean[] checkpoints = {true, false, false, false, false};
    boolean checkpoint;
    int progress = 10;
    int lastKnownRound = 0;
    boolean fourCorners = false;
    @Override
    public int chooseAction()
    {   
        //int[] checkTop = {returnBoundsCorrection(getRow() - 1), returnBoundsCorrection(getCol() - 1)};
        //if(returnPotential(checkTop) > 5){
          //  return ACQUIRE;
        //}
        //if(Math.random() < .5 && returnCurrentMarkers(checkTop) > 3)
          //  return AGE;
        //if(returnCurrentMarkers(checkTop) > 3){
          //  return AGE;
        //}
        
        
        
        if(lastKnownRound != getRoundNumber()){
            resetMem();
        }
        if(fourCorners){
            return fourCorners();
        }
        
        
        opponentLocs.add(getEnemyLoc());
        if((getLocation()).equals(opponentLocs.get(0))){
            checkpoint = true;
            progress = 0;
        }

            if(checkpoint && opponentLocs.size() > progress + 1){
                progress++;
                //System.out.println("Progress num is " + (progress));
                //System.out.println("opp locs list is " + opponentLocs);
                //System.out.println("opp locs size is " + opponentLocs.size());
                //System.out.println("target location will be " + opponentLocs.get(progress).getRow() + " " +  (opponentLocs.get(progress)).getCol());
                return moveTo((opponentLocs.get(progress).getRow()), (opponentLocs.get(progress)).getCol());
            } else if (checkpoint) {
               return AGE;
            } else {
               return moveTo((opponentLocs.get(0)).getRow(), (opponentLocs.get(0)).getCol());
            }
           
    }
        
        
    
    public boolean rowNeedsFill(int rowID){
        if(rowID == 0){
            for(int i = 0; i < 21; i++){
                if(getArena()[0][i] == null){
                    return true;
                }
            }
        }
        if(rowID == 1){
            for(int i = 0; i < 21; i++){
                if(getArena()[i][21] == null){
                    return true;
                }
            }
        }
        if(rowID == 2){
            for(int i = 0; i < 21; i++){
                if(getArena()[21][i] == null){
                    return true;
                }
            }
        }
        if(rowID == 3){
            for(int i = 0; i < 21; i++){
                if(getArena()[i][0] == null){
                    return true;
                }
            }
        }
    return false;
    }
    
    public int moveTo(int row, int col){
        int movesuggestion = writePathTo(new Location(row, col)).get(0);
        if(hasBot((getLocation()).getAdjacentLocation(movesuggestion))){
            return ACQUIRE;
        }
        //System.out.println("move suggestion is " + movesuggestion);
        //System.out.println("after add is " + (movesuggestion + 1000));
        //if(movesuggestion < 271){
          //  return movesuggestion + 1000;
        //} else {
            return movesuggestion;
        //}
        
    }
    //simplifies fourCorners()
    public int numToRow(int num){
        if(num == 0){
            return 1;
        } else if(num == 1) {
            return 1;
        } else if(num == 2) {
            return 19;
        } else if(num == 3){
            return 19;
        } else{
            return 1;
        }
    }
    //simplifies fourCorners()
    public int numToCol(int num){
        if(num == 0){
            return 8;
        } else if(num == 1) {
            return 12;
        } else if(num == 2) {
            return 12;
        } else if(num == 3){
            return 8;
        } else {
            return 8;
        }
    }
    
    public Location getLocation(){
         return new Location(getRow(), getCol());
    }
    
    public ArrayList<Integer> writePathTo(Location objective){  
        int length = objective.distanceTo(getLocation());
        int lengthAddOn = -1;
        lengthAddOn++;
        ArrayList<Integer> moveOptions = new ArrayList<Integer>();
        for(int xMovesNeeded = 0; xMovesNeeded < xLocTo(objective); xMovesNeeded++){
                moveOptions.add(getDirectionHorizontal(objective));
        }
        for(int yMovesNeeded = 0; yMovesNeeded < yLocTo(objective); yMovesNeeded++){
                moveOptions.add(getDirectionVertical(objective));
        }
            
        ArrayList<Integer> path = returnPath(moveOptions, length, objective);
        return path;
    }
    public int xLocTo(Location objective){
        return abs(getCol() - objective.getCol());
    }
    public int yLocTo(Location objective){
        return abs(getRow() - objective.getRow());
    }
    
    public int getDirectionHorizontal(Location objective){
        if(getCol() > objective.getCol()){
            return MOVE_WEST;
        } else {
            return MOVE_EAST;
        }
    }
    
    public int getDirectionVertical(Location objective){
        if(getRow() > objective.getRow()){
            return MOVE_NORTH;
        } else {
            return MOVE_SOUTH;
        }
    }
    static int[] shuffleArray(int[] ar)
  {
    // If running on Java 6 or older, use `new Random()` on RHS here
    Random rnd = ThreadLocalRandom.current();
    for (int i = ar.length - 1; i > 0; i--)
    {
      int index = rnd.nextInt(i + 1);
      // Simple swap
      int a = ar[index];
      ar[index] = ar[i];
      ar[i] = a;
    }
    return ar;
  }
  public ArrayList<Integer> returnPath(ArrayList<Integer> movesNeeded, int length, Location objective){
      int extensionCoder = -1;
      while(true){
        extensionCoder++;
        for(int i = 0;  i < extensionCoder; i++){
            //movesNeeded.add(getAddendum());
        }
        ArrayList<Integer> path = new ArrayList<Integer>();
        Collections.shuffle(movesNeeded);
        path = movesNeeded;
        for(int pathsTried = 0; pathsTried < 10; pathsTried++){
        
        Location ghostLoc = new Location(getLocation().getRow(), getLocation().getCol());
        for(int i = 0; i < path.size(); i++){
            ghostLoc = ghostLoc.getAdjacentLocation(path.get(i));
            if(getArena()[ghostLoc.getRow()][ghostLoc.getCol()] instanceof Block){
                break;
            }
            //if(getArena()[ghostLoc.getRow()][ghostLoc.getCol()] instanceof Bot){
              ///  setName("I Brake for Bots");
                //System.out.println("breaking for bots");
                //break;
            //}
        }
        if(ghostLoc.equals(objective)){
           return path; 
            }
        if(extensionCoder > 10){
          //System.out.println("using addendum");
          //System.out.println("arraylist is " + (moveTowardCenter()));
          ArrayList<Integer> out = new ArrayList<Integer>();
          out.add(moveTo(10,10));
          return out;
        }
      }
      
      
    }
  
  }
  public ArrayList<Integer> moveTowardCenter(){
      ArrayList out = new ArrayList();
      Location center = new Location(11, 11);
      if(center.distanceTo(new Location(center.getRow(), getLocation().getCol())) > center.distanceTo(new Location(getLocation().getRow(), center.getCol()))){
          out.add(getDirectionHorizontal(center));
      } else {
          out.add(getDirectionVertical(center));
      }
      return (out);
  } 
  
  
  public Location getEnemyLoc(){
      for(int row = 0; row < 21; row++){
          for(int col = 0; col < 21; col++){
              if(getArena()[row][col] instanceof Bot && (getRow() != row || getCol() != col)){
                  return new Location(row, col);
              }
        }
    }
  return new Location(0, 0);
  }
  
  //public Location checkForPath(ArrayList<Location> memory){
      
  //}
  
  public ArrayList<Location> getAdjacentLocations(Location start){
      ArrayList<Location> lazyList = new ArrayList<Location>();
      
      for(int i = 0; i < 4; i++){
          if((start.getAdjacentLocation(i * 90)).isValidLocation()){
              lazyList.add(start.getAdjacentLocation(i * 90));
          }
      }
      return lazyList;
  }
  
  public int randomMove(){
      int direction = (int)(Math.random()*4);
        
        if(direction == 0) return MOVE_NORTH;        
        if(direction == 1) return MOVE_EAST;        
        if(direction == 2) return MOVE_SOUTH;        
        if(direction == 3) return MOVE_WEST;        
        
        return REST;
  }
  
  public void resetMem(){
      opponentLocs = new ArrayList<Location>(); 
  }
  
  public boolean hasBot(Location testCase){
      if(testCase.isValidLocation()){
        if(getArena()[testCase.getRow()][testCase.getCol()] instanceof Bot && notMe(new Location(testCase.getRow(), testCase.getCol()))){
            return true; 
        } else {
            return false;
        }
      } else {
          return false;
      }
   
  }
  
  public boolean notMe(Location test){
      if(getRow() != test.getRow() || getCol() != test.getCol()){
          return true;
      }
      return false;
  }
  public boolean hasBlock(Location testCase){
      if(getArena()[testCase.getRow()][testCase.getCol()] instanceof Block){
          return true; 
      } else {
          return false;
      }
      
  }
  public int fourCorners(){
        if(allTrue(checkpoints)){
            for(int i = 0; i < checkpoints.length; i++){
               checkpoints[i] = false;
          }
        }
        for(int index = 0; index < 5; index++){
            if(getRow() == numToRow(index) && getCol() == numToCol(index)){
                if(getRow() == numToRow(4) && getCol() == numToCol(4)){
                    if(checkpoints[0] == true){
                        checkpoints[4] = true;
                    }
                    checkpoints[0] = true;
                } else {
                //System.out.println("checkpoint " + index + " has been reached");
                checkpoints[index] = true;
                }
            }
        }
        
        if(checkpoints[3] == true){
            //System.out.println("moveTo()" + new Location(numToRow(4), numToCol(4)));
            return moveTo(numToRow(4), numToCol(4));
        } else if(checkpoints[2] == true){
            return moveTo(numToRow(3), numToCol(3));
        } else if(checkpoints[1] == true){
            return moveTo(numToRow(2), numToCol(2));
        } else if(checkpoints[0] == true){
            return moveTo(numToRow(1), numToCol(1));
        } else{
            //System.out.println("moving to " + new Location(numToRow(0), numToCol(0)));
            return moveTo(numToRow(0), numToRow(0));
        }
        
        
    
  }
       public boolean allTrue(boolean[] test){
           for(int i = 0; i < test.length; i++){
               if(test[i] == false){
                   return false;
               }
           }
           return true;
       }
       
        public int returnPotential(int[] topleftcoordinate){
        topleftcoordinate[0] = returnBoundsCorrection(topleftcoordinate[0]);
        topleftcoordinate[1] = returnBoundsCorrection(topleftcoordinate[1]);
        int counter = 0;
        for(int row = 0; row < 3; row++){
            for(int col = 0; col < 3; col++){
                if(topleftcoordinate[0] + row > 20 || topleftcoordinate[1] + col > 20){
                    break;
                }
                if((getArena()[topleftcoordinate[0] + row][topleftcoordinate[1] + col]) == null){
                    counter++;
                }
                
            }
        }
        return counter;
    }
        
    public int returnBoundsCorrection(int input){
        if(input > 20){
            return 20;
        }
        if(input < 0){
            return 0;
        }
        return input;
    }
    
    public int returnCurrentMarkers(int[] topleftcoordinate){
        topleftcoordinate[0] = returnBoundsCorrection(topleftcoordinate[0]);
        topleftcoordinate[1] = returnBoundsCorrection(topleftcoordinate[1]);
        int counter = 0;
        for(int row = 0; row < 3; row++){
            for(int col = 0; col < 3; col++){
                if(topleftcoordinate[0] + row > 20 || topleftcoordinate[1] + col > 20){
                    break;
                }
                if((getArena()[topleftcoordinate[0] + row][topleftcoordinate[1] + col]) instanceof Marker){
                    counter++;
                }
                
            }
        }
        return counter;
    }
}



  


