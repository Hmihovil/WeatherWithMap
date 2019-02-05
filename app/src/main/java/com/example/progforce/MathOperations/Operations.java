package com.example.progforce.MathOperations;

public class Operations {

    public static String getMoth(Integer dOfMonth) {
        String dayOfMonth = new String();
        if (dOfMonth == 0) {
            dayOfMonth = "January ";
        }
        if (dOfMonth == 1) {
            dayOfMonth = "February ";
        }
        if (dOfMonth == 2) {
            dayOfMonth = "March ";
        }
        if (dOfMonth == 3) {
            dayOfMonth = "April ";
        }
        if (dOfMonth == 4) {
            dayOfMonth = "May ";
        }
        if (dOfMonth == 5) {
            dayOfMonth = "June ";
        }
        if (dOfMonth == 6) {
            dayOfMonth = "July ";
        }
        if (dOfMonth == 7) {
            dayOfMonth = "August ";
        }
        if (dOfMonth == 8) {
            dayOfMonth = "September ";
        }
        if (dOfMonth == 9) {
            dayOfMonth = "October ";
        }
        if (dOfMonth == 10) {
            dayOfMonth = "November ";
        }
        if (dOfMonth == 11) {
            dayOfMonth = "December ";
        }
        return dayOfMonth;
    }

    public static String getDayOfWeek(Integer dOfWeek) {
        String dayOfWeek = new String();
        if (dOfWeek == 1) {
            dayOfWeek = "Sunday, ";
        }
        if (dOfWeek == 2) {
            dayOfWeek = "Monday, ";
        }
        if (dOfWeek == 3) {
            dayOfWeek = "Tuesday, ";
        }
        if (dOfWeek == 4) {
            dayOfWeek = "Wednesday, ";
        }
        if (dOfWeek == 5) {
            dayOfWeek = "Thursday, ";
        }
        if (dOfWeek == 6) {
            dayOfWeek = "Friday, ";
        }
        if (dOfWeek == 7) {
            dayOfWeek = "Saturday, ";
        }
        return dayOfWeek;
    }


}
