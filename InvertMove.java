import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class InvertMove here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class InvertMove extends GameObj
{
    public boolean solidAgainst(GameObj obj) {
        return !(obj instanceof Pushy || obj instanceof LaserRay);
    }

    public boolean solidAgainstPost(GameObj obj) {
        if (obj instanceof Pushy) {
            remove();
            obj.extraData.putBoolean("Invert", true);
            Sound.drop.play();
        }
        return false;
    }
    
    public int getId() {
        return 25;
    }   
}
