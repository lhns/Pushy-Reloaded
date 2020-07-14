import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Netifier here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Netifier extends Tool
{
    public void edit(GameObj gameObj) {
        if (gameObj instanceof PowerObj) ((PowerObj)gameObj).setNetwork(getNetwork());
    }
    
    public void edited() {
        Sound.power.play();
    }
    
    public int getNetwork() {
        return 0;
    }
}
