import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class PowerLightDetector here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PowerLightDetector extends PowerObj
{
    public void created() {
        super.created();
        setProvidePower(true);
    }
    
    public void notProvidePower() {
        super.created();
    }
    
    public boolean oneDirectionalProvider() {
        return false;
    }
    
    public boolean solidAgainst(GameObj obj) {
        return false;
    }
    
    public boolean canProvidePower() {
        return true;
    }
    
    public int getId() {
        return 164;
    }
    
    public int getRenderOrder() {
        return GameObj.R_Bck;
    }
}
