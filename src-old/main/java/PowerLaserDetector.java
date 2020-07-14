import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class PowerLaserDetector here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PowerLaserDetector extends PowerObj implements IColored
{
    public void update() {
        super.update();
        boolean active = isActive();
        setImage(getColoredIcon(getColor(), active));
        setProvidePower(active);
    }
    
    public String getColoredIcon(int color, boolean power) {
        if (power && color == 0) return "186.png";
        if (power && color == 1) return "187.png";
        if (power && color == 2) return "188.png";
        if (power && color == 3) return "189.png";
        if (power && color == 4) return "190.png";
        if (!power && color == 1) return "192.png";
        if (!power && color == 2) return "193.png";
        if (!power && color == 3) return "194.png";
        if (!power && color == 4) return "195.png";
        return "191.png";
    }
    
    public int getColor() {
        return extraData.getInt("color");
    }
    
    public void setColor(int color) {
        extraData.setInt("color", color);
        setImage(getColoredIcon(color, isActive()));
    }
    
    public boolean isActive() {
        for (int dir = 0; dir<=3; dir++) {
            for (Object obj : getObjectsAtOffset(xLookingOffset(dir), yLookingOffset(dir), LaserRay.class)) {
                if (isRayType((LaserRay)obj, (dir+2)%4, getColor())) return true;
            }
        }
        return false;
    }
    
    public void onPowerUpdate() {
        if (isProvidingPower()) Sound.detect.play();
    }
    
    public boolean isRayType(LaserRay obj, int dir, int color) {
        return obj.getDir()==dir && obj.getColor()==getColor();
    }
    
    public boolean oneDirectionalProvider() {
        return false;
    }
    
    public boolean canProvidePower() {
        return true;
    }
    
    public boolean pushable(GameObj obj) {
        return (obj instanceof PowerPistonHead);
    }
    
    public int getId() {
        return 155;
    }
}
