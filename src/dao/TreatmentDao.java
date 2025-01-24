package dao;

import core.Database;
import entity.Treatment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TreatmentDao {
    private Connection connection;

    public TreatmentDao() {
        this.connection = Database.getInstance();
    }

    public boolean createTreatment(Treatment treatment) {
        String query = "INSERT INTO treatments (patient_id, doctor_id, diagnosis, applied_treatments) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement pr = this.connection.prepareStatement(query);
            pr.setInt(1, treatment.getPatientId());
            pr.setInt(2, treatment.getDoctorId());
            pr.setString(3, treatment.getDiagnosis());
            pr.setString(4, treatment.getAppliedTreatments());
            int affectedRows = pr.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Treatment> getTreatmentsByPatient(int patientId) {
        String query = "SELECT * FROM treatments WHERE patient_id = ?";
        try {
            PreparedStatement pr = this.connection.prepareStatement(query);
            pr.setInt(1, patientId);
            ResultSet rs = pr.executeQuery();
            ArrayList<Treatment> treatments = new ArrayList<>();
            while (rs.next()) {
                treatments.add(match(rs));
            }
            return treatments;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static Treatment match(ResultSet rs) throws SQLException {
        Treatment treatment = new Treatment();
        treatment.setId(rs.getInt("id"));
        treatment.setPatientId(rs.getInt("patient_id"));
        treatment.setDoctorId(rs.getInt("doctor_id"));
        treatment.setDiagnosis(rs.getString("diagnosis"));
        treatment.setAppliedTreatments(rs.getString("applied_treatments"));
        return treatment;
    }
}