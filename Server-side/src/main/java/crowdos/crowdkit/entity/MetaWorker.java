package crowdos.crowdkit.entity;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MetaWorker {
    private Long id;

    private double longitude;

    private double latitude;

    private Date arrivalTime;
    private List<MetaTask> assignedTasks;

    public List<MetaTask> getAssignedTasks() {
        return assignedTasks;
    }

    public void assignTask(MetaTask task) {
        assignedTasks.add(task);
    }

    public Long getId() {
        return id;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public Date getArrivalTime() {
        return arrivalTime;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setArrivalTime(Date arrivalTime) {
        this.arrivalTime = arrivalTime;
    }
    
    @Override
    public String toString(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DecimalFormat decimalFormat = new DecimalFormat("#.0000");
        return "Worker No." + id +
                "  location(" + decimalFormat.format(longitude) + "," +
                decimalFormat.format(latitude) + ")" +
                "  arrivalTime:" + dateFormat.format(arrivalTime).toString();
    }
}
