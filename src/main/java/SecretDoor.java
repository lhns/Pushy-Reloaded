import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class SecretDoor here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class SecretDoor extends GameObj
{
    
    public void update() 
    {
        if (getWorld().extraData.getBoolean("secretDoorsOpen")) {
            setImage("103.bmp");
        } else {
            setImage("14.bmp");
        }
    }
    
    public boolean solidAgainst(GameObj obj) {
        return !getWorld().extraData.getBoolean("secretDoorsOpen");
    }
    
    public int getRenderOrder() {
        return GameObj.R_Bck;
    }
    
    public int getId() {
        return 14;
    }
}
