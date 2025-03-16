package model;

import java.util.Date;

public class Criminal {
    private int id;
    private String name;
    private String crimeType;
    private Date crimeDate;
    private int punishmentYears;

    public Criminal() {}

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getCrimeType() { return crimeType; }
    public void setCrimeType(String crimeType) { this.crimeType = crimeType; }
    public Date getCrimeDate() { return crimeDate; }
    public void setCrimeDate(Date crimeDate) { this.crimeDate = crimeDate; }
    public int getPunishmentYears() { return punishmentYears; }
    public void setPunishmentYears(int punishmentYears) {
        this.punishmentYears = punishmentYears;
    }
}