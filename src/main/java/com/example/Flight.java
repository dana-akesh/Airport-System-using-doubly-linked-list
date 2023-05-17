package com.example;


import DataStructure.DoublyLinkedList;

public class Flight implements Comparable<Flight> {
    private int flightNumber;
    private String airLineName;
    private String source;
    private String destination;
    private int capacity;
    private DoublyLinkedList<Passenger> passengers;

    public Flight(int flightNumber, String airLineName, String source, String destination, int capacity) {
        this.flightNumber = flightNumber;
        this.airLineName = airLineName;
        this.source = source;
        this.destination = destination;
        this.capacity = capacity;
        this.passengers = new DoublyLinkedList<>();
    }

    public Flight(int flightNumber) {
        this.flightNumber = flightNumber;
    }

    public int getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(int flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getAirLineName() {
        return airLineName;
    }

    public void setAirLineName(String airLineName) {
        this.airLineName = airLineName;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public DoublyLinkedList<Passenger> getPassengers() {
        return passengers;
    }

    public void setPassengers(DoublyLinkedList<Passenger> passengers) {
        this.passengers = passengers;
    }

    public void addPassenger(Passenger passenger) {
        passengers.insert(passenger);
    }

    public void editFlightInfo(Flight o) {
        if (this.getFlightNumber() == o.getFlightNumber()) {
            this.setAirLineName(o.getAirLineName());
            this.setSource(o.getSource());
            this.setDestination(o.getDestination());
            this.setCapacity(o.getCapacity());
        }
    }

    @Override
    public String toString() {
        return flightNumber + "," +
                airLineName + ',' +
                source + ',' +
                destination + ',' +
                capacity;
    }


    @Override
    public int compareTo(Flight o) {
        if (this.flightNumber - o.flightNumber > 0)
            return 1;
        else if (this.flightNumber - o.flightNumber < 0)
            return -1;
        return 0;
    }
}
