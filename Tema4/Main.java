import BusinessLayer.*;
import BusinessLayer.MenuItem;
import PresentationLayer.AdministratorGraphicalUserInterface;
import PresentationLayer.ChefGraphicalUserInterface;
import PresentationLayer.WaiterGraphicalUserInterface;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args)
    {



        String fileName = "restaurant.ser";

        Restaurant res = new Restaurant(fileName);
        ChefGraphicalUserInterface window2 = new ChefGraphicalUserInterface(res);
        //window2.chefFrame.setVisible(true);

        AdministratorGraphicalUserInterface window1 = new AdministratorGraphicalUserInterface(res,fileName);
        // window1.frameAdmin.setVisible(true);

        WaiterGraphicalUserInterface window3 = new WaiterGraphicalUserInterface(res);
        //window3.waiterFrame.setVisible(true);

        res.getMeniu().add(new BaseProduct("base0",500));

        res.createNewMenuItem("base1",1);
        res.createNewMenuItem("base2",2);
        res.deleteMenuItem("base1");

        ArrayList<MenuItem> arr = new ArrayList<MenuItem>();
        CompositeProduct arr2 = new CompositeProduct("comp2",arr);
        arr2.add(new BaseProduct("in_comp1",3));
        arr2.add(new BaseProduct("in_comp2",3));
        res.createNewMenuItem(arr2);

        ArrayList<MenuItem> arr3 = new ArrayList<MenuItem>();
        arr3.add(arr2);
        arr3.add(new BaseProduct("cartofi",6969));
        Order ord = new Order(1,2);
        res.createNewOrder(ord.getOrderID(),2,arr3);

       //res.print();

    }

}
