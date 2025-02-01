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

    public ArrayList<Medicine> getAllMedicines() {
        ArrayList<Medicine> medicines = new ArrayList<>();
        String query = "SELECT * FROM medicines";
        try {
            PreparedStatement pr = this.connection.prepareStatement(query);
            ResultSet rs = pr.executeQuery();
            while(rs.next()) {
                medicines.add(match(rs));
            }
            return medicines;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean createMedicine(Medicine medicine) {
        String query = "INSERT INTO medicines (name, stock, dosage) VALUES (?, ?, ?)";
        try {
            PreparedStatement pr = this.connection.prepareStatement(query);
            pr.setString(1, medicine.getName());
            pr.setInt(2, medicine.getStock());
            pr.setInt(3, medicine.getDosage());
            int affectedRows = pr.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public boolean assignMedicineToPatient(int patientId, int medicineId) {
        String query = "INSERT INTO PatientMedicine (patientId, medicineId) VALUES (?, ?)";
        try {
            PreparedStatement pr = this .connection.prepareStatement(query);
            pr.setInt(1, patientId);
            pr.setInt(2, medicineId);
            int affectedRows = pr.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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

    public int getLastInsertedMedicineId() {
        String query = "SELECT * FROM medicines ORDER BY id DESC LIMIT 1";
        try {
            PreparedStatement pr = this.connection.prepareStatement(query);
            ResultSet rs = pr.executeQuery();
            int id = 0;
            while (rs.next()) {
                id = rs.getInt("id");
            }
            return id;
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
