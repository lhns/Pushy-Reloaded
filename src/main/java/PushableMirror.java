import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class PushableMirror here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PushableMirror extends Mirror
{
    public boolean pushable(GameObj obj) {
        return (obj instanceof Pushy || obj instanceof PowerPistonHead);
    }
    
    public int getId() {
        return 171;
    }
}
