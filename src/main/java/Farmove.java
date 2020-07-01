import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Farmove here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Farmove extends GameObj
{
    public boolean solidAgainst(GameObj obj) {
        return !(obj instanceof Pushy || obj instanceof LaserRay);
    }

    public boolean solidAgainstPost(GameObj obj) {
        if (obj instanceof Pushy) {
            remove();
            obj.extraData.putBoolean("Farmove", true);
            Sound.drop.play();
        }
        return false;
    }
    
    public int getId() {
        return 35;
    }
}
