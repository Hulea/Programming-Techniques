package PresentationLayer;

import BusinessLayer.BaseProduct;
import BusinessLayer.CompositeProduct;
import BusinessLayer.MenuItem;
import BusinessLayer.Restaurant;
import DataLayer.FileWriter;

import java.awt.EventQueue;

import javax.swing.*;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.SQLOutput;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;

public class AdministratorGraphicalUserInterface {

    private JFrame frameAdmin;
    private JTable table;
    private JTextField textField;
    private JTextField textField_1;
    private JTextField textField_2;
    private String fileName;
    private Restaurant restaurant ;
    private DefaultTableModel model = new DefaultTableModel();
    private CompositeProduct aux_comp ;
    private JComboBox combo;
    private JTextField textField_3;


    public AdministratorGraphicalUserInterface(Restaurant aux,String filename){
        this.restaurant = new Restaurant(filename);
        this.fileName = filename;
        restaurant = aux;
        initialize();
        this.frameAdmin.setVisible(true);
    }


    private void refreshComboBox(){
        this.combo.removeAllItems();
        for(MenuItem i : restaurant.getMeniu())
            this.combo.addItem((i.getName()));
    }

    private void refreshTable()
    {
        this.table.removeAll();
        Vector<Vector<Object>> rows = new Vector<Vector<Object>>();
        Vector<String> name = new Vector<>();
        name.add("Index");name.add("Name"); name.add("Components"); name.add("Price");
        int counter = 0;
        for(MenuItem m: restaurant.getMeniu()) {

            Vector<Object> row = new Vector<>();
            row.add(counter);
            counter++;
            row.add(m.getName());
            if(m instanceof CompositeProduct) {
                row.addElement(m.toString());
            }
            else row.addElement("");
            row.add(m.getPrice());
            rows.add(row);
        }

        table.setModel(new DefaultTableModel(rows,name));
    }


    private void initialize() {

        frameAdmin = new JFrame();
        frameAdmin.getContentPane().setBackground(Color.WHITE);
        frameAdmin.setBounds(100, 100, 930, 585);
        frameAdmin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameAdmin.getContentPane().setLayout(null);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(452, 11, 452, 524);
        frameAdmin.getContentPane().add(scrollPane);


        combo = new JComboBox();
        combo.setBounds(286, 34, 108, 22);
        frameAdmin.getContentPane().add(combo);


        textField_3 = new JTextField();
        textField_3.setColumns(10);
        textField_3.setBounds(69, 238, 170, 37);
        frameAdmin.getContentPane().add(textField_3);

        aux_comp = new CompositeProduct(textField_3.getText());


        table = new JTable();
        table.setModel(model);
        scrollPane.setViewportView(table);
        table.setBackground(Color.LIGHT_GRAY);

        JLabel lblAdmin = new JLabel("Admin");
        lblAdmin.setFont(new Font("Times New Roman", Font.PLAIN, 28));
        lblAdmin.setBounds(10, 11, 108, 59);
        frameAdmin.getContentPane().add(lblAdmin);

        JButton btnCreate = new JButton("Create");
        btnCreate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {


                    restaurant.createNewMenuItem(textField_1.getText(), Double.parseDouble(textField_2.getText()));
                    restaurant.print();

                refreshComboBox();
                refreshTable();
                FileWriter.fw(restaurant.getMeniu(),fileName);
            }
        });
        btnCreate.setBounds(10, 444, 89, 23);
        frameAdmin.getContentPane().add(btnCreate);

        JButton btnDelete = new JButton("Delete");
        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                try{
                    //String aux = textField.getText();
                   // System.out.println("!!!!!!!!!!!" + aux);
                   // res.deleteMenuItem(aux);
                   restaurant.deleteMenuItem(Integer.parseInt(textField.getText()));
                   restaurant.print();
                }
                catch(Exception e1)
                {
                    System.out.println(e1);
                }
                refreshComboBox();
                refreshTable();
                FileWriter.fw(restaurant.getMeniu(),fileName);

            }
        });
        btnDelete.setBounds(10, 478, 89, 23);
        frameAdmin.getContentPane().add(btnDelete);

        JButton btnEdit = new JButton("Edit");
        btnEdit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                try{

                    restaurant.editMenuItem(Integer.parseInt(textField.getText()),new BaseProduct(textField_1.getText(),Double.parseDouble(textField_2.getText())));
                    restaurant.print();
                }
                catch(Exception e1){
                    System.out.println(e1);
                }
                refreshTable();
                refreshComboBox();
                FileWriter.fw(restaurant.getMeniu(),fileName);
            }
        });
        btnEdit.setBounds(10, 512, 89, 23);
        frameAdmin.getContentPane().add(btnEdit);

        textField = new JTextField();
        textField.setBounds(69, 95, 170, 37);
        frameAdmin.getContentPane().add(textField);
        textField.setColumns(10);

        textField_1 = new JTextField();
        textField_1.setColumns(10);
        textField_1.setBounds(69, 143, 170, 37);
        frameAdmin.getContentPane().add(textField_1);

        textField_2 = new JTextField();
        textField_2.setColumns(10);
        textField_2.setBounds(69, 190, 170, 37);
        frameAdmin.getContentPane().add(textField_2);

        JLabel lblId = new JLabel("ID");
        lblId.setBounds(10, 106, 48, 14);
        frameAdmin.getContentPane().add(lblId);

        JLabel lblName = new JLabel("Name");
        lblName.setBounds(10, 154, 48, 14);
        frameAdmin.getContentPane().add(lblName);

        JLabel lblPrice = new JLabel("Price");
        lblPrice.setBounds(10, 201, 48, 14);
        frameAdmin.getContentPane().add(lblPrice);

        JButton btnAddComposite = new JButton("Add Composite");
        btnAddComposite.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                restaurant.getMeniu().add(aux_comp);
                aux_comp = new CompositeProduct(textField_3.getText());
                refreshTable();
                refreshComboBox();
                FileWriter.fw(restaurant.getMeniu(),fileName);

            }
        });
        btnAddComposite.setBounds(109, 444, 150, 23);
        frameAdmin.getContentPane().add(btnAddComposite);

        JButton btnEditComposite = new JButton("Add to Composite");
        btnEditComposite.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                aux_comp.setName(textField_3.getText());
                MenuItem aux = search(combo.getSelectedItem().toString());
                aux_comp.add(aux);

                refreshComboBox();
                refreshTable();
                FileWriter.fw(restaurant.getMeniu(),fileName);

            }
        });
        btnEditComposite.setBounds(109, 478, 150, 23);
        frameAdmin.getContentPane().add(btnEditComposite);


        JLabel label = new JLabel("+");
        label.setBounds(10, 249, 48, 14);
        frameAdmin.getContentPane().add(label);


        JButton btnRefresh = new JButton("Refresh");
        btnRefresh.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                refreshComboBox();
                refreshTable();

            }
        });
        btnRefresh.setBounds(10, 360, 89, 23);
        frameAdmin.getContentPane().add(btnRefresh);

        JButton button = new JButton("Remove from Composite");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {


                aux_comp.setName(textField_3.getText());
                int aux = search2(combo.getSelectedItem().toString());
                aux_comp.items.remove(aux);

                refreshComboBox();
                refreshTable();


                aux_comp = new CompositeProduct();

                FileWriter.fw(restaurant.getMeniu(),fileName);
            }
        });
        button.setBounds(109, 512, 150, 23);
        frameAdmin.getContentPane().add(button);

    }

    public MenuItem search(String nume){
        for(MenuItem i : restaurant.getMeniu()){
            if(nume.equals(i.getName()))
                return i;
        }
        System.out.println("Nu exista produsul");
        return null;
    }

    public int search2(String nume){
        int counter = -1;
        for(MenuItem i : restaurant.getMeniu()){
            if(nume.equals(i.getName()))
            counter++;
        }
        if(counter == -1) {
            System.out.println("Does not contain specified item");
            return -1;
        }
        else {
            counter++;
            return counter;
        }
    }



}
