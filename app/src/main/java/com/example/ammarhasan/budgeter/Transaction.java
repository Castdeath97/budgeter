package com.example.ammarhasan.budgeter;

import android.text.format.DateFormat;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.ServerValue;

import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

/**
 * @author Ammar Hasan 150454388 May 2018
 * Class Purpose: This class represents transactions
 * carried by user
 */
public class Transaction {

    private boolean credit;
    private double amount;
    private Budget budget;
    private DateTime dateTime;

    /**
     * Transaction default constructor
     */
    public Transaction() {

    }

    /**
     * Transaction constructor
     *
     * @param credit was the transaction credit (true) (or debit (false))
     * @param amount amount of transaction
     * @param budget budget transaction belongs to
     */
    public Transaction(Boolean credit, double amount, Budget budget) {
        this.credit = credit;
        this.amount = amount;
        this.budget = budget;
        this.dateTime = new DateTime();
    }

    // getters and setters

    public boolean getCredit() {
        return credit;
    }

    public void setCredit(Boolean credit) {
        this.credit = credit;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Budget getBudget() {
        return budget;
    }

    public void setBudget(Budget budget) {
        this.budget = budget;
    }

    public DateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(DateTime dateTime) {
        this.dateTime = dateTime;
    }
}
