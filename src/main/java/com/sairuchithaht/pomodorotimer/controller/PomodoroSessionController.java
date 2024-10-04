package com.sairuchithaht.pomodorotimer.controller;

import com.sairuchithaht.pomodorotimer.model.PomodoroSession;
import com.sairuchithaht.pomodorotimer.service.PomodoroSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sessions")
public class PomodoroSessionController {

    @Autowired
    private PomodoroSessionService sessionService;

    // Create a new session
    @PostMapping
    public ResponseEntity<String> createSession(@RequestBody PomodoroSession session) {
        PomodoroSession savedSession = sessionService.saveSession(session);
        return ResponseEntity.ok("Session created ");
    }

    // Get all sessions
    @GetMapping
    public ResponseEntity<List<PomodoroSession>> getAllSessions() {
        List<PomodoroSession> sessions = sessionService.getAllSessions();
        return ResponseEntity.ok(sessions);
    }
}
