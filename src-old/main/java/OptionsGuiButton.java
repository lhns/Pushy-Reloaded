import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class OptionsGuiButton here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class OptionsGuiButton extends OptionsGui
{
    public String text = null;
    /**
     * Act - do whatever the OptionsGuiButton wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        if (Greenfoot.getMouseInfo().getButton() == 1) {
            int x = Greenfoot.getMouseInfo().getX();
            int y = Greenfoot.getMouseInfo().getY();
            if (x > getX() - 3 && x < getX() + 3 && y == getY()) {
                clicked();
            }
            updateImg();
        }
    }
    
    public void updateImg() {
        GreenfootImage img = new GreenfootImage("selectorField.png");
        if (text != null) img.drawString(text, 40, 20);
        setImage(img);
    }
    
    public void clicked() {
    }
}
