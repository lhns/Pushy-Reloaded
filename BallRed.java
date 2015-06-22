import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Red here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class BallRed extends Ball
{
    public void created() {
        super.created();
        setColor(1);
    }

    public int getId() {
        return 1;
    }
}
