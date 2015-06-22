import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class ButtonSmall here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ButtonSmall extends Button
{    
    public boolean solidAgainstPost(GameObj obj) {
        getWorld().extraData.putBoolean("secretDoorsOpen", true);
        Sound.drop.play();
        return false;
    }
    
    public void onLeave(GameObj obj) {
        getWorld().extraData.putBoolean("secretDoorsOpen", false);
    }
    
    public int getId() {
        return 172;
    }
}
