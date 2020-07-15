package BusinessLayer;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Order implements Serializable {

    private int orderID;
    private Date date;
    private int table;

    public int getOrderID(){
        return this.orderID;
    }

    public int getOrderTable(){
        return this.table;
    }

    public Order(int orderID, int table) {
        this.orderID = orderID;
        this.date = new Date();
        this.table = table;
    }

    public String toString(){
        String aux = new String();

        aux += "Order ID: " + this.orderID + ", ";
        aux += "Date: " + this.date + ", ";
        aux += "Table: " + this.table;

        return aux;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return orderID == order.orderID &&
                table == order.table &&
                Objects.equals(date, order.date);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + orderID;
        result = 31 * result + date.hashCode();
        result = 31 * result + table;
        return result;
    }
}
