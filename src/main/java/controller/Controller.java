package controller;

import dao.CurrencyDao;
import datasource.dbconn;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.sql.SQLException;
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

    private CurrencyDao currencyDao;
    public Controller(){
        this.currencyDao = new CurrencyDao();
    }
    public void initialize(){
        this.currencyDao = new CurrencyDao(new dbconn());
        try{
            List<String> currencyNames = currencyDao.getCurrencyNames();
            fromChoice.getItems().addAll(currencyNames);
            toChoice.getItems().addAll(currencyNames);
        } catch (SQLException e) {
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
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public void clear(){
        inputField.setText("");
        resultField.setText("");
    }
}