package com.example;

import DataStructure.DoublyLinkedList;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Driver extends Application {
    private static DoublyLinkedList<Flight> flightsList = new DoublyLinkedList<>();
    private static ObservableList<Flight> observableFlight = FXCollections.observableArrayList();
    private ObservableList<Passenger> observablePassengers = FXCollections.observableArrayList();

    public static void main(String[] args) {
        try {
            Application.launch();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void writeFiles(File f1, File f2) throws FileNotFoundException {

        if (f1 != null && f2 != null) {
            PrintWriter print1 = new PrintWriter(f1);
            for (int i = 0; i < flightsList.length(); i++) {
                print1.println(flightsList.get(i).toString());
            }
            print1.close();
            PrintWriter print2 = new PrintWriter(f2);
            for (int i = 0; i < flightsList.length(); i++) {
                for (int j = 0; j < flightsList.get(i).getPassengers().length(); j++) {
                    print2.println(flightsList.get(i).getPassengers().get(j).toString());
                }
            }
            print2.close();
        }
    }

    private void readFiles(File flightsFile, File passengerFile) throws FileNotFoundException, ParseException {
        Scanner flightScanner = new Scanner(flightsFile);
        while (flightScanner.hasNext()) {
            String[] fTokens = flightScanner.nextLine().split(",");
            if (fTokens.length == 5) {
                Flight tempFlight = new Flight(Integer.parseInt(fTokens[0].trim()), fTokens[1], fTokens[2], fTokens[3], Integer.parseInt(fTokens[4].trim()));
                flightsList.insert(tempFlight);
                observableFlight.add(tempFlight);
            }
            Scanner passengerScanner = new Scanner(passengerFile);
            DoublyLinkedList<Passenger> passengersList = new DoublyLinkedList<>();
            while (passengerScanner.hasNext()) {
                String[] pTokens = passengerScanner.nextLine().split(",");
                if (fTokens[0].trim().equals(pTokens[0].trim()) && pTokens.length == 6) {
                    Date date = new SimpleDateFormat("dd/MM/yyyy").parse(pTokens[5].trim());
                    Passenger tempPassenger = new Passenger(Integer.parseInt(pTokens[0].trim()), Integer.parseInt(pTokens[1].trim()), pTokens[2], pTokens[3], pTokens[4], date);
                    flightsList.search(new Flight(Integer.parseInt(fTokens[0].trim()))).getData().addPassenger(tempPassenger);
                    passengersList.insert(tempPassenger);
                }
            }
            flightsList.search(new Flight(Integer.parseInt(fTokens[0].trim()))).getData().setPassengers(passengersList);
        }
    }

    @Override
    public void start(Stage primaryStage) throws FileNotFoundException, ParseException {
        FileChooser fileChooserFlight = new FileChooser();
        fileChooserFlight.setTitle("Open Flights File");
        fileChooserFlight.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        File f1 = fileChooserFlight.showOpenDialog(primaryStage);

        FileChooser passengerChooserFlight = new FileChooser();
        passengerChooserFlight.setTitle("Open Passengers File");
        passengerChooserFlight.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        File f2 = passengerChooserFlight.showOpenDialog(primaryStage);
        if (f1 != null && f2 != null) {
            readFiles(f1, f2);
        }

        // tabs
        Tab mainTab = new Tab("Main Page");
        mainTab.setClosable(false);

        Tab flightTab = new Tab("Flights Page");
        flightTab.setClosable(false);

        Tab passengerTab = new Tab("Passengers Page");
        passengerTab.setClosable(false);

        // Main page
        VBox mainPage = new VBox(20);
        mainTab.setContent(mainPage);
        mainPage.setAlignment(Pos.CENTER);
        ImageView mainPageImage = new ImageView("https://img.icons8.com/bubbles/452/airplane-take-off.png");
        Label mainLabel = new Label("Flight Reservation System");
        mainLabel.setFont(Font.font("Georgia", FontWeight.BOLD, 45));
        mainPage.getChildren().addAll(mainPageImage, mainLabel);

        // Flight page
        VBox flightPage = new VBox(30);
        flightPage.setAlignment(Pos.CENTER);
        flightPage.setPadding(new Insets(0, 50, 0, 50));

        // Label
        Label fLabel = new Label("Flights Page");
        fLabel.setFont(Font.font("Georgia", FontWeight.BOLD, 45));

        // table for flights.
        // Flight number column
        TableColumn<Flight, String> flightNumberColumn = new TableColumn<>("Flight Number");
        flightNumberColumn.setMinWidth(100);
        flightNumberColumn.setCellValueFactory(new PropertyValueFactory<>("flightNumber"));

        // from column
        TableColumn<Flight, String> airLineColumn = new TableColumn<>("Airline Name");
        airLineColumn.setMinWidth(200);
        airLineColumn.setCellValueFactory(new PropertyValueFactory<>("airLineName"));

        // Source column
        TableColumn<Flight, String> sourceColumn = new TableColumn<>("Source");
        sourceColumn.setMinWidth(200);
        sourceColumn.setCellValueFactory(new PropertyValueFactory<>("source"));

        // Destination column
        TableColumn<Flight, String> destinationColumn = new TableColumn<>("Destination");
        destinationColumn.setMinWidth(200);
        destinationColumn.setCellValueFactory(new PropertyValueFactory<>("destination"));

        // calendar column
        TableColumn<Flight, Integer> capacityColumn = new TableColumn<>("Capacity");
        capacityColumn.setMinWidth(100);
        capacityColumn.setCellValueFactory(new PropertyValueFactory<>("capacity"));

        TableView<Flight> flightsTable = new TableView<>();
        flightsTable.setMaxSize(800, 600);
        flightsTable.setItems(observableFlight);
        flightsTable.getColumns().addAll(flightNumberColumn, airLineColumn, sourceColumn, destinationColumn, capacityColumn);

        // to sort the table accusingly
        flightNumberColumn.setSortType(TableColumn.SortType.ASCENDING);
        flightsTable.getSortOrder().add(flightNumberColumn);
        flightsTable.sort();
        flightTab.setContent(flightPage);

        // Adding a new flight
        TextField flightNumberTFAdd = new TextField();
        flightNumberTFAdd.setPromptText("FLight Number"); //to set the hint text
        TextField airlineTFAdd = new TextField();
        airlineTFAdd.setPromptText("Airline Name"); //to set the hint text
        TextField sourceTFAdd = new TextField();
        sourceTFAdd.setPromptText("Source"); //to set the hint text
        TextField destinationTFAdd = new TextField();
        destinationTFAdd.setPromptText("Destination"); //to set the hint text
        TextField capacityTFAdd = new TextField();
        capacityTFAdd.setPromptText("Capacity"); //to set the hint text
        Button addFlightB = new Button("Add Flight");

        // combobox for editing the flight information
        ComboBox<String> flightNumberCB = new ComboBox<>();
        ComboBox<String> flightNumberPCB1 = new ComboBox<>();
        ComboBox<String> flightNumberPCB2 = new ComboBox<>();
        ComboBox<String> flightNumberPCB3 = new ComboBox<>();
        addFlightB.setOnAction(e -> {
            try {
                if (flightNumberTFAdd.getText() == null || flightNumberTFAdd.getText().trim().isEmpty()) {
                    failAlert("Flight number is empty");
                    return;
                }
                int fNum = Integer.parseInt(flightNumberTFAdd.getText().trim());
                for (int i = 0; i < flightsList.length(); i++) {
                    if (flightsList.get(i).getFlightNumber() == fNum) {
                        failAlert("Flight number is duplicated");
                        return;
                    }
                }
                if (airlineTFAdd.getText() == null || airlineTFAdd.getText().trim().isEmpty()) {
                    failAlert("Airline name is empty");
                    return;
                }
                if (sourceTFAdd.getText() == null || sourceTFAdd.getText().trim().isEmpty()) {
                    failAlert("Source is empty");
                    return;
                }
                if (destinationTFAdd.getText() == null || destinationTFAdd.getText().trim().isEmpty()) {
                    failAlert("Destination is empty");
                    return;
                }
                if (capacityTFAdd.getText() == null || capacityTFAdd.getText().trim().isEmpty()) {
                    failAlert("Capacity is empty");
                    return;
                }
                int cap = Integer.parseInt(capacityTFAdd.getText().trim());
                Flight newFlight = new Flight(fNum, airlineTFAdd.getText().trim(), sourceTFAdd.getText().trim(), destinationTFAdd.getText().trim(), cap);
                flightsList.insert(newFlight);
                observableFlight.add(newFlight);
                flightNumberCB.getItems().add("" + newFlight.getFlightNumber());
                flightNumberPCB1.getItems().add("" + newFlight.getFlightNumber());
                flightNumberPCB2.getItems().add("" + newFlight.getFlightNumber());
                flightNumberPCB3.getItems().add("" + newFlight.getFlightNumber());
                refreshFlightsTable();
                successAlert("Flight added successfully");
            } catch (NumberFormatException e1) {
                failAlert("Flight Number and Capacity should be integers.");
            } catch (Exception e2) {
                failAlert("Error Occurred");
                e2.printStackTrace();
            }
        });

        HBox addFlightHB = new HBox(20);
        addFlightHB.setAlignment(Pos.CENTER);
        addFlightHB.getChildren().addAll(flightNumberTFAdd, airlineTFAdd, sourceTFAdd, destinationTFAdd, capacityTFAdd, addFlightB);

        // Editing a flight information
        TextField airlineTFedit = new TextField();
        airlineTFedit.setPromptText("Airline Name"); //to set the hint text
        TextField sourceTFedit = new TextField();
        sourceTFedit.setPromptText("Source"); //to set the hint text
        TextField destinationTFedit = new TextField();
        destinationTFedit.setPromptText("Destination"); //to set the hint text
        TextField capacityTFedit = new TextField();
        capacityTFedit.setPromptText("Capacity"); //to set the hint text
        Button editFlightB = new Button("Edit Flight");

        editFlightB.setOnAction(e -> {
            try {
                if (airlineTFedit.getText() == null || airlineTFedit.getText().trim().isEmpty()) {
                    failAlert("Airline is empty");
                    return;
                }
                if (sourceTFedit.getText() == null || sourceTFedit.getText().trim().isEmpty()) {
                    failAlert("Source is empty");
                    return;
                }
                if (destinationTFedit.getText() == null || destinationTFedit.getText().trim().isEmpty()) {
                    failAlert("Destination is empty");
                    return;
                }
                if (capacityTFedit.getText() == null || capacityTFedit.getText().trim().isEmpty()) {
                    failAlert("Capacity is empty");
                    return;
                }
                int cap = Integer.parseInt(capacityTFedit.getText().trim());
                int num = Integer.parseInt(flightNumberCB.getSelectionModel().getSelectedItem());
                Flight temp = new Flight(num, airlineTFedit.getText().trim(), sourceTFedit.getText().trim(), destinationTFedit.getText().trim(), cap);
                flightsList.search(new Flight(num)).getData().editFlightInfo(temp);
                for (int i = 0; i < observableFlight.size(); i++) {
                    if (observableFlight.get(i).getFlightNumber() == num) {
                        observableFlight.remove(i);
                        break;
                    }
                }
                observableFlight.add(temp);
                refreshFlightsTable();
                successAlert("Flight information edited successfully");
                airlineTFedit.clear();
                sourceTFedit.clear();
                destinationTFedit.clear();
                capacityTFedit.clear();
            } catch (NumberFormatException capE) {
                failAlert("Invalid capacity");
            }
        });

        HBox editFlightHB = new HBox(20);
        editFlightHB.setAlignment(Pos.CENTER);
        editFlightHB.getChildren().addAll(flightNumberCB, airlineTFedit, sourceTFedit, destinationTFedit, capacityTFedit, editFlightB);
        flightPage.getChildren().addAll(fLabel, flightsTable, addFlightHB, editFlightHB);

        VBox passengerPage = new VBox(25);
        passengerPage.setAlignment(Pos.CENTER);
        passengerPage.setPadding(new Insets(50, 50, 50, 50));

        // Label
        Label pLabel = new Label("Passengers Page");
        pLabel.setFont(Font.font("Georgia", FontWeight.BOLD, 45));

        // table for flights.
        // Flight number column
        TableColumn<Passenger, Integer> ticketNumberColumn = new TableColumn<>("Ticket Number");
        ticketNumberColumn.setMinWidth(150);
        ticketNumberColumn.setCellValueFactory(new PropertyValueFactory<>("ticketNumber"));

        // fullName column
        TableColumn<Passenger, String> fullNameColumn = new TableColumn<>("Full Name");
        fullNameColumn.setMinWidth(150);
        fullNameColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));

        // Source column
        TableColumn<Passenger, String> passportNumberColumn = new TableColumn<>("passport Number");
        passportNumberColumn.setMinWidth(200);
        passportNumberColumn.setCellValueFactory(new PropertyValueFactory<>("passportNumber"));

        // Destination column
        TableColumn<Passenger, String> nationalityColumn = new TableColumn<>("nationality");
        nationalityColumn.setMinWidth(100);
        nationalityColumn.setCellValueFactory(new PropertyValueFactory<>("nationality"));

        // calendar column
        TableColumn<Passenger, Date> birthDateColumn = new TableColumn<>("birthDate");
        birthDateColumn.setMinWidth(200);
        birthDateColumn.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
        TableView<Passenger> passengersTable = new TableView<>();
        passengersTable.setMaxSize(800, 600);
        passengersTable.getColumns().addAll(ticketNumberColumn, fullNameColumn, passportNumberColumn, nationalityColumn, birthDateColumn);
        flightNumberPCB1.setOnAction(e -> {
            observablePassengers.clear();
            int num = Integer.parseInt(flightNumberPCB1.getSelectionModel().getSelectedItem().trim());
            for (int i = 0; i < flightsList.search(new Flight(num)).getData().getPassengers().length(); i++) {

                observablePassengers.add(flightsList.search(new Flight(num)).getData().getPassengers().get(i));
            }
            passengersTable.setItems(observablePassengers);
            passengersTable.getSortOrder().add(fullNameColumn);
            passengersTable.sort();
        });

        HBox reserveTicketHB = new HBox(20);
        reserveTicketHB.setAlignment(Pos.CENTER);
        TextField fullName = new TextField();
        fullName.setPromptText("Full name"); //to set the hint text
        TextField passportNumber = new TextField();
        passportNumber.setPromptText("Passport Number"); //to set the hint text
        TextField nationality = new TextField();
        nationality.setPromptText("Nationality"); //to set the hint text
        DatePicker birthDate = new DatePicker();
        birthDate.setPromptText("Birthdate"); //to set the hint text
        Button reserveTicketB = new Button("Reserve a ticket");
        reserveTicketB.setOnAction(e -> {
            Flight tempFlight = new Flight(Integer.parseInt(flightNumberPCB2.getSelectionModel().getSelectedItem().trim()));
            if (flightsList.search(tempFlight).getData().getCapacity() > (flightsList.search(tempFlight).getData().getPassengers().length()))
            {
                try {
                    if (fullName.getText() == null || fullName.getText().trim().isEmpty()) {
                        failAlert("fullName is empty");
                        return;
                    }
                    if (passportNumber.getText() == null || passportNumber.getText().trim().isEmpty()) {
                        failAlert("passportNumber is empty");
                        return;
                    }
                    if (nationality.getText() == null || nationality.getText().trim().isEmpty()) {
                        failAlert("nationality is empty");
                        return;
                    }
                    if (birthDate.getValue() == null || birthDate.getEditor().getText().trim().isEmpty()) {
                        failAlert("date of birth is empty");
                        return;
                    }
                    Date dIn = new SimpleDateFormat("MM/dd/yyyy").parse(birthDate.getEditor().getText().trim());

                    int ticket;
                    if (flightsList.search(tempFlight).getData().getPassengers().length() == 0)
                        ticket = 0;
                    else if (flightsList.search(tempFlight).getData().getPassengers().length() == 1) {
                        ticket = flightsList.search(tempFlight).getData().getPassengers().get(0).getTicketNumber() + 1;
                    } else {
                        ticket = flightsList.search(tempFlight).getData().getPassengers().get(0).getTicketNumber();
                        for (int i = 1; i < flightsList.search(tempFlight).getData().getPassengers().length(); i++) {
                            if (ticket < flightsList.search(tempFlight).getData().getPassengers().get(i).getTicketNumber()) {
                                ticket = flightsList.search(tempFlight).getData().getPassengers().get(i).getTicketNumber();
                            }
                        }
                    }

                    Passenger tempPassenger = new Passenger(Integer.parseInt(flightNumberPCB2.getSelectionModel().getSelectedItem().trim()), ticket + 1,
                            fullName.getText(), passportNumber.getText(), nationality.getText(), dIn);
                    flightsList.search(new Flight(Integer.parseInt(flightNumberPCB2.getSelectionModel().getSelectedItem().trim()))).getData().addPassenger(tempPassenger);
                    observablePassengers.clear();
                    int num = Integer.parseInt(flightNumberPCB1.getSelectionModel().getSelectedItem().trim());
                    for (int i = 0; i < flightsList.search(new Flight(num)).getData().getPassengers().length(); i++) {
                        observablePassengers.add(flightsList.search(new Flight(num)).getData().getPassengers().get(i));
                    }
                    successAlert("Passenger added successfully");
                } catch (Exception e2) {
                    e2.printStackTrace();
                    failAlert("Error in inputs");
                }
            }else failAlert("Error: flight is full");
        });
        reserveTicketHB.getChildren().addAll(flightNumberPCB2, fullName, passportNumber, nationality, birthDate, reserveTicketB);

        HBox checkCancelHB = new HBox(20);
        checkCancelHB.setAlignment(Pos.CENTER);
        TextField fullNameReservation = new TextField();
        fullNameReservation.setPromptText("Full name"); //to set the hint
        Button cancelReservationB = new Button("Cancel reservation");
        cancelReservationB.setOnAction(e -> {
            Flight tempFlight = new Flight(Integer.parseInt(flightNumberPCB3.getSelectionModel().getSelectedItem().trim()));
            for (int i = 0; i < flightsList.search(tempFlight).getData().getPassengers().length(); i++) {
                if (flightsList.search(tempFlight).getData().getPassengers().get(i).getFullName().equalsIgnoreCase(fullNameReservation.getText().trim())) {
                    flightsList.search(tempFlight).getData().getPassengers().delete(flightsList.search(tempFlight).getData().getPassengers().get(i));
                    successAlert("Passenger has been removed successfully");
                    observablePassengers.clear();
                    int num = Integer.parseInt(flightNumberPCB1.getSelectionModel().getSelectedItem().trim());
                    for (int j = 0; j < flightsList.search(new Flight(num)).getData().getPassengers().length(); j++) {
                        observablePassengers.add(flightsList.search(new Flight(num)).getData().getPassengers().get(j));
                    }
                    return;
                }
            }
            failAlert("Passenger doesn't exist on this flight");
        });
        Button checkReservationB = new Button("Check reservation");
        checkReservationB.setOnAction(e -> {
            Flight tempFlight = new Flight(Integer.parseInt(flightNumberPCB3.getSelectionModel().getSelectedItem().trim()));
            int flightLength = flightsList.search(tempFlight).getData().getPassengers().length();
            for (int i = 0; i < flightLength; i++) {
                if (flightsList.search(tempFlight).getData().getPassengers().get(i).getFullName().equalsIgnoreCase(fullNameReservation.getText().trim())) {
                    successAlert("Passenger has a reservation on this flight");
                    return;
                }
            }
            failAlert("Passenger doesn't has a reservation on this flight");
        });
        checkCancelHB.getChildren().addAll(flightNumberPCB3, fullNameReservation, cancelReservationB, checkReservationB);

        HBox searchPassengerHB = new HBox(20);
        searchPassengerHB.setAlignment(Pos.CENTER);
        Button searchForPassengerB = new Button("Search for passenger by name");
        searchForPassengerB.setOnAction(e -> {
            TextArea TA = new TextArea();
            for (int i = 0; i < flightsList.length(); i++) {
                for (int j = 0; j < flightsList.get(i).getPassengers().length(); j++) {
                    if (flightsList.get(i).getPassengers().get(j) != null && flightsList.get(i).getPassengers().get(j).getFullName().equalsIgnoreCase(fullNameReservation.getText().trim())) {
                        TA.appendText(flightsList.get(i).getPassengers().get(j).print() + "\n");
                    }
                }
            }
            if (TA.getText().equals("")) {
                failAlert("Passenger doesn't have any reservation or the field in empty.");
                return;
            }
            Stage searchStage = new Stage();
            Scene searchScene = new Scene(TA);
            searchStage.setTitle("Search result");
            searchStage.setScene(searchScene);
            searchStage.show();
        });
        searchPassengerHB.getChildren().addAll(searchForPassengerB);

        // setting the items of the combo boxes
        for (int i = 0; i < flightsList.length(); i++) {
            flightNumberCB.getItems().addAll("" + flightsList.get(i).getFlightNumber());
            flightNumberPCB1.getItems().addAll("" + flightsList.get(i).getFlightNumber());
            flightNumberPCB2.getItems().addAll("" + flightsList.get(i).getFlightNumber());
            flightNumberPCB3.getItems().addAll("" + flightsList.get(i).getFlightNumber());
            flightNumberCB.getSelectionModel().select(0);
            flightNumberPCB2.getSelectionModel().select(0);
            flightNumberPCB3.getSelectionModel().select(0);
        }
        passengerPage.getChildren().addAll(pLabel, flightNumberPCB1, passengersTable, reserveTicketHB, checkCancelHB, searchPassengerHB);
        passengerTab.setContent(passengerPage);

        TabPane tabPane = new TabPane();
        tabPane.getTabs().addAll(mainTab, flightTab, passengerTab);
        tabPane.setBackground(new Background(new BackgroundFill(Color.web("#FFFFE0"), CornerRadii.EMPTY, Insets.EMPTY)));

        Scene scene = new Scene(tabPane);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Flight reservation system");
        primaryStage.setResizable(false);
        primaryStage.setMaximized(true);
        primaryStage.show();
        primaryStage.setOnCloseRequest(
                event -> {
                    try {
                        writeFiles(f1, f2);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    private void refreshFlightsTable() {
        TableColumn<Flight, String> flightNumberColumn = new TableColumn<>("Flight Number");
        flightNumberColumn.setMinWidth(100);
        flightNumberColumn.setCellValueFactory(new PropertyValueFactory<>("flightNumber"));

        // from column
        TableColumn<Flight, String> airLineColumn = new TableColumn<>("Airline Name");
        airLineColumn.setMinWidth(200);
        airLineColumn.setCellValueFactory(new PropertyValueFactory<>("airLineName"));

        // Source column
        TableColumn<Flight, String> sourceColumn = new TableColumn<>("Source");
        sourceColumn.setMinWidth(200);
        sourceColumn.setCellValueFactory(new PropertyValueFactory<>("source"));

        // Destination column
        TableColumn<Flight, String> destinationColumn = new TableColumn<>("Destination");
        destinationColumn.setMinWidth(200);
        destinationColumn.setCellValueFactory(new PropertyValueFactory<>("destination"));

        // calendar column
        TableColumn<Flight, Integer> capacityColumn = new TableColumn<>("Capacity");
        capacityColumn.setMinWidth(100);
        capacityColumn.setCellValueFactory(new PropertyValueFactory<>("capacity"));

        TableView<Flight> flightsTable = new TableView<>();
        flightsTable.setMaxSize(800, 600);
        flightsTable.setItems(observableFlight);
        flightsTable.getColumns().addAll(flightNumberColumn, airLineColumn, sourceColumn, destinationColumn, capacityColumn);

        // to sort the table accusingly
        flightNumberColumn.setSortType(TableColumn.SortType.ASCENDING);
        flightsTable.getSortOrder().add(flightNumberColumn);
        flightsTable.sort();
    }

    private void successAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message);
        alert.show();
    }

    private void failAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message);
        alert.show();
    }

}
