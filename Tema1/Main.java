import javax.swing.*;
import java.awt.*;
import java.util.Collections;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Main {

    public static void main(String[] args) {

        Monom aux = new Monom(2,3);
        System.out.println(aux.toString());

        Polinom aux1 = new Polinom("3X^2 + 4X^1 + 1X^0");
        System.out.println(Polinom.inmultirePoli(aux1,aux1).toString());

        Interfata window = new Interfata();
        window.calcFrame.setVisible(true);


    }



}
