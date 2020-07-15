package BusinessLayer;
import java.io.Serializable;

public abstract class MenuItem implements Serializable {

    private String name;
    private double price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String toString(){
        return "Name: " + name + " ; Price : " + price;
    }

    public abstract double computePrice();
}
