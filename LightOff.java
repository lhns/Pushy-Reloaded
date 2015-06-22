import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class LOff here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class LightOff extends LightOn
{
    public void update() {
    }
    
    public boolean solidAgainst(GameObj obj) {
        if (obj instanceof Pushy && obj.extraData.getBoolean("Charged")) {
            obj.extraData.setBoolean("Charged", false);
            Sound.power.play();
            remove();
            new LightOn().addToWorld(getWorld(), getX(), getY());
        }
        return false;
     }
     
     public int getId() {
        return 31;
    } 
}
