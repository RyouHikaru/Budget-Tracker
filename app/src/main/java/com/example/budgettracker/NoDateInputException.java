package com.example.budgettracker;

class NoDateInputException extends Exception{
    public NoDateInputException() {}
    @Override
    public String getMessage() {
        return "Please enter date";
    }
}
