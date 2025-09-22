package com.jenkins.practice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

import com.jenkins.practice.entity.Volunteer;

@Repository
public interface VolunteerRepository extends JpaRepository<Volunteer, Integer> {

    // Find by exact event name
    Volunteer findByEventName(String eventName);

    // Find all events containing a keyword in the name (case-insensitive)
    List<Volunteer> findByEventNameContainingIgnoreCase(String keyword);

    // Find all events on a specific date
    List<Volunteer> findByDate(String date);

    // Find all events with hours greater than or equal to a value
    List<Volunteer> findByHoursGreaterThanEqual(int hours);
}
