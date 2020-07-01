import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class PowerEmitter here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PowerEmitter extends PowerObj
{
    public boolean solidAgainst(GameObj obj) {
        return !(obj instanceof LaserRay);
    }

    public boolean oneDirectionalProvider() {
        return false;
    }
    
    public boolean canProvidePower() {
        return true;
    }
    
    public boolean isProvidingPower() {
        return true;
    }
    
    public int getId() {
        return 137;
    }
}
