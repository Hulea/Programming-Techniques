import java.util.ArrayList;

public class Queue extends Thread{

    private ArrayList<Client> clienti;
    private int waitingTimeInQueue;
    private volatile boolean ok = true;
    private volatile boolean nu = false;

    public void run(){
        while(ok) {
            if(nu) {
                this.decreaseCurrentClientTime();
                nu = false;
            }
        }
    }

    public void setNu(boolean aux){
        this.nu = aux;
    }

    public String toString() {
        String s = this.getName();
        if(clienti.isEmpty())
            s += "closed";
        else{
            for(Client i:clienti)
                s += i.toString();
        }
        return s;
    }

    public Queue(){
        clienti = new ArrayList<Client>();
        this.waitingTimeInQueue = 0;
    }

    public void add(Client aux)
    {
        this.clienti.add(aux);
        this.waitingTimeInQueue += aux.getT_service();
    }

    public ArrayList<Client> getClienti() {
        return clienti;
    }

    public int getWaitingTime() {
        return waitingTimeInQueue;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public boolean hasClients(){
        if(clienti.isEmpty())
            return false;
        else return true;
    }

    public void decreaseCurrentClientTime() {
        if (!clienti.isEmpty()) {
            if(this.clienti.get(0).getT_service() > 0)
                this.clienti.get(0).decreaseServiceTime();
            if(this.clienti.get(0).getT_service() == 0)
                this.clienti.remove(0);
        }
    }
}
