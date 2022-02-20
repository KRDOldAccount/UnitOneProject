package ata.unit.one.project.backend;

import java.io.*;
import java.util.HashMap;

public class Storage implements Serializable {

    public void store(String fileName, HashMap map) {
        try {
            FileOutputStream fos = new FileOutputStream(fileName);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(map);
            oos.close();
            fos.close();
        } catch (Exception ignored) {
        }
    }

    public HashMap retrieve(String fileName) {
        HashMap map = null;
        try {
            FileInputStream fis = new FileInputStream(fileName);
            ObjectInputStream ois = new ObjectInputStream(fis);
            map = (HashMap) ois.readObject();
            ois.close();
            fis.close();
        } catch (IOException | ClassNotFoundException ex) {
        }
        return map;
    }
}
