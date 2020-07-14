import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class PowerButtonLight here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PowerButtonSmall extends PowerButton
{
    public boolean solidAgainst(GameObj obj) {
        return false;
    }
    
    public boolean solidAgainstPost(GameObj obj) {
        Sound.klick.play();
        setProvidePower(true);
        return false;
    }
    
    public void onLeave(GameObj obj) {
        Sound.klick.play();
        setProvidePower(false);
    }
    
    public int getId() {
        return 138;
    }
}
