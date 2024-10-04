package com.sairuchithaht.pomodorotimer.repository;

import com.sairuchithaht.pomodorotimer.model.PomodoroSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PomodoroSessionRepository extends JpaRepository<PomodoroSession, Long> {
}
