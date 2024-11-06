module de.lubowiecki.fxstarter {
    requires javafx.controls;
    requires javafx.fxml;


    opens de.lubowiecki.fxstarter to javafx.fxml;
    exports de.lubowiecki.fxstarter;
}