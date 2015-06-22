import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class ColorBlock here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ColorBlock extends GameObj implements IColored
{
    public void update() 
    {
        setImage(getColoredIcon(getColor(), false));
    }
    
    public boolean pushable(GameObj obj) {
        if (obj instanceof Pushy || obj instanceof PowerPistonHead) return true;
        return false;
    }
    
    public void onMove() {
        setColor(getNextColor());
        Sound.drop.play();
    }
    
    public String getColoredIcon(int color, boolean bool) {
        if (color==1) return "36.bmp";
        if (color==2) return "37.bmp";
        if (color==3) return "38.bmp";
        if (color==4) return "112.bmp";
        return "119.bmp";
    }
    
    public int getColor() {
        return extraData.getInt("color");
    }
    
    public void setColor(int color) {
        extraData.setInt("color", color);
        setImage(getColoredIcon(color, false));
    }
    
    public int getNextColor() {
        return getColor()>=3?1:getColor()+1;
    }
}
