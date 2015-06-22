import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;

/**
 * Write a description of class MotionSensor here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MotionSensor extends GameObj
{
    public void update() 
    {
        if (extraData.getBoolean("closed")) {
            setImage("23.bmp");
        } else if (extraData.getBoolean("active")) {
            setImage("109.bmp");
        } else {
            setImage("20.bmp");
        }
    }
    
    public void created() {
        getWorld().openGoal();
    }
    
    public boolean solidAgainst(GameObj obj) {
        if (obj instanceof Pushy && !extraData.getBoolean("active")) {
            extraData.putBoolean("active", true);
            Sound.power.play();
            new RenderSensor().addToWorld(getWorld(), getX(), getY(), this);
            List<Actor> sensors = getWorld().getObjects(MotionSensor.class);
            sensors.remove(this);
            for (Actor actor : sensors) {
                MotionSensor sensor = (MotionSensor)actor;
                if (!sensor.extraData.getBoolean("closed") && sensor.extraData.getBoolean("active")) {
                    extraData.putBoolean("closed", true);
                    sensor.extraData.putBoolean("closed", true);
                    getWorld().closeGoal();
                    getWorld().closeGoal();
                    Sound.drop.play();
                    return false;
                }
            }
        }
        return extraData.getBoolean("closed");
    }
    
    public int getRenderOrder() {
        return GameObj.R_Bck;
    }
    
    public int getId() {
        return 20;
    }
}
