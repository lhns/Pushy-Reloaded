import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class RenderSensorLaser here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class RenderSensorLaser extends RenderObj
{
    public GameObj firstSensor = null;
    public GameObj secondSensor = null;
    public int xOffset, yOffset;
    public float ticks=0;
    public boolean subtr = false;
    public int cooldown = 6000;
    
    public void update() {
        if (secondSensor==null || secondSensor.getWorld()==null) {
            remove();
        } else {
            double vecX = secondSensor.getX()-firstSensor.getX();
            double vecY = secondSensor.getY()-firstSensor.getY();
            double hypo = Math.sqrt(vecX*vecX+vecY*vecY);
            if (subtr && cooldown<=0) {
                ticks-=0.002;
                if (ticks<=0) {
                    cooldown = 6000;
                    subtr=!subtr;
                }
            } else if (cooldown<=0) {
                ticks+=0.002;
                if (ticks>=hypo+1) {
                    cooldown = 6000;
                    subtr=!subtr;
                }
            }
            cooldown --;
            xOffset = (int)((ticks/hypo)*vecX);
            yOffset = (int)((ticks/hypo)*vecY);
            if (getX()==firstSensor.getX() || getY()==firstSensor.getY() || getX()==secondSensor.getX() || getY()==secondSensor.getY()) {
                setVisible(false);
            } else {
                setVisible(true);
            }
            setRotation((int)Math.toDegrees(Math.acos((vecY * vecY + hypo * hypo - vecX * vecX)/ (2 * vecY * hypo))));
            //getImage().scale((int)Math.abs(vecX), (int)Math.abs(vecY));
        }
    }
}
