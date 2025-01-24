package dao;

import core.Database;
import entity.Doctor;
import entity.Patient;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DoctorDao {
    private Connection connection;

    public DoctorDao() {
        this.connection = Database.getInstance();
    }

    public Doctor findDoctorByLogin(String username, String password) {
        Doctor doctor = null;
        String query = "SELECT * FROM doctors WHERE username=? AND password=?";

        try {
            PreparedStatement pr = this.connection.prepareStatement(query);
            pr.setString(1, username);
            pr.setString(2, password);
            ResultSet rs = pr.executeQuery();
            if (rs.next()) {
                doctor = match(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return doctor;
    };

    public ArrayList<String> getDoctorNamesByBranch(int id) {
        ArrayList<String> doctors = new ArrayList<>();
        String query = "SELECT doctors.name FROM branches INNER JOIN doctors ON branches.id = doctors.branchID WHERE branches.id=?";
        ResultSet rs = null;
        try {
            PreparedStatement pr = this.connection.prepareStatement(query);
            pr.setInt(1, id);
            rs = pr.executeQuery();
            while (rs.next()) {
                String doctorName = rs.getString("name");
                doctors.add(doctorName);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return doctors;
    };

    public ArrayList<Doctor> getDoctorsByBranch(int branch_id) {
        try {
            ArrayList<Doctor> doctors = new ArrayList<>();
            String query = "SELECT * FROM doctors WHERE branchID=?";
            ResultSet rs = null;
            PreparedStatement pr = this.connection.prepareStatement(query);
            pr.setInt(1, branch_id);
            rs = pr.executeQuery();
            while (rs.next()) {
                doctors.add(match(rs));
            }
            System.out.println(doctors);
            return doctors;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static Doctor match(ResultSet rs) throws SQLException {
        Doctor doctor = new Doctor();
        doctor.setId(rs.getInt("id"));
        doctor.setUsername(rs.getString("username"));
        doctor.setName(rs.getString("name"));
        doctor.setPhone(rs.getString("phone"));
        doctor.setTc(rs.getString("tc"));
        return doctor;
    };
}
