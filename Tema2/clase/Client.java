import java.util.Random;

public class Client {

    private int ID;
    private int t_arrival;
    private int t_service;

    public Client(int id,int ta1,int ta2,int ts1,int ts2){
        this.ID = id;
        Random random = new Random();
        this.t_arrival = random.nextInt(ta2 - ta1 + 1) + ta1 ;
        this.t_service = random.nextInt(ts2 - ts1 + 1) + ts1;
    }

    public Client(int id,int ta,int ts) {
        this.ID = id;
        this.t_arrival = ta;
        this.t_service = ts;
    }

    public Client(){}

    public String toString(){
        return "(" + this.getID() + "," + this.getT_arrival() + "," + this.getT_service() + "); ";
    }

    public int getID() {
        return ID;
    }

    public int getT_arrival() {
        return t_arrival;
    }

    public int getT_service() {
        return t_service;
    }

    public void decreaseServiceTime(){
        this.t_service--;
    }

}
