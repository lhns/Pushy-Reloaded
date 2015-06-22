import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class PowerLightDetectorOn here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PowerLightDetectorOff extends PowerLightDetector
{
    public void created() {
        notProvidePower();
    }
    
    public int getId() {
        return 144;
    }
}
