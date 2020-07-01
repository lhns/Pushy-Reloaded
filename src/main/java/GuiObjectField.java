import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class GuiObjectField here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class GuiObjectField extends ObjectSelectorGui
{
    ObjectSelector selector;
    GameObj obj;
    /**
     * Act - do whatever the GuiObjectField wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        if (Greenfoot.getMouseInfo().getButton() == 1) {
            int x = Greenfoot.getMouseInfo().getX();
            int y = Greenfoot.getMouseInfo().getY();
            if (x > getX() - 3 && x < getX() + 3 && y == getY()) {
                selector.selected = this;
            }
            updateImg();
        }
    }
    
    public void setGameObj(Class<? extends GameObj> gameObjClass) {
        if (gameObjClass==null) return;
        try {
            obj = (GameObj)gameObjClass.newInstance();
            selector.addObject(obj, getX() - 2, getY());
        } catch (Exception e) {
            e.printStackTrace();
        }
        updateImg();
    }
    
    public void updateImg() {
        GreenfootImage img = new GreenfootImage("selectorField"+(selector.selected==this?"Active":"")+".png");
        if (obj != null) img.drawString(obj.getClass().getName(), 40, 20);
        setImage(img);
    }
}
