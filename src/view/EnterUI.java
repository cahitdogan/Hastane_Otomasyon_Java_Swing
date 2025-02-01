package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EnterUI extends JFrame {
    private JPanel container;
    private JButton btn_patient_login_ui;
    private JButton btn_doctor_login_ui;
    private JButton btn_secretary_login_ui;

    public EnterUI() {
        this.add(container);
        this.setSize(1000,600);
        this.setTitle("Hastane Otomasyonu");
        this.setVisible(true);
        int x = (Toolkit.getDefaultToolkit().getScreenSize().width - this.getSize().width) / 2;
        int y = (Toolkit.getDefaultToolkit().getScreenSize().height - this.getSize().height) / 2;
        this.setLocation(x, y);

        btn_patient_login_ui.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> {
                PatientLoginUI patientLoginUI = new PatientLoginUI();
                patientLoginUI.setVisible(true);
            });
        });

        btn_doctor_login_ui.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> {
                DoctorLoginUI doctorLoginUI = new DoctorLoginUI();
                doctorLoginUI.setVisible(true);
            });
        });
        btn_secretary_login_ui.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> {
                SecretaryLoginUI secretaryLoginUI = new SecretaryLoginUI();
                secretaryLoginUI.setVisible(true);
            });
        });
    }
}
