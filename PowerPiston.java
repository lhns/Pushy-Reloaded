import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class PowerPiston here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PowerPiston extends PowerObj
{
    private PowerPistonHead head = null;
    private boolean tryAgain = false;
    
    public void created() {
        super.created();
        head = new PowerPistonHead();
        head.addToWorld(getWorld(), getX(), getY(), getDir(), this);
    }
    
    public void onPowerUpdate() {
        if (getPower()) {
            tryAgain = !extend();
        } else {
            retract();
        }
    }
    
    public void update() {
        super.update();
        if (tryAgain) {
            tryAgain = !extend();
        }
        if (!getPower()) retract();
    }
    
    private boolean extend() {
        if (!isExtended()) {
            if (head.move(getDir(), 1)) {
                Sound.crack.play();
                head.setDir(getDir());
                head.extended = true;
                setImage("136.png");
            } else {
                return false;
            }
        }
        return true;
    }
    
    private void retract() {
        if (isExtended()) {
            Sound.crack.play();
            head.setDir(getDir());
            head.setLocation(getX(), getY());
            head.extended = false;
            setImage("137.png");
        }
    }
    
    public void onMove() {
        head.setDir(getDir());
        head.setLocation(getX(), getY());
        head.extended = false;
        onPowerUpdate();
    }
    
    public boolean canReceivePowerThroughFront(boolean directional) {
        return false;
    }
    
    public void onRotate(int newDir) {
        head.setDir(newDir);
    }
    
    public boolean pushable(GameObj obj) {
        return !isExtended() && (obj instanceof PowerPistonHead || obj instanceof PowerPiston);
    }
    
    private boolean isExtended() {
        return head.getX()!=getX() || head.getY()!=getY();
    }
    
    public boolean rotate() {
        return true;
    }
    
    public void onRemove() {
        head.remove();
    }
    
    public int getRenderOrder() {
        return GameObj.R_Frnt;
    }
    
    public int getId() {
        return 132;
    }
}
