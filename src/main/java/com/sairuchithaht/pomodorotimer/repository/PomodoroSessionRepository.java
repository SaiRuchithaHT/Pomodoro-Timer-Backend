package com.sairuchithaht.pomodorotimer.repository;

import com.sairuchithaht.pomodorotimer.model.PomodoroSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.time.LocalDateTime;

@Repository
public interface PomodoroSessionRepository extends JpaRepository<PomodoroSession, Long> {
    List<PomodoroSession> findByUsername(String username);
    List<PomodoroSession> findAllByUsernameAndStartTimeBetween(String username, LocalDateTime start, LocalDateTime end);
}
