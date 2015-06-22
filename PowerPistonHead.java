import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class PowerPistonHead here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PowerPistonHead extends GameObj
{
    boolean extended = false;

    public int getRenderOrder() {
        return GameObj.R_Mid;
    }
    
    public boolean rotate() {
        return true;
    }
    
    public boolean solidAgainst(GameObj obj) {
        return extended;
    }
}
