package view;

import dao.PatientDao;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PatientRegisterUI extends JFrame {
    private JLabel lbl_title;
    private JPanel pnl_title;
    private JTextField fld_name;
    private JTextField fld_tc;
    private JTextField fld_mail;
    private JTextField fld_phone;
    private JTextField fld_address;
    private JPasswordField fld_password;
    private JButton btn_register;
    private JPanel container;
    private JPanel pnl_form;
    private JLabel lbl_name;
    private JLabel lbl_tc;
    private JLabel lbl_mail;
    private JLabel lbl_phone;
    private JLabel lbl_address;
    private JLabel lbl_password;

    public PatientRegisterUI() {
        this.add(container);
        this.setTitle("Kayıt");
        this.setVisible(false);
        this.setSize(400, 600);
        int x = (Toolkit.getDefaultToolkit().getScreenSize().width - this.getSize().width) / 2;
        int y = (Toolkit.getDefaultToolkit().getScreenSize().height - this.getSize().height) / 2;
        this.setLocation(x, y);

        this.btn_register.addActionListener(e -> {
            String tc = this.fld_tc.getText();
            String mail = this.fld_mail.getText();

            PatientDao patientDao = new PatientDao();
            boolean result = patientDao.checkIfAlreadyRegistered(tc, mail);

            if (result) {
                JOptionPane.showMessageDialog(null, "Bu bilgilerle bir kullanıcı zaten kayıtlı!", "HATA", JOptionPane.INFORMATION_MESSAGE);
            } else {
                String name = this.fld_name.getText();
                String address = this.fld_address.getText();
                String phone = this.fld_phone.getText();
                char[] passwordArray = this.fld_password.getPassword();
                String password = new String(passwordArray);

                result = patientDao.registerPatient(name, tc, address, mail, phone, password);

                if (result) {
                    JOptionPane.showMessageDialog(null, "Kayıt başarıyla oluşturuldu!", "Başarılı", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Kayıt başarısız oldu!", "HATA", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
    }
}
