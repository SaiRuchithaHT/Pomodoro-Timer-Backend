package com.sairuchithaht.pomodorotimer.service;

import com.sairuchithaht.pomodorotimer.model.UserStats;
import com.sairuchithaht.pomodorotimer.repository.UserStatsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.time.LocalDate;

@Service
public class UserStatsService {

    @Autowired
    private UserStatsRepository userStatsRepository;

    // Save a session
    public UserStats saveSession(UserStats session) {
        return userStatsRepository.save(session);
    }

    // Method to update user stats
    public void updateUserStats(LocalDate sessionDate) {

        UserStats userStats = getSession(); 
        if(userStats == null) {
            userStats = new UserStats();
            userStats.setLastStudiedDate(sessionDate);
            userStats.setDaysAccessed(1);
            userStats.setStreakCount(1);
            saveSession(userStats);
            return;
        }
        if (userStats.getLastStudiedDate() != null && userStats.getLastStudiedDate().equals(sessionDate)) {
            return;
        } else {
            LocalDate lastStudied = userStats.getLastStudiedDate();
            if (sessionDate.isAfter(lastStudied)) {
                userStats.setDaysAccessed(userStats.getDaysAccessed() + 1);
                if (sessionDate.equals(lastStudied.plusDays(1))) {
                    userStats.setStreakCount(userStats.getStreakCount() + 1);
                } else {
                    userStats.setStreakCount(1); 
                }
                userStats.setLastStudiedDate(sessionDate);
            }
        }
        saveSession(userStats);
    }

    // Get all sessions
    public UserStats getSession() {
        List<UserStats> userStatsList = userStatsRepository.findAll();
        if (userStatsList.isEmpty()) {
            return null; 
        }
        return userStatsList.get(0);
    }

}
