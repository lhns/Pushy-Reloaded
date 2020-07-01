import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Projectile here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class RenderProjectile extends RenderObj
{
    public void created() {
        setDir(-1);
    }
    
    public void update()
    {
        if (getWorld().keyManager.isKeyHit("space")) {
            Sound.tone.play();
            setDir(getCreator().getDir());
        }
        if (getDir() >= 0) {
            if (new java.util.Random().nextInt(10)==0) move(getDir(), 1);
        } else {
            setLocation(getCreator().getX(), getCreator().getY());
        }
    }
    
    public boolean needsCreator() {
        return true;
    }
    
    public void lostCreator() {
        remove();
        new Projectile().addToWorld(getWorld(), getX(), getY());
    }
}
