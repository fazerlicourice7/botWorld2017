/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package brain;
import actor.BotBrain;
import static actor.BotBrain.EAST;
import static actor.BotBrain.NORTH;
import static actor.BotBrain.SOUTH;
import static actor.BotBrain.WEST;
import actor.GameObject;
import grid.Location;

/**
 * @author 17mohammeda
 */
public class MillionBot extends BotBrain {
int counter = 0;
int counter2 = 0;
int pass = 2;
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
        
        if (counter < pass) {
            //System.out.println(move);
            if (getCol() == 6 && getRow() == 14) {
                counter= counter +1;
                //System.out.print("<2 " +counter +" ,");
            }
        }
            else if (counter >= pass) {
                //System.out.println(move);
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
                
              // System.out.println(counter2);
                
            }
            else if ( counter2>0 && counter2<51 )
            {
                direction = 5000;
                counter2++;
                
               // System.out.println(counter2);
            }
            
            }
            if (move==63)
            {
                direction = SOUTH;
            }
            if (move ==64)
            {
                direction = SOUTH;
            }
            if (move ==65)
            {
                direction = SOUTH;
            }
            if (move ==66)
            {
                direction = SOUTH;
            }
            if (move ==67)
            {
                direction = 4000;
            }
            if (move ==68)
            {
                direction = 5000;
            }
            if (move ==69)
            {
                direction = SOUTH;
            }
            if (move ==70)
            {
                direction = SOUTH;
            }
            if (move ==71)
            {
                direction = 4000;
            }
            if (move ==72)
            {
                direction = 5000;
            }
            if (move ==73)
            {
                direction = EAST;
            }
            if (move ==74)
            {
                direction = EAST;
            }
            if (move ==75)
            {
                direction = 4000;
            }
            if (move ==76)
            {
                direction = 5000;
            }
            if (move ==77)
            {
                direction = NORTH;
            }
            if (move ==78)
            {
                direction = NORTH;
            }
            if (move ==79)
            {
                direction = 4000;
            }
            if (move ==80)
            {
                direction = 5000;
            }
            if (move ==81)
            {
                direction = EAST;
            }
            if (move ==82)
            {
                direction = EAST;
            }
            if (move ==83)
            {
                direction = 4000;
            }
            if (move ==84)
            {
                direction = 4000;
            }
            if (move ==85)
            {
                direction = NORTH;
            }
            if (move ==86)
            {
                direction =EAST;
            }
            if (move ==87)
            {
                direction = EAST;
            }
            if (move ==88)
            {
                direction = 4000;
            }
            if (move ==89)
            {
                direction = 5000;
            }
            if (move ==90)
            {
                direction = NORTH;
            }
            if (move ==91)
            {
                direction = EAST;
            }
            if (move ==92)
            {
                direction = NORTH;
            }
            if (move ==93)
            {
                direction = NORTH;
            }
            if (move ==94)
            {
                direction = EAST;
            }
            if (move ==95)
            {
                direction = EAST;
            }
            if (move ==96)
            {
                direction = 4000;
            }
            if (move ==97)
            {
                direction = 4000;
            }
            if (move ==98)
            {
                direction = EAST;
            }
            if (move ==99)
            {
                direction = EAST;
            }
            if (move ==100)
            {
                direction = 4000;
            }
            if (move ==101)
            {
                direction = 5000;
            }
            if (move ==102)
            {
                direction = NORTH;
            }
            if (move ==103)
            {
                direction = NORTH;
            }
            if (move ==104)
            {
                direction = NORTH;
            }
            if (move ==105)
            {
                direction = 4000;
            }
            if (move ==106)
            {
                direction = 5000;
            }
            if (move ==107)
            {
                direction = WEST;
            }
            if (move ==108)
            {
                direction = WEST;
            }
            if (move ==109)
            {
                direction = 4000;
            }
            if (move ==110)
            {
                direction = 5000;
            }
            if (move ==111)
            {
                direction = WEST;
            }
            if (move ==112)
            {
                direction = WEST;
            }
            if (move ==113)
            {
                direction = NORTH;
            }
            if (move ==114)
            {
                direction = NORTH;
            }
            if (move ==115)
            {
                direction = 4000;
            }
            if (move ==116)
            {
                direction = 5000;
            }
            if (move ==117)
            {
                direction = NORTH;
            }
            if (move ==118)
            {
                direction = NORTH;
            }
            if (move ==119)
            {
                direction = NORTH;
            }
            if (move ==120)
            {
                direction = EAST;
            }
            if (move ==121)
            {
                direction = 4000;
            }
            if (move ==122)
            {
                direction = 5000;
            }
            if (move ==123)
            {
                direction = EAST;
            }
            if (move ==124)
            {
                direction = EAST;
            }
            if (move ==125)
            {
                direction = 4000;
            }
            if (move ==126)
            {
                direction = 5000;
            }
            if (move ==127)
            {
                direction = NORTH;
            }
            if (move ==128)
            {
                direction = NORTH;
            }
            if (move ==129)
            {
                direction = 4000;
            }
            if (move ==130)
            {
                direction = 5000;
            }
            if (move ==131)
            {
                direction = WEST;
            }
            if (move ==132)
            {
                direction = WEST;
            }
            if (move ==133)
            {
                direction = 4000;
            }
            if (move ==134)
            {
                direction = 5000;
            }
            if (move == 135)
            {
                direction = SOUTH;
            }
            if (move == 136)
            {
                direction = SOUTH;
            }
            if (move == 137)
            {
                direction = SOUTH;
            }
            if (move == 138)
            {
                direction = WEST;
            }
            if (move == 139)
            {
                direction = WEST;
            }
            if (move == 140)
            {
                direction = WEST;
            }
            if (move == 141)
            {
                direction = WEST;
            }
            if (move == 142)
            {
                direction = NORTH;
            }
            if (move == 143)
            {
                direction = 4000;
            }
            if (move == 144)
            {
                direction = 5000;
            }
            if (move == 145)
            {
                direction = NORTH;
            }
            if (move == 146)
            {
                direction = NORTH;
            }
            if (move == 147)
            {
                direction = 4000;
            }
            if (move == 148)
            {
                direction = 5000;
            }
            if (move == 149)
            {
                direction = WEST;
            }
            if (move == 150)
            {
                direction = WEST;
            }
            if (move == 151)
            {
                direction = 4000;
            }
             if (move == 152)
            {
                direction = 5000;
            }
            if (move == 153)
            {
                direction = WEST;
            }
            if (move == 154)
            {
                direction = WEST;
            }
            if (move == 155)
            {
                direction = 4000;
            }
            if (move == 156)
            {
                direction = 5000;
            }
            if (move == 157)
            {
                direction = SOUTH;
            }
            if (move == 158)
            {
                direction = SOUTH;
            }
            if (move == 159)
            {
                direction = 4000;
            }
            if (move == 160)
            {
                direction = 5000;
            }
            if (move == 161)
            {
                direction = SOUTH;
            }
            if (move == 162)
            {
                direction = SOUTH;
            }
            if (move == 163)
            {
                direction = SOUTH;
            }
            if (move == 164)
            {
                direction = SOUTH;
            }
            if (move == 165)
            {
                direction = 4000;
            }
            if (move == 166)
            {
                direction = 5000;
            }
            if (move == 167)
            {
                direction = WEST;
            }
            if (move == 168)
            {
                direction = WEST;
            }
            if (move == 169)
            {
                direction = 4000;
            }
            if (move == 170)
            {
                direction = 5000;
            }
            if (move == 171)
            {
                direction = WEST;
            }
            if (move == 172)
            {
                direction = WEST;
            }
            if (move == 173)
            {
                direction = 4000;
            }
            if (move == 174)
            {
                direction = 5000;
            }
            if (move == 175)
            {
                direction = WEST;
            }
            if (move == 176)
            {
                direction = WEST;
            }
            if (move == 177)
            {
                direction = 4000;
            }
            if (move == 178)
            {
                direction = 5000;
            }
            if (move == 179)
            {
                direction = SOUTH;
            }
            if (move == 180)
            {
                direction = SOUTH;
            }
            if (move == 181)
            {
                direction = 4000;
            }
            if (move == 182)
            {
                direction = 5000;
            }
            if (move == 183)
            {
                direction = SOUTH;
            }
            if (move == 184)
            {
                direction = SOUTH;
            }
            if (move == 185)
            {
                direction = 4000;
            }
            if (move == 186)
            {
                direction = 5000;
            }
            if (move == 187)
            {
                direction = EAST;
            }
            if (move == 188)
            {
                direction = EAST;
            }
            if (move == 189)
            {
                direction = EAST;
            }
            if (move == 190)
            {
                direction = SOUTH;
            }
            if (move == 191)
            {
                direction = SOUTH;
            }
            if (move == 192)
            {
                direction = SOUTH;
            }
            if (move == 193)
            {
                direction = 4000;
            }
            if (move == 194)
            {
                direction = 5000;
            }
            if (move == 195)
            {
                direction = SOUTH;
            }
            if (move == 196)
            {
                direction = WEST;
            }
            if (move == 197)
            {
                direction = WEST;
            }
            if (move == 198)
            {
                direction = 4000;
            }
            if (move == 199)
            {
                direction = 5000;
            }
            if (move == 200)
            {
                direction = WEST;
            }
            if (move == 201)
            {
                direction = WEST;
            }
            if (move == 202)
            {
                direction = 4000;
            }
            if (move == 203)
            {
                direction = 5000;
            }
            if (move == 204)
            {
                direction = SOUTH;
            }
            if (move == 205)
            {
                direction = SOUTH;
            }
            if (move == 206)
            {
                direction = 4000;
            }
            if (move == 207)
            {
                direction = 5000;
            }
            if (move == 208)
            {
                direction = SOUTH;
            }
            if (move == 209)
            {
                direction = SOUTH;
            }
            if (move == 210)
            {
                direction = 4000;
            }
            if (move == 211)
            {
                direction = 5000;
            }
            if (move == 212)
            {
                direction = EAST;
            }
            if (move == 213)
            {
                direction = EAST;
            }
            if (move == 214)
            {
                direction = 4000;
            }
            if (move == 215)
            {
                direction = 5000;
            }
            if (move == 216)
            {
                direction = NORTH;
            }
            if (move == 217)
            {
                direction = NORTH;
            }
            if (move == 218)
            {
                direction = 4000;
            }
            if (move == 219)
            {
                direction = 5000;
            }
            if (move ==220)
            {
                direction = EAST;
            }
            if (move ==221)
            {
                direction = EAST;
            }
            if (move ==222)
            {
                direction = NORTH;
            }
            if (move ==223)
            {
                direction = NORTH;
            }
            if (move ==224)
            {
                direction = NORTH;
            }
            if (move ==225)
            {
                direction = NORTH;
            }
            if (move ==226)
            {
                direction = NORTH;
            }
            if (move ==227)
            {
                direction = NORTH;
            }
            if (move ==228)
            {
                direction = NORTH;
            }
            if (move ==229)
            {
                direction = WEST;
            }if (move ==230)
            {
                direction = NORTH;
            }
            if (move ==231)
            {
                direction = NORTH;
            }
            if (move ==232)
            {
                direction = EAST;
            }
            if (move ==233)
            {
                direction = NORTH;
            }
            if (move ==234)
            {
                direction = NORTH;
            }
            if (move ==235)
            {
                direction = NORTH;
            }
            if (move ==236)
            {
                direction = NORTH;
            }
            if (move ==237)
            {
                direction = NORTH;
            }
            if (move ==238)
            {
                direction = WEST;
            }
            if (move ==239)
            {
                direction = 4000;
            }
            if (move ==240)
            {
                direction = 5000;
            }
            if (move ==241)
            {
                direction = WEST;
            }
            if (move ==242)
            {
                direction = WEST;
            }
            if (move ==243)
            {
                direction = 4000;
            }
            if (move ==244)
            {
                direction = 5000;
            }
            if (move ==245)
            {
                direction = NORTH;
            }
            if (move ==246)
            {
                direction = NORTH;
            }
            if (move ==247)
            {
                direction = NORTH;
            }
            if (move ==248)
            {
                direction = WEST;
            }
            if (move ==249)
            {
                direction = 4000;
            }
            if (move ==250)
            {
                direction = 5000;
            }
            if (move ==251)
            {
                direction = EAST;
            }
            if (move ==252)
            {
                direction = EAST;
            }
            if (move ==253)
            {
                direction = 4000;
            }
            if (move ==254)
            {
                direction = 5000;
            }
            if (move ==255)
            {
                direction = EAST;
            }
            if (move ==256)
            {
                direction = EAST;
            }
            if (move ==257)
            {
                direction = 4000;
            }
            if (move ==258)
            {
                direction = 5000;
            }
            if (move >258)
            {
                direction =5000 ;
            }
            
            
            
            
        ////////////////////////////// blocks off bottom left side
       
            ////////////////////////////////////////////////////////// 
        if(move<63)
                {
     if (getRow() == 6 && getCol() == 14) {
            direction = SOUTH;
        } else if (getRow() == 14 && getCol() == 14) {
            direction = WEST;
        } else if (getCol() == 10 && canMove(NORTH) && getRow() != 14) {
            direction = NORTH;
        } else if (getCol() == 10 && canMove(SOUTH) && getRow() != 6 ) {
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
        } else if (getRow() == 14 && getCol() != 6&& move<63) {
            direction = WEST;
        } else if (getCol() == 6&& counter < pass ) {
            direction = NORTH;
             //System.out.println("NORTH");
        } /////////////////////////////////////////// other special cases
        else if (getRow() == 14 && getCol() == 6) {
            direction = NORTH;
        } ////////////////////////////////////////////////// 
        ////////////////////////////////////////////////// special cases (inner four corners)
        ///////////////////////////////////////   four cardinal directions
        else if (getCol() == 11 && getRow() != 6) {
            direction = SOUTH;
        } else if (getCol() == 14 && getRow() != 6) {
            direction = SOUTH;
        } ////////////////////////////////
        else if (getCol() == 9 && getRow() != 6 ) {
            direction = NORTH;
        } else if (getCol() == 4 && getRow() != 7) {
            direction = NORTH;

        }

         ///////////////////////////////
        ////////////////////////////////
        ////////////////////////////////  other special cases 
        //System.out.println(direction);
        
    }
        return direction;
    }
    /**
     * Author: Aarib Moahammed Sets name to Aarb6i from the beginning but
     * changes to WINNER when score is 1500
     */
    public void name() {
        setName("Competition");
        if (getScore() >= 1500) {
            setName("WINNER");
        }

    }

    /**
     * Author: Aarib Mohammed Sets name to FIRST PLACE if the rounds it wins is
     * more than 50
     */
    public void rounds() {
        if (getRoundsWon() >= 50)
        {
            setName("MillionBot");
        }
        
    }

    public boolean canMove(int directionChoice) {
        int direction = directionChoice % 1000;

        Location myLoc = new Location(getRow(), getCol());
        Location next = myLoc.getAdjacentLocation(direction);
        if (!next.isValidLocation()) {
            return false;
        }

        GameObject onNext =  getArena()[next.getRow()][next.getCol()];
        if (onNext == null) {
            return true;
        } else {
            return false;
        }
    }
}
 