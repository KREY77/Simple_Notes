module org.example.databases {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens org.example.databases to javafx.fxml;
    exports org.example.databases;
}