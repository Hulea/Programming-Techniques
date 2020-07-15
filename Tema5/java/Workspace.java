import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
public class Workspace {

    private ArrayList<MonitoredData> extractedValues;
    private long nrOfDistinctDays;
    private HashMap<String, Integer> activityFrequency;
    private HashMap<Integer, HashMap<String, Integer>> monitored;
    private HashMap<String, Duration> totalTimePerActivity;
    private List<String> filteredValues;

    public Workspace() {
        this.extractedValues = getValues();
        this.nrOfDistinctDays = countDistinctDays();
        this.activityFrequency = getActivityFrequency();
        this.monitored = getActivitiesPerDay();
        this.totalTimePerActivity = totalActivityTime();
        this.filteredValues = filter();
        this.printValues();
    }

    public void printValues() {
        for (MonitoredData i : this.extractedValues)
            System.out.println(i.toString());

        System.out.println(this.nrOfDistinctDays);
        System.out.println(activityFrequency.toString().replace(",", "\n"));

        System.out.println(monitored.toString()
                .replace("{", "\n\t[")
                .replace("}", "\n\t[")
                .replace("["," ")
                .replace("]"," "));


        for(String i : totalTimePerActivity.keySet()) {
            System.out.println(totalTimePerActivity.get(i));
            System.out.println(i + " :\n");
            System.out.println("Days: " + totalTimePerActivity.get(i).toDays());
            System.out.println("Hours: " + totalTimePerActivity.get(i).toHours()%60);
            System.out.println("Minutes: " + totalTimePerActivity.get(i).toMinutes()%60);
            System.out.println("Seconds: " + totalTimePerActivity.get(i).getSeconds() % 60);
            System.out.println("\n");
        }

        System.out.println(filteredValues);
    }

    public ArrayList<MonitoredData> getValues() {

        ArrayList<MonitoredData> toReturn = new ArrayList<MonitoredData>();
        String fileName = "Activities.txt";

        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            stream.forEach(aux -> toReturn.add(new MonitoredData(aux.substring(0, 19), aux.substring(21, 40), aux.substring(42))));
        } catch (Exception e) {
            e.printStackTrace();
        }


        int ok = 0;
        for (MonitoredData i : toReturn) {
            if (ok == 0) {
                WriteInFile wr = new WriteInFile(i.toString() + "\n", "Task_1.txt", false);
            } else {
                WriteInFile wr = new WriteInFile(i.toString() + "\n", "Task_1.txt", true);
            }
            ok++;
        }


        return toReturn;
    }

    public long countDistinctDays() {

        long nrDays;

        List<String> listOfDates = new ArrayList<String>();

        extractedValues.forEach(aux -> listOfDates.add((aux.getDate())));

        nrDays = listOfDates.stream().distinct().count();

        WriteInFile wr = new WriteInFile(Long.toString(nrDays), "Task_2.txt", false);

        return nrDays;

    }

    public static void increment(Map<String, Integer> mapAux, String key) {
        mapAux.putIfAbsent(key, 0);
        mapAux.put(key, mapAux.get(key) + 1);
    }

    public HashMap<String, Integer> getActivityFrequency() {

        HashMap<String, Integer> freq = new HashMap<String, Integer>();

        extractedValues.forEach(aux -> increment(freq, aux.getActivityLabel()));

        WriteInFile wr = new WriteInFile(freq.toString().replace(",", "\n"), "Task_3.txt", false);

        return freq;
    }


    public HashMap<String, Integer> rethash(String date) {

        HashMap<String, Integer> toReturn = new HashMap<String, Integer>();

        extractedValues.stream().filter(aux -> aux.getDate().equals(date)).forEach(aux2 -> increment(toReturn, aux2.getActivityLabel()));

        return toReturn;
    }

    public HashMap<Integer, HashMap<String, Integer>> getActivitiesPerDay() {

        HashMap<Integer, HashMap<String, Integer>> toReturn = new HashMap<Integer, HashMap<String, Integer>>();

        List<String> dates = new ArrayList<String>();

        List<String> finalDates = dates;
        extractedValues.forEach(aux -> finalDates.add(aux.getDate()));
        dates = finalDates.stream().distinct().collect(Collectors.toList());

        HashMap<String, Integer> gioni = new HashMap<String, Integer>();

        dates.forEach(aux_date -> toReturn.put(toReturn.size(), rethash(aux_date)));

        WriteInFile wr = new WriteInFile(toReturn.toString()
                .replace("{", "\n\t[")
                .replace("}", "\n\t[")
                .replace("[", " ")
                .replace("]", " "), "Task_4.txt", false);

        return toReturn;

    }

    public static void incrementTime(Map<String, Duration> mapAux, String key, String startTime, String endTime) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        LocalDateTime aux1 = LocalDateTime.parse(startTime,formatter);
        LocalDateTime aux2 = LocalDateTime.parse(endTime,formatter);

        mapAux.putIfAbsent(key, Duration.ZERO);

        mapAux.put(key, mapAux.get(key).plus(Duration.between(aux1, aux2)));
    }

    public HashMap<String, Duration> totalActivityTime() {

        HashMap<String, Duration> toReturn = new HashMap<String, Duration>();

        extractedValues.forEach(aux -> incrementTime(toReturn, aux.getActivityLabel(), aux.getStartTime(), aux.getEndTime()));

        boolean ok = false;

        for(String i : toReturn.keySet()) {
            WriteInFile wr1 = new WriteInFile(i + " :\n","Task_5.txt",ok);
            ok=true;
            WriteInFile wr2 = new WriteInFile("Days: " + toReturn.get(i).toDays() + "\n","Task_5.txt",true);
            WriteInFile wr3 = new WriteInFile("Hours: " + toReturn.get(i).toHours() % 60 + "\n","Task_5.txt",true);
            WriteInFile wr4 = new WriteInFile("Minutes: " + toReturn.get(i).toMinutes() % 60 + "\n","Task_5.txt",true);
            WriteInFile wr5 = new WriteInFile("Seconds: " + toReturn.get(i).getSeconds() % 60 + "\n","Task_5.txt",true);
            WriteInFile wr6 = new WriteInFile("\n","Task_5.txt",true);
        }

        return toReturn;

    }

    public List<String> filter(){

        List<String> toReturn = new ArrayList<String>() ;

        SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");

        ArrayList<String> allActivities = new ArrayList<>();
        List<String> distinctActivities = new ArrayList<>();

        extractedValues.forEach(p -> allActivities.add(p.getActivityLabel()));
        distinctActivities = allActivities.stream().distinct().collect(Collectors.toList());

        HashMap<String, Integer> countFilteredActivities = new HashMap<String, Integer>();
        List<MonitoredData> filteredActivities;

        filteredActivities = extractedValues.stream().filter(
                aux -> 	{
                    try {
                        return (format.parse(aux.getEndTime()).getTime()/1000 - format.parse(aux.getStartTime()).getTime()/1000)/60 % 60 < 5;
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    return false;
                }
        ).collect(Collectors.toList());

        distinctActivities.forEach(aux ->
                countFilteredActivities.put(aux, (int)filteredActivities.stream().filter(aux2 -> aux2.getActivityLabel().equals(aux)).count()));

        for(String s : distinctActivities)
        {
            double proc = 1.0 * countFilteredActivities.get(s)  / activityFrequency.get(s);

            if(proc > 0.9)
                toReturn.add(s);

        }

        WriteInFile wr = new WriteInFile(toReturn.toString(),"Task_6.txt",false);

        return toReturn;
    }

}