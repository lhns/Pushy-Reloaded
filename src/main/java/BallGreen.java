import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Green here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class BallGreen extends Ball
{
    public void created() {
        super.created();
        setColor(4);
    }

    public int getId() {
        return 3;
    }
}
