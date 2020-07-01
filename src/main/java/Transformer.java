import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Transformer here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Transformer extends GameObj
{
    public boolean solidAgainst(GameObj obj) {
        return !(obj instanceof Pushy || obj instanceof LaserRay);
    }
    
    public boolean solidAgainstPost(GameObj obj) {
        if (obj instanceof Pushy) {
            if (!obj.extraData.getBoolean("Charged")) {
                Sound.power.play();
                obj.extraData.putBoolean("Charged", true);
            } else {
                getWorld().loose();
            }
        }
        return false;
    }
    
    public int getRenderOrder() {
        return GameObj.R_Bck;
    }
    
    public int getId() {
        return 27;
    }
}
