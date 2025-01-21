package dao;

import core.Database;
import entity.Patient;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PatientDao {
    private Connection connection;

    public PatientDao() {
        this.connection = Database.getInstance();
    }

    public Patient findPatientByLogin(String tc, String password) {
        Patient patient = null;
        String query = "SELECT * FROM patients WHERE tc = ? AND password = ?";

        try {
            PreparedStatement pr = this.connection.prepareStatement(query);
            pr.setString(1, tc);
            pr.setString(2, password);
            ResultSet rs = pr.executeQuery();
            if (rs.next()) {
                patient = match(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return patient;
    }

    public ArrayList<Patient> findAll() {
        ArrayList<Patient> patients = new ArrayList<>();
        ResultSet rs = null;
        try {
            rs = this.connection.createStatement().executeQuery("SELECT * FROM patients");
            while (rs.next()) {
                patients.add(match(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return patients;
    }

    public boolean registerPatient(String name, String tc, String address, String mail, String phone, String password) {
        String query = "INSERT INTO patients (name, tc, address, mail, phone, password) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement pr = this.connection.prepareStatement(query);
            pr.setString(1, name);
            pr.setString(2, tc);
            pr.setString(3, address);
            pr.setString(4, mail);
            pr.setString(5, phone);
            pr.setString(6, password);
            int affectedRows = pr.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean checkIfAlreadyRegistered(String tc, String mail) {
        String query = "SELECT tc, mail FROM patients WHERE tc = ? OR mail = ?";
        try {
            PreparedStatement pr = this.connection.prepareStatement(query);
            pr.setString(1, tc);
            pr.setString(2, mail);
            ResultSet rs = pr.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static Patient match(ResultSet rs) throws SQLException {
        Patient patient = new Patient();
        patient.setId(rs.getInt("id"));
        patient.setTc(rs.getString("tc"));
        patient.setName(rs.getString("name"));
        patient.setPhone(rs.getString("phone"));
        patient.setMail(rs.getString("mail"));
        patient.setAddress(rs.getString("address"));
        return patient;
    }
}