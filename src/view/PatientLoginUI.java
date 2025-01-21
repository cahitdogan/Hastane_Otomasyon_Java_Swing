package view;
import dao.PatientDao;
import entity.Patient;

import javax.swing.*;
import java.awt.*;

public class PatientLoginUI extends JFrame {
    private JPanel pnl_title;
    private JPanel container;
    private JPanel pnl_form;
    private JTextField fld_tc;
    private JButton btn_login;
    private JLabel lbl_tc;
    private JButton btn_register;
    private JLabel lbl_password;
    private JPasswordField fld_password;

    public PatientLoginUI() {
        this.add(container);
        this.setTitle("Hasta Girişi");
        this.setSize(600, 600);
        this.setVisible(false);
        int x = (Toolkit.getDefaultToolkit().getScreenSize().width - this.getSize().width) / 2;
        int y = (Toolkit.getDefaultToolkit().getScreenSize().height - this.getSize().height) / 2;
        this.setLocation(x, y);

        this.btn_login.addActionListener(e -> {
            if (this.fld_tc.getText().isEmpty() || this.fld_password.getPassword().length == 0) {
                JOptionPane.showMessageDialog(null, "Tüm alanları doldurunuz!", "HATA", JOptionPane.INFORMATION_MESSAGE);
            } else {
                String tc = this.fld_tc.getText();
                char[] passwordArray = this.fld_password.getPassword();
                String password = new String(passwordArray);
                PatientDao patientDao = new PatientDao();
                Patient patient = patientDao.findPatientByLogin(tc, password);
                if (patient == null) {
                    JOptionPane.showMessageDialog(null, "Böyle bir kullanıcı bulunamadı.", "HATA", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    PatientPanel patientEnter = new PatientPanel(patient);
                    patientEnter.setVisible(true);
                    this.setVisible(false);
                }
            }
        });

        btn_register.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> {
                PatientRegisterUI patientRegisterUI = new PatientRegisterUI();
                patientRegisterUI.setVisible(true);
            });
        });
    }
}
