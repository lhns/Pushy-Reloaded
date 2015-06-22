import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class RenderMagicBlock here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class RenderMagicBlock extends RenderObj
{
    public void update() {
        remove();
    }
    
    public int getDelay() {
         return 100;
    }
}
