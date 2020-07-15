
public class Monom implements Comparable{

    private int coef;
    private int putere;

    public void setCoef(int coef) {
        this.coef = coef;
    }

    public void setPutere(int putere) {
        this.putere = putere;
    }

    public int getCoef() {
        return coef;
    }

    public int getPutere() {
        return putere;
    }


    public Monom(int coef,int putere){

        setPutere(putere);
        setCoef(coef);
    }

    public Monom() {}

    public int compareTo(Object obj){
        if(this.putere==((Monom)obj).putere)
            return 0;
        if(this.putere > ((Monom)obj).putere)
            return -1;//pt ord inversa
        return 1;
    }

    public static Monom adunareMonom(Monom m1,Monom m2){
        Monom aux = new Monom();

        if(m1.getPutere() == m2.getPutere()) {
            aux.setCoef(m1.coef + m2.getCoef());
            aux.setPutere(m1.getPutere());
        }

        return aux;
    }
    public static Monom scadereMonom(Monom m1,Monom m2){
        Monom aux = new Monom();

        if(m1.getPutere() == m2.getPutere()) {
            aux.setCoef(m1.coef - m2.getCoef());
            aux.setPutere(m1.getPutere());
        }

        return aux;
    }
    public static Monom inmultireMonom(Monom m1,Monom m2){
        Monom aux = new Monom();

            aux.setCoef(m1.coef * m2.getCoef());
            aux.setPutere(m1.getPutere() + m2.getPutere());

        return aux;
    }

    public static Monom impartireMonom(Monom m1,Monom m2){
        Monom aux = new Monom();

        aux.setPutere(m1.getPutere() - m2.getPutere());
        aux.setCoef((m1.getCoef() / m2.getCoef()));

        return aux;
    }


    public void swap(Monom m1)
    {
        Monom aux = m1;
        m1.setPutere(this.getPutere());
        m1.setCoef(this.getCoef());
        this.setCoef(aux.getCoef());
        this.setPutere(aux.getPutere());
    }

   public String toString()
    {

        if(this.getCoef() == 0)
            return " +0 ";
        else if(this.getPutere() == 0) {
            if(this.getCoef() > 0)
            return "+" + this.getCoef() + " ";
            else return this.getCoef() + " ";
        }
        else if(this.getPutere() == 1)
        {
            if(this.getCoef() > 0)
                return "+" + this.getCoef() + "X ";
            else return this.getCoef() + "X ";
        }
        else
        {
            if(this.getCoef()>0)
                return "+ " + this.coef + "X^" + this.putere + " ";
            else return this.coef + "X^" + this.putere + " ";
        }


    }
}
