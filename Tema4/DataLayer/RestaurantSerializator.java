package DataLayer;

import BusinessLayer.MenuItem;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class RestaurantSerializator {
    public static ArrayList<MenuItem> serialize(String aux){
        ArrayList<MenuItem> menu= new ArrayList<MenuItem>();
        try {
            FileInputStream fileInputStream = new FileInputStream(".//" + aux);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            int size = objectInputStream.readInt();
            for(int i=0;i<size;i++){
                menu.add((MenuItem)objectInputStream.readObject());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return menu;
    }
}