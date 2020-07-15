package orderManagement.model;

import orderManagement.businessLayer.Client;
import orderManagement.businessLayer.Orders;
import orderManagement.businessLayer.Product;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class ExtractValues {

    private String filename;

    /**
     * Method used for extracting the given values from a .txt file
     * @param filename the file we extract values from
     */
    public ExtractValues(String filename) {

        this.filename = filename;
        ArrayList<String> data = new ArrayList<String>();
        try {
            File f = new File(this.filename);
            Scanner sc = new Scanner(f);

            while (sc.hasNextLine()) {
                data.add(sc.nextLine());
            }

        } catch (FileNotFoundException e) {
            System.out.println("error file not found");
            e.printStackTrace();
        }


        int clientCount = 1;
        int productCount = 1;
        int orderCount = 1;
        int billCount = 1;
       for (String i : data)
        {
            String[] values = i.split(" ");

            String clientName = null;//new String();
            String clientAddress =null;// new String();
            String productName = null;//new String();
            String productPrice =null;// new String();
            String productQuantity =null;//new String();
            String orderClientName =null;//new String();
            String orderProductName = null;//new String();
            String orderWantedQuantity = null;//new String();

            if (values[0].equals("Insert")) {

                if (values[1].replace(":", "").equals("client")) {

                    clientName = values[2] + " ";
                    clientName += values[3].replace(",", "");
                    clientAddress = values[4];
                    Client.addClient(clientName, clientAddress);
                }

                if (values[1].replace(":", "").equals("product")) {

                    productName = values[2].replace(",", "");
                    productQuantity = values[3].replace(",", "");
                    productPrice = values[4];
                    Product.addProduct(productName, Integer.parseInt(productQuantity), Double.parseDouble(productPrice));
                }

            } else if (values[0].equals("Delete")) {
                if (values[1].replace(":", "").equals("client")) {

                    clientName = values[2] + " ";
                    clientName += values[3].replace(",", "");
                    clientAddress = values[4];
                    Client.deleteClient(clientName, clientAddress);
                }

                if (values[1].replace(":", "").equals("order_management.businessLayer.Product")) {

                    productName = values[2].replace(",", "");
                    Product.deleteProduct(productName);
                }

            } else if (values[0].replace(":", "").equals("Order")) {
                orderClientName = values[1] + " ";
                orderClientName += values[2].replace(",", "");
                orderProductName = values[3].replace(",", "");
                orderWantedQuantity = values[4];
                Orders.addOrder(orderClientName, orderProductName, Integer.parseInt(orderWantedQuantity));
                Orders.generateBill("Bill" + billCount + ".pdf",orderClientName,orderProductName,Integer.parseInt((orderWantedQuantity)));
                billCount++;

            } else if (values[0].equals("Report")) {
                if (values[1].equals("client")) {
                    Client.addClientsToPdf("ClientReport" + clientCount + ".pdf");
                    clientCount++;
                }
                if (values[1].equals("product")) {
                    Product.addProductToPdf("ProductReport" + productCount +".pdf");
                    productCount++;
                }
                if (values[1].equals("order")) {
                    Orders.addOrdersToPdf("OrderReport" + orderCount + ".pdf");
                    orderCount++;
                }
            }
        }
    }


}
