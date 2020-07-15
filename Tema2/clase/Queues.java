import java.util.ArrayList;
import java.util.Iterator;

public class Queues extends Thread {

    private ArrayList<Queue> queues;
    private ArrayList<Client> clienti;
    private int simTime;
    private int totalClients;
    private boolean ok;

    String destination;

    public Queues(ExtractValues aux,String destination) {

        this.destination = destination;
        this.queues = new ArrayList<Queue>(aux.getTotalQueues());//init queues

        for(int i=1;i<=aux.getTotalQueues();i++)
            queues.add(new Queue());

        this.clienti = new ArrayList<Client>(aux.getTotalClients());//init client list
        this.ok = true;
        this.simTime = aux.getSimulationInterval();
        this.totalClients = aux.getTotalClients();

         for (int i = 1; i <= aux.getTotalClients(); i++) {
             Client cl = new Client(i, aux.getMinArrivingTime(), aux.getMaxArrivingTime(), aux.getMinServiceTime(), aux.getMaxServiceTime());//set client values
         clienti.add(cl);
         }
    }




    public void run() {

        double averageWaitingTime = 0;
        int tmp = 0;

        for (Queue i : queues) {
            i.setName("Queue "+ tmp +":" + " ");
            i.start();
            tmp++;
        }

        int cnt = 0;

        while (ok && cnt<=this.simTime) {

            this.removeFromWaiting(cnt);

            for(Queue i : queues)
                for(Client j : i.getClienti())
                    averageWaitingTime++;

            //System.out.println("Time " + cnt);
            InsertValues v1 = new InsertValues("Time " + cnt + "\n",this.destination);


            //System.out.print("Waiting clients:");
            InsertValues v2 = new InsertValues("Waiting clients:",this.destination);

            String clientString = new String();
            this.waitingClientsToString(clientString);

            //System.out.println(clientString);
            InsertValues v3 = new InsertValues(clientString + "\n",this.destination);

            for(Queue i : queues) {
                //System.out.println(i.toString());
                InsertValues v4 = new InsertValues(i.toString() + "\n",this.destination);
            }
            cnt++;

            this.forQueues();

            if(this.checkQueues() && this.clienti.isEmpty())
                break;

            //System.out.println();
            InsertValues v5 = new InsertValues("\n",this.destination);
        }

        for (Queue i : queues) {
            i.setOk(false);
        }

        //System.out.println();
        InsertValues v6 = new InsertValues("\n",this.destination);
        averageWaitingTime /= totalClients;
        //System.out.println("Average waiting time: " + averageWaitingTime);

        InsertValues v7 = new InsertValues("Average waiting time: " +averageWaitingTime,this.destination);

    }

    public String waitingClientsToString(String str) {
        for (Client i : this.clienti)
            str = str + i.toString();
        return str;
    }


    public void forQueues(){

        for(Queue i : queues)
            i.setNu(true);
        try {
            sleep(1);//fara sleep procesele se suprapun
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void removeFromWaiting(int counter) {
        Client auxclient;
        Iterator<Client> p = this.clienti.iterator();
        while (p.hasNext()) {
            Queue auxqueue = this.getMin();
            auxclient = p.next();
            if (auxclient.getT_arrival() <= counter) {
                auxqueue.add(auxclient);
                p.remove();
            }
        }
    }

    public Queue getMin() {
        int minim = (int)Double.POSITIVE_INFINITY;
        for(Queue i : queues)
            if(i.getWaitingTime() < minim)
                minim = i.getWaitingTime();

        Queue aux = new Queue();

        for(Queue i : queues)
            if(i.getWaitingTime() == minim) {
                aux = i;
                break;
            }
        return aux;
    }

    public boolean checkQueues(){
        for(Queue i:queues)
            if(i.hasClients())
                return false;
        return true;
    }
}





