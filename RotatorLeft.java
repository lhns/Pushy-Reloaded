import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class RotatorLeft here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class RotatorLeft extends Rotator
{
    public void edit(GameObj gameObj) {
        gameObj.setDir((gameObj.getDir()+3)%4);
    }   
}
