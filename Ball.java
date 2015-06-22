import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Ball here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Ball extends GameObj implements IColored
{
    public void update() 
    {
        setImage(getColoredIcon(getColor(), false));
    }
    
    public String getColoredIcon(int color, boolean bool) {
        if (color==1) return "1.png";
        if (color==2) return "2.png";
        if (color==3) return "113.png";
        if (color==4) return "3.png";
        return "100.png";
    }
    
    public int getColor() {
        return extraData.getInt("color");
    }
    
    public void setColor(int color) {
        extraData.setInt("color", color);
        setImage(getColoredIcon(color, false));
    }

    public boolean pushable(GameObj obj) {
        return (obj instanceof Pushy || obj instanceof PowerPistonHead);
    }
    
    public void created() {
        getWorld().openGoal();
    }
    
    public void onRemove() {
        getWorld().closeGoal();
    }
}
