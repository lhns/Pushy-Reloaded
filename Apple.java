import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Apple here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Apple extends GameObj
{
    public boolean solidAgainst(GameObj obj) {
        return !(obj instanceof Pushy || obj instanceof LaserRay);
    }
    
    public boolean solidAgainstPost(GameObj obj) {
        if (obj instanceof Pushy) {
            remove();
            Sound.drop.play();
        }
        return false;
    }
    
    public boolean pushable(GameObj obj) {
        return (obj instanceof PowerPistonHead);
    }
    
    public void created() {
        getWorld().openGoal();
    }
    
    public void onRemove() {
        getWorld().closeGoal();
    }
    
    public int getId() {
        return 21;
    }
}
