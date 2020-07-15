package BusinessLayer;

public class BaseProduct extends MenuItem{

    public BaseProduct(String name,double price)
    {
        this.setName(name);
        this.setPrice(price);
    }

    @Override
    public double computePrice() {
        return this.getPrice();
    }
}
