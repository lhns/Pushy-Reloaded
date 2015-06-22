import java.util.*;
import java.io.*;
/**
 * Write a description of class ExtraData here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ExtraData  
{
    private Map<String, Object> datamap;
    
    public ExtraData()
    {
        datamap = new HashMap<String, Object>();
    }

    public void putString(String name, String data) {
        datamap.put(name, data);
    }
    
    public void putInt(String name, int data) {
        datamap.put(name, data);
    }
    
    public void putFloat(String name, float data) {
        datamap.put(name, data);
    }
    
    public void putLong(String name, long data) {
        datamap.put(name, data);
    }
    
    public void putDouble(String name, double data) {
        datamap.put(name, data);
    }
    
    public void putBoolean(String name, boolean data) {
        datamap.put(name, data);
    }
    
    private void putObject(String name, Object data) {
        datamap.put(name, data);
    }
    
    public void setString(String name, String data)     {putString(name, data);}
    public void setInt(String name, int data)           {putInt(name, data);}
    public void setFloat(String name, float data)       {putFloat(name, data);}
    public void setLong(String name, long data)         {putLong(name, data);}
    public void setDouble(String name, double data)     {putDouble(name, data);}
    public void setBoolean(String name, boolean data)   {putBoolean(name, data);}
    private void setObjectj(String name, Object data)     {putObject(name, data);}
    
    
    public String getString(String name) {
        Object obj = datamap.get(name);
        return (obj!=null||obj instanceof String)?(String)obj:"";
    }
    
    public int getInt(String name) {
        Object obj = datamap.get(name);
        return (obj!=null||obj instanceof Integer)?(Integer)obj:0;
    }
    
    public float getFloat(String name) {
        Object obj = datamap.get(name);
        return (obj!=null||obj instanceof Float)?(Float)obj:0;
    }
    
    public long getLong(String name) {
        Object obj = datamap.get(name);
        return (obj!=null||obj instanceof Long)?(Long)obj:0;
    }
    
    public double getDouble(String name) {
        Object obj = datamap.get(name);
        return (obj!=null||obj instanceof Double)?(Double)obj:0;
    }
    
    public boolean getBoolean(String name) {
        Object obj = datamap.get(name);
        return (obj!=null||obj instanceof Boolean)?(Boolean)obj:false;
    }
    
    private Object getObject(String name) {
        return datamap.get(name);
    }
    
    public byte[] toByteArray() {
        try {
            ByteArrayOutputStream arrayStream = new ByteArrayOutputStream();
            DataOutputStream stream = new DataOutputStream(arrayStream);
            for (String key : datamap.keySet()) {
                Object obj = datamap.get(key);
                byte[] bytes = key.getBytes();
                stream.writeInt(bytes.length);
                stream.write(bytes);
                if (obj instanceof String) {
                    bytes = ((String)obj).getBytes();
                    stream.write(1);
                    stream.writeInt(bytes.length);
                    stream.write(bytes);
                } else if (obj instanceof Integer) {
                    stream.write(2);
                    stream.writeInt((Integer)obj);
                } else if (obj instanceof Float) {
                    stream.write(3);
                    stream.writeFloat((Float)obj);
                } else if (obj instanceof Long) {
                    stream.write(4);
                    stream.writeLong((Long)obj);
                } else if (obj instanceof Double) {
                    stream.write(5);
                    stream.writeDouble((Double)obj);
                } else if (obj instanceof Boolean) {
                    stream.write(6);
                    stream.writeBoolean((Boolean)obj);
                }
            }
            return arrayStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new byte[]{};
    }
    
    public static ExtraData fromByteArray(byte[] array) {
        ExtraData data = new ExtraData();
        try {
            ByteArrayInputStream arrayStream = new ByteArrayInputStream(array);
            DataInputStream stream = new DataInputStream(arrayStream);
            while (stream.available() > 0) {
                byte[] bytes = new byte[stream.readInt()];
                stream.read(bytes);
                String key = new String(bytes);
                int type = stream.read();
                if (type == 1) {
                    bytes = new byte[stream.readInt()];
                    stream.read(bytes);
                    data.putString(key, new String(bytes));
                } else if (type == 2) {
                    data.putInt(key, stream.readInt());
                } else if (type == 3) {
                    data.putFloat(key, stream.readFloat());
                } else if (type == 4) {
                    data.putLong(key, stream.readLong());
                } else if (type == 5) {
                    data.putDouble(key, stream.readDouble());
                } else if (type == 6) {
                    data.putBoolean(key, stream.readBoolean());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }
}
