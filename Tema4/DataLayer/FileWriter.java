package DataLayer;

import BusinessLayer.MenuItem;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class FileWriter {
    public static void fw(ArrayList<MenuItem> menu,String aux){
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(".//" + aux);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeInt(menu.size());
            for(MenuItem menuItem: menu)
                objectOutputStream.writeObject(menuItem);
            objectOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}