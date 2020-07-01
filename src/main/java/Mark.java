import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Mark here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Mark extends GameObj
{
    public boolean solidAgainst(GameObj obj) {
        return !(obj instanceof Pushy || obj instanceof LaserRay);
    }
    
    public boolean solidAgainstPost(GameObj obj) {
        if (obj instanceof Pushy) {
            if (!obj.extraData.getBoolean("marked")) {
                Sound.drop.play();
                obj.extraData.setBoolean("marked", true);
            }
        }
        return false;
    }
    
    public void onObjEvent(GameObj obj, String event) {
        if (event.equals("pushyMove")) {
            if (getWorld().getObjectsAt(obj.getX(), obj.getY(), null).size()==1 && ((Pushy)obj).extraData.getBoolean("marked")) {
                new Mark().addToWorld(getWorld(), obj.getX(), obj.getY());
            }
        }
    }

    public int getId() {
        return 11;
    }
}
