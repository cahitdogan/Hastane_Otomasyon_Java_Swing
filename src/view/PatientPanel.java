package view;

import dao.AppointmentDao;
import dao.MedicineDao;
import dao.TreatmentDao;
import entity.Appointment;
import entity.Medicine;
import entity.Patient;
import entity.Treatment;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class PatientPanel extends JFrame {
    private Patient patient;
    private JLabel lbl_title;
    private JPanel container;
    private JPanel pnl_header;
    private JPanel pnl_body;
    private JButton btn_exit;
    private JPanel pnl_appointments;
    private JPanel pnl_medicines;
    private JPanel pnl_treatments;
    private JLabel lbl_appointments_title;
    private JButton btn_new_appointment;
    private JTable table_appointments;
    private JTable table_medicines;
    private JTable table_treatments;
    private JPanel pnl_appointments_title;
    private JLabel lbl_treatments_title;
    private JLabel lbl_medicines_title;

    public PatientPanel(Patient patient) {
        this.patient = patient;
        this.add(container);
        this.setTitle("Hasta Paneli");
        this.setSize(1200, 850);
        this.setVisible(false);
        int x = (Toolkit.getDefaultToolkit().getScreenSize().width - this.getSize().width) / 2;
        int y = (Toolkit.getDefaultToolkit().getScreenSize().height - this.getSize().height) / 2;
        this.setLocation(x, y);
        this.lbl_title.setText(patient.getName() + " Hoş Geldiniz!");

        btn_new_appointment.addActionListener(e -> {
            PatientAppointmentUI patientAppointmentUI = new PatientAppointmentUI(patient, this);
            patientAppointmentUI.setVisible(true);
        });

        btn_exit.addActionListener(e -> {
            this.setVisible(false);
        });
    }

    public void refreshAppointmentsTable() {
        String[] columns = {"Doktor", "Branş", "Tarih", "Durum"};
        AppointmentDao appointmentDao = new AppointmentDao();
        ArrayList<Appointment> appointments = appointmentDao.getAppointmentsByPatient(patient.getId());
        Object[][] data = new Object[appointments.size()][columns.length];
        for (int i = 0; i < appointments.size(); i++) {
            Appointment appointment = appointments.get(i);
            data[i][0] = appointment.getDoctorName();
            data[i][1] = appointment.getBranch();
            data[i][2] = appointment.getDay() + " " + appointment.getMonth() + " " + appointment.getHour();
            data[i][3] = appointment.getStatus();
        }
        DefaultTableModel tableModel = new DefaultTableModel(data, columns);
        table_appointments.setModel(tableModel);
    }

    private void createUIComponents() {
        String[] columns = {"DOKTOR", "BRANŞ", "TARİH", "DURUM"};
        AppointmentDao appointmentDao = new AppointmentDao();
        ArrayList<Appointment> appointments = appointmentDao.getAppointmentsByPatient(patient.getId());
        Object[][] data = new Object[appointments.size()][columns.length];
        for (int i = 0; i < appointments.size(); i++) {
            Appointment appointment = appointments.get(i);
            data[i][0] = appointment.getDoctorName();
            data[i][1] = appointment.getBranch();
            data[i][2] = appointment.getDay() + " " + appointment.getMonth() + " " + appointment.getHour();
            data[i][3] = appointment.getStatus();
        }
        DefaultTableModel tableModel = new DefaultTableModel(data, columns);
        table_appointments = new JTable(tableModel);
        table_appointments.setAutoCreateColumnsFromModel(true);
        table_appointments.setShowVerticalLines(true);
        table_appointments.setGridColor(new Color(200, 200, 200));
        table_appointments.setIntercellSpacing(new Dimension(1, 1));
        table_appointments.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        String[] columns2 = {"İLAÇ", "DOZAJ"};
        MedicineDao medicineDao = new MedicineDao();
        ArrayList<Medicine> medicines= medicineDao.getMedicinesByPatient(patient.getId());
        Object[][] data2 = new Object[medicines.size()][columns2.length];
        for (int i = 0; i < medicines.size(); i++) {
            Medicine medicine = medicines.get(i);
            data2[i][0] = medicine.getName();
            data2[i][1] = medicine.getDosage();
        }
        DefaultTableModel tableModel2 = new DefaultTableModel(data2, columns2);
        table_medicines = new JTable(tableModel2);
        table_medicines.setAutoCreateColumnsFromModel(true);
        table_medicines.setShowVerticalLines(true);
        table_medicines.setGridColor(new Color(200, 200, 200));
        table_medicines.setIntercellSpacing(new Dimension(1, 1));
        table_medicines.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        String[] columns3 = {"TEŞHİS", "TEDAVİ"};
        TreatmentDao treatmentDao = new TreatmentDao();
        ArrayList<Treatment> treatments = treatmentDao.getTreatmentsByPatient(patient.getId());
        Object[][] data3 = new Object[treatments.size()][columns3.length];
        for (int i = 0; i < treatments.size(); i++) {
            Treatment treatment = treatments.get(i);
            data3[i][0] = treatment.getDiagnosis();
            data3[i][1] = treatment.getAppliedTreatments();
        }
        DefaultTableModel tableModel3 = new DefaultTableModel(data3, columns3);
        table_treatments = new JTable(tableModel3);
        table_treatments.setAutoCreateColumnsFromModel(true);
        table_treatments.setShowVerticalLines(true);
        table_treatments.setGridColor(new Color(200, 200, 200));
        table_treatments.setIntercellSpacing(new Dimension(1, 1));
        table_treatments.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    }
}
