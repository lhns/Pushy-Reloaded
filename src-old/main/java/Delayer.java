import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Delayer here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Delayer extends PowerSwitch
{
    boolean gettingPower = false;
    boolean update = false;
    
    public void update() {
        super.update();
        if (!update && getPower()!=gettingPower) {
            gettingPower = !gettingPower;
            if (getPower()) {
                setProvidePower(true);
                setImage("216.png");
            } else {
                setProvidePower(false);
                setImage("215.png");
            }
        }
        if (update) update = false;
    }
    
    public boolean solidAgainst(GameObj obj) {
        return true;
    }

    public void onPowerUpdate() {
        if (getPower() != gettingPower) update = true;
    }
    
    public int getDelay() {
         return 80;
    }
    
    public int getId() {
        return 162;
    }
}
