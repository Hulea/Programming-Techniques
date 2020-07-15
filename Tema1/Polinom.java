import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.Collections;

public class Polinom {

    private ArrayList<Monom> monoame;
    private int nrTermeni;

    public Polinom() {
        this.monoame = new ArrayList<Monom>();
    }

    public Polinom(String input) {

        monoame = new ArrayList<Monom>();

        Pattern p = Pattern.compile("(-?\\b\\d+)[xX]\\^(-?\\d+\\b)");
        Matcher m = p.matcher(input);

        while (m.find()) {
            Monom auxm = new Monom();
            //System.out.println("Coef: " + m.group(1));
            auxm.setCoef(Integer.parseInt(m.group(1)));
            //System.out.println("Degree: " + m.group(2));
            auxm.setPutere(Integer.parseInt(m.group(2)));

            this.add(auxm);
        }
    }

    public static Polinom deriv(Polinom p) {
        Polinom aux = new Polinom();

        for (Monom i : p.monoame) {
            if (i.getCoef() != 0) {
                if (i.getPutere() == 0) {
                    i.setCoef(0);
                    aux.add(i);
                } else {
                    i.setCoef(i.getCoef() * i.getPutere());
                    i.setPutere(i.getPutere() - 1);
                    aux.add(i);
                }
            }
        }
        return aux;
    }

    public static Polinom integr(Polinom p) {
        Polinom aux = new Polinom();
        for (Monom i : p.monoame) {
            if (i.getPutere() == 0 && i.getCoef() != 0) {
                i.setPutere(1);
                aux.add(i);
            } else if (i.getCoef() != 0) {
                i.setCoef(i.getCoef() / i.getPutere());
                i.setPutere(i.getPutere() + 1);
                aux.add(i);
            }
        }
        return aux;
    }

    public static Polinom adunarePoli(Polinom p1, Polinom p2) {
        Polinom aux = new Polinom();
        for (Monom i : p1.monoame) {
            for (Monom j : p2.monoame) {
                if (i.getPutere() == j.getPutere())
                    aux.add(Monom.adunareMonom(i, j));
            }
        }
        for (Monom i : p1.monoame) {
            int ok = 0;
            for (Monom j : p2.monoame)
                if (i.getPutere() == j.getPutere())
                    ok = 1;

            if (ok == 0)
                aux.add(i);
        }
        for (Monom i : p2.monoame) {
            int ok = 0;
            for (Monom j : p1.monoame) {
                if (i.getPutere() == j.getPutere())
                    ok = 1;
            }
            if (ok == 0)
                aux.add(i);
        }
        return aux;
    }

    public static Polinom scaderePoli(Polinom p1, Polinom p2) {
        Polinom aux = new Polinom();
        for (Monom i : p1.monoame) {
            for (Monom j : p2.monoame) {
                if (i.getPutere() == j.getPutere())
                    aux.add(Monom.scadereMonom(i, j));
            }
        }
        for (Monom i : p1.monoame) {
            int ok = 0;
            for (Monom j : p2.monoame)
                if (i.getPutere() == j.getPutere())
                    ok = 1;

            if (ok == 0)
                aux.add(i);
        }

        for (Monom i : p2.monoame) {
            int ok = 0;
            for (Monom j : p1.monoame)
                if (i.getPutere() == j.getPutere())
                    ok = 1;
            if (ok == 0) {
                Monom aux2 = new Monom(-i.getCoef(), i.getPutere());
                aux.add(aux2);
            }
        }

        Polinom result = new Polinom();

        for (Monom i : aux.monoame) {
            if (i.getCoef() != 0)
                result.add(i);
        }

        return result;
    }

    public static Polinom inmultirePoli(Polinom p1, Polinom p2) {
        Polinom aux = new Polinom();

        for (Monom i : p1.monoame) {
            for (Monom j : p2.monoame) {
                int ok = 0;

                Monom aux2 = Monom.inmultireMonom(i, j);

                int put = aux2.getPutere();

                for (Monom k : aux.monoame) {
                    if (k.getPutere() == put) {
                        ok = 1;
                        aux.aceeasiPutere(aux2);
                    }
                }
                if (ok == 0)
                    aux.add(aux2);
            }
        }

        return aux;
    }

    public static void impartirePoli(Polinom p1, Polinom p2, Polinom cat, Polinom rest) {
        Polinom po1 = new Polinom(p1.toString());
        Polinom aux = new Polinom();

        try {
            while (po1.primaPutere() >= p2.primaPutere()) {
                aux.add(Monom.impartireMonom(po1.monoame.get(0), p2.monoame.get(0)));

                cat.add(aux.monoame.get(0));

                po1 = Polinom.scaderePoli(po1, Polinom.inmultirePoli(aux, p2));

                aux.removeFirst();

            }
        } catch (Exception e) {

        }
        for (Monom m : po1.monoame) {
            rest.add(m);
            rest.sort();
        }
    }

    public void add(Monom mono) {
        monoame.add(mono);
        nrTermeni++;
    }

    public void sort() {
        Collections.sort(this.monoame);
    }

    public void print() {
        int aux = 0;
        for (Monom i : monoame) {
            if (aux == nrTermeni - 1) {
                System.out.print(i.toString());
                aux++;
            } else {
                System.out.print(i.toString());
                aux++;
            }
        }
        System.out.println();
    }

    public String toString() {
        this.sort();

        String aux = "";
        for (Monom i : monoame) {
            aux = aux + i.toString();
        }
        return aux;
    }

    public void aceeasiPutere(Monom mon) {
        for (Monom i : this.monoame) {
            if (mon.getPutere() == i.getPutere()) {
                i.setCoef(i.getCoef() + mon.getCoef());
            }
        }
    }

    public int primaPutere() {
        return monoame.get(0).getPutere();
    }

    public void removeFirst() {
        monoame.remove(0);
    }

}


