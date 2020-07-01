import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class PowerRepeaterPushable here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PowerRepeaterPushable extends PowerRepeater
{
    boolean currentlyPushed = false;
    
    public void onPowerUpdate() {
        if (getPower()) {
            Sound.induct.play();
            setImage("156.bmp");
        } else {
            setImage("155.bmp");
        }
    }
    
    public boolean solidAgainst(GameObj obj) {
        return true;
    }
    
    public boolean pushable(GameObj obj) {
        if (obj instanceof Pushy || obj instanceof PowerPistonHead) {
            currentlyPushed = true;
            updateAllOtherPower();
            currentlyPushed = false;
            return true;
        }
        return false;
    }
    
    public void onMove() {
        updatePower();
        updateAllOtherPower();
    }
    
    public void onRotate(int newDir) {
        updatePower();
        updateAllOtherPower();
    }
    
    public boolean isProvidingPower() {
        if (currentlyPushed) return false;
        return super.isProvidingPower();
    }
    
    public int getId() {
        return 136;
    }
}
