import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Rotator here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Rotator extends Tool
{
    public void edit(GameObj gameObj) {
        gameObj.setDir((gameObj.getDir()+1)%4);
    }
    
    public void edited() {
        Sound.crack.play();
    }
}
