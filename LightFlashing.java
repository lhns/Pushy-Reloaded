import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class LightFlashing here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class LightFlashing extends LightOn
{
    public void update() {
         remove();
         new LightOff().addToWorld(getWorld(), getX(), getY());
    }
    
    public int getId() {
        return 32;
    } 
}
