import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;
/**
 * Write a description of class RenderObj here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class RenderObj extends GameObj
{
    public boolean noClip() {
        return true;
    }
    
    public boolean solidAgainst(GameObj obj) {
        return false;
    }
    
    public boolean canLeaveWorld() {
        return true;
    }
    
    public int getRenderOrder() {
        return GameObj.R_Max;
    }
    
    public int getDelay() {
         return 1;
    }
}
