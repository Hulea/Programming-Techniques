package BusinessLayer;

import java.util.ArrayList;
import java.util.Arrays;

public class CompositeProduct extends MenuItem{

    public ArrayList<MenuItem> items ;
    private String name;
    private double price;

    public CompositeProduct(){
        this.items = new ArrayList<MenuItem>();
    }

  public CompositeProduct(String name){
      this.name = name;
      this.items = new ArrayList<MenuItem>();
  }

  public CompositeProduct(String name,ArrayList<MenuItem> aux){
      items = new ArrayList<MenuItem>();
      this.name = name;
      for(MenuItem i : aux)
          this.price += i.getPrice();
  }

    public void add(MenuItem aux){
        this.price += aux.getPrice();
        this.items.add(aux);
    }

    public void deleteFromWithin(MenuItem aux){
        int counter = 0;
        for(MenuItem i : this.items) {
            if (aux.equals(i))
                this.items.remove(counter);
                counter++;
        }
    }

    public double getPrice(){
        return this.price;
    }

    public void setPrice(double price){
        this.price = price;
    }

    public String getName(){
      return this.name;
    }

    public String toString(){
        String aux = new String();

        for(MenuItem i : this.items) {
            aux = aux + i.getName() + ", ";
        }
        //aux += "; Price:" + price;
        return aux;
    }

    @Override
    public double computePrice() {
        double priceToReturn = 0;
        for(MenuItem i : this.items)
            priceToReturn += i.computePrice();

        return priceToReturn;
    }


}
