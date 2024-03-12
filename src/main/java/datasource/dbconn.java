package datasource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class dbconn {
    private static Connection conn = null;

    public static Connection getConn(){
        if (conn == null){
            try{
                conn = DriverManager.getConnection(
                        "jdbc:mariadb://localhost:3306/currency?user=root&password=root");
            }catch (SQLException e){
                System.out.println("Connection failed");
                e.printStackTrace();
                return null;
            }
        }
        return conn;
    }
}