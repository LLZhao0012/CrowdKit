package crowdos.crowdkit.entity;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MetaTask {
    private Long id;

    private String description;

    private Date startTime;

    private Date deadline;

    private double longitude;

    private double latitude;

    private int number;

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getDeadline() {
        return deadline;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public int getNumber() {
        return number;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setNumber(int number) {
        this.number = number;
    }
    
    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DecimalFormat decimalFormat = new DecimalFormat("#.0000");
        return "Task No." + id + "  startTime:" + dateFormat.format(startTime).toString() +
                "  deadline:" + dateFormat.format(deadline).toString() +
                "  location(" + decimalFormat.format(longitude) + "," +
                decimalFormat.format(latitude) + ")" +
                "  need worker:" + number +
                "  description:" +description;
    }
    
}
