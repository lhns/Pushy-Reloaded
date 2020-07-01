import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Prism here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Prism extends Glass
{
    public boolean canReflect(int dir) {
        return dir==getDir() || dir == getDir(3);
    }
    
    public int getReflectDir(int dir) {
        if (dir==getDir()) return getDir(1);
        return addDirs(dir, 3);
    }
    
    public boolean isReflecting(int dir, int color) {
        for (Object obj : getObjectsAtOffset(0, 0, LaserRay.class)) {
            if (((LaserRay)obj).getColor() == color && canReflect(((LaserRay)obj).getDir()) && getReflectDir(((LaserRay)obj).getDir())==dir) return true;
        }
        return false;
    }
    
    public int getId() {
        return 148;
    }
    
    public boolean rotate() {
        return true;
    }
}
