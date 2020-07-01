import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class ObjectSelector here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ObjectSelector extends World
{
    private PushyWorld world;
    public GuiObjectField selected = null;
    /**
     * Constructor for objects of class ObjectSelector.
     * 
     */
    public ObjectSelector(PushyWorld world)
    {
        super(30, 25, 34);
        this.world = world;
        int i = 0;
        boolean added;
        for (int col = 0; col < getWidth()/5; col++) {
            for (int row = 0; row < getHeight(); row++) {
                added = false;
                while (!added) {
                    if (i < GameObj.gameObjects.size()) {
                        GameObj obj = GameObj.gameObjects.get(i);
                        if (!(obj instanceof RenderObj) && (obj.getId() >= 0 || (obj instanceof Tool && obj.getClass() != Tool.class))) {
                            GuiObjectField field = new GuiObjectField();
                            addObject(field, col * 5 + 2, row);
                            field.selector = this;
                            field.setGameObj(obj.getClass());
                            added = true;
                        }
                    } else {
                        break;
                    }
                    i++;
                }
            }
        }
    }
    
    public void act() {
        if (!Greenfoot.isKeyDown("e")) {
            Greenfoot.setWorld(world);
        }
    }
}
