package view;

import dao.AppointmentDao;
import dao.BranchDao;
import dao.DoctorDao;
import entity.Appointment;
import entity.Doctor;
import entity.Patient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class PatientAppointmentUI extends JFrame {
    private JComboBox<String> cbox_branch;
    private JComboBox<String> cbox_doctor;
    private JComboBox<String> cbox_month;
    private JComboBox<String> cbox_time;
    private JButton btn_save;
    private JPanel container;
    private JPanel pnl_title;
    private JPanel pnl_form;
    private JLabel lbl_title;
    private JLabel lbl_branch;
    private JLabel lbl_doctor;
    private JLabel lbl_date;
    private JComboBox cbox_day;
    private JComboBox cbox_hour;
    private JPanel pnl_date;
    private JLabel lbl_hour;
    private JLabel lbl_day;
    private JLabel lbl_month;

    private PatientPanel patientPanel;

    public PatientAppointmentUI(Patient patient, PatientPanel patientPanel) {
        this.patientPanel = patientPanel;
        createUIComponents();

        container = new JPanel();
        container.setLayout(new GridLayout(9, 1, 3, 3));
        this.add(container);
        container.add(lbl_branch);
        container.add(cbox_branch);
        container.add(lbl_doctor);
        container.add(cbox_doctor);
        pnl_date = new JPanel();
        container.add(pnl_date);

        pnl_date.setLayout(new GridLayout(2, 3, 1, 3));
        pnl_date.add(lbl_month);
        pnl_date.add(lbl_day);
        pnl_date.add(lbl_hour);
        pnl_date.add(cbox_month);
        pnl_date.add(cbox_day);
        pnl_date.add(cbox_hour);
        container.add(btn_save);

        this.setTitle("Randevu Oluştur");
        this.setSize(500, 500);
        this.setVisible(false);

        int x = (Toolkit.getDefaultToolkit().getScreenSize().width - this.getSize().width) / 2;
        int y = (Toolkit.getDefaultToolkit().getScreenSize().height - this.getSize().height) / 2;
        this.setLocation(x, y);

        cbox_branch.addActionListener(e -> {
            cbox_doctor.removeAllItems();
            int selectedBranchID = cbox_branch.getSelectedIndex() + 1;
            DoctorDao doctorDao = new DoctorDao();
            ArrayList<String> doctors = doctorDao.getDoctorNamesByBranch(selectedBranchID);
            for (int i = 0; i < doctors.size(); i++) {
                cbox_doctor.addItem(doctors.get(i));
            }
        });

        btn_save.addActionListener(e -> {
            Appointment appointment = new Appointment();
            appointment.setPatientName(patient.getName());
            appointment.setDoctorName(this.cbox_doctor.getSelectedItem().toString());
            appointment.setPatientTc(patient.getTc());
            appointment.setMonth(this.cbox_month.getSelectedItem().toString());
            appointment.setDay(Integer.parseInt(this.cbox_day.getSelectedItem().toString()));
            appointment.setHour(this.cbox_hour.getSelectedItem().toString());
            appointment.setBranch(cbox_branch.getSelectedItem().toString());
            appointment.setStatus("active");
            appointment.setPatientId(patient.getId());

            DoctorDao doctorDao = new DoctorDao();
            int branch = cbox_branch.getSelectedIndex() + 1;
            ArrayList<Doctor> doctors = doctorDao.getDoctorsByBranch(branch);

            for (int i = 0; i < doctors.size(); i++) {
                if (doctors.get(i).getName().equals(cbox_doctor.getSelectedItem())) {
                    appointment.setDoctorId(doctors.get(i).getId());
                }
            }

            AppointmentDao appointmentDao = new AppointmentDao();
            if (appointmentDao.createAppointment(appointment)) {
                JOptionPane.showMessageDialog(null, "Randevu başarıyla kaydedildi!", "Başarılı", JOptionPane.INFORMATION_MESSAGE);
                refreshAppointmentsTable();
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Randevu kaydedilirken hata oluştu!", "HATA", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }

    private void refreshAppointmentsTable() {
        if (patientPanel != null) {
            patientPanel.refreshAppointmentsTable();
        }
    }

    private void createUIComponents() {
        BranchDao branchDao = new BranchDao();
        String[] options = branchDao.getBranches();
        this.cbox_branch = new JComboBox<>(options);
        this.cbox_branch.setSelectedIndex(0);
        this.cbox_branch.setPreferredSize(new Dimension(250, 50));
    }
}
