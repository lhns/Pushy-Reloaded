import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;
import java.lang.*;
import java.io.*;
import org.lolhens.classfile.*;

/**
 * Write a description of class GameObj here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class GameObj extends Actor
{
    public static boolean reading = false;

    public ExtraData extraData = new ExtraData();
    
    private PushyWorld world;
    private int dir = 0, lastDir = 0, sheduledDir = 0, lockedDelay = -1;
    private boolean isDead=false, dontNotify=false;
    private GameObj currMovingObjChange=null;
    private GameObj creator = null;
    private long lastUpdated=0;
    private int scheduleX, scheduleY;
    private boolean scheduled = false;
    
    public static List<GameObj> gameObjects = new ArrayList<GameObj>();
    public static final int N=0, E=1, S=2, W=3, R_Bck=0, R_Mid=1, R_Frnt=2, R_Wire=3, R_Player=4, R_Max=5;
    public static final boolean showConflicts = true;
    
    public final void act() {
        if (needsCreator() && getCreator()==null) lostCreator();
        if (isDead()) {
            callLeaveMethods();
            int oldX = getX();
            int oldY = getY();
            world.removeObject(this);
            if (!reading) getWorld().send(oldX, oldY);
        } else if (world != null && shouldUpdate()) {
            update();
        }
    }
    
    public final boolean shouldUpdate() {
        int delay = lockedDelay>-1?lockedDelay:getDelay();
        if (System.currentTimeMillis()-lastUpdated>=delay) {
            lastUpdated = System.currentTimeMillis();
            return true;
        } else {
            return false;
        }
    }
    
    protected final void addedToWorld(World world) {
        if (world instanceof PushyWorld) {
            this.world = (PushyWorld)world;
        } else if (world instanceof ObjectSelector) {
            this.world = null;
        } else {
            this.world = null;
            // System.out.println("Cannot assign world!!!");
        }
        if (this.world != null) {
            shouldUpdate();
            created();
        }
    }
    
    public final void setDir(int dir) {
        this.dir = dir;
        updateDir();
        if (dir != lastDir) onRotate(dir);
        lastDir = dir;
    }
    
    public final void addDir(int rotRight) {
        setDir(getDir(rotRight));
    }
    
    public final int getDir() {
        return dir;
    }
    
    public final int getDir(int rotRight) {
        return addDirs(getDir(), rotRight);
    }
    
    public final int addDirs(int dir1,int dir2) {
        return (dir1+dir2)%4;
    }
    
    public final void schedule() {
        scheduleX = getX();
        scheduleY = getY();
        scheduled = true;
    }
    
    public final void send() {
        if (scheduled) {
           if (!reading) getWorld().send(scheduleX, scheduleY);
           scheduled = false;
        }
        if (!reading) getWorld().send(getX(), getY());
    }
    
    public final boolean move(int dir, int steps) {
        try {
            GameObj currMovingObj=this;
            boolean ret=false;
            currMovingObj.setDir(dir);
            currMovingObj = updateCurrMovingObj(currMovingObj);
            if (dir >= 0 && steps > 0 && currMovingObj.askForLeave()) {
                if (currMovingObj.canMove(dir) || currMovingObj.noClip()) {
                    currMovingObj = updateCurrMovingObj(currMovingObj);
                    currMovingObj.callLeaveMethods();
                    currMovingObj = updateCurrMovingObj(currMovingObj);
                    currMovingObj.setRotation((dir-1)*90);
                    currMovingObj = updateCurrMovingObj(currMovingObj);
                    currMovingObj.dontNotify();
                    currMovingObj.schedule();
                    currMovingObj.move(1);
                    currMovingObj.send();
                    currMovingObj = updateCurrMovingObj(currMovingObj);
                    currMovingObj.onMove();
                    currMovingObj = updateCurrMovingObj(currMovingObj);
                    ret=true;
                }
            }
            currMovingObj.updateDir();
            if (steps > 1) currMovingObj.move(dir, steps-1);
            return ret;
        } catch (Exception e) {
        }
        return false;
    }
    
    private final void updateDir() {
        if (rotate()) {
            setRotation(dir*90);
        } else {
            setRotation(0);
        }
        send();
    }
    
    public final void setDelay(int delay) {
        lockedDelay = delay;
    }
    
    public final void addDelay(int delay) {
        if (lockedDelay==-1) lockedDelay = 0;
        lockedDelay+=delay;
    }
    
    public final void resetDelay() {
        lockedDelay = -1;
    }
    
    public final int getLockedDelay() {
        return lockedDelay;
    }
    
    public final boolean isOutOfWorld() {
        return isOutOfWorld(getX(), getY());
    }
    
    public final boolean isOutOfWorld(int x, int y) {
        return x<0 || y<0 || x>world.getWidth() || y>world.getHeight();
    }
    
    public final boolean canMove(int dir) {
        if ((dir==0 && getY()<=0)
         || (dir==3 && getX()<=0) 
         || (dir==1 && getX()>=world.getWidth()-1) 
         || (dir==2 && getY()>=world.getHeight()-1)) {
             if (canLeaveWorld()) remove();
             return false;
        }
        
        sheduledDir = dir;
        List objects = getObjectsAtOffset(xLookingOffset(getDir()), yLookingOffset(getDir()), null);
        boolean canMove=true;
        GameObj obj = null;
        for (int count=0; count < objects.size(); count++) {
            if (objects.get(count) instanceof GameObj) {
                obj = (GameObj)objects.get(count);
                if (obj.solidAgainst(this) && !(canPush(obj) && obj.pushable(this) && obj.move(this.getDir(), 1))) canMove=false;
            }
        }
        if (canMove) {
            for (int count=0; count < objects.size(); count++) {
                if (objects.get(count) instanceof GameObj) {
                    obj = (GameObj)objects.get(count);
                    if (obj.solidAgainstPost(this) || !obj.pushablePost(this)) canMove=false;
                }
            }
        }
        sheduledDir = 0;
        
        return canMove;
    }
    
    public final void callLeaveMethods() {
        if (noClip()) return;
        List objects = getObjectsAtOffset(0, 0, null);
        for (int count=0; count < objects.size(); count++) {
            if (objects.get(count) != null && objects.get(count) instanceof GameObj && objects.get(count)!=this) {
                ((GameObj)objects.get(count)).onLeave(this);
            }
        }
    }
    
    public final boolean askForLeave() {
        List objects = getObjectsAtOffset(0, 0, null);
        boolean ret=true;
        for (int count=0; count < objects.size(); count++) {
            if (objects.get(count) != null && objects.get(count) instanceof GameObj && objects.get(count)!=this) {
                if (!((GameObj)objects.get(count)).canLeave(this)) ret=false;
            }
        }
        return ret;
    }
    
    public final PushyWorld getWorld() {
        return world;
    }
    
    public final int getSheduledDir() {
        return sheduledDir;
    }
    
    public final int xLookingOffset(int dir) {
        return dir==1?1:dir==3?-1:0;
    }
    public final int yLookingOffset(int dir) {
        return dir==0?-1:dir==2?1:0;
    }
    
    public final void addLocation(int x, int y) {
        setLocation(getX()+x, getY()+y);
    }
    
    public final void setLocation(int x, int y) {
        if (x == getX() && y == getY()) return;
        if (!dontNotify) callLeaveMethods();
        schedule();
        super.setLocation(x, y);
        send();
    }
    
    public final void replaceMovingObj(GameObj obj) {
        currMovingObjChange=obj;
    }
    
    private final GameObj updateCurrMovingObj(GameObj current) {
        if (currMovingObjChange==null) return current;
        GameObj obj = currMovingObjChange;
        currMovingObjChange = null;
        return obj;
    }
    
    public static final Class getClassForId(int id) {
        for (GameObj gameObj : gameObjects) {
            if (gameObj.getId()==id) return gameObj.getClass();
        }
        return null;
    }
    
    public static final void loadGameObjects() {
        try {
            Set<URLClassLocation> classLocations = new URLClassLocation(GameObj.class).discoverSourceURL();
            
            Iterator<URLClassLocation> iterator = classLocations.iterator();
            while (iterator.hasNext())
                if (iterator.next().toString().contains("PushyWorld")) iterator.remove();
        
            for (URLClassLocation classLocation : classLocations) {
                Class<?> gameObjClass = classLocation.loadClass(GameObj.class.getClassLoader());
                
                if (gameObjClass.toString().startsWith("class") && !gameObjClass.toString().contains(".") && gameObjClass != PushyWorld.class && gameObjClass != ObjectSelector.class) {
                    Object gameObjInstance = null;
                    
                    try {
                        gameObjInstance = gameObjClass.newInstance();
                    } catch (Exception e) {
                    }
                    
                    if (gameObjInstance != null && gameObjInstance instanceof GameObj) {
                        GameObj gameObj = (GameObj) gameObjInstance;
                        testForId(gameObj);
                        try {
                            gameObj.setImage(gameObj.getId() + ".bmp");
                        } catch (Exception e) {
                            try {
                                gameObj.setImage(gameObj.getId() + ".png");
                            } catch (Exception e2) {
                                e2.printStackTrace();
                            }
                        }
                        gameObjects.add(gameObj);
                    }
                }
            }
        } catch (IOException e) {
            return;
        } catch (ClassNotFoundException e) {
            return;
        }
    }
    
    private static final void testForId(GameObj newGameObj) {
        if (!showConflicts || newGameObj.getId()<0) return;
        for (GameObj gameObj : gameObjects) {
            if (gameObj.getId()==newGameObj.getId()) System.out.println("Id "+newGameObj.getId()+" is already used by "+gameObj+" when adding "+newGameObj+"!");
        }
    }
    
    public final void postEvent(String event) {
        List<Object> objects = world.getObjects(null);
        for (Object obj : objects) {
            if (obj instanceof GameObj) {
                ((GameObj) obj).onObjEvent(this, event);
            } else if (obj instanceof Actor) {
                
            }
        }
    }
    
    public final void addToWorld(PushyWorld world, int x, int y) {
        world.addObject(this, x, y);
        send();
    }
    
    public final void addToWorld(PushyWorld world, int x, int y, int dir) {
        world.addObject(this, x, y);
        setDir(dir);
        send();
    }
    
    public final void addToWorld(PushyWorld world, int x, int y, GameObj creator) {
        world.addObject(this, x, y);
        setCreator(creator);
        send();
    }
    
    public final void addToWorld(PushyWorld world, int x, int y, ExtraData extraData) {
        world.addObject(this, x, y);
        this.extraData = extraData;
        send();
    }
    
    public final void addToWorld(PushyWorld world, int x, int y, int dir, GameObj creator) {
        world.addObject(this, x, y);
        setDir(dir);
        setCreator(creator);
        send();
    }
    
    public final void setVisible(boolean value) {
        getImage().setTransparency(value?255:0);
    }
    
    public final void remove() {
        onRemove();
        isDead=true;
    }
    
    public final boolean isDead() {
        return isDead;
    }
    
    public final List<GameObj> getObjectsAtOffset(int offX, int offY, Class cls) {
        List<GameObj> gameObjects = new ArrayList<GameObj>();
        for (Object obj : super.getObjectsAtOffset(offX, offY, cls)) if (obj instanceof GameObj) gameObjects.add((GameObj)obj);
        return gameObjects;
    }
    
    public final GameObj getCreator() {
        return creator;
    }
    
    public final void setCreator(GameObj creator) {
        this.creator = creator;
    }
    
    public final int getFinalId() {
        return getRedirectedId()>-1 ? getRedirectedId() : getId();
    }
    
    public final void dontNotify() {
        dontNotify = true;
    }
    
    public int getId() {
        return -1;
    }
    
    public int getRedirectedId() {
        return -1;
    }
    
    public boolean solidAgainst(GameObj obj) {
        return true;
    }
    
    public boolean canPush(GameObj obj) {
        return true;
    }
    
    public boolean pushable(GameObj obj) {
        return false;
    }
    
    public boolean solidAgainstPost(GameObj obj) {
        return false;
    }
    
    public boolean pushablePost(GameObj obj) {
        return true;
    }
    
    public boolean canLeave(GameObj obj) {
        return true;
    }
    
    public void onLeave(GameObj obj) {
        
    }
    
    public void onMove() {
        
    }
    
    public void onObjEvent(GameObj obj, String event) {
        
    }
    
    public void onRemove() {
        
    }
    
    public void onRotate(int newDir) {
        
    }
    
    public boolean rotate() {
        return false;
    }
    
    public void created() {
        
    }
    
    public void update() {
        
    }
    
    public boolean noClip() {
        return false;
    }
    
    public boolean needsCreator() {
        return false;
    }
    
    public void lostCreator() {
        remove();
    }
    
    public boolean canLeaveWorld() {
        return false;
    }
    
    public int getDelay() {
        return 0;
    }
    
    public void load(DataInputStream stream) throws Exception {
        
    }
    
    public void save(DataOutputStream stream) throws Exception {
        
    }
    
    public int getRenderOrder() {
        return R_Mid;
    }
}
