import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class GYellow here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class GYellow extends ColorBlockGoal
{
    public void created() {
        super.created();
        extraData.putInt("color", 3);
    }
    
    public int getId() {
        return 112;
    }
}
