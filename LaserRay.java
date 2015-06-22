import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class LaserRay here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class LaserRay extends GameObj implements IColored
{
    public void update() {
        if (!canStay((LaserRay)this) || !isEmitted()) {
            remove();
        } else {
            setLaser(getDir(), true);
        }
    }
    
    public String getColoredIcon(int color, boolean bool) {
        if (color == 1) return "170.png";
        if (color == 2) return "182.png";
        if (color == 3) return "183.png";
        if (color == 4) return "184.png";
        return "185.png";
    }
    
    public int getColor() {
        return extraData.getInt("color");
    }
    
    public void setColor(int color) {
        extraData.setInt("color", color);
        setImage(getColoredIcon(color, false));
    }
    
    public boolean solidAgainst(GameObj obj) {
        return false;
    }

    public boolean isEmitted() {
        boolean emitted = false, blocked = false;
        for (Object obj : getObjectsAtOffset(xLookingOffset(getDir(2)), yLookingOffset(getDir(2)), GameObj.class)) {
            if (obj instanceof LaserRay && isRayType((LaserRay)obj, getDir(), getColor())) emitted = true;
            if (obj instanceof PowerLaser && isLaserType((PowerLaser)obj, getDir(), getColor())) emitted = true;
            if (obj instanceof Mirror && ((Mirror)obj).canReflect(getDir()) && !((Mirror)obj).isReflecting(getDir(), getColor())) blocked = true;
        }
        for (Object obj : getObjectsAtOffset(0, 0, GameObj.class)) {
            if (obj instanceof Prism && ((Prism)obj).isReflecting(getDir(), getColor())) {
                emitted = true;
                blocked = false;
            }
        }
        return emitted && !blocked;
    }
    
    public void setLaser(int dir, boolean val) {
        if (isOutOfWorld(getX()+xLookingOffset(dir), getY()+yLookingOffset(dir))) return;
        
        Prism prism = null;
        if (getObjectsAtOffset(0, 0, Prism.class).size()>0) {
            prism = (Prism)getObjectsAtOffset(0, 0, Prism.class).get(0);
            if (!prism.canReflect(dir)) prism = null;
        }
        
        boolean existing = true, existingMirrored = true;
        if (prism == null || !(prism instanceof Mirror)) {
            existing = false;
            for (Object obj : getObjectsAtOffset(xLookingOffset(dir), yLookingOffset(dir), LaserRay.class)) {
                if (isRayType((LaserRay)obj, dir, getColor())) {
                    if (existing) {
                        ((GameObj)obj).remove();
                    } else {
                        existing = true;
                    }
                }
            }
        }
        if (prism != null) {
            existingMirrored = false;
            int reflectDir = prism.getReflectDir(dir);
            for (Object obj : getObjectsAtOffset(0, 0, LaserRay.class)) {
                if (isRayType((LaserRay)obj, reflectDir, getColor())) {
                    if (existingMirrored) {
                        ((GameObj)obj).remove();
                    } else {
                        existingMirrored = true;
                    }
                }
            }
        }
        
        if (val) {
            if (!existing) {
                LaserRay laser = new LaserRay();
                laser.setColor(getColor());
                laser.addToWorld(getWorld(), getX(), getY(), dir);
                if (!laser.move(dir, 1)) getWorld().removeObject(laser);
            }
            if (!existingMirrored) {
                LaserRay laser = new LaserRay();
                laser.setColor(getColor());
                laser.addToWorld(getWorld(), getX(), getY(), prism.getReflectDir(dir));
            }
        }
    }
    
    public boolean canStay(LaserRay ray) {
        for (Object obj : ray.getObjectsAtOffset(0, 0, GameObj.class)) {
            if (obj != this && ((GameObj)obj).solidAgainst(ray)) return false;
        }
        return true;
    }
    
    public boolean isRayType(LaserRay obj, int dir, int color) {
        return obj.getDir()==dir && obj.getColor()==color;
    }
    
    public boolean isLaserType(PowerLaser obj, int dir, int color) {
        return obj.getDir()==dir && obj.getColor()==color;
    }
    
    public boolean canPush(GameObj obj) {
        return obj instanceof LaserRay;
    }
    
    public boolean rotate() {
        return true;
    }
    
    public int getDelay() {
         return 0;
    }
}
