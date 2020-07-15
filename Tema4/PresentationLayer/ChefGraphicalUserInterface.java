package PresentationLayer;

import BusinessLayer.MenuItem;
import BusinessLayer.Order;
import BusinessLayer.Restaurant;

import javax.swing.JFrame;
import java.awt.Font;
import javax.swing.JTable;
import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class ChefGraphicalUserInterface implements Observer {

    private JFrame chefFrame;
    private JTable table;
    private JTextField textField;
    private JTextField textField_1;
    private JTextField textField_2;
    private Restaurant restaurant;


    private void populateTable() {

        this.table.removeAll();
        Vector<Vector<Object>> randuri = new Vector<Vector<Object>>();
        Vector<String> aux = new Vector<>();
        aux.add("OrderID"); aux.add("Table"); aux.add("Items");

        for(Order i: restaurant.getComenziHash().keySet()) {
            Vector<Object> rand = new Vector<>();
            rand.add(i.getOrderID());
            rand.add(i.getOrderTable());

            String products = new String();

            for(MenuItem j : restaurant.getComenziHash().get(i)) {
                products += j.getName() + ",";

            }
            rand.add(products);
            randuri.add(rand);
        }

        table.setModel(new DefaultTableModel(randuri,aux));
    }

    public ChefGraphicalUserInterface(Restaurant res) {
        this.restaurant = res;
        initialize();
        this.chefFrame.setVisible(true);
    }


    private void initialize() {
        chefFrame = new JFrame();
        chefFrame.getContentPane().setBackground(Color.WHITE);
        chefFrame.setBounds(100, 100, 809, 481);
        chefFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        chefFrame.getContentPane().setLayout(null);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(284, 11, 452, 391);
        chefFrame.getContentPane().add(scrollPane);

        table = new JTable();
        scrollPane.setViewportView(table);
        table.setBackground(Color.LIGHT_GRAY);

        JLabel lblAdmin = new JLabel("Chef");
        lblAdmin.setFont(new Font("Times New Roman", Font.PLAIN, 28));
        lblAdmin.setBounds(10, 11, 108, 59);
        chefFrame.getContentPane().add(lblAdmin);

        JButton btnCreate = new JButton("Refresh");
        btnCreate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                populateTable();
            }
        });
        btnCreate.setBounds(10, 307, 89, 23);
        chefFrame.getContentPane().add(btnCreate);


        textField = new JTextField();
        textField.setBounds(10, 97, 170, 37);
        chefFrame.getContentPane().add(textField);
        textField.setColumns(10);

        textField_1 = new JTextField();
        textField_1.setColumns(10);
        textField_1.setBounds(10, 165, 170, 37);
        chefFrame.getContentPane().add(textField_1);

        textField_2 = new JTextField();
        textField_2.setColumns(10);
        textField_2.setBounds(10, 232, 170, 37);
        chefFrame.getContentPane().add(textField_2);
    }


    @Override
    public void update(Observable o, Object arg) {
        String[] aux = (String[]) arg;
        ((DefaultTableModel)table.getModel()).addRow(aux);
    }
}
