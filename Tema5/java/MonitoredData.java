import java.util.Date;

public class MonitoredData {

    private String startTime,endTime,activityLabel;

    public String toString(){
        return startTime + " , " + endTime + " , " + activityLabel;
    }

    public MonitoredData(String startTime, String endTime, String activityLabel) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.activityLabel = activityLabel;
    }

    public String getDate(){
        return startTime.substring(0,10);
    }

    public String getActivityLabel(){
        return this.activityLabel;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }
}
