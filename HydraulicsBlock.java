import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class HydraulicsBlock here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class HydraulicsBlock extends PowerPistonHead
{
    public boolean pushable(GameObj obj) {
        return true;
    }
    
    public int getRenderOrder() {
        return GameObj.R_Mid;
    }
    
    public boolean rotate() {
        return false;
    }
    
    public boolean solidAgainst(GameObj obj) {
        return true;
    }
    
    public int getId() {
        return 163;
    }
}
