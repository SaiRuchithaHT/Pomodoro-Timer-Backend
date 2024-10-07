package com.sairuchithaht.pomodorotimer.repository;

import com.sairuchithaht.pomodorotimer.model.UserStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserStatsRepository extends JpaRepository<UserStats, Long> {
}
