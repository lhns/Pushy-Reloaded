import greenfoot.*;
import java.util.*;
/**
 * Write a description of class KeyManager here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class KeyManager  
{
    private List<String> keyPressed = new ArrayList<String>();
    
    public KeyManager()
    {
    }
    
    public boolean isKeyHit(String key) {
        if (Greenfoot.isKeyDown(key)) {
            if (!keyPressed.contains(key)) {
                keyPressed.add(key);
                return true;
            }
        } else {
            keyPressed.remove(key);
        }
        return false;
    }
    
    public boolean isKeyDown(String key) {
        if (Greenfoot.isKeyDown(key)) {
            if (!keyPressed.contains(key)) {
                keyPressed.add(key);
            }
            return true;
        } else {
            keyPressed.remove(key);
        }
        return false;
    }
    
    public boolean isKeyUp(String key) {
        if (Greenfoot.isKeyDown(key)) {
            if (!keyPressed.contains(key)) {
                keyPressed.add(key);
            }
        } else {
            keyPressed.remove(key);
            return true;
        }
        return false;
    }
    
    public void updateKey(String key) {
        if (Greenfoot.isKeyDown(key)) {
            if (!keyPressed.contains(key)) {
                keyPressed.add(key);
            }
        } else {
            keyPressed.remove(key);
        }
    }
    
    public void flushKeys() {
        keyPressed.clear();
    }
}
