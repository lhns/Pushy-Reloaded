import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Remover here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Remover extends Tool
{
    public void edit(GameObj gameObj) {
        gameObj.remove();
    }
    
    public void edited() {
        Sound.drop.play();
    }
}
