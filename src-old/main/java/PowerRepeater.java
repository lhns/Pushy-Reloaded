import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;

/**
 * Write a description of class PowerRepeater here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PowerRepeater extends PowerObj
{
    public boolean rotate() {
        return true;
    }
    
    public void onPowerUpdate() {
        if (getPower()) {
            Sound.induct.play();
            setImage("126.png");
        } else {
            setImage("125.png");
        }
    }
    
    public boolean isProvidingPower() {
        return getPower();
    }
    
    public boolean solidAgainst(GameObj obj) {
        return false;
    }
    
    public int getRenderOrder() {
        return GameObj.R_Wire;
    }
    
    public int getId() {
        return 124;
    }
    
    public boolean canProvidePower() {
        return true;
    }
}
