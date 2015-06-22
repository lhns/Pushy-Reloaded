import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class MagicBlock here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MagicBlock extends GameObj
{
    public boolean pushable(GameObj obj) {
        return (obj instanceof Pushy || obj instanceof PowerPistonHead);
    }
    
    public void onMove() {
        boolean solved = false;
        for (Object objInRange : getObjectsInRange(1, getClass())) {
            if (objInRange instanceof GameObj) {
                ((GameObj)objInRange).remove();
                getWorld().addObject(new RenderMagicBlock(), ((GameObj)objInRange).getX(),((GameObj)objInRange).getY());
                getWorld().closeGoal();
                solved = true;
            }
        }
        if (solved) {
            Sound.drop.play();
            remove();
            getWorld().addObject(new RenderMagicBlock(), getX(),getY());
            getWorld().closeGoal();
        }
    }
    
    public void created() {
        getWorld().openGoal();
    }
    
    public int getId() {
        return 19;
    } 
}
