import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class PowerPressButton here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PowerPressButton extends PowerSwitch
{
    boolean activated = true;
    
    public void update() {
        super.update();
        if (!activated && isProvidingPower()) {
            setProvidePower(false);
            setImage("153.png");
        }
        if (activated) activated = false;
    }
    
    public boolean solidAgainst(GameObj obj) {
        if (obj instanceof Pushy) {
            Sound.klick.play();
            setProvidePower(true);
            activated = true;
            setImage("154.png");
        }
        return true;
    }

    public void onPowerUpdate() {
        if (isProvidingPower()) {
            setImage("154.png");
        } else {
            setImage("153.png");
        }
    }
    
    public int getDelay() {
         return 80;
    }
    
    public int getId() {
        return 146;
    }
}
