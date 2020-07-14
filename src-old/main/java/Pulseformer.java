import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Pulseformer here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Pulseformer extends PowerPressButton
{
    boolean gettingPower = false;
    
    public void onPowerUpdate() {
        if (getPower()) {
            if (!gettingPower) {
                Sound.klick.play();
                setProvidePower(true);
                activated = true;
                gettingPower = true;
            }
            if (isProvidingPower()) {
                setImage("212.png");
            } else {
                setImage("210.png");
            }
        } else {
            gettingPower = false;
            if (isProvidingPower()) {
                setImage("211.png");
            } else {
                setImage("209.png");
            }
        }
    }
    
    public boolean solidAgainst(GameObj obj) {
        if (obj instanceof Pushy) {
            Sound.klick.play();
            setProvidePower(true);
            activated = true;
            if (getPower()) {
                setImage("212.png");
            } else {
                setImage("211.png");
            }
        }
        return true;
    }
    
    public int getId() {
        return 160;
    }
}
