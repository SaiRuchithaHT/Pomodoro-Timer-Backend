package com.sairuchithaht.pomodorotimer.repository;

import com.sairuchithaht.pomodorotimer.model.PomodoroSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.time.LocalDateTime;

@Repository
public interface PomodoroSessionRepository extends JpaRepository<PomodoroSession, Long> {
    // Custom query to get all sessions that started on the current day
    List<PomodoroSession> findAllByStartTimeBetween(LocalDateTime startOfDay, LocalDateTime endOfDay);
}
