package dao;

import core.Database;
import entity.Appointment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AppointmentDao {
    Connection connection;

    public AppointmentDao() {
        this.connection = Database.getInstance();
    }

    public boolean createAppointment(Appointment appointment, int patientID, int appointmentID, int doctorID) {
        PreparedStatement pr = null;
        String query = "";
        try {
            query = "INSERT INTO appointments (patient-name, doctor-name, patient-tc, date, time) VALUES (?,?,?,?,?)";
            pr = connection.prepareStatement(query);
            pr.setString(1, appointment.getPatientName());
            pr.setString(2, appointment.getDoctorName());
            pr.setString(3, appointment.getBranch());
            pr.setString(4, appointment.getPatientTc());
            pr.setString(5, appointment.getMonth());
            pr.setInt(6, appointment.getDay());
            pr.setString(7, appointment.getHour());
            int affectedRows = pr.executeUpdate();


            query = "INSERT INTO PatientAppointment VALUES (?, ?)";
            pr = connection.prepareStatement(query);
            pr.setInt(1, patientID);
            pr.setInt(2, appointmentID);
            pr.executeUpdate();

            query = "INSERT INTO DoctorAppointment VALUES (?, ?)";
            pr = connection.prepareStatement(query);
            pr.setInt(1, doctorID);
            pr.setInt(2, appointmentID);
            pr.executeUpdate();

            return affectedRows > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
