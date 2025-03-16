package model;

import java.util.Date;

public class Complaint {
    private int id;
    private String complainantName;
    private String description;
    private Date incidentDate;
    private String status;
    private int policeStationId;

    public Complaint() {}

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getComplainantName() { return complainantName; }
    public void setComplainantName(String name) { this.complainantName = name; }
    public String getDescription() { return description; }
    public void setDescription(String desc) { this.description = desc; }
    public Date getIncidentDate() { return incidentDate; }
    public void setIncidentDate(Date date) { this.incidentDate = date; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public int getPoliceStationId() { return policeStationId; }
    public void setPoliceStationId(int id) { this.policeStationId = id; }
}