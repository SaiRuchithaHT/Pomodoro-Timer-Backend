package com.sairuchithaht.pomodorotimer.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "user_stats")
public class UserStats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate lastStudiedDate;  
    private int streakCount;            
    private int daysAccessed;           

    
    public UserStats() {}

    public UserStats(LocalDate lastStudiedDate, int streakCount, int daysAccessed) {
        this.lastStudiedDate = lastStudiedDate;
        this.streakCount = streakCount;
        this.daysAccessed = daysAccessed;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getLastStudiedDate() {
        return lastStudiedDate;
    }

    public void setLastStudiedDate(LocalDate lastStudiedDate) {
        this.lastStudiedDate = lastStudiedDate;
    }

    public int getStreakCount() {
        return streakCount;
    }

    public void setStreakCount(int streakCount) {
        this.streakCount = streakCount;
    }

    public int getDaysAccessed() {
        return daysAccessed;
    }

    public void setDaysAccessed(int daysAccessed) {
        this.daysAccessed = daysAccessed;
    }
}
