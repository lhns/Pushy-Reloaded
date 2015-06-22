import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class GameMenu here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class GameMenu extends World
{
    public PushyWorld pushyWorld;
    /**
     * Constructor for objects of class GameMenu.
     * 
     */
    public GameMenu()
    {    
        super(35, 28, 32); 
        addObject(new Menu(), 2, 0);
    }
}
