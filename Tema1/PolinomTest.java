
import static org.junit.Assert.*;

public class PolinomTest{


    @org.junit.Test
    public void adunarePoli() {
        Polinom aux = new Polinom();
        Polinom a1 = new Polinom("+ 1X^2 + 1X^1");
        Polinom a2 = new Polinom ("+ 1X^2 + 2X^1");
        Polinom a3 = Polinom.adunarePoli(a1,a2);
        Polinom a4 = new Polinom("+ 2X^2 + 3X^1");
        assertEquals(a3.toString(),a4.toString());

    }

    @org.junit.Test
    public void scaderePoli(){
        Polinom aux = new Polinom();
        Polinom a1 = new Polinom("+ X^1");
        Polinom a2 = new Polinom("+ X^1");
        Polinom a3 = Polinom.scaderePoli(a1,a2);
        Polinom a4 = new Polinom("+ 0");
        assertEquals(a3.toString(),a4.toString());
    }

    @org.junit.Test
    public void inmultirePoli(){
        Polinom aux = new Polinom();
        Polinom a1 = new Polinom("+ 2X^2");
        Polinom a2 = new Polinom("+ 3X^1");
        Polinom a3 = Polinom.inmultirePoli(a1,a2);
        Polinom a4 = new Polinom("+ 6X^3");
        assertEquals(a3.toString(),a4.toString());
    }

    @org.junit.Test
    public void impartirePoli(){
        Polinom aux = new Polinom();
        Polinom a1 = new Polinom("+ 3X^2 + 1X^1 + 1X^0");
        Polinom a2 = new Polinom("+ 1X^1 + 1X^0");
        Polinom cat1 = new Polinom();
        Polinom rest1 = new Polinom();
        Polinom.impartirePoli(a1,a2,cat1,rest1);
        Polinom cat = new Polinom("+ 3X^1 - 3X^0");
        Polinom rest = new Polinom("+ 3X^0");
    }

    @org.junit.Test
    public void deriv(){
        Polinom a1 = new Polinom("+ 1X^3 + 2X^2 + 5X^1 + 2X^0");
        Polinom a2 = Polinom.deriv(a1);
        Polinom a3 = new Polinom("+ 3X^2 +4X^1 +5X^0 + 0X^0");
        assertEquals(a3.toString(),a2.toString());
    }

    @org.junit.Test
    public void integr(){
        Polinom a1 = new Polinom("+ 1X^3 + 2X^2 + X^1 + 2X^0");
        Polinom a2 = Polinom.integr(a1);
        Polinom a3 = new Polinom("+ 0X^4 + 1X^3 + 2X^1");
        assertEquals(a3.toString(),a2.toString());
    }
}