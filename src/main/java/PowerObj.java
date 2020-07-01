import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;
import java.io.*;


/**
 * Write a description of class PowerObj here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PowerObj extends GameObj
{
    private boolean power=false, providePower=false, prevPower=false, prevProvidePower=false, firstUpdate = true, secondUpdate = true;
    private GreenfootImage original = null;
    private int powerTimeoutMax = 30, powerTimeout = powerTimeoutMax;
    
    public void update() {
        if ((!firstUpdate && secondUpdate) || powerTimeout<=0) {
            secondUpdate = false;
            powerTimeout = powerTimeoutMax;
            updatePower();
        }
        if (prevPower != power || prevProvidePower != providePower || firstUpdate) {
            prevPower = power;
            prevProvidePower = providePower;
            firstUpdate = false;
            updateOtherPower();
        }
        powerTimeout--;
    }
    
    public void onPowerUpdate() {
        
    }
    
    public boolean isProvidingPower() {
        return providePower;
    }
    
    public boolean canProvidePower() {
        return false;
    }
    
    public boolean canReceivePowerThroughFront(boolean directional) {
        return !canProvidePower() && directional;
    }
    
    public boolean oneDirectionalProvider() {
        return true;
    }
    
    public final void updateOtherPower() {
        if (oneDirectionalProvider()) {
            updatePowerAt(getDir());
        } else {
            updateAllOtherPower();
        }
        onPowerUpdate();
    }
    
    public final void updateAllOtherPower() {
        for (int i = 0; i<=3; i++) updatePowerAt(i);
    }
    
    public final void setProvidePower(boolean power) {
        providePower = power;
    }
    
    public final void updatePower() {
        this.power=isGettingPower();
    }
    
    public final boolean getPower() {
        return power;
    }
    
    public final boolean isGettingPower() {
        List<Object> objects = new ArrayList();
        for (int i=0; i<=3; i++) objects.addAll(getObjectsAtOffset(xLookingOffset(i), yLookingOffset(i), PowerObj.class));
        for (Object obj : objects) {
            if (isGettingPowerFrom((PowerObj)obj)) return true;
        }
        return false;
    }
    
    public final boolean isGettingPowerFrom(PowerObj obj) {
        if (!isSameNetwork(obj)) return false;
        for (int i=0; i<=3; i++) {
            if (obj.getX()==getX()+xLookingOffset(i) && obj.getY()==getY()+yLookingOffset(i)) {
                if (obj.canProvidePower() && obj.isProvidingPower()) {
                    if ((!obj.oneDirectionalProvider() || obj.getDir(2)==i) && (!oneDirectionalProvider() || i!=getDir() || canReceivePowerThroughFront(obj.oneDirectionalProvider()))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    private final void updatePowerAt(int lookdir) {
         List<GameObj> objects = getObjectsAtOffset(xLookingOffset(lookdir), yLookingOffset(lookdir), PowerObj.class);
         if (objects.size()>0) {
             for (Object obj : objects) {
                 if (isSameNetwork((PowerObj)obj)) ((PowerObj)obj).updatePower();
             }
         }
    }
    
    private final boolean isSameNetwork(PowerObj obj) {
        return (getNetwork()==0 || obj.getNetwork()==0 || getNetwork()==obj.getNetwork());
    }
    
    public int getDelay() {
         return 2;
    }
    
    public void setNetwork(int network) {
        extraData.setInt("network", network);
        drawNetwork(network);
    }
    
    public int getNetwork() {
        return extraData.getInt("network");
    }
    
    public void load(DataInputStream stream) throws Exception {
        setNetwork(stream.readInt());
        power = stream.readBoolean();
        providePower = stream.readBoolean();
    }
    
    public void save(DataOutputStream stream) throws Exception {
        stream.writeInt(getNetwork());
        stream.writeBoolean(power);
        stream.writeBoolean(providePower);
    }
    
    public void setImage(String img) {
        original = null;
        super.setImage(img);
        drawNetwork(getNetwork());
    }
    
    private void drawNetwork(int network) {
        if (original == null) original = getImage();
        GreenfootImage img = new GreenfootImage(original);
        Color color = new Color(255, 255, 255, 0);
        if (network==1) color = new Color(0, 0, 255);
        if (network==2) color = new Color(0, 200, 0);
        if (network==3) color = new Color(255, 0, 0);
        if (network==4) color = new Color(255, 255, 0);
        img.setColor(color);
        img.drawRect(network,network,31-network*2,31-network*2);
        setImage(img);
    }
}
