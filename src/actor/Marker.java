package actor;

import java.awt.Color;

/**
 * A <code>Marker</code> is a GameObject that ages over time. 
 * Bots can place Markers on the board in multiple ways. <br />
 * Markers gain points for the Bot that placed them.  
 */

public class Marker extends GameObject
{
    private final Color DEFAULT_COLOR = Color.PINK;
    private final int MAX_AGE = 1000;
    private int age;
    
    /**
     * Constructs a pink tail.
     */
    public Marker()
    {
        age = 0;
        setColor(DEFAULT_COLOR);
    }

    /**
     * Constructs a tail of a given color.
     * @param initialColor the initial color of this marker
     */
    public Marker(Color initialColor)
    {
        age = 0;
        setColor(initialColor);
    }

    /**
     * Constructs a copy of this Marker.
     * @param in the Marker being copied.
     */
    public Marker(Marker in)
    {
        super(in);
        age = 0;
        setColor(in.getColor());
    }
    /**
     * Causes this Marker to age.
     */
    @Override
    public void act()
    {
        age++;
        if(age > MAX_AGE) age = MAX_AGE;
    }
       
    public int getValue()
    {
        return age/10;
    }
    public int getAge()
    {
        return age;
    }
    
    public void increaseAge(int amount)
    {
        age += amount;
        if(age > MAX_AGE) age = MAX_AGE;
    }
            
    
    @Override
    public String toString()
    {
        return "Marker age="+age;
    }
    
    @Override
    public GameObject getClone()
    {
        GameObject clone = new Marker(this);
        return clone;
    }

}
