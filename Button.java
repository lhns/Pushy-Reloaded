import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Button here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Button extends GameObj
{
    public boolean solidAgainst(GameObj obj) {
        return false;
    }
    
    public boolean solidAgainstPost(GameObj obj) {
        if (!(obj instanceof Pushy)) {
            getWorld().extraData.putBoolean("secretDoorsOpen", true);
            Sound.drop.play();
        }
        return false;
    }
    
    public void onLeave(GameObj obj) {
        if (!(obj instanceof Pushy)) {
            getWorld().extraData.putBoolean("secretDoorsOpen", false);
        }
    }
    
    public void onRemove() {
        getWorld().extraData.putBoolean("secretDoorsOpen", false);
    }
    
    public int getRenderOrder() {
        return GameObj.R_Bck;
    }
    
    public int getId() {
        return 13;
    }
}
