package com.example.ammarhasan.budgeter;

import java.time.ZonedDateTime;

/**
 * @author Ammar Hasan 150454388 May 2018
 * Class Purpose: This class is a container for ZonedDateTime
 * providing a empty constructor to allow it to work with firebase
 */
public class DateTime {

    private int day;
    private int month;
    private int year;

    /**
     * Default empty constructor, sets zonedDateTime to now
     */
    public DateTime() {

        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        day = zonedDateTime.getDayOfMonth();
        month = zonedDateTime.getMonthValue();
        year = zonedDateTime.getYear();
    }

    /**
     * DateTime constructor
     * @param zonedDateTime Zoned time
     */
    public DateTime(ZonedDateTime zonedDateTime) {
        day = zonedDateTime.getDayOfMonth();
        month = zonedDateTime.getMonthValue();
        year = zonedDateTime.getYear();
    }

    // getter and setters

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
