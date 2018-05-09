package com.example.ammarhasan.budgeter;

import java.time.ZonedDateTime;

/**
 * @author Ammar Hasan 150454388 May 2018
 * Class Purpose: This class is a container for ZonedDateTime
 * providing a empty constructor to allow it to work with firebase
 */
public class DateTime {
    private ZonedDateTime zonedDateTime;

    /**
     * Default empty constructor, sets zonedDateTime to now
     */
    public DateTime() {
        this.zonedDateTime = ZonedDateTime.now();
    }

    /**
     * DateTime constructor
     * @param zonedDateTime Zoned time
     */
    public DateTime(ZonedDateTime zonedDateTime) {
        this.zonedDateTime = zonedDateTime;
    }

    public String getZonedDateTimeString() {
        return zonedDateTime.toString();
    }
}
