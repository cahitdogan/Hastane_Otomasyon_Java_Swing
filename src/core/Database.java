package core;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//singleton design pattern
public class Database {
    private static Database instance = null;
    private Connection connection = null;
    private final String URL = "jdbc:sqlite:database.db";

    private Database() {
        try {
            this.connection = DriverManager.getConnection(URL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Connection getConnection() {
        return connection;
    }

    public static Connection getInstance() {
        try {
            if (instance == null || instance.getConnection().isClosed()) {
                instance = new Database();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return instance.getConnection();
    }
}
