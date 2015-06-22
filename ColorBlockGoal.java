import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class ColorBlockGoal here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ColorBlockGoal extends GameObj implements IColored
{
    public void update() 
    {
        setImage(getColoredIcon(getColor(), false));
    }
    
    public String getColoredIcon(int color, boolean bool) {
        if (color==1) return "39.bmp";
        if (color==2) return "117.bmp";
        if (color==3) return "116.bmp";
        if (color==4) return "118.bmp";
        return "120.bmp";
    }
    
    public int getColor() {
        return extraData.getInt("color");
    }
    
    public void setColor(int color) {
        extraData.setInt("color", color);
        setImage(getColoredIcon(color, false));
    }

    public void created() {
        getWorld().openGoal();
    }
    
    public boolean solidAgainst(GameObj obj) {
        if (obj instanceof ColorBlock && getColor()==((ColorBlock)obj).getNextColor()) return false;
        if (obj instanceof Pushy || obj instanceof LaserRay) return false;
        return true;
    }
    
    public boolean solidAgainstPost(GameObj obj) {
        if (obj instanceof ColorBlock && getColor()==((ColorBlock)obj).getNextColor()) {
            getWorld().closeGoal();
        }
        return false;
    }
    
    public void onLeave(GameObj obj) {
        if (obj instanceof ColorBlock && ((IColored)obj).getColor()==getColor()) getWorld().openGoal();
    }
    
    public int getRenderOrder() {
        return GameObj.R_Bck;
    }
}
