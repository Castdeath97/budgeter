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


    /**
     * Container method for .getDayOfMonth()
     * @return Day of the month as an int
     */
    public int getDay(){
        return zonedDateTime.getDayOfMonth();
    }

    /**
     * Container method for .getMonthValue()
     * @return Month as an int
     */
    public int getMonth(){
        return zonedDateTime.getMonthValue();
    }

    /**
     * Container method for .getYear
     * @return Year as an int
     */
    public int getYear(){
        return zonedDateTime.getYear();
    }
}
