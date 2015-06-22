import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class PowerSwitch here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PowerSwitch extends PowerObj
{
    public boolean solidAgainst(GameObj obj) {
        if (obj instanceof Pushy) {
            Sound.klick.play();
            setProvidePower(!isProvidingPower());
        }
        return true;
    }
    
    public void onPowerUpdate() {
        if (isProvidingPower()) {
            setImage("133.png");
        } else {
            setImage("132.png");
        }
    }
    
    public boolean oneDirectionalProvider() {
        return false;
    }
    
    public int getRenderOrder() {
        return GameObj.R_Wire;
    }
    
    public int getId() {
        return 128;
    }
    
    public boolean canProvidePower() {
        return true;
    }
}
