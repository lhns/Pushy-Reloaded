import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Menu here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Menu extends ObjectSelectorGui
{
    /**
     * Act - do whatever the Menu wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        if (Greenfoot.mouseClicked(this)) {
            int x = Greenfoot.getMouseInfo().getX();
            int y = Greenfoot.getMouseInfo().getY();
            if (x >= getX()-2 && x <= getX()+2 && y == getY()) {
                if (getWorld() instanceof PushyWorld) {
                    GameMenu gameMenu = new GameMenu();
                    gameMenu.pushyWorld = (PushyWorld)getWorld();
                    Greenfoot.setWorld(gameMenu);
                } else if (getWorld() instanceof GameMenu) {
                    Greenfoot.setWorld(((GameMenu)getWorld()).pushyWorld);
                }
            }
        }
    }    
}
