package com.sairuchithaht.pomodorotimer.service;

import com.sairuchithaht.pomodorotimer.model.PomodoroSession;
import com.sairuchithaht.pomodorotimer.repository.PomodoroSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PomodoroSessionService {

    @Autowired
    private PomodoroSessionRepository pomodoroSessionRepository;

    // Save a session
    public PomodoroSession saveSession(PomodoroSession session) {
        return pomodoroSessionRepository.save(session);
    }

    // Get all sessions
    public List<PomodoroSession> getAllSessions() {
        return pomodoroSessionRepository.findAll();
    }
}
