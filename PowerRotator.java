import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class PowerRotator here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PowerRotator extends PowerObj
{
    public void onPowerUpdate() {
        if (getPower()) {
            setImage("248.png");
            for (Object obj : getObjectsAtOffset(xLookingOffset(getDir()), yLookingOffset(getDir()), GameObj.class)) {
                GameObj gameObj = (GameObj) obj;
                gameObj.setDir((gameObj.getDir()+3)%4);
            }
        } else {
            setImage("245.png");
        }
    }
    
    public boolean rotate() {
        return true;
    }
    
    public boolean canReceivePowerThroughFront(boolean directional) {
        return false;
    }
    
    public int getId() {
        return 177;
    }
}
