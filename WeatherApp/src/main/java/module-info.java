module fi.tuni.comp_se_110.weatherapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires java.xml;
    requires java.net.http;
    requires com.google.gson;

    opens fi.tuni.comp_se_110.weatherapp to javafx.fxml;
    exports fi.tuni.comp_se_110.weatherapp;
}