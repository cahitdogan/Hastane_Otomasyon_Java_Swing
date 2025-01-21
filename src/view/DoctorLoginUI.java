package view;
import dao.DoctorDao;
import entity.Doctor;

import javax.swing.*;
import java.awt.*;

public class DoctorLoginUI extends JFrame {

    private JTextField fld_username;
    private JPasswordField fld_password;
    private JButton btn_login;
    private JPanel container;
    private JPanel pnl_title;
    private JPanel pnl_form;
    private JLabel lbl_title;
    private JLabel lbl_username;
    private JLabel lbl_password;

    public DoctorLoginUI() {
        this.setTitle("Doktor Girişi");
        this.add(container);
        this.setSize(600, 600);
        this.setVisible(false);
        int x = (Toolkit.getDefaultToolkit().getScreenSize().width - this.getSize().width) / 2;
        int y = (Toolkit.getDefaultToolkit().getScreenSize().height - this.getSize().height) / 2;
        this.setLocation(x, y);

        this.btn_login.addActionListener(e -> {
            if (this.fld_username.getText().isEmpty() || this.fld_password.getPassword().length == 0) {
                JOptionPane.showMessageDialog(null, "Tüm alanları doldurunuz!", "HATA", JOptionPane.INFORMATION_MESSAGE);
            } else {
                String username = fld_username.getText();
                char[] passwordArray = fld_password.getPassword();
                String password = new String(passwordArray);
                DoctorDao doctorDao = new DoctorDao();
                Doctor doctor = doctorDao.findDoctorByLogin(username, password);

                if (doctor == null) {
                    JOptionPane.showMessageDialog(null, "Böyle bir kullanıcı bulunamadı!", "HATA", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Giriş başarılı!", "HATA", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
    }
}
