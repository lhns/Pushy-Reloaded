import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;
/**
 * Write a description of class Teleporter here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Teleporter extends GameObj
{
    public boolean solidAgainst(GameObj obj) {
        if (obj instanceof Pushy || obj instanceof Apple || obj instanceof Key || obj instanceof LaserRay) {
            return getWorld().getObjects(Teleporter.class).size()<=1;
        }
        return true;
    }
    
    public boolean solidAgainstPost(GameObj obj) {
        if (obj instanceof Pushy || obj instanceof Apple || obj instanceof Key) {
            List<Teleporter> teles = getWorld().getObjects(Teleporter.class);
            teles.remove(this);
            if (teles.size()==0) return false;
            Teleporter tele = null;
            while (tele == this || tele == null) {
                tele = (Teleporter)teles.get((new Random()).nextInt(teles.size()));
            }
            obj.setLocation(tele.getX(), tele.getY());
            Sound.drop.play();
        }
        return true;
    }
    
    public int getRenderOrder() {
        return GameObj.R_Bck;
    }
    
    public int getId() {
        return 8;
    }
}
