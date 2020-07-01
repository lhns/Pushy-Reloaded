import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class PowerDoor here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PowerDoor extends PowerObj
{
    boolean open = false;
    
    public void onPowerUpdate() {
        boolean wasOpen = open;
        if (getPower()) {
            setImage("103.bmp");
            open = true;
        } else {
            setImage("129.bmp");
            open = false;
        }
        if (wasOpen != open) {
            Sound.drop.play();
        }
    }
    
    public boolean solidAgainst(GameObj obj) {
        return !getPower();
    }
    
    public int getRenderOrder() {
        return GameObj.R_Bck;
    }
    
    public int getId() {
        return 129;
    }
}
