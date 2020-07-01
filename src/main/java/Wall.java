import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Wall here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Wall extends GameObj
{
    public int getRenderOrder() {
        return GameObj.R_Bck;
    }
    
    public int getId() {
        return 12;
    }
    
}
