import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class DartBoard here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class DartBoard extends GameObj
{
    public boolean solidAgainst(GameObj obj) {
        return !(obj instanceof LaserRay);
    }

    public boolean pushable(GameObj obj) {
        if (obj instanceof RenderProjectile) {
            remove();
            Sound.drop.play();
        }
        return (obj instanceof Pushy || obj instanceof PowerPistonHead);
    }
    
    public void created() {
        getWorld().openGoal();
    }
    
    public void onRemove() {
        getWorld().closeGoal();
    }
    
    public int getId() {
        return 28;
    }
}
