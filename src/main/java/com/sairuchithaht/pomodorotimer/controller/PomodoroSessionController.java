package com.sairuchithaht.pomodorotimer.controller;

import com.sairuchithaht.pomodorotimer.model.PomodoroSession;
import com.sairuchithaht.pomodorotimer.model.SessionStats;
import com.sairuchithaht.pomodorotimer.model.UserStats;
import com.sairuchithaht.pomodorotimer.service.PomodoroSessionService;
import com.sairuchithaht.pomodorotimer.service.UserStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.time.LocalDate;

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
        PomodoroSession savedSession = pomodoroSessionService.saveSession(session);
        // Update user stats when a session is created
        userStatsService.updateUserStats(LocalDate.now());
        return ResponseEntity.ok("Session created ");
    }

    // Get all sessions
    @GetMapping
    @RequestMapping("/getAllSessions")
    public ResponseEntity<List<PomodoroSession>> getAllSessions() {
        List<PomodoroSession> sessions = pomodoroSessionService.getAllSessions();
        return ResponseEntity.ok(sessions);
    }

    // Get Activity Summary
    @GetMapping
    @RequestMapping("/getActivitySummary")
    public ResponseEntity<SessionStats> getActivitySummary() {
        int totalStudiedTime = calculateTotalStudiedTime();
        UserStats userStats = userStatsService.getSession();
        if (userStats == null) {
            userStats = new UserStats(); 
            userStats.setDaysAccessed(0); 
            userStats.setStreakCount(0);   
        }
        SessionStats sessionStats = new SessionStats(totalStudiedTime/60, totalStudiedTime%60, userStats.getDaysAccessed(), userStats.getStreakCount());
        return ResponseEntity.ok(sessionStats);
    }

    public int calculateTotalStudiedTime(){
        List<PomodoroSession> sessions = pomodoroSessionService.getSessionsOfTheDay();
        int totalStudiedTime = 0;
        for(int i=0; i<sessions.size(); i++){
            totalStudiedTime += sessions.get(i).getDuration();
        }
        return totalStudiedTime;
    }
        
}
