import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ExtractValues {

    private String filename;
    private int totalClients;
    private int totalQueues;
    private int simulationInterval;
    private int minArrivingTime;
    private int maxArrivingTime;
    private int minServiceTime;
    private int maxServiceTime;

    public ExtractValues(String filename){

        this.filename = filename;
        String data = new String();
        try{
            File f = new File(this.filename);
            Scanner sc = new Scanner(f);

            while(sc.hasNextLine()){
                data = data + " " + sc.nextLine();
            }

        } catch (FileNotFoundException e) {
            System.out.println("error file not found");
            e.printStackTrace();
        }

        String[] values = data.split("\\D+");

        this.totalClients =Integer.parseInt(values[1]);
        this.totalQueues = Integer.parseInt(values[2]);
        this.simulationInterval= Integer.parseInt(values[3]);
        this.minArrivingTime= Integer.parseInt(values[4]);
        this.maxArrivingTime= Integer.parseInt(values[5]);
        this.minServiceTime= Integer.parseInt(values[6]);
        this.maxServiceTime= Integer.parseInt(values[7]);
    }

    public int getTotalClients() {
        return totalClients;
    }

    public int getTotalQueues() {
        return totalQueues;
    }

    public int getSimulationInterval() {
        return simulationInterval;
    }

    public int getMinArrivingTime() {
        return minArrivingTime;
    }

    public int getMaxArrivingTime() {
        return maxArrivingTime;
    }

    public int getMinServiceTime() {
        return minServiceTime;
    }

    public int getMaxServiceTime() {
        return maxServiceTime;
    }
}
