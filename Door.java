import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Door here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Door extends GameObj
{
    public boolean solidAgainst(GameObj obj) {
        if (obj instanceof Pushy && obj.extraData.getInt("Key")>0) {
            remove();
            obj.extraData.putInt("Key", obj.extraData.getInt("Key")-1);
            Sound.drop.play();
            return false;
        }
        return true;
    }
    
    public int getId() {
        return 34;
    }
}
