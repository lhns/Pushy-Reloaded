import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Projectile here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Projectile extends GameObj
{
    public boolean solidAgainst(GameObj obj) {
        return !(obj instanceof Pushy || obj instanceof PowerPistonHead || obj instanceof LaserRay);
    }
    
    public boolean solidAgainstPost(GameObj obj) {
        if (obj instanceof Pushy) {
            remove();
            new RenderProjectile().addToWorld(getWorld(), getX(), getY(), obj);
        } else if (obj instanceof PowerPistonHead) {
            remove();
            Sound.tone.play();
            new RenderProjectile().addToWorld(getWorld(), getX(), getY(), obj.getDir(), obj);
        }
        return false;
    }
    
    public int getId() {
        return 26;
    }
}
