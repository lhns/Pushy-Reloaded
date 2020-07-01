import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Hole here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Hole extends GameObj implements IColored
{
    public void update() 
    {
        setImage(getColoredIcon(getColor(), false));
    }
    
    public String getColoredIcon(int color, boolean bool) {
        if (color==1) return "4.bmp";
        if (color==2) return "5.bmp";
        if (color==3) return "114.bmp";
        if (color==4) return "6.bmp";
        return "101.bmp";
    }
    
    public int getColor() {
        return extraData.getInt("color");
    }
    
    public void setColor(int color) {
        extraData.setInt("color", color);
        setImage(getColoredIcon(color, false));
    }
    
    public boolean solidAgainst(GameObj obj) {
        if (obj instanceof Pushy || obj instanceof LaserRay) return false;
        if (obj instanceof Ball) {
            if (getColor()==((IColored)obj).getColor()) {
                return false;
            }
        }
        return true;
    }
    
    public boolean solidAgainstPost(GameObj obj) {
        if (obj instanceof Ball) {
            if (getColor()==((IColored)obj).getColor()) {
                obj.remove();
                Sound.drop.play();
            }
        }
        return false;
    }
    public int getRenderOrder() {
        return GameObj.R_Bck;
    }
}