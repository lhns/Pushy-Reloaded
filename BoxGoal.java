import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class BoxGoal here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class BoxGoal extends GameObj
{
    public void created() {
        getWorld().openGoal();
    }
    
    public boolean solidAgainst(GameObj obj) {
        return false;
    }
    
    public boolean solidAgainstPost(GameObj obj) {
        if (obj instanceof Box) {
            getWorld().closeGoal();
            Sound.drop.play();
        }
        return false;
    }
    
    public void onLeave(GameObj obj) {
        if (obj instanceof Box) {
            getWorld().openGoal();
        }
    }
    
    public int getRenderOrder() {
        return GameObj.R_Bck;
    }
    
    public int getId() {
        return 18;
    }
}
