package com.jenkins.practice.service;

import java.util.List;
import com.jenkins.practice.entity.Volunteer;

public interface VolunteerService {
    Volunteer addEvent(Volunteer event);
    List<Volunteer> getAllEvents();
    Volunteer getEventById(int id);
    Volunteer updateEvent(Volunteer event);
    void deleteEventById(int id);

    // Extra queries
    Volunteer getEventByName(String eventName);
    List<Volunteer> searchEventsByKeyword(String keyword);
    List<Volunteer> getEventsByDate(String date);
    List<Volunteer> getEventsByMinHours(int hours);
}
