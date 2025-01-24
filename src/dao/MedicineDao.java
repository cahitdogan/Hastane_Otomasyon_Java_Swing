package dao;

import core.Database;
import entity.Medicine;
import entity.Patient;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MedicineDao {
    private Connection connection;

    public MedicineDao() {
        this.connection = Database.getInstance();
    }

    public ArrayList<Medicine> getMedicinesByPatient(int patientId) {
        String query = "SELECT medicines.id, medicines.name, medicines.dosage, medicines.stock FROM medicines INNER JOIN PatientMedicine ON PatientMedicine.MedicineID = medicines.id WHERE PatientMedicine.patientID = ?";
        try {
            PreparedStatement pr = this.connection.prepareStatement(query);
            pr.setInt(1, patientId);
            ResultSet rs = pr.executeQuery();
            ArrayList<Medicine> medicines = new ArrayList<>();
            while (rs.next()) {
                medicines.add(match(rs));
            }
            return medicines;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private static Medicine match(ResultSet rs) throws SQLException {
        Medicine medicine = new Medicine();
        medicine.setId(rs.getInt("id"));
        medicine.setDosage(rs.getInt("dosage"));
        medicine.setName(rs.getString("name"));
        medicine.setStock(rs.getInt("stock"));
        return medicine;
    };
}
