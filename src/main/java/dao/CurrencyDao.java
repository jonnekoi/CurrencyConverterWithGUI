package dao;

import datasource.dbconn;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CurrencyDao {
    private dbconn db;

    public CurrencyDao(dbconn db){
        this.db = db;
    }

    public CurrencyDao() {

    }

    public double getExchangeRate(String Currency) throws SQLException{
        String query = "SELECT conversion_rate FROM CURRENCIES WHERE abbreviation = ?";
        Connection connection = db.getConn();
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, Currency);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()){
            return resultSet.getDouble("conversion_rate");
        }else {
            throw new SQLException("Currency not found!");
        }
    }

    public List<String> getCurrencyNames() throws SQLException {
        List<String> currencyNames = new ArrayList<>();
        String query = "SELECT abbreviation FROM CURRENCIES";
        Connection connection = db.getConn();
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            currencyNames.add(resultSet.getString("abbreviation"));
        }

        return currencyNames;
    }
}