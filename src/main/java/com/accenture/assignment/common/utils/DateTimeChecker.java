package com.accenture.assignment.common.utils;

import java.time.LocalTime;

public class DateTimeChecker {

    public boolean isTimeinRange(LocalTime startTime, LocalTime endTime, LocalTime checkTime) {
        return checkTime.isAfter(startTime) && checkTime.isBefore(endTime);
    }

}
