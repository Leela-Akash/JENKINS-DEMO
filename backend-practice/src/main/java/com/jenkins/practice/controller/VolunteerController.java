package com.jenkins.practice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.jenkins.practice.entity.Volunteer;
import com.jenkins.practice.service.VolunteerService;

@RestController
@RequestMapping("/springbootvolunteerapi")
@CrossOrigin(origins = "http://localhost:5173") // allow your frontend
public class VolunteerController {

    @Autowired
    private VolunteerService volunteerService;

    @GetMapping("/")
    public String home() {
        return "Volunteer Hours Tracker API is running!";
    }

    // Create
    @PostMapping("/add")
    public ResponseEntity<Volunteer> addEvent(@RequestBody Volunteer event) {
        Volunteer savedEvent = volunteerService.addEvent(event);
        return new ResponseEntity<>(savedEvent, HttpStatus.CREATED);
    }

    // Read All
    @GetMapping("/all")
    public ResponseEntity<List<Volunteer>> getAllEvents() {
        List<Volunteer> events = volunteerService.getAllEvents();
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    // Read by ID
    @GetMapping("/get/{id}")
    public ResponseEntity<?> getEventById(@PathVariable int id) {
        Volunteer event = volunteerService.getEventById(id);
        if (event != null) {
            return new ResponseEntity<>(event, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Event with ID " + id + " not found.", HttpStatus.NOT_FOUND);
        }
    }

    // Update
    @PutMapping("/update")
    public ResponseEntity<?> updateEvent(@RequestBody Volunteer event) {
        Volunteer existing = volunteerService.getEventById(event.getId());
        if (existing != null) {
            Volunteer updatedEvent = volunteerService.updateEvent(event);
            return new ResponseEntity<>(updatedEvent, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Cannot update. Event with ID " + event.getId() + " not found.", HttpStatus.NOT_FOUND);
        }
    }

    // Delete
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteEvent(@PathVariable int id) {
        Volunteer existing = volunteerService.getEventById(id);
        if (existing != null) {
            volunteerService.deleteEventById(id);
            return new ResponseEntity<>("Event with ID " + id + " deleted successfully.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Cannot delete. Event with ID " + id + " not found.", HttpStatus.NOT_FOUND);
        }
    }
}
