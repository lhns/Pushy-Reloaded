import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class RenderSensor here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class RenderSensor extends RenderObj
{
    float rot=0;
    public void update() 
    {
        rot = rot>=360?0:rot+0.2F;
        setRotation((int)rot);
        if (getCreator().extraData.getBoolean("closed")) remove();
    }
    
    public int getRenderOrder() {
        return GameObj.R_Mid;
    }
    
    public boolean needsCreator() {
        return true;
    }
}
