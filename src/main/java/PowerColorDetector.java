import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;

/**
 * Write a description of class PowerColorDetector here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PowerColorDetector extends PowerObj implements IColored
{
    public void update() 
    {
        super.update();
        setImage(getColoredIcon(getColor(), false));
        
        boolean found = false;
        List<GameObj> colorblocks = getObjectsAtOffset(xLookingOffset(getDir()), yLookingOffset(getDir()), ColorBlock.class);
        if (colorblocks.size()>0) {
            for (Object colorblock : colorblocks) {
                if (((IColored)colorblock).getColor()==getColor()) found = true;
            }
        }
        setProvidePower(found);
    }
    
    public String getColoredIcon(int color, boolean bool) {
        if (color==1) return "160.bmp";
        if (color==2) return "161.bmp";
        if (color==3) return "162.bmp";
        if (color==4) return "163.bmp";
        return "159.bmp";
    }
    
    public int getColor() {
        return extraData.getInt("color");
    }
    
    public void setColor(int color) {
        extraData.setInt("color", color);
        setImage(getColoredIcon(color, false));
    }
    
    public int getDelay() {
         return 200;
    }
    
    public boolean rotate() {
        return true;
    }
    
    public boolean oneDirectionalProvider() {
        return false;
    }
    
    public boolean canProvidePower() {
        return true;
    }
}
