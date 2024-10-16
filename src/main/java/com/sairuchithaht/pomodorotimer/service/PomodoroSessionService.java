package com.sairuchithaht.pomodorotimer.service;

import com.sairuchithaht.pomodorotimer.model.PomodoroSession;
import com.sairuchithaht.pomodorotimer.repository.PomodoroSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.time.LocalDate; 
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

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
    public List<PomodoroSession> getSessionsOfTheDay(String userTimeZone, String username) {
        ZoneId zoneId = ZoneId.of(userTimeZone);

        ZonedDateTime startOfDayUserTimeZone = ZonedDateTime.of(LocalDate.now(zoneId), LocalTime.MIDNIGHT, zoneId); // 00:00 in user time zone
        ZonedDateTime endOfDayUserTimeZone = ZonedDateTime.of(LocalDate.now(zoneId), LocalTime.MAX, zoneId); // 23:59 in user time zone

        ZonedDateTime startOfDayUTC = startOfDayUserTimeZone.withZoneSameInstant(ZoneOffset.UTC);
        ZonedDateTime endOfDayUTC = endOfDayUserTimeZone.withZoneSameInstant(ZoneOffset.UTC);

        LocalDateTime startOfDay = startOfDayUTC.toLocalDateTime();
        LocalDateTime endOfDay = endOfDayUTC.toLocalDateTime();

        return pomodoroSessionRepository.findAllByUsernameAndStartTimeBetween(username, startOfDay, endOfDay);
    }
}
