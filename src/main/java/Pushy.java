import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;
import java.io.*;

/**
 * Write a description of class Pushy here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Pushy extends GameObj
{
    public boolean rotate() {
        return true;
    }
    
    public int getRenderOrder() {
        return GameObj.R_Player;
    }
    
    public boolean pushable(GameObj obj) {
        return (obj instanceof PowerPistonHead);
    }
    
    public boolean solidAgainst(GameObj obj) {
        return !(obj instanceof LightOn);
    }
}
