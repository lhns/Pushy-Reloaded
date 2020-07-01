import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class PowerInverter here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PowerInverter extends PowerObj
{
    public boolean rotate() {
        return true;
    }
    
    public void onPowerUpdate() {
        if (getPower()) {
            Sound.induct.play();
            setImage("131.png");
        } else {
            setImage("130.png");
        }
    }
    
    public boolean isProvidingPower() {
        return !getPower();
    }
    
    public boolean solidAgainst(GameObj obj) {
        return false;
    }
    
    public int getRenderOrder() {
        return GameObj.R_Wire;
    }
    
    public int getId() {
        return 120;
    }
    
    public boolean canProvidePower() {
        return true;
    }
}
