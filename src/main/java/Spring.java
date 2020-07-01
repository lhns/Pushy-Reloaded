import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Spring here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Spring extends GameObj
{
    public boolean solidAgainst(GameObj obj) {
        if (obj instanceof Pushy) {
            return false;
        }
        return !(obj instanceof LaserRay);
    }
    
    public boolean solidAgainstPost(GameObj obj) {
        if (obj instanceof Pushy) {
            //obj.addLocation(xLookingOffset(obj.getDir()), yLookingOffset(obj.getDir()));
            obj.move(1, 1);
            Sound.tone.play();
        }
        return false;
    }
    
    public int getId() {
        return 114;
    }
}
