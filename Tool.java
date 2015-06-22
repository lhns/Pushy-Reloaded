import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;

/**
 * Write a description of class Tool here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Tool extends GameObj
{
    public static boolean singleMode = true;

    public void created() {
        Greenfoot.start();
        setDelay(80);
        if (singleMode) {
            GameObj obj = getWorld().getTopmostGameObjAt(getX(), getY());
            if (obj != null) edit(obj);
        } else {
            List<GameObj> objects = getObjectsAtOffset(0, 0, GameObj.class);
            objects.remove(this);
            for (GameObj obj : objects) {
                edit(obj);
            }
        }
        edited();
    }
    
    public void update() {
        getWorld().removeObject(this);
        //Greenfoot.stop();
    }
    
    public boolean solidAgainst(GameObj obj) {
        return false;
    }
    
    public int getRenderOrder() {
        return GameObj.R_Max;
    }
    
    public void edit(GameObj gameObj) {
        
    }
    
    public void edited() {
        
    }
}
