package dao;

import core.Database;
import entity.Appointment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AppointmentDao {
    Connection connection;

    public AppointmentDao() {
        this.connection = Database.getInstance();
    }

    public boolean createAppointment(Appointment appointment) {
        PreparedStatement pr = null;
        try {
            String query = "INSERT INTO appointments (patient_name, doctor_name, patient_tc, month, day, hour, branch, status, doctor_id, patient_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            pr = connection.prepareStatement(query);
            pr.setString(1, appointment.getPatientName());
            pr.setString(2, appointment.getDoctorName());
            pr.setString(3, appointment.getPatientTc());
            pr.setString(4, appointment.getMonth());
            pr.setInt(5, appointment.getDay());
            pr.setString(6, appointment.getHour());
            pr.setString(7, appointment.getBranch());
            pr.setString(8, appointment.getStatus());
            pr.setInt(9, appointment.getDoctorId());
            pr.setInt(10, appointment.getPatientId());
            int affectedRows = pr.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Appointment> getAppointmentsByPatient(int patientId) {
        String query = "SELECT * FROM appointments WHERE patient_id = ?";
        try {
            PreparedStatement pr = this.connection.prepareStatement(query);
            pr.setInt(1, patientId);
            ResultSet rs = pr.executeQuery();
            ArrayList<Appointment> appointments = new ArrayList<>();
            while (rs.next()) {
                appointments.add(match(rs));
            }
            return appointments;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    };

    public ArrayList<Appointment> getAppointmentsByDoctor(int DoctorId) {
        ArrayList<Appointment> appointments = new ArrayList<>();
        String query = "SELECT * FROM appointments WHERE doctor_id = ?";
        try {
            PreparedStatement pr = this.connection.prepareStatement(query);
            pr.setInt(1, DoctorId);
            ResultSet rs = pr.executeQuery();
            while (rs.next()) {
                appointments.add(match(rs));
            }
            return appointments;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static Appointment match(ResultSet rs) throws SQLException {
        Appointment appointment = new Appointment();
        appointment.setDoctorId(rs.getInt("id"));
        appointment.setStatus(rs.getString("status"));
        appointment.setPatientId(rs.getInt("patient_id"));
        appointment.setDoctorName(rs.getString("doctor_name"));
        appointment.setPatientName(rs.getString("patient_name"));
        appointment.setPatientTc(rs.getString("patient_tc"));
        appointment.setMonth(rs.getString("month"));
        appointment.setDay(rs.getInt("day"));
        appointment.setHour(rs.getString("hour"));
        appointment.setBranch(rs.getString("branch"));

        return appointment;
    }
}