import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;
import java.io.*;
import java.nio.*;
import org.lolhens.network.*;
import org.lolhens.network.packet.*;
import org.lolhens.network.protocol.*;
import org.lolhens.classfile.*;
import org.lolhens.primitive.*;
import javax.swing.*;

/**
 * Write a description of class PushyWorld here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PushyWorld extends World
{
    private ObjectSelector selector;
    private GameObj dragged = null;
    public ExtraData extraData = new ExtraData();
    KeyManager keyManager = new KeyManager();
    public ProtocolProvider<SimplePacket> networkInterface = null;
    public List<SimplePacket> packetQueue = new ArrayList<SimplePacket>();
    
    public PushyWorld()
    {
        super(35/*20*/, 28/*12*/, 32);
        
        GameObj.loadGameObjects();
        selector = new ObjectSelector(this);
        
        calibrateRenderOrder();
        
        LevelFile lvlFile = new LevelFile(getLevelFile("1"), this);
        lvlFile.load();
        extraData.setInt("currLvl", 1);
        
        Greenfoot.start();
    }
    
    public void openGoal() {
        extraData.putInt("openGoals", extraData.getInt("openGoals")+1);
    }
    
    public void closeGoal() {
        extraData.putInt("openGoals", extraData.getInt("openGoals")-1);
    }
    
    public void send() {
        if (networkInterface != null) {
            try {
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                new LevelFile(this).writePe(new DataOutputStream(stream));
                byte[] bytes = stream.toByteArray();
                //networkInterface.send(new SimplePacket(0, bytes));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public void send(int x, int y) {
        if (networkInterface != null) {
            try {
                ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
                DataOutputStream stream = new DataOutputStream(byteStream);
                stream.write(PrimitiveUtil.INTEGER.toByteArray(x, ByteOrder.BIG_ENDIAN));
                stream.write(PrimitiveUtil.INTEGER.toByteArray(y, ByteOrder.BIG_ENDIAN));
                GameObj gameObj = null;
                for (Object obj : getObjects(null)) {
                    if (obj instanceof GameObj) {
                        gameObj = (GameObj)obj;
                        if (gameObj.getId()>0 && gameObj.getX() == x && gameObj.getY() == y) {
                            stream.writeInt(gameObj.getFinalId());
                            ByteArrayOutputStream tmpByteStream = new ByteArrayOutputStream();
                            DataOutputStream tmpStream = new DataOutputStream(tmpByteStream);
                            new LevelFile(this).writeObject(tmpStream, gameObj);
                            stream.writeInt(tmpByteStream.size());
                            tmpByteStream.writeTo(stream);
                            tmpStream.close();
                            tmpByteStream.close();
                        }
                    }
                }
                byte[] bytes = byteStream.toByteArray();
                //networkInterface.send(new SimplePacket(1, bytes));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
    }
    
    public void processPacket(SimplePacket packet) {
        if (packet.channel == 0) {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(packet.data);
            try {
                new LevelFile(this).readPe(new DataInputStream(inputStream));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (packet.channel == 1) {
            int x = PrimitiveUtil.INTEGER.fromByteArray(packet.data, ByteOrder.BIG_ENDIAN);
            int y = PrimitiveUtil.INTEGER.fromByteArray(packet.data, 4, ByteOrder.BIG_ENDIAN);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(Arrays.copyOfRange(packet.data, 8, packet.data.length));
            for (Object obj : getObjects(null)) {
                if (obj instanceof GameObj) {
                    GameObj gameObj = (GameObj)obj;
                    if (gameObj.getX()==x && gameObj.getY()==y) gameObj.remove();
                }
            }
            try {
                DataInputStream stream = new DataInputStream(inputStream);
                while(stream.available()>0) {
                    int id = stream.readInt();
                    byte[] data = new byte[stream.readInt()];
                    stream.read(data);
                    ByteArrayInputStream tmpByteStream = new ByteArrayInputStream(data);
                    DataInputStream tmpStream = new DataInputStream(tmpByteStream);
                    new LevelFile(this).readObject(tmpStream, id);
                    tmpStream.close();
                    tmpByteStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            GameObj.reading = true;
            forceUpdate();
            GameObj.reading = false;
        } else if (packet.channel == 2) {
            GameObj.reading = true;
            clear();
            forceUpdate();
            GameObj.reading = false;
        } else if (packet.channel == 3) {
            try {
                //networkInterface.send(new SimplePacket(4));
            } catch (Exception e) {
            }
        } else if (packet.channel == 4) {
            for (Object obj : getObjects(null)) {
                if (obj instanceof LocalPushy) {
                    LocalPushy pushy = (LocalPushy)obj;
                    RemotePushy remotePushy = new RemotePushy();
                    addObject(remotePushy, pushy.getX(), pushy.getY());
                    break;
                }
            }
        }
    }
    
    public void act() {
        while (packetQueue.size() > 0) {
            SimplePacket packet = null;
            if (packetQueue != null && packetQueue.size() > 0) packet = packetQueue.get(0);
            if (packet != null) processPacket(packet);
            packetQueue.remove(0);
        }
        if (selector.selected != null) {
            if (Greenfoot.mouseDragged(null)) {
                if (dragged == null) {
                    dragged = getTopmostGameObjAt(Greenfoot.getMouseInfo().getX(), Greenfoot.getMouseInfo().getY());
                } else {
                    if (dragged.isDead()) {
                        dragged = null;
                    } else {
                        dragged.setLocation(Greenfoot.getMouseInfo().getX(), Greenfoot.getMouseInfo().getY());
                    }
                }
            } else if (Greenfoot.mouseClicked(null)) {
                if (dragged != null) {
                    dragged = null;
                } else {
                    GameObj obj = selector.selected.obj;
                    if (Greenfoot.getMouseInfo().getButton() == 1 && obj != null) {
                        GameObj newObj = null;
                        try {
                            newObj = obj.getClass().newInstance();
                        } catch (Exception e) {
                        }
                        if (newObj != null && newObj instanceof GameObj) {
                            ((GameObj)newObj).addToWorld(this, Greenfoot.getMouseInfo().getX(), Greenfoot.getMouseInfo().getY());
                        }
                    } else if (Greenfoot.getMouseInfo().getButton() == 3) {
                        new Remover().addToWorld(this, Greenfoot.getMouseInfo().getX(), Greenfoot.getMouseInfo().getY());
                    }
                }
            }
        }
        if (Greenfoot.isKeyDown("e")) {
            Greenfoot.setWorld(selector);
        }
    }
    
    public GameObj getTopmostGameObjAt(int x, int y) {
        GameObj topmost = null;
        for (Object obj : getObjectsAt(x, y, null)) {
            if (!(obj instanceof GameObj)) continue;
            GameObj gameObj = (GameObj) obj;
            if (!gameObj.isDead() && !(gameObj instanceof Tool) &&
               (topmost == null || gameObj.getRenderOrder() > topmost.getRenderOrder())) topmost = gameObj;
        }
        return topmost;
    }
    
    public void calibrateRenderOrder() {
        Class order[] = new Class[GameObj.gameObjects.size() + 2];
        
        order[0] = GuiObjectField.class;
        order[1] = Menu.class;
        
        int countArray=2;
        for (int countPriority=GameObj.R_Max; countPriority >= 0; countPriority--) {
            for (int count=0; count < GameObj.gameObjects.size(); count++) {
                if (GameObj.gameObjects.get(count).getRenderOrder()==countPriority) {
                    order[countArray] = GameObj.gameObjects.get(count).getClass();
                    countArray++;
                }
            }
        }
        setPaintOrder(order);
    }
    
    public void win() {
        Sound.pushy.play();
        if (extraData.getString("currLvlFile").equals(getLevelFile(String.valueOf(extraData.getInt("currLvl"))).getAbsolutePath())) {
            extraData.setInt("currLvl", extraData.getInt("currLvl")+1);
        }
        LevelFile lvlFile = new LevelFile(getLevelFile(String.valueOf(extraData.getInt("currLvl"))), this);
        lvlFile.load();
        //Greenfoot.stop();
    }
    
    public File getLevelFile(String name) {
        String ext = ".pe";
        if (new File("level/"+name+".lev").exists()) ext = ".lev";
        return new File("level/"+name+ext);
    }
    
    public void loose() {
        LevelFile lvlFile = new LevelFile(new File("level/"+extraData.getInt("currLvl")+".lev"), this);
        lvlFile.load();
        //Greenfoot.stop();
    }
    
    public void forceUpdate() {
        List<Object> objects = getObjects(null);
        for (Object obj : objects) {
            if (obj instanceof Actor) {
                ((Actor)obj).act();
            }
        }
    }
    
    public void clear() {
        List<Object> objects = getObjects(null);
        for (Object obj : objects) {
            if (obj instanceof GameObj) {
                ((GameObj) obj).remove();
            } else if (obj instanceof Actor) {
                removeObject((Actor)obj);
            }
        }
        extraData.putInt("openGoals", 0);
        addObject(new Menu(), 2, 0);
        try {
            //if (!GameObj.reading && networkInterface != null) networkInterface.send(new SimplePacket(2, new byte[0]));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void clean() {
        clear();
        for (int opp = 0; opp<=1; opp++) {
            for (int i = 0; i<=getHeight(); i++) {
                new Wall().addToWorld(this, getWidth() * opp, i);
            }
        }
        for (int opp = 0; opp<=1; opp++) {
            for (int i = 0; i<=getWidth(); i++) {
                new Wall().addToWorld(this, i, getHeight() * opp);
            }
        }
        new Pushy().addToWorld(this, 1, 1);
    }
}
