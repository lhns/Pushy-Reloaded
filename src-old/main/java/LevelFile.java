import java.io.*;
import java.util.*;
import org.lolhens.network.packet.*;
/**
 * Write a description of class LevelFile here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class LevelFile  
{
    private File file;
    private PushyWorld pushyWorld;
    private static final int levSizeX = 20, levSizeY = 12;
    private boolean remote = false;
    
    /**
     * Constructor for objects of class LevelFile
     */
    public LevelFile(File file, PushyWorld pushyWorld)
    {
        this.file = file;
        this.pushyWorld = pushyWorld;
    }
    
    public LevelFile(PushyWorld pushyWorld) {
        this(null, pushyWorld);
        remote = true;
    }

    public void load() {
        pushyWorld.clean();
        if (!file.exists()) return;
        pushyWorld.extraData.setString("currLvlFile", file.getAbsolutePath());
        if (getFileExt().equals(".pe")) {
            loadPe();
            return;
        }
        if (!getFileExt().equals(".lev")) return;
        try {
            FileInputStream filestream = new FileInputStream(file);
            DataInputStream stream = new DataInputStream(filestream);
            
            int blockMap[][] = new int[pushyWorld.getWidth()][pushyWorld.getHeight()];
            
            boolean firstTime = true;
            boolean corruptVal = false;
            for (int x = 0; x < levSizeX; x++) {
                for (int y = 0; y < levSizeY; y++) {
                    if (firstTime) {
                        firstTime = false;
                    } else {
                        stream.skipBytes(125);
                    }
                    corruptVal = stream.readByte()!=2;
                    stream.skipBytes(1);
                    blockMap[x][y] = Integer.valueOf(stream.readByte());
                    if (corruptVal) blockMap[x][y] = 0;
                }
            }
            stream.close();
            filestream.close();
            
            pushyWorld.clear();
            GameObj.reading = true;
            pushyWorld.forceUpdate();
            GameObj.reading = false;
            
            for (int y = 0; y < levSizeY; y++) {
                for (int x = 0; x < levSizeX; x++) {
                    if (blockMap[x][y]>0) {
                        Class objClass = GameObj.getClassForId(blockMap[x][y]);
                        if (objClass != null) {
                            Object obj = objClass.newInstance();
                            if (obj != null && obj instanceof GameObj) {
                                GameObj gameObj = (GameObj)obj;
                                pushyWorld.addObject(gameObj, x, y);
                            }
                        }
                    }
                 }
             }
        } catch (Exception e) {
            // e.printStackTrace();
        }
        
        if (pushyWorld.networkInterface != null) {
            try {
                //pushyWorld.networkInterface.send(new SimplePacket(3));
            } catch (Exception e) {
            }
        }
        
        if (!remote) pushyWorld.send(); 
    }
    
    public void save() {
        if (getFileExt().equals(".pe")) {
            savePe();
            return;
        }
        if (!getFileExt().equals(".lev")) return;
        try {
            int blockMap[][] = new int[pushyWorld.getWidth()][pushyWorld.getHeight()];
            
            for (int y = 0; y < pushyWorld.getHeight(); y++) {
                for (int x = 0; x < pushyWorld.getWidth(); x++) {
                    List<Object> objects = pushyWorld.getObjectsAt(x, y, null);
                    for (Object obj : objects) {
                        if (obj instanceof GameObj) {
                            blockMap[x][y]=((GameObj)obj).getFinalId();
                            break;
                        }
                    }
                 }
             }
             
            FileOutputStream filestream = new FileOutputStream(file);
            DataOutputStream stream = new DataOutputStream(filestream);
            
            boolean firstTime = true;
            for (int x = 0; x < 20; x++) {
                for (int y = 0; y < 12; y++) {
                    if (firstTime) {
                        firstTime = false;
                    } else {
                        for (int i=0; i<125; i++) {
                            stream.writeByte(0);
                        }
                    }
                    stream.writeByte(2);
                    stream.writeByte(0);
                    stream.writeByte(blockMap[x][y]);
                }
            }
            stream.close();
            filestream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void loadPe() {
        try {
            FileInputStream filestream = new FileInputStream(file);
            DataInputStream stream = new DataInputStream(filestream);
            readPe(stream);
            stream.close();
            filestream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (pushyWorld.networkInterface != null) {
            try {
                //pushyWorld.networkInterface.send(new SimplePacket(3));
            } catch (Exception e) {
            }
        }
    }
    
    private void savePe() {
        try {
            FileOutputStream filestream = new FileOutputStream(file);
            DataOutputStream stream = new DataOutputStream(filestream);
            writePe(stream);
            stream.close();
            filestream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void readPe(DataInputStream stream) throws Exception {
        GameObj.reading = true;
        pushyWorld.clear();
        pushyWorld.forceUpdate();
        GameObj.reading = false;
        int id = 0;
        while(stream.available()>0) {
            id = stream.readInt();
            byte[] data = new byte[stream.readInt()];
            stream.read(data);
            ByteArrayInputStream tmpByteStream = new ByteArrayInputStream(data);
            DataInputStream tmpStream = new DataInputStream(tmpByteStream);
            readObject(tmpStream, id);
            tmpStream.close();
            tmpByteStream.close();
        }
    }
    
    public void writePe(DataOutputStream stream) throws Exception {
        GameObj gameObj = null;
        for (Object obj : pushyWorld.getObjects(null)) {
            if (obj instanceof GameObj) {
                gameObj = (GameObj)obj;
                if (gameObj.getId()>0) {
                    stream.writeInt(gameObj.getFinalId());
                    ByteArrayOutputStream tmpByteStream = new ByteArrayOutputStream();
                    DataOutputStream tmpStream = new DataOutputStream(tmpByteStream);
                    writeObject(tmpStream, gameObj);
                    stream.writeInt(tmpByteStream.size());
                    tmpByteStream.writeTo(stream);
                    tmpStream.close();
                    tmpByteStream.close();
                }
            }
        }
    }
    
    public void readObject(DataInputStream tmpStream, int id) throws Exception {
        int x = 0, y = 0, d = 0, dl = -1;
        x = tmpStream.readInt();
        y = tmpStream.readInt();
        d = tmpStream.readInt();
        dl = tmpStream.readInt();
        Class objClass = GameObj.getClassForId(id);
        if (remote) {
            if (objClass == LocalPushy.class) {
                objClass = RemotePushy.class;
            } else if (objClass == RemotePushy.class) {
                objClass = LocalPushy.class;
            }
        }
        if (objClass != null) {
            if (remote) GameObj.reading = true;
            try {
                Object obj = objClass.newInstance();
                if (obj != null && obj instanceof GameObj) {
                    GameObj gameObj = (GameObj)obj;
                    pushyWorld.addObject(gameObj, x, y);
                    gameObj.setDir(d);
                    gameObj.setDelay(dl);
                    gameObj.load(tmpStream);
                    if (tmpStream.available() >= 4) {
                        byte[] bytes = new byte[tmpStream.readInt()];
                        tmpStream.read(bytes);
                        ExtraData extraData = ExtraData.fromByteArray(bytes);
                        gameObj.extraData = extraData;
                    }
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            GameObj.reading = false;
        }
    }
    
    public void writeObject(DataOutputStream tmpStream, GameObj gameObj) throws Exception {
        tmpStream.writeInt(gameObj.getX());
        tmpStream.writeInt(gameObj.getY());
        tmpStream.writeInt(gameObj.getDir());
        tmpStream.writeInt(gameObj.getLockedDelay());
        gameObj.save(tmpStream);
        byte[] extraData = gameObj.extraData.toByteArray();
        tmpStream.writeInt(extraData.length);
        tmpStream.write(extraData);
    }
    
    private String getFileExt() {
        return file.getAbsolutePath().substring(file.getAbsolutePath().lastIndexOf("."));
    }
}
