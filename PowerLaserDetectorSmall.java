import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class PowerLaserDetectorSmall here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PowerLaserDetectorSmall extends PowerLaserDetector
{
    public String getColoredIcon(int color, boolean power) {
        if (power && color == 0) return "217.png";
        if (power && color == 1) return "218.png";
        if (power && color == 2) return "219.png";
        if (power && color == 3) return "220.png";
        if (power && color == 4) return "221.png";
        if (!power && color == 1) return "222.png";
        if (!power && color == 2) return "223.png";
        if (!power && color == 3) return "224.png";
        if (!power && color == 4) return "225.png";
        return "231.png";
    }
    
    public boolean solidAgainst(GameObj obj) {
        return !(obj instanceof LaserRay);
    }
    
    public int getId() {
        return 165;
    }
}
