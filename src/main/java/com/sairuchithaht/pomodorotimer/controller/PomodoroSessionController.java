package com.sairuchithaht.pomodorotimer.controller;

import com.sairuchithaht.pomodorotimer.model.PomodoroSession;
import com.sairuchithaht.pomodorotimer.model.SessionStats;
import com.sairuchithaht.pomodorotimer.model.UserStats;
import com.sairuchithaht.pomodorotimer.service.PomodoroSessionService;
import com.sairuchithaht.pomodorotimer.service.UserStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@RestController
public class PomodoroSessionController {

    @Autowired
    private PomodoroSessionService pomodoroSessionService;

    @Autowired
    private UserStatsService userStatsService;

    // Create a new session
    @PostMapping
    @RequestMapping("/createSession")
    public ResponseEntity<String> createSession(@RequestBody PomodoroSession session) {
        // Check if duration is negative or less thn 1 minute
        if(!(session.getDuration()>0))
            return ResponseEntity.badRequest().body("Invalid session duration");

        
        LocalDate sessionDate;
        try {
            sessionDate = parseLocaldate(session.getLocalDate());
        } catch (DateTimeParseException e) {
            return ResponseEntity.badRequest().body("Invalid date format: " + e.getMessage());
        }
        pomodoroSessionService.saveSession(session);
        userStatsService.updateUserStats(sessionDate);
        return ResponseEntity.ok("Pomodoro session successfully created");
    }

    // Get all sessions
    @GetMapping
    @RequestMapping("/getAllSessions")
    public ResponseEntity<List<PomodoroSession>> getAllSessions(@RequestParam String userTimeZone) {
        List<PomodoroSession> sessions = pomodoroSessionService.getAllSessions();
        for (PomodoroSession session : sessions) {
            ZonedDateTime utcStartTime = session.getStartTime().atZone(ZoneOffset.UTC);
            ZonedDateTime utcEndTime = session.getEndTime().atZone(ZoneOffset.UTC);
            ZoneId userZoneId = ZoneId.of(userTimeZone);
            
            session.setStartTime(utcStartTime.withZoneSameInstant(userZoneId).toLocalDateTime());
            session.setEndTime(utcEndTime.withZoneSameInstant(userZoneId).toLocalDateTime());
        }
        return ResponseEntity.ok(sessions);
    }

    // Get Activity Summary
    @GetMapping
    @RequestMapping("/getActivitySummary")
    public ResponseEntity<SessionStats> getActivitySummary(@RequestParam String userTimeZone) {
        int totalStudiedTime = calculateTotalStudiedTime(userTimeZone);
        UserStats userStats = userStatsService.getSession();
        if (userStats == null) {
            userStats = new UserStats(); 
            userStats.setDaysAccessed(0); 
            userStats.setStreakCount(0);   
        }
        SessionStats sessionStats = new SessionStats(totalStudiedTime/60, totalStudiedTime%60, userStats.getDaysAccessed(), userStats.getStreakCount());
        return ResponseEntity.ok(sessionStats);
    }

    public int calculateTotalStudiedTime(String userTimeZone){
        List<PomodoroSession> sessions = pomodoroSessionService.getSessionsOfTheDay(userTimeZone);
        int totalStudiedTime = 0;
        for(int i=0; i<sessions.size(); i++){
            totalStudiedTime += sessions.get(i).getDuration();
        }
        return totalStudiedTime;
    }
        
    public LocalDate parseLocaldate(String sessionDate) throws DateTimeParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
        return LocalDate.parse(sessionDate, formatter);
    }
}
