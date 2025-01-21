package dao;

import core.Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BranchDao {
    Connection connection;

    public BranchDao() {
        this.connection = Database.getInstance();
    }

    public String[] getBranches() {
        String query = "SELECT * FROM branches";
        String rowCountQuery = "SELECT COUNT(*) FROM branches";
        try {
            Statement rowCountStatement = connection.createStatement();
            ResultSet rowCountResultSet = rowCountStatement.executeQuery(rowCountQuery);
            rowCountResultSet.next();
            int rowCount = rowCountResultSet.getInt(1);

            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            String[] branches = new String[rowCount];
            int i = 0;
            while (rs.next()) {
                branches[i] = rs.getString("name");
                i++;
            }
            return branches;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
