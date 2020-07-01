import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class BYellow here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class BallYellow extends Ball
{
    public void created() {
        super.created();
        setColor(3);
    }

    public int getId() {
        return 101;
    }   
}
