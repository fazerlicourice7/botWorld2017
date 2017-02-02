/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package brain;

import actor.BotBrain;
import static actor.BotBrain.BLOCK_EAST;
import static actor.BotBrain.BLOCK_NORTH;
import static actor.BotBrain.BLOCK_SOUTH;
import static actor.BotBrain.BLOCK_WEST;
import static actor.BotBrain.EAST;
import static actor.BotBrain.NORTH;
import static actor.BotBrain.SOUTH;
import static actor.BotBrain.WEST;
import actor.GameObject;
import actor.Marker;
import grid.Location;

/**
 * @author 17mohammeda
 */
public class AaribWall extends BotBrain {
int counter = 0;
int counter2 = 0;
int move = 0;

public void initForRound()
{
    counter = 0;
    counter2 = 0;
            
}




    @Override
    public int chooseAction() {
 
        name();
        int direction = -1;
        direction = path();
        move = getMoveNumber();
        rounds();
        return direction;
    }

    /**
     * Author: Aarib Mohammed Created a loop which bot follows in to get all
     * prizes and creates blocks that close off 4 corners
     *
     * @return direction
     */
    public int path() {
        int direction = -1;
        
        if (counter < 5) {
            if (getCol() == 6 && getRow() == 14) {
                counter= counter +1;
                //System.out.print("<2 " +counter +" ,");
            }
        }
            else if (counter >=5) {
            if(getRow() == 12 && getCol()==6) {
                direction = EAST;
                //System.out.print(" >=2 " +counter +" ");
            }
            else if (getCol()==6){
                direction = NORTH;
             //System.out.println("NORTH2");
            }
    
            else if (getCol()==7&& getRow()==12)
            {
                direction= EAST;
            }
            else if (getCol()==8&& getRow()==12 && counter2 ==0)
            {
                direction= 4000;
                counter2 = counter2+1;
               //System.out.println(counter2);
                
            }
            else if ( counter2>0 && counter2<500 )
            {
                direction = 5000;
                counter2++;
                //System.out.println(counter2);
            }
            }
        if (move==130)
            {
                direction = NORTH;
            }
            if (move ==131)
            {
                direction = NORTH;
            }
            if (move ==132)
            {
                direction = NORTH;
            }
            if (move ==133)
            {
                direction = NORTH;
            }
            if (move ==134)
            {
                direction = 4000;
            }
            if (move ==135)
            {
                direction = 5000;
            }
            if (move ==136)
            {
                direction = EAST;
            }
            if (move ==137)
            {
                direction = EAST;
            }
            if (move ==138)
            {
                direction = EAST;
            }
            if (move ==139)
            {
                direction = EAST;
            }
            if (move ==140)
            {
                direction = 4000;
            }
            if (move ==141)
            {
                direction = 5000;
            }
            if (move ==142)
            {
                direction = SOUTH;
            }
            if (move ==143)
            {
                direction = SOUTH;
            }
            if (move ==144)
            {
                direction = SOUTH;
            }
            if (move ==145)
            {
                direction = SOUTH;
            }
           
            if (move ==146)
            {
                direction = 4000;
            }
            if (move ==147)
            {
                direction = 5000;
            }
            if (move ==148)
            {
                direction = WEST;
            }
            if (move ==149)
            {
                direction = WEST;
            }
             if (move ==150)
            {
                direction = 4000;
            }
             if (move >150)
            {
                direction = 5000;
            }
            
            
            
            
            
                
            
            
            
        
        
        ////////////////////////////// blocks off bottom left side
        if(move<130)
                {
        if (getRow() == 14 && getCol() == 5 && canMove(SOUTH)) {
            direction = BLOCK_SOUTH;
        } ///// top left
        else if (getRow() == 6 && getCol() == 5 && canMove(NORTH)) {
            direction = BLOCK_NORTH;
        } //////////////////////////////// Blocks off top right
        else if (getRow() == 6 && getCol() == 15 && canMove(NORTH)) {
            direction = BLOCK_NORTH;
            /////bottom right
        } else if (getRow() == 15 && getCol() == 14 && canMove(EAST)) {
            direction = BLOCK_EAST;
            //////////////////////////////////////////////// blocks off  middle
        } else if (getRow() == 14 && canMove(SOUTH)) {
            direction = BLOCK_SOUTH;
        } else if (getCol() == 6 && canMove(WEST)) {
            direction = BLOCK_WEST;
        } else if (getRow() == 6 && canMove(NORTH)) {
            direction = BLOCK_NORTH;
        } else if (getCol() == 14 && canMove(EAST)) {
            direction = BLOCK_EAST;
            ////////////////////////////////////////////////////////// 
        } else if (getRow() == 6 && getCol() == 14) {
            direction = SOUTH;
        } else if (getRow() == 14 && getCol() == 14) {
            direction = WEST;
        } else if (getCol() == 10 && canMove(NORTH) && getRow() != 14 && counter2 <50) {
            direction = NORTH;
        } else if (getCol() == 10 && canMove(SOUTH) && getRow() != 6 && counter2 <50) {
            direction = SOUTH;

            ////////////////////////////////////// 4 SPECIAL 
        } else if (getRow() == 6 && getCol() == 15) {
            direction = WEST;
            if (getRow() == 6 && getCol() == 14) {
                direction = SOUTH;
            }
        } 
        /////////////////////////////////////////// 4 main NSWE
        else if (getRow() == 6) {
            direction = EAST;
        } else if (getCol() == 14) {
            direction = SOUTH;
        } else if (getRow() == 14 && getCol() != 6) {
            direction = WEST;
        } else if (getCol() == 6&& counter <5) {
            direction = NORTH;
             //System.out.println("NORTH");
        } /////////////////////////////////////////// other special cases
        else if (getRow() == 14 && getCol() == 6) {
            direction = NORTH;
        } ////////////////////////////////////////////////// 
        ////////////////////////////////////////////////// special cases (inner four corners)
        ///////////////////////////////////////   four cardinal directions
        else if (getCol() == 11 && getRow() != 6 && counter2 <50) {
            direction = SOUTH;
        } else if (getCol() == 14 && getRow() != 6) {
            direction = SOUTH;
        } ////////////////////////////////
        else if (getCol() == 9 && getRow() != 6) {
            direction = NORTH;
        } else if (getCol() == 4 && getRow() != 7) {
            direction = NORTH;

        }
                }
         ///////////////////////////////
        ////////////////////////////////
        ////////////////////////////////  other special cases 
        //System.out.println(direction);
        return direction;
    }
//    public int acquire()
//    {
//        int direction = 0;
//         
//        if (counter < 3) {
//            if (getCol() == 6 && getRow() == 14) {
//                counter= counter +1;
//                System.out.print("<2 " +counter +" ");
//            }
//        }
//            else if (counter >= 3) {
//            if(getRow() == 12 && getCol()==6) {
//                direction = EAST;
//                System.out.print(" >=2 " +counter +" ");
//            }
//            else 
//                direction = NORTH;
//        }
//        
//                
//       return direction;
//    }
//    

    /**
     * Author: Aarib Moahammed Sets name to Aarb6i from the beginning but
     * changes to WINNER when score is 1500
     */
    public void name() {
        setName("AaribWall");
        

    }

    /**
     * Author: Aarib Mohammed Sets name to FIRST PLACE if the rounds it wins is
     * more than 50
     */
    public void rounds() {
        if (getRoundsWon() > 50);
        {
            setName("Winner");
        }
        
    }

    public boolean canMove(int directionChoice) {
        int direction = directionChoice % 1000;

        Location myLoc = new Location(getRow(), getCol());
        Location next = myLoc.getAdjacentLocation(direction);
        if (!next.isValidLocation()) {
            return false;
        }

        GameObject onNext = getArena()[next.getRow()][next.getCol()];
        if (onNext == null|| onNext instanceof Marker ) {
            return true;
        } else {
            return false;
        }
    }
}
