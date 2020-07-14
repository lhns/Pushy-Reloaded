import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;

/**
 * Write a description of class PowerLaser here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PowerLaser extends PowerObj implements IColored
{
    public void update() {
        super.update();
        if (getPower()) {
            setLaser(getDir(), true);
        } else {
            setLaser(getDir(), false);
        }
    }
    
    public void onPowerUpdate() {
        if (getPower()) {
            setImage(getColoredIcon(getColor(), true));
            Sound.power.play();
        } else {
            setImage(getColoredIcon(getColor(), false));
            Sound.induct.play();
        }
    }
    
    public String getColoredIcon(int color, boolean power) {
        if (power && color == 0) return "174.png";
        if (power && color == 1) return "169.png";
        if (power && color == 2) return "177.png";
        if (power && color == 3) return "180.png";
        if (power && color == 4) return "178.png";
        if (!power && color == 1) return "168.png";
        if (!power && color == 2) return "176.png";
        if (!power && color == 3) return "181.png";
        if (!power && color == 4) return "179.png";
        return "175.png";
    }
    
    public int getColor() {
        return extraData.getInt("color");
    }
    
    public void setColor(int color) {
        extraData.setInt("color", color);
        setImage(getColoredIcon(color, getPower()));
    }
    
    public void setLaser(int dir, boolean val) {
        if (isOutOfWorld(getX()+xLookingOffset(dir), getY()+yLookingOffset(dir))) return;
        
        boolean existing = !val;
        for (Object obj : getObjectsAtOffset(xLookingOffset(dir), yLookingOffset(dir), LaserRay.class)) {
            if (isRayType((LaserRay)obj, dir, getColor())) {
                if (existing) {
                    ((GameObj)obj).remove();
                } else {
                    existing = true;
                }
            }
        }
        
        if (val) {
            if (!existing) {
                LaserRay laser = new LaserRay();
                laser.setColor(getColor());
                laser.addToWorld(getWorld(), getX(), getY(), dir);
                if (!laser.move(dir, 1)) getWorld().removeObject(laser);
            }
        }
    }
    
    public boolean isRayType(LaserRay obj, int dir, int color) {
        return obj.getDir()==dir && obj.getColor()==color;
    }
    
    public boolean isLaserType(PowerLaser obj, int dir, int color) {
        return obj.getDir()==dir && obj.getColor()==color;
    }

    public boolean rotate() {
        return true;
    }
    
    public int getDelay() {
         return 0;
    }
    
    public int getId() {
        return 150;
    }
}
