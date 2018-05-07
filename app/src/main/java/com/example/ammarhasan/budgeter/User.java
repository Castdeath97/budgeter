package com.example.ammarhasan.budgeter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ammar Hasan  150454388 April 2018
 * Class Purpose: this class represents a user object
 * which will be used to store user information
 */
public class User {

    private double bankAmount;
    private List<Budget> budgets; // list of budgets set by user
    private double projectedSpend;

    private static final String NOT_ENOUGH_BANK = "Not enough bank for budget";
    private static final String NOT_ENOUGH_ALLO = "Amount spent is too high for allocation!";
    private static final String NAME_USED = "Name was used";


    // #TODO: Check Savings method
    // #TODO: Add transactions (User can interact with these)
    // #TODO: Do checks

    /**
     * User default constructor
     */
    public User() {
        bankAmount = 0.0;
        projectedSpend = 0.0;
        budgets = new ArrayList<Budget>();
    }


    /**
     * Finds a given budget
     * @param budgetName Name of budget to find
     * @return Budget as a Budget object or null if not found
     */
    public Budget findBudget(String budgetName){
        for(Budget b : budgets){
            if(b.getName().equals(budgetName)){
                return b;
            }
        }
        return null; // not found
    }

    public void carryDebitTransaction(Budget budget, double amount){

    }

    public void resetBudget(Budget b){

    }

    /**
     * Adds a budget to user budgets
     * @param budget user new budget
     * @throws IllegalArgumentException if name is used or not enough bank exists
     */
    public void addBudget(Budget budget) throws IllegalArgumentException{

        // error checking

        if(bankAmount - (budget.getRemaining() + projectedSpend) < 0){
            throw new IllegalArgumentException(NOT_ENOUGH_BANK);
        }

        for (Budget b: budgets) {
            if(b.getName().equals(budget.getName())){
                throw new IllegalArgumentException(NAME_USED);
            }
        }

        budgets.add(budget);
        projectedSpend = projectedSpend + budget.getAllocated();
    }

    /**
     * Updates a given budget by budgetName
     * @param budgetName budget name to update
     * @param newBudgetName new budget name
     * @param newAllocated new allocated amount
     * @throws IllegalArgumentException thrown when not enough bank exists or name is used
     */
    public void updateBudget(String budgetName, String newBudgetName,
                             double newAllocated) throws IllegalArgumentException{

        // find budget
        Budget budget = findBudget(budgetName);

        // find difference in allocated amount
        double allocatedDifference = newAllocated - budget.getRemaining();

        // error checking

        if ((bankAmount - (allocatedDifference + projectedSpend)) < 0) {
            throw new IllegalArgumentException(NOT_ENOUGH_BANK);
        }

        if(newAllocated < budget.getRemaining()){
            throw new IllegalArgumentException(NOT_ENOUGH_ALLO);
        }

        for (Budget b : budgets) {
            if (b.getName().equals(newBudgetName) && !b.getName().equals(budget.getName())) {
                throw new IllegalArgumentException(NAME_USED);
            }
        }

        // update the budget
        budget.setRemaining(newAllocated - (budget.getAllocated() - budget.getRemaining()));
        budget.setAllocated(newAllocated);
        budget.setName(newBudgetName);
        projectedSpend = projectedSpend + allocatedDifference;
    }


    /**
     * Remove a budget from user budgets
     * @param budget budget to remove
     */
    public void removeBudget(Budget budget){
        budgets.remove(budget);
        projectedSpend = projectedSpend - budget.getRemaining();
    }

    // getters and setters

    public double getBankAmount() {
        return bankAmount;
    }

    public void setBankAmount(double bankAmount) {
        this.bankAmount = bankAmount;
    }

    public List<Budget> getBudgets() {
        return budgets;
    }

    public void setBudgets(List<Budget> budgets) {
        this.budgets = budgets;
    }

    public double getProjectedSpend() {
        return projectedSpend;
    }

    public void setProjectedSpend(double projectedSpend) {
        this.projectedSpend = projectedSpend;
    }
}
