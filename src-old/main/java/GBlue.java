import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class GBlue here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class GBlue extends ColorBlockGoal
{
    public void created() {
        super.created();
        extraData.putInt("color", 2);
    }
    
    public int getId() {
        return 113;
    }
}
