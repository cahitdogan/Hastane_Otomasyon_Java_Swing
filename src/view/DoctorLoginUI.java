package view;
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
            }
        });
    }
}
