import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class PowerLight here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PowerLight extends PowerObj implements IColored
{
    public void created() {
        super.created();
        setImage("148.bmp");
    }

    public void onPowerUpdate() {
        setImage(getColoredIcon(getColor(), getPower()));
    }
    
    public String getColoredIcon(int color, boolean power) {
        if (power && color == 0) return "147.bmp";
        if (power && color == 1) return "240.png";
        if (power && color == 2) return "244.png";
        if (power && color == 3) return "238.png";
        if (power && color == 4) return "242.png";
        if (!power && color == 1) return "239.png";
        if (!power && color == 2) return "243.png";
        if (!power && color == 3) return "237.png";
        if (!power && color == 4) return "241.png";
        return "148.bmp";
    }
    
    public int getColor() {
        return extraData.getInt("color");
    }
    
    public void setColor(int color) {
        extraData.setInt("color", color);
        setImage(getColoredIcon(color, getPower()));
    }
    
    public boolean solidAgainst(GameObj obj) {
        return false;
    }
    
    public int getId() {
        return 131;
    }
    
    public int getRenderOrder() {
        return GameObj.R_Bck;
    }
}
