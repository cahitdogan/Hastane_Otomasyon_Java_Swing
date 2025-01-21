package dao;

import core.Database;

import java.sql.Connection;

public class MedicineDao {
    private Connection connection;

    public MedicineDao() {
        this.connection = Database.getInstance();
    }


}
