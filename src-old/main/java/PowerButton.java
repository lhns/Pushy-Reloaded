import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class PowerButton here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PowerButton extends PowerObj
{
    public boolean solidAgainst(GameObj obj) {
        return false;
    }
    
    public boolean solidAgainstPost(GameObj obj) {
        if (!(obj instanceof Pushy)) {
            Sound.drop.play();
            setProvidePower(true);
        }
        return false;
    }
    
    public void onLeave(GameObj obj) {
        if (!(obj instanceof Pushy)) {
            Sound.drop.play();
            setProvidePower(false);
        }
    }
    
    public void onRemove() {
        Sound.drop.play();
        setProvidePower(false);
    }
    
    public boolean oneDirectionalProvider() {
        return false;
    }
    
    public int getId() {
        return 130;
    }
    
    public boolean canProvidePower() {
        return true;
    }
    
    public int getRenderOrder() {
        return GameObj.R_Bck;
    }
}
