module fi.tuni.comp_se_110.weatherapp {
    requires javafx.controls;
    requires javafx.fxml;

    opens fi.tuni.comp_se_110.weatherapp to javafx.fxml;
    exports fi.tuni.comp_se_110.weatherapp;
}
