module com.example.tp_javafx {
    requires javafx.controls;
    requires javafx.fxml;


    opens david.color to javafx.fxml;
    exports david.color;
    exports david.color.controller;
    opens david.color.controller to javafx.fxml;
}