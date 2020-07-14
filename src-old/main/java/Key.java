import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Key here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Key extends GameObj
{
    public boolean solidAgainst(GameObj obj) {
        return !(obj instanceof Pushy || obj instanceof LaserRay);
    }
    
    public boolean solidAgainstPost(GameObj obj) {
        if (obj instanceof Pushy) {
            remove();
            obj.extraData.putInt("Key", obj.extraData.getInt("Key")+1);
            Sound.drop.play();
        }
        return false;
    }
    
    public boolean pushable(GameObj obj) {
        return (obj instanceof PowerPistonHead);
    }
    
    public int getId() {
        return 33;
    }
    
    public int getRenderOrder() {
        return GameObj.R_Bck;
    }
}
