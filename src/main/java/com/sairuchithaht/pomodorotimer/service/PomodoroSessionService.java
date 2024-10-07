package com.sairuchithaht.pomodorotimer.service;

import com.sairuchithaht.pomodorotimer.model.PomodoroSession;
import com.sairuchithaht.pomodorotimer.repository.PomodoroSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.time.LocalDate; 
import java.time.LocalDateTime;
import java.time.LocalTime;

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

    // Get all sessions of the day
    public List<PomodoroSession> getSessionsOfTheDay() {
        LocalDateTime startOfDay = LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT); // 00:00 today
        LocalDateTime endOfDay = LocalDateTime.of(LocalDate.now(), LocalTime.MAX); // 23:59 today

        return pomodoroSessionRepository.findAllByStartTimeBetween(startOfDay, endOfDay);
    }
}
