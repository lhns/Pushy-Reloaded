import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class House here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class House extends GameObj
{
    public boolean solidAgainst(GameObj obj) {
        return !(obj instanceof Pushy);
    }
    
    public boolean solidAgainstPost(GameObj obj) {
        if (obj instanceof Pushy) {
            if (getWorld().extraData.getInt("openGoals")<=0) {
                obj.remove();
                getWorld().win();
            }
        }
        return false;
    }
    
    public int getRenderOrder() {
        return GameObj.R_Bck;
    }
    
    public int getId() {
        return 7;
    }
}
