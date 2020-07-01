import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Dye here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Dye extends GameObj implements IColored
{
    public void update() 
    {
        setImage(getColoredIcon(getColor(), false));
    }
    
    public String getColoredIcon(int color, boolean bool) {
        if (color==1) return "15.bmp";
        if (color==2) return "16.bmp";
        if (color==3) return "115.bmp";
        if (color==4) return "17.bmp";
        return "102.bmp";
    }
    
    public int getColor() {
        return extraData.getInt("color");
    }
    
    public void setColor(int color) {
        extraData.setInt("color", color);
        setImage(getColoredIcon(color, false));
    }
    
    public boolean solidAgainst(GameObj obj) {
        return !(obj instanceof Pushy || obj instanceof Ball || obj instanceof LaserRay);
    }

    public boolean solidAgainstPost(GameObj obj) {
        if (obj instanceof Ball) {
            ((IColored)obj).setColor(getColor());
            remove();
            Sound.drop.play();
        }
        return false;
    }
    public int getRenderOrder() {
        return GameObj.R_Bck;
    }
}
