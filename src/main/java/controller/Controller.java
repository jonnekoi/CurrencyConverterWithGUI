package controller;

import dao.CurrencyDao;
import datasource.dbconn;
import javafx.fxml.FXML;
import jakarta.persistence.EntityManager;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class Controller {
    @FXML
    private TextField inputField;
    @FXML
    private TextField resultField;
    @FXML
    private ChoiceBox<String> fromChoice;
    @FXML
    private ChoiceBox<String> toChoice;
    @FXML
    private Text databaseStatusField;
    @FXML
    private TextField addNameField;
    @FXML
    private TextField addAbbField;
    @FXML
    private TextField addRateField;
    @FXML
    private Text addErrorField;

    private CurrencyDao currencyDao;
    public Controller(){
        this.currencyDao = new CurrencyDao(dbconn.getEntityManager());
    }
    public void initialize(){
        this.currencyDao = new CurrencyDao(dbconn.getEntityManager());
        try {
            EntityManager em = dbconn.getEntityManager();
            if (!em.isOpen()) {
                databaseStatusField.setText("Database connection error");
                databaseStatusField.setFill(javafx.scene.paint.Color.RED);
                databaseStatusField.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
                return;
            } else {
                databaseStatusField.setText("Database connected");
                databaseStatusField.setFill(javafx.scene.paint.Color.GREEN);
                databaseStatusField.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
            }
            List<String> currencyNames = currencyDao.getCurrencyNames();
            fromChoice.getItems().addAll(currencyNames);
            toChoice.getItems().addAll(currencyNames);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void convert(){
        String from = fromChoice.getValue();
        String to = toChoice.getValue();
        String inputValue = inputField.getText();
        double result = 0;

        if (inputValue == null || inputValue.isEmpty()) {
            System.out.println("Input field is empty");
            return;
        }

        double amount = 0;
        try{
            amount = Double.parseDouble(inputValue);
        } catch (NumberFormatException e){
            System.out.println("Invalid number format");
            return;
        }

        try{
            double fromRate = currencyDao.getExchangeRate(from);
            double toRate = currencyDao.getExchangeRate(to);
            result = amount * toRate / fromRate;
            resultField.setText(String.valueOf(result));
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void clear(){
        inputField.clear();
        resultField.clear();
    }
    public void addWindow() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/addWindow.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addCurr(){
        String name = addNameField.getText();
        String abbreviation = addAbbField.getText();
        String rateText = addRateField.getText();

        if (name.isEmpty() || abbreviation.isEmpty() || rateText.isEmpty()) {
            addErrorField.setText("Currency add error!");
            addErrorField.setFill(javafx.scene.paint.Color.RED);
            addErrorField.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
            return;
        }

        double rate = Double.parseDouble(rateText);

        if (currencyDao.currencyExists(name, abbreviation)) {
            addErrorField.setText("Currency already exists!");
            addErrorField.setFill(javafx.scene.paint.Color.RED);
            addErrorField.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
            return;
        }

        try {
            currencyDao.addCurrency(name, abbreviation, rate);
            addNameField.clear();
            addAbbField.clear();
            addRateField.clear();
            addErrorField.setText("Currency added successfully!");
            addErrorField.setFill(javafx.scene.paint.Color.GREEN);
            addErrorField.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        } catch (Exception e) {
            e.printStackTrace();
            addErrorField.setText("Currency add error!");
            addErrorField.setFill(javafx.scene.paint.Color.RED);
            addErrorField.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        }
    }

    public void addClear(){
        addNameField.clear();
        addAbbField.clear();
        addRateField.clear();
    }

    public void refresh(){
        List<String> currencyNames = currencyDao.getCurrencyNames();
        fromChoice.getItems().removeAll(fromChoice.getItems());
        toChoice.getItems().removeAll(toChoice.getItems());
        fromChoice.getItems().addAll(currencyNames);
        toChoice.getItems().addAll(currencyNames);
        inputField.clear();
        resultField.clear();
    }
}