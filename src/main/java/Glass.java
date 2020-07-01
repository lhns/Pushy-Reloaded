import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Glass here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Glass extends GameObj
{
    public boolean solidAgainst(GameObj obj) {
        return !(obj instanceof LaserRay || obj instanceof LightOn);
    }

    public int getId() {
        return 147;
    }
    
    public int getRenderOrder() {
        return GameObj.R_Frnt;
    }
}
