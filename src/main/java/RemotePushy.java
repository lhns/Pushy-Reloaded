import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import org.lolhens.network.*;

/**
 * Write a description of class RemotePushy here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class RemotePushy extends Pushy
{
    public AbstractClient<?> client;

    public void update()
    {
        if (extraData.getBoolean("Charged")) {
            setImage("pushyChargedMP.png");
        } else {
            setImage("pushyMP.png");
        }
    }
    
    public boolean solidAgainst(GameObj obj) {
        return !(obj instanceof Pushy);
    }
    
    public int getId() {
        return 180;
    }
}
