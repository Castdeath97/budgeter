package com.example.ammarhasan.budgeter;

import java.time.ZonedDateTime;
import java.util.Date;

/**
 * @author Ammar Hasan 150454388 May 2018
 * Class Purpose: This class represents transactions
 * carried by user
 */
public class Transaction {

    //private ZonedDateTime date;
    private Boolean credit;
    private Double amount;
    private Budget budget;

    /**
     * Transaction default constructor
     */
    public Transaction(){

    }

    /**
     * Transaction constructor
     * @param date date of transaction
     * @param credit was the transaction credit (true) (or debit (false))
     * @param amount amount of transaction
     * @param budget budget transaction belongs to
     */
    public Transaction(Boolean credit, Double amount, Budget budget) {
        //this.date = date;
        this.credit = credit;
        this.amount = amount;
        this.budget = budget;
    }

    // getters and setters

    /*
    public ZonedDateTime getDate() {
        return date;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    } */

    public Boolean getCredit() {
        return credit;
    }

    public void setCredit(Boolean credit) {
        this.credit = credit;
    }

    public Double getAmount() {
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
}
