import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class GRed here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class GRed extends ColorBlockGoal
{
    public void created() {
        super.created();
        extraData.putInt("color", 1);
    }
    
    public int getId() {
        return 39;
    }
}
