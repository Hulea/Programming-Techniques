package BusinessLayer;

import DataLayer.RestaurantSerializator;

import java.awt.*;
import java.util.*;
import java.io.FileWriter;
import java.io.IOException;

public class Restaurant extends Observable implements IRestaurantProcessing {

    private String fileName;
    private ArrayList<MenuItem> meniu = new ArrayList<MenuItem>();
    private Map<Order,ArrayList<MenuItem>> comenziHash;
    private final static int noTables = 100;
    private Observer obs;


    public ArrayList<MenuItem> getMenu(){
        return this.meniu;
    }


    public  Map<Order,ArrayList<MenuItem>> getComenziHash(){
        return this.comenziHash;
    }


    public int getNoTables(){
        return noTables;
    }


    public void deleteContaining(MenuItem aux,ArrayList<MenuItem> meniuaux){

        int count = 0;

        for(MenuItem i : meniuaux){
            if(i instanceof CompositeProduct)
                ((CompositeProduct) i).deleteFromWithin(aux);
            else
                meniuaux.remove(count);
            count++;

        }

    }


    public void print(){
        for(MenuItem i : meniu)
            if(i instanceof BaseProduct)
                System.out.println(i.toString());
            else if(i instanceof CompositeProduct)
                System.out.println(i.toString());

        System.out.println();

        //System.out.println(comenziHash.toString());
        int count = 0;
        for(Order i : this.comenziHash.keySet()) {

            System.out.print(count + " ");
            count++;
            System.out.println(i.toString());

        }

        count = 0;
        for(ArrayList<MenuItem> i : this.comenziHash.values()) {

            System.out.print(count + " ");
            count++;
//            System.out.println(i.toString());
            for(MenuItem j : i)
            {
                System.out.println(j.toString());
            }
        }

        System.out.println();
    }


    public ArrayList<MenuItem> getMeniu(){
        return this.meniu;
    }


    public Restaurant(String fileName)
    {
        this.fileName = fileName;
        this.meniu = RestaurantSerializator.serialize(fileName);
        this.comenziHash = new HashMap<Order,ArrayList<MenuItem>>();
    }


    @Override
    public void createNewMenuItem(String name,double price) {
        BaseProduct aux = new BaseProduct(name,price);
        meniu.add(aux);
    }


    @Override
    public void createNewMenuItem(MenuItem aux) {
        meniu.add(aux);
    }


    @Override
    public void deleteMenuItem(int i) {
        meniu.remove(i);
    }

    @Override
    public void deleteMenuItem(String name) {
        int aux = 0;
        int cnt = 0, ok=0;
        for(MenuItem i : meniu) {
            if(i.getName().equals(name)) {
                aux = cnt;
                ok=1;
            }
            cnt++;
        }

        if(ok==1)
            meniu.remove(aux);
        else System.out.println("The specified item does not exist");
    }



    @Override
    public void editMenuItem(int i,MenuItem newItem) {
        meniu.set(i,newItem);
    }


    @Override
    public void createNewOrder(int id,int tableId, ArrayList<MenuItem> ordrs) {

        Order aux = new Order(id,tableId);
        comenziHash.put(aux,ordrs);
        notifyObservers(obs);
    }


    public void notifyObserver(Observable obs,String aux){
        this.obs.update(obs,aux);
    }


    @Override
    public double computePrice() {
        double priceToReturn = 0;
        for(MenuItem i : this.meniu)
            priceToReturn += i.computePrice();

        return priceToReturn;
    }


    @Override
    public void generateBill(int orderId) throws IOException {

        double totalPrice = 0;
        Order aux = searchOrder(orderId);
        String out = aux.toString() + "\n";

        Collection<MenuItem> ion = null;

        try{
            FileWriter g = new FileWriter("bill" + orderId +".txt",false);

            for(Order i : comenziHash.keySet())
                if(i.getOrderID() == orderId)
                {
                    ion = comenziHash.get(i);
                }

            for(MenuItem j : ion) {
                out += j.toString();
                totalPrice += j.getPrice();
            }
            g.write(out + "\n" + "Total price: " + totalPrice);

            g.close();
        }
        catch(IOException e) {
            System.out.println("an err has occ");
            e.printStackTrace();
        }

    }


    public Order searchOrder(int orderId){
        for(Order i : this.comenziHash.keySet())
            if (orderId == i.getOrderID())
                return i;
        System.out.println("The specified order does not exits!");
        return null;
    }
}
