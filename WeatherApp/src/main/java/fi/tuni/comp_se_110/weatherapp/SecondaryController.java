package fi.tuni.comp_se_110.weatherapp;

import fi.tuni.comp_se_110.weatherapp.App;
import java.io.IOException;
import javafx.fxml.FXML;

public class SecondaryController {

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
}