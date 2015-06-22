import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class PowerRotatorLeft here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PowerRotatorLeft extends PowerRotator
{
    public void onPowerUpdate() {
        if (getPower()) {
            setImage("249.png");
            for (Object obj : getObjectsAtOffset(xLookingOffset(getDir()), yLookingOffset(getDir()), GameObj.class)) {
                GameObj gameObj = (GameObj) obj;
                gameObj.setDir((gameObj.getDir()+1)%4);
            }
        } else {
            setImage("246.png");
        }
    }
    
    public int getId() {
        return 178;
    }
}
