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
    public List<PomodoroSession> getSessionsOfTheDay(String userTimeZone) {
    // Get the user's time zone
    ZoneId zoneId = ZoneId.of(userTimeZone);

    // Get the start and end of the day in the user's time zone
    ZonedDateTime startOfDayUserTimeZone = ZonedDateTime.of(LocalDate.now(zoneId), LocalTime.MIDNIGHT, zoneId); // 00:00 in user time zone
    ZonedDateTime endOfDayUserTimeZone = ZonedDateTime.of(LocalDate.now(zoneId), LocalTime.MAX, zoneId); // 23:59 in user time zone

    // Convert to UTC
    ZonedDateTime startOfDayUTC = startOfDayUserTimeZone.withZoneSameInstant(ZoneOffset.UTC);
    ZonedDateTime endOfDayUTC = endOfDayUserTimeZone.withZoneSameInstant(ZoneOffset.UTC);

    // Get LocalDateTime for querying the database
    LocalDateTime startOfDay = startOfDayUTC.toLocalDateTime();
    LocalDateTime endOfDay = endOfDayUTC.toLocalDateTime();

    // Query the database for sessions between start and end of the day in UTC
    return pomodoroSessionRepository.findAllByStartTimeBetween(startOfDay, endOfDay);
}

}
