import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class PowerLatch here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PowerLatch extends PowerSwitch
{
    boolean wasGettingPower = false;

    public void onPowerUpdate() {
        if (getPower()) {
            if (isProvidingPower()) {
                setImage("152.png");
            } else {
                setImage("151.png");
            }
        } else {
            if (isProvidingPower()) {
                setImage("150.png");
            } else {
                setImage("149.png");
            }
        }
        if (getPower() && !wasGettingPower) {
            Sound.klick.play();
            setProvidePower(!isProvidingPower());
        }
        wasGettingPower = getPower();
    }
    
    public int getId() {
        return 145;
    }
}
