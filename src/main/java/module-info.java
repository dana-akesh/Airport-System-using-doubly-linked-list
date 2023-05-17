module com.example.project1 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.project1 to javafx.fxml;
    exports com.example;
    exports DataStructure;
}