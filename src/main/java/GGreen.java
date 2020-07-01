import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class GGreen here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class GGreen extends ColorBlockGoal
{
    public void created() {
        super.created();
        extraData.putInt("color", 4);
    }
    
    public int getId() {
        return 111;
    }
}
