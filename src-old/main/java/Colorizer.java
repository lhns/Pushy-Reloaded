import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Colorizer here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Colorizer extends Tool implements IColored
{
    public void edit(GameObj gameObj) {
        if (gameObj instanceof IColored) ((IColored)gameObj).setColor(getColor());
    }
    
    public void edited() {
        Sound.detect.play();
    }
    
    public String getColoredIcon(int color, boolean bool) {
        if (color==1) return "227.png";
        if (color==2) return "228.png";
        if (color==3) return "229.png";
        if (color==4) return "230.png";
        return "226.png";
    }
    
    public int getColor() {
        return 0;
    }
    
    public void setColor(int color) {
    }
}
