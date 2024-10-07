package com.sairuchithaht.pomodorotimer.model;

import java.util.Set;

public class SessionStats {
    private int hoursFocused;
    private int minutesFocused;
    private int daysAccessed;
    private int dayStreak;

    public SessionStats() {}

    public SessionStats(int hoursFocused, int minutesFocused, int daysAccessed, int dayStreak) {
        this.hoursFocused = hoursFocused;
        this.minutesFocused = minutesFocused;
        this.daysAccessed = daysAccessed;
        this.dayStreak = dayStreak;
    }

    public int getHoursFocused() {
        return hoursFocused;
    }

    public void setHoursFocused(int hoursFocused) {
        this.hoursFocused = hoursFocused;
    }

    public int getMinutesFocused() {
        return minutesFocused;
    }

    public void setMinutesFocused(int minutesFocused) {
        this.minutesFocused = minutesFocused;
    }

    public int getDaysAccessed() {
        return daysAccessed;
    }

    public void setDaysAccessed(int daysAccessed) {
        this.daysAccessed = daysAccessed;
    }

    public int getDayStreak() {
        return dayStreak;
    }

    public void setDayStreak(int dayStreak) {
        this.dayStreak = dayStreak;
    }
}
