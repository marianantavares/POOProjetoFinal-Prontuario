package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import util.LoadParameter;

public class MySQLDatabaseConnection implements DatabaseConnection {
    private static MySQLDatabaseConnection instance;
    private Connection conn;
    private final String DBNAME = LoadParameter.getValor("DBNAME");
    private final String DBADDRESS = LoadParameter.getValor("DBADDRESS");
    private final String DBPASSWORD = LoadParameter.getValor("DBPASSWORD");
    private final String DBPORT = LoadParameter.getValor("DBPORT");
    private final String DBUSER = LoadParameter.getValor("DBUSER");

    public MySQLDatabaseConnection() { }

    public static synchronized MySQLDatabaseConnection getInstance() {
        if (instance == null) {
            instance = new MySQLDatabaseConnection();
        }
        return instance;
    }
    
    @Override
    public Connection getConnection() {
        try {
            if (conn == null || conn.isClosed()) {
                conn = DriverManager.getConnection("jdbc:mysql://"+DBADDRESS+":"+DBPORT+"/"+DBNAME, DBUSER, DBPASSWORD);
            }
            return conn;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    @Override
    public void disconnect() {
        try {
            if(conn != null && !conn.isClosed()) {
                conn.close();
                conn = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
