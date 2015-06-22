import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;

/**
 * Write a description of class LOn here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class LightOn extends GameObj
{
     public void update() {
         int[] checkdirs = new int[4];
         
         boolean off = false;
         boolean[] invalid = new boolean[4];
         
         checkdirs[3]=getDir(2);
         invalid[checkdirs[3]] = true;
         
         for (int i = 0; i<3; i++) {
             checkdirs[i] = getRandom(invalid);
             invalid[checkdirs[i]]=true;
         }
         
         boolean success = false;
         int lookdir = 0;
         for (int i = 0; i<4; i++) {
             lookdir = checkdirs[i];
             GameObj light = getLightAt(lookdir);
             if (light!=null) {
                 remove();
                 new LightOff().addToWorld(getWorld(), getX(), getY());
                 light.remove();
                 if (light instanceof LightOff) {
                     new LightOn().addToWorld(getWorld(), light.getX(), light.getY(), lookdir);
                 } else if (light instanceof PowerLightDetector) {
                     if (light instanceof PowerLightDetectorOff) {
                         new PowerLightDetector().addToWorld(getWorld(), light.getX(), light.getY(), lookdir);
                     } else {
                         new PowerLightDetectorOff().addToWorld(getWorld(), light.getX(), light.getY(), lookdir);
                     }
                 }
                 success = true;
                 break;
             }
         }
         if (!success) {
             remove();
             new LightFlashing().addToWorld(getWorld(), getX(), getY());
         }
     }
     
     private int getRandom(boolean[] invalid) {
         int ret = new Random().nextInt(invalid.length-1);
         while (invalid[ret]) ret = new Random().nextInt(invalid.length);
         return ret;
     }
     
     private GameObj getLightAt(int lookdir) {
         List<GameObj> lights = getObjectsAtOffset(xLookingOffset(lookdir), yLookingOffset(lookdir), LightOff.class);
         if (lights.size()>0) {
             for (GameObj obj : getObjectsAtOffset(xLookingOffset(lookdir), yLookingOffset(lookdir), GameObj.class)) {
                 if (obj.solidAgainst(this)) return null;
             }
             return (GameObj)lights.get(0);
         }
         lights = getObjectsAtOffset(xLookingOffset(lookdir), yLookingOffset(lookdir), PowerLightDetector.class);
         if (lights.size()>0) return (GameObj)lights.get(0);
         return null;
     }
     
     public void created() {
        if (!(this instanceof LightOff)) {
            getWorld().openGoal();
        }
     }
     
     public boolean solidAgainst(GameObj obj) {
        return false;
     }
     
     public int getDelay() {
         return 800;
     }
    
     public void onRemove() {
        if (!(this instanceof LightOff)) {
            getWorld().closeGoal();
        }
     }
    
     public int getId() {
        return 29;
     }
     
     public int getRenderOrder() {
        return GameObj.R_Bck;
     }
}
