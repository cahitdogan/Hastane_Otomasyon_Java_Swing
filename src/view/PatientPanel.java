package view;

import entity.Patient;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        this.add(container);
        this.setTitle("Hasta Paneli");
        this.setSize(1200, 850);
        this.setVisible(false);
        int x = (Toolkit.getDefaultToolkit().getScreenSize().width - this.getSize().width) / 2;
        int y = (Toolkit.getDefaultToolkit().getScreenSize().height - this.getSize().height) / 2;
        this.setLocation(x, y);
        this.lbl_title.setText(patient.getName() + " Hoş Geldiniz!");
        this.patient = patient;

        btn_new_appointment.addActionListener(e -> {
            PatientAppointmentUI patientAppointmentUI = new PatientAppointmentUI(patient);
            patientAppointmentUI.setVisible(true);
        });
    }

    /*private void createUIComponents() {
        String[] columns = {"No", "İlaç Adı", "Dozaj"};
        DefaultTableModel tableModel = new DefaultTableModel(data, columns);
        table_medicines = new JTable(tableModel);
        table_medicines.setFont(new Font("Tahoma", Font.PLAIN, 14));
    }*/
}
