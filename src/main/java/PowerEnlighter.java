import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;

/**
 * Write a description of class PowerEnlighter here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PowerEnlighter extends PowerObj
{
    public void onPowerUpdate() {
        if (getPower()) {
            setImage("214.png");
            List<GameObj> lights = getObjectsAtOffset(0, 0, LightOff.class);
            if (lights.size()>0) {
                lights.get(0).remove();
                new LightOn().addToWorld(getWorld(), getX(), getY());
            }
            } else {
            setImage("213.png");
        }
    }
    
    public boolean solidAgainst(GameObj obj) {
        return false;
    }
    
    public int getId() {
        return 161;
    }
}
