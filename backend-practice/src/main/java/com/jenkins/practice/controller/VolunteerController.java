package com.jenkins.practice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.jenkins.practice.entity.Volunteer;
import com.jenkins.practice.service.VolunteerService;

@RestController
@RequestMapping("/") // removed redundant /springbootvolunteerapi
@CrossOrigin(origins = "*")
public class VolunteerController {

    @Autowired
    private VolunteerService volunteerService;

    @GetMapping("/")
    public String home() {
        return "Volunteer Hours Tracker API is running!";
    }

    @PostMapping("/add")
    public ResponseEntity<Volunteer> addEvent(@RequestBody Volunteer event) {
        Volunteer savedEvent = volunteerService.addEvent(event);
        return new ResponseEntity<>(savedEvent, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Volunteer>> getAllEvents() {
        return new ResponseEntity<>(volunteerService.getAllEvents(), HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getEventById(@PathVariable int id) {
        Volunteer event = volunteerService.getEventById(id);
        if (event != null) {
            return new ResponseEntity<>(event, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Event with ID " + id + " not found.", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateEvent(@RequestBody Volunteer event) {
        Volunteer existing = volunteerService.getEventById(event.getId());
        if (existing != null) {
            return new ResponseEntity<>(volunteerService.updateEvent(event), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Cannot update. Event with ID " + event.getId() + " not found.", HttpStatus.NOT_FOUND);
        }
    }

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
