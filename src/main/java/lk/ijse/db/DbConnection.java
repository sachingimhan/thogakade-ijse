package lk.ijse.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @auther sachin
 * @date 2023-11-08
 */
public class DbConnection {

    private static DbConnection dbConnection;
    private static String USERNAME = "sachin";
    private static String PASSWORD = "rockey123";
    private static String URL = "jdbc:mysql://localhost:3306/thogakade";
    private Connection connection = null;

    private DbConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    public static DbConnection getInstance() throws SQLException, ClassNotFoundException {
        if (dbConnection == null){
            dbConnection = new DbConnection();
        }
        return dbConnection;
    }

    public Connection getConnection(){
        return connection;
    }

}
