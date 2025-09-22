package com.jenkins.practice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jenkins.practice.entity.Volunteer;
import com.jenkins.practice.repository.VolunteerRepository;

@Service
public class VolunteerServiceImpl implements VolunteerService {

    @Autowired
    private VolunteerRepository volunteerRepository;

    @Override
    public Volunteer addEvent(Volunteer event) {
        return volunteerRepository.save(event);
    }

    @Override
    public List<Volunteer> getAllEvents() {
        return volunteerRepository.findAll();
    }

    @Override
    public Volunteer getEventById(int id) {
        Optional<Volunteer> opt = volunteerRepository.findById(id);
        return opt.orElse(null);
    }

    @Override
    public Volunteer updateEvent(Volunteer event) {
        return volunteerRepository.save(event);
    }

    @Override
    public void deleteEventById(int id) {
        volunteerRepository.deleteById(id);
    }

    // ---------------- Extra query methods ----------------

    @Override
    public Volunteer getEventByName(String eventName) {
        return volunteerRepository.findByEventName(eventName);
    }

    @Override
    public List<Volunteer> searchEventsByKeyword(String keyword) {
        return volunteerRepository.findByEventNameContainingIgnoreCase(keyword);
    }

    @Override
    public List<Volunteer> getEventsByDate(String date) {
        return volunteerRepository.findByDate(date);
    }

    @Override
    public List<Volunteer> getEventsByMinHours(int hours) {
        return volunteerRepository.findByHoursGreaterThanEqual(hours);
    }
}
