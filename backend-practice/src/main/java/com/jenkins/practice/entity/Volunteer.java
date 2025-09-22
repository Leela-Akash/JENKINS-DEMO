package com.jenkins.practice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "volunteer_events")
public class Volunteer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto-increment ID
    @Column(name = "event_id")
    private int id;

    @Column(name = "event_name", nullable = false, length = 100)
    private String eventName;

    @Column(name = "event_description", length = 255)
    private String description;

    @Column(name = "event_date", nullable = false, length = 20)
    private String date; // you can switch to LocalDate if needed

    @Column(name = "event_hours", nullable = false)
    private int hours;

    // Getters & Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    @Override
    public String toString() {
        return "Volunteer [id=" + id + ", eventName=" + eventName + ", description=" + description 
                + ", date=" + date + ", hours=" + hours + "]";
    }
}
