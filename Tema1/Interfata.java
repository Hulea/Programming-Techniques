import java.awt.EventQueue;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Interfata {

    public JFrame calcFrame;
    private JTextField Poli1;
    private JTextField Poli2;
    private JTextField catField;
    private JTextField restField;
    private JTextField rezField;
    private JButton butonAdunare;
    private JButton butonScadere;
    private JButton butonInmultire;
    private JButton butonImpartire;
    private JButton butonDerivare;
    private JButton butonIntegrare;


    public Interfata() {
        initialize();
    }

    private void initialize() {
        calcFrame = new JFrame();
        calcFrame.setTitle("Polynomial Calculator");
        calcFrame.setBounds(100, 100, 500, 350);
        calcFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        calcFrame.getContentPane().setLayout(null);

        Poli1 = new JTextField();
        Poli1.setBounds(10, 30, 200, 40);
        calcFrame.getContentPane().add(Poli1);
        Poli1.setColumns(10);

        Poli2 = new JTextField();
        Poli2.setColumns(10);
        Poli2.setBounds(270, 30, 200, 40);
        calcFrame.getContentPane().add(Poli2);

        catField = new JTextField();
        catField.setColumns(10);
        catField.setBounds(270, 100, 200, 40);
        catField.setEditable(false);
        calcFrame.getContentPane().add(catField);

        restField = new JTextField();
        restField.setColumns(10);
        restField.setBounds(270, 170, 200, 40);
        restField.setEditable(false);
        calcFrame.getContentPane().add(restField);

        rezField = new JTextField();
        rezField.setColumns(10);
        rezField.setBounds(10,240,460,40);
        rezField.setEditable(false);
        calcFrame.getContentPane().add(rezField);

        butonAdunare = new JButton("Adunare");
        butonAdunare.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                try{

                    if(Poli1.getText().length()==0 || Poli2.getText().length()==0)
                        JOptionPane.showMessageDialog(butonAdunare,"No input!");

                    Polinom aux1 = new Polinom(Poli1.getText());
                    Polinom aux2 = new Polinom(Poli2.getText());

                    rezField.setText(Polinom.adunarePoli(aux1,aux2).toString());

                }
                catch(Exception e1){
                    System.out.println(e1);
                    JOptionPane.showMessageDialog(butonAdunare,"Incorrect input");
                }

            }
        });
        butonAdunare.setBounds(10, 100, 90, 25);
        calcFrame.getContentPane().add(butonAdunare);

        butonScadere = new JButton("Scadere");
        butonScadere.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                try{

                    if(Poli1.getText().length()==0 || Poli2.getText().length()==0)
                        JOptionPane.showMessageDialog(butonAdunare,"No input!");

                    Polinom aux1 = new Polinom(Poli1.getText());
                    Polinom aux2 = new Polinom(Poli2.getText());

                    rezField.setText(Polinom.scaderePoli(aux1,aux2).toString());

                }
                catch(Exception e1){
                    System.out.println(e1);
                }
            }
        });
        butonScadere.setBounds(120, 100, 90, 25);
        calcFrame.getContentPane().add(butonScadere);

        butonInmultire = new JButton("Imnultire");
        butonInmultire.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                try{

                    if(Poli1.getText().length()==0 || Poli2.getText().length()==0)
                        JOptionPane.showMessageDialog(butonAdunare,"No input!");

                    Polinom aux1 = new Polinom(Poli1.getText());
                    Polinom aux2 = new Polinom(Poli2.getText());

                    rezField.setText(Polinom.inmultirePoli(aux1,aux2).toString());

                }
                catch(Exception e1){
                    System.out.println(e1);
                }
            }
        });


        butonInmultire.setBounds(10, 140, 90, 25);
        calcFrame.getContentPane().add(butonInmultire);

        butonImpartire = new JButton("Impartire");
        butonImpartire.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{

                    if(Poli1.getText().length()==0 || Poli2.getText().length()==0)
                        JOptionPane.showMessageDialog(butonAdunare,"No input!");

                    Polinom aux1 = new Polinom(Poli1.getText());
                    Polinom aux2 = new Polinom(Poli2.getText());

                    Polinom cat = new Polinom();
                    Polinom rest = new Polinom();

                    Polinom.impartirePoli(aux1,aux2,cat,rest);

                    catField.setText(cat.toString());
                    restField.setText(rest.toString());
                }
                catch(Exception e1){
                    System.out.println(e1);
                }
            }
        });
        butonImpartire.setBounds(120, 140, 90, 25);
        calcFrame.getContentPane().add(butonImpartire);

        butonDerivare = new JButton("Derivare");
        butonDerivare.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                try{

                    if(Poli1.getText().length()==0)
                        JOptionPane.showMessageDialog(butonAdunare,"No input!");

                    Polinom aux1 = new Polinom(Poli1.getText());

                    rezField.setText(Polinom.deriv(aux1).toString());

                }
                catch(Exception e1){
                    System.out.println(e1);
                }
            }
        });
        butonDerivare.setBounds(10, 180, 90, 25);
        calcFrame.getContentPane().add(butonDerivare);

        butonIntegrare = new JButton("Integrare");
        butonIntegrare.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                try{

                    if(Poli1.getText().length()==0)
                        JOptionPane.showMessageDialog(butonAdunare,"No input!");

                    Polinom aux1 = new Polinom(Poli1.getText());

                    rezField.setText(Polinom.integr(aux1).toString());

                }
                catch(Exception e1){
                    System.out.println(e1);
                }
            }
        });
        butonIntegrare.setBounds(120, 180, 90, 25);
        calcFrame.getContentPane().add(butonIntegrare);

        JLabel labelRest = new JLabel("Rest");
        labelRest.setBounds(270, 145, 90, 15);
        calcFrame.getContentPane().add(labelRest);

        JLabel labelCat = new JLabel("Cat");
        labelCat.setBounds(270, 80, 90, 15);
        calcFrame.getContentPane().add(labelCat);

        JLabel labelPoli1 = new JLabel("Polinom 1");
        labelPoli1.setBounds(10, 5, 90, 15);
        calcFrame.getContentPane().add(labelPoli1);

        JLabel labelPoli2 = new JLabel("Polinom 2");
        labelPoli2.setBounds(270, 5, 90, 15);
        calcFrame.getContentPane().add(labelPoli2);


    }
}
