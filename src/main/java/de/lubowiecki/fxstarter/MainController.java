package de.lubowiecki.fxstarter;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.*;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    private static final String USER_HOME_PATH = System.getProperty("user.home");
    private static final String SER_FILE = USER_HOME_PATH + "/products.ser";

    private List<Product> products;

    @FXML
    private TextField name;

    @FXML
    private TextArea description;

    @FXML
    private Spinner<Integer> count;

    @FXML
    private DatePicker availableSince;

    @FXML
    private TextField price;

    @FXML
    private TableView<Product> productTable;

    @FXML // Macht die Methode für die View nutzbar
    public void save() {
        Product p = new Product();
        p.setName(name.getText());
        p.setDescription(description.getText());
        p.setCount(count.getValue());
        p.setAvailableSince(availableSince.getValue());
        p.setPrice(Double.parseDouble(price.getText()));

        products.add(p);

        saveToFile();
        clearFields();
        showProducts();
    }

    private void clearFields() {
        name.clear();
        description.clear();
        count.getValueFactory().setValue(0);
        availableSince.setValue(null);
        price.clear();
    }

    private void showProducts() {
        productTable.setItems(FXCollections.observableList(products));
    }

    private void saveToFile() {
        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(SER_FILE))) {
            out.writeObject(products);
        }
        catch (IOException e) {
            e.printStackTrace(); // TODO: Exception richtig behandeln
        }
    }

    private void loadFromFile() {

        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(SER_FILE))) {
            products = (List<Product>) in.readObject();
        }
        catch (Exception e) {
            products = new ArrayList<>();
            e.printStackTrace(); // TODO: Exception richtig behandeln
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadFromFile();
        showProducts();
    }
}