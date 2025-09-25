package com.jenkins.practice.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "volunteer_events")
public class Volunteer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto-increment ID
    @Column(name = "event_id")
    private Integer id;

    @Column(name = "event_name", nullable = false, length = 100)
    private String eventName;

    @Column(name = "event_description", length = 255)
    private String description;

    @Column(name = "event_date", nullable = false, length = 20)
    private String date;

    @Column(name = "event_hours", nullable = false)
    private int hours;

    @Column(name = "volunteer_name", length = 100)   // ✅ new field
    private String volunteerName;

    @Column(name = "contact", length = 100)          // ✅ new field
    private String contact;

    // Getters & Setters
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
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

    public String getVolunteerName() {
        return volunteerName;
    }
    public void setVolunteerName(String volunteerName) {
        this.volunteerName = volunteerName;
    }

    public String getContact() {
        return contact;
    }
    public void setContact(String contact) {
        this.contact = contact;
    }

    @Override
    public String toString() {
        return "Volunteer [id=" + id + ", eventName=" + eventName + ", description=" + description
                + ", date=" + date + ", hours=" + hours
                + ", volunteerName=" + volunteerName + ", contact=" + contact + "]";
    }
}
