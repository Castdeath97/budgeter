package com.example.ammarhasan.budgeter;

/**
 * @author Ammar Hasan 150454388 May 2018
 * Class Purpose: This class represents a budget
 * object, which will be used to store budgets
 */
public class Budget {

    private double allocated;
    private double remaining;
    private String name;

    /**
     * Budget default constructor
     */
    public Budget(){

    }

    /**
     * Budget constructor
     * @param name Budget name
     * @param allocated amount allocated to budget
     */
    public Budget(String name, double allocated){
        this.allocated = allocated;
        this.name = name;
        remaining = allocated;
    }

    // equals and hashcode override
    public boolean equals(Object o) {
        if (!(o instanceof Budget)) {
            return false;
        }
        Budget other = (Budget) o;
        return name.equals(other.name);
    }

    public int hashCode() {
        return name.hashCode();
    }

    // getters and setters

    public double getAllocated() {
        return allocated;
    }

    public void setAllocated(double allocated) {
        this.allocated = allocated;
    }

    public double getRemaining() {
        return remaining;
    }

    public void setRemaining(double remaining) {
        this.remaining = remaining;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
