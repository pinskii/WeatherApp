package fi.tuni.comp_se_110.weatherapp;

import fi.tuni.comp_se_110.weatherapp.App;
import java.io.IOException;
import javafx.fxml.FXML;

public class PrimaryController {

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
}
