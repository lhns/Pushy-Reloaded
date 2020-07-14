import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Blue here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class BallBlue extends Ball
{
    public void created() {
        super.created();
        setColor(2);
    }

    public int getId() {
        return 2;
    }
}
