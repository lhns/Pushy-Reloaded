import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Cleaner here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Cleaner extends Remover
{
    public void edited() {
        getWorld().clean();
        Sound.drop.play();
    }  
}
