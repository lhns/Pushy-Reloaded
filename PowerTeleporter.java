import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;

/**
 * Write a description of class PowerTeleporter here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PowerTeleporter extends PowerObj
{
    public void update() {
        super.update();
    }
    
    public boolean solidAgainst(GameObj obj) {
        if (obj instanceof Pushy || obj instanceof Apple || obj instanceof Key || obj instanceof LaserRay) {
            return false;
        }
        return true;
    }
    
    public void onPowerUpdate() {
        if (getPower()) {
            teleport();
            setImage("233.bmp");
        } else {
            setImage("232.bmp");
        }
    }
    
    public int getRenderOrder() {
        return GameObj.R_Bck;
    }
    
    public void teleport() {
        for (Object obj : getWorld().getObjects(Pushy.class)) {
            ((Pushy)obj).setLocation(getX(), getY());
        }
    }
    
    public int getId() {
        return 170;
    }
}
