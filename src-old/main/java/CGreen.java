import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Red here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class CGreen extends ColorBlock
{
    public int getNextColor() {
        return getColor()>=4?1:getColor()+1;
    }

    public void created() {
        setColor(4);
    }
    
    public int getId() {
        return 110;
    } 
}
