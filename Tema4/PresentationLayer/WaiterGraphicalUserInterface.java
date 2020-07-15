package PresentationLayer;

import BusinessLayer.MenuItem;
import BusinessLayer.Order;
import BusinessLayer.Restaurant;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

public class WaiterGraphicalUserInterface {

    private JFrame waiterFrame;
    private JTable table;
    public Restaurant restaurant;
    private ArrayList<MenuItem> items = new ArrayList<>();
    private JComboBox c1,c2;
    private Order order_aux;


    public WaiterGraphicalUserInterface(Restaurant res) {
        this.restaurant = res;
        initialize();
        this.waiterFrame.setVisible(true);
    }

    private void populateComboTables(){
        this.c1.removeAllItems();
        for(int i = 1; i<= restaurant.getNoTables(); i++)
            this.c1.addItem(i);

    }

    private void populateComboItems(){
        this.c2.removeAllItems();
        for(MenuItem i : restaurant.getMeniu())
            this.c2.addItem((i.getName()));
    }

    private void populateTable() {

        this.table.removeAll();
        Vector<Vector<Object>> rows = new Vector<Vector<Object>>();
        Vector<String> name = new Vector<>();
        name.add("OrderID"); name.add("Table"); name.add("Items");

        for(Order o: restaurant.getComenziHash().keySet()) {
            Vector<Object> row = new Vector<>();
            row.add(o.getOrderID());
            row.add(o.getOrderTable());

            String products = new String();

            for(MenuItem j : restaurant.getComenziHash().get(o)) {
                products += j.getName() + ",";

            }
            row.add(products);
            rows.add(row);
        }

        table.setModel(new DefaultTableModel(rows,name));
    }

    public Order searchOrder(int orderId){
        for(Order i : restaurant.getComenziHash().keySet())
            if (orderId == i.getOrderID())
                return i;
        System.out.println("The specified order does not exits!");
        return null;
    }

    private MenuItem searchMenuItem(String aux){
        for(MenuItem i : restaurant.getMeniu())
            if(i.getName().equals(aux))
                return i;

        System.out.println("Specifie item does not exist!");
        return null;
    }

    private void initialize() {
        waiterFrame = new JFrame();
        waiterFrame.getContentPane().setBackground(Color.WHITE);
        waiterFrame.setBounds(100, 100, 880, 433);
        waiterFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        waiterFrame.getContentPane().setLayout(null);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(402, 7, 452, 384);
        waiterFrame.getContentPane().add(scrollPane);

        table = new JTable();
        scrollPane.setViewportView(table);
        table.setBackground(Color.LIGHT_GRAY);

        JLabel lblAdmin = new JLabel("Waiter");
        lblAdmin.setFont(new Font("Times New Roman", Font.PLAIN, 28));
        lblAdmin.setBounds(10, 11, 108, 59);
        waiterFrame.getContentPane().add(lblAdmin);

        JButton btnAddToOrder = new JButton("Add Item to Order");
        btnAddToOrder.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                items.add(searchMenuItem(c2.getSelectedItem().toString()));

            }
        });
        btnAddToOrder.setBounds(10, 310, 120, 23);
        waiterFrame.getContentPane().add(btnAddToOrder);

        JButton btnCreate = new JButton("Add Order");
        btnCreate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int order_id_aux = restaurant.getComenziHash().keySet().size();
                int table_id_aux = (int)c1.getSelectedItem();
                order_aux = new Order(order_id_aux,table_id_aux);
                restaurant.getComenziHash().put(order_aux,items);
                items = new ArrayList<>();
                populateTable();

            }
        });
        btnCreate.setBounds(10, 343, 120, 23);
        waiterFrame.getContentPane().add(btnCreate);

        JButton btnBill = new JButton("Bill");
        btnBill.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                Order aux = searchOrder((int)table.getValueAt(table.getSelectedRow(),0));
                try {
                    restaurant.generateBill(aux.getOrderID());
                    restaurant.getComenziHash().remove(aux);

                    populateTable();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

            }
        });
        btnBill.setBounds(140, 343, 120, 23);
        waiterFrame.getContentPane().add(btnBill);

        JButton btnEdit = new JButton("Refresh");
        btnEdit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                populateComboItems();
                populateComboTables();
                populateTable();

            }
        });
        btnEdit.setBounds(272, 343, 120, 23);
        waiterFrame.getContentPane().add(btnEdit);

        c1 = new JComboBox();
        c1.setBounds(10, 100, 120, 30);
        waiterFrame.getContentPane().add(c1);

        c2 = new JComboBox();
        c2.setBounds(178, 100, 120, 30);
        waiterFrame.getContentPane().add(c2);


        JLabel lblNewLabel = new JLabel("Table ID");
        lblNewLabel.setBounds(10, 75, 48, 14);
        waiterFrame.getContentPane().add(lblNewLabel);

        JLabel lblMenuItemFor = new JLabel("Menu Item for Order");
        lblMenuItemFor.setBounds(178, 75, 120, 14);
        waiterFrame.getContentPane().add(lblMenuItemFor);

        populateComboItems();
        populateComboTables();
        populateTable();


    }
}
