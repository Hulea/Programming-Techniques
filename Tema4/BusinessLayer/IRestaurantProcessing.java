package BusinessLayer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public interface IRestaurantProcessing {
//admin


    public void createNewMenuItem(String name,double price);


    public void createNewMenuItem(MenuItem aux);


    public void deleteMenuItem(int i);


    public void deleteMenuItem(String name);


    public void editMenuItem(int i,MenuItem newItem);
//waiter


    public void createNewOrder(int id,int tableId, ArrayList<MenuItem> order);


    public double computePrice();


    public void generateBill(int orederId) throws IOException;

}
