package com.example;

import java.util.Date;

public class Passenger implements Comparable<Passenger> {
    private int flightNumber;
    private int ticketNumber;
    private String fullName;
    private String passportNumber;
    private String nationality;
    private Date birthDate;

    public Passenger(int flightNumber, int ticketNumber, String fullName, String passportNumber, String nationality, Date birthDate) {
        this.flightNumber = flightNumber;
        this.ticketNumber = ticketNumber;
        this.fullName = fullName;
        this.passportNumber = passportNumber;
        this.nationality = nationality;
        this.birthDate = birthDate;
    }

    public int getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(int flightNumber) {
        this.flightNumber = flightNumber;
    }

    public int getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(int ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public String toString() {
        return flightNumber + "," +
                ticketNumber + "," +
                fullName + "," +
                passportNumber + "," +
                nationality + "," +
                birthDate.getDate() + '/' +
                (birthDate.getMonth() + 1) + '/' +
                (birthDate.getYear() + 1900);
    }

    public String print() {
        return "Passenger{" +
                "flightNumber=" + flightNumber +
                ", ticketNumber=" + ticketNumber +
                ", fullName='" + fullName + '\'' +
                ", passportNumber='" + passportNumber + '\'' +
                ", nationality='" + nationality + '\'' +
                ", birthDate=" + birthDate.getDate() + '/' +
                (birthDate.getMonth() + 1) + '/' +
                (birthDate.getYear() + 1900) +
                '}';
    }

    @Override
    public int compareTo(Passenger o) {
        if (this.getFullName().compareTo(o.getFullName()) > 0)
            return 1;
        else if (this.getFullName().compareTo(o.getFullName()) < 0)
            return -1;
        return 0;
    }
}
