package dao;

import core.Database;
import entity.LabTest;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LabTestDao {
    private final Connection connection;

    public LabTestDao() {
        this.connection = Database.getInstance();
    }

    public boolean createLabTest(LabTest labTest) {
        String query = "INSERT INTO lab_tests (patient_id, doctor_name, test_name, test_details, request_date) VALUES (?, ?, ?, ?, ?);";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, labTest.getPatientId());
            ps.setString(2, labTest.getDoctorName());
            ps.setString(3, labTest.getTestName());
            ps.setString(4, labTest.getTestDetails());
            ps.setString(5, labTest.getRequestDate());
            return ps.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<LabTest> getLabTestsByPatient(int patientId) {
        ArrayList<LabTest> labTests = new ArrayList<>();
        String query = "SELECT * FROM lab_tests WHERE patient_id = ?;";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, patientId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                LabTest labTest = new LabTest();
                labTest.setId(rs.getInt("id"));
                labTest.setPatientId(rs.getInt("patient_id"));
                labTest.setDoctorName(rs.getString("doctor_name"));
                labTest.setTestName(rs.getString("test_name"));
                labTest.setTestDetails(rs.getString("test_details"));
                labTest.setRequestDate(rs.getString("request_date"));
                labTests.add(labTest);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return labTests;
    }
}