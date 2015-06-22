import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class PowerDartBoard here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PowerDartBoard extends PowerObj
{
    public boolean pushable(GameObj obj) {
        if (obj instanceof RenderProjectile) {
            setProvidePower(true);
            setImage("165.bmp");
            Sound.drop.play();
        }
        return false;
    }
    
    public boolean solidAgainst(GameObj obj) {
        return !(obj instanceof LaserRay);
    }
    
    public boolean oneDirectionalProvider() {
        return false;
    }
    
    public boolean canProvidePower() {
        return true;
    }
    
    public int getId() {
        return 143;
    }
}
