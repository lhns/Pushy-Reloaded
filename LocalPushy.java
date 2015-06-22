import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import javax.swing.*;
import com.dafttech.network.*;
import com.dafttech.network.packet.*;
import com.dafttech.network.protocol.*;
import com.dafttech.network.disconnect.*;
/**
 * Write a description of class LocalPushy here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class LocalPushy extends Pushy
{
    private boolean moveAgain = false;
    
    public void update()
    {
        if (moveAgain) moveAgain = move(getDir(), 1);
        checkKeys();
        if (extraData.getBoolean("Charged")) {
            setImage("124.png");
        } else {
            setImage("10.png");
        }
    }
    
    public void onMove() {
        if (extraData.getBoolean("Farmove")) moveAgain = true;
        postEvent("pushyMove");
    }

    private void checkKeys() 
    {
        boolean invert = extraData.getBoolean("Invert");
        if (getWorld().keyManager.isKeyHit(invert?"down":"up"))     move(0, 1);
        if (getWorld().keyManager.isKeyHit(invert?"left":"right"))  move(1, 1);
        if (getWorld().keyManager.isKeyHit(invert?"up":"down"))     move(2, 1);
        if (getWorld().keyManager.isKeyHit(invert?"right":"left"))  move(3, 1);
        
        if (getWorld().keyManager.isKeyHit(invert?"s":"w")) move(0, 1);
        if (getWorld().keyManager.isKeyHit(invert?"a":"d")) move(1, 1);
        if (getWorld().keyManager.isKeyHit(invert?"w":"s")) move(2, 1);
        if (getWorld().keyManager.isKeyHit(invert?"d":"a")) move(3, 1);
        
        if (getWorld().keyManager.isKeyHit("enter")) {
            LevelFile lvlFile = new LevelFile(getWorld().getLevelFile(String.valueOf(getWorld().extraData.getInt("currLvl"))), getWorld());
            lvlFile.load();
        }
        if (getWorld().keyManager.isKeyHit("n")) {
            getWorld().extraData.setInt("currLvl", getWorld().extraData.getInt("currLvl")+1);
            LevelFile lvlFile = new LevelFile(getWorld().getLevelFile(String.valueOf(getWorld().extraData.getInt("currLvl"))), getWorld());
            lvlFile.load();
        }
        if (getWorld().keyManager.isKeyHit("b") && getWorld().extraData.getInt("currLvl")>1) {
            getWorld().extraData.setInt("currLvl", getWorld().extraData.getInt("currLvl")-1);
            LevelFile lvlFile = new LevelFile(getWorld().getLevelFile(String.valueOf(getWorld().extraData.getInt("currLvl"))), getWorld());
            lvlFile.load();
        }
        if (getWorld().keyManager.isKeyHit("m")) {
            System.out.println("Level "+getWorld().extraData.getInt("currLvl"));
            //world.getBackground().drawString("Level "+world.extraData.getInt("currLvl"), 1, 1);
        }
        if (getWorld().keyManager.isKeyHit("o")) {
            JFileChooser filechooser = new JFileChooser();
            if (filechooser.showOpenDialog(null)==JFileChooser.APPROVE_OPTION) {
                LevelFile lvlFile = new LevelFile(filechooser.getSelectedFile(), getWorld());
                lvlFile.load();
            }
        }
        if (getWorld().keyManager.isKeyHit("c")) {
            String serverMode = JOptionPane.showInputDialog("Server? (Y/N)");
            if (serverMode != null) {
                String address = JOptionPane.showInputDialog("Adress/Port");
                if (address != null) {
                    try {
                        if (serverMode.toLowerCase().equals("y")) {
                            getWorld().networkInterface = new Server<SimplePacket>(SimpleProtocol.class, address) {
                                public void receive(Client<SimplePacket> client, SimplePacket packet) {
                                    getWorld().packetQueue.add(packet);
                                    for (Client<SimplePacket> cClient : getClients()) {
                                        try {
                                            if (cClient != client) cClient.send(packet);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                                
                                public void connect(Client<SimplePacket> client) {
                                    for (Client<SimplePacket> cClient : getClients()) {
                                        try {
                                            if (cClient != client) cClient.send(new SimplePacket(4));
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    for (Object obj : getWorld().getObjects(null)) {
                                        if (obj instanceof LocalPushy) {
                                            LocalPushy pushy = (LocalPushy)obj;
                                            RemotePushy remotePushy = new RemotePushy();
                                            remotePushy.client = client;
                                            getWorld().addObject(remotePushy, pushy.getX(), pushy.getY());
                                            break;
                                        }
                                    }
                                    getWorld().forceUpdate();
                                    getWorld().send();
                                }
                                
                                public void disconnect(Client<SimplePacket> client, Disconnect reason) {
                                    for (Object obj : getWorld().getObjects(null)) {
                                        if (obj instanceof RemotePushy) {
                                            if (((RemotePushy)obj).client == client) ((GameObj)obj).remove();
                                        }
                                    }
                                }
                            };
                        } else {
                            getWorld().networkInterface = new Client<SimplePacket>(SimpleProtocol.class, address) {
                                public void receive(SimplePacket packet) {
                                    getWorld().packetQueue.add(packet);
                                }
                            };
                        }
                    } catch (Exception e) {
                    }
                }
            }
        }
    }
    
    public int getId() {
        return 10;
    }
}
