package view;

import dao.*;
import entity.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class DoctorPanel extends JFrame {
    private JPanel container;
    private JPanel pnl_appointments;
    private JTable tbl_appointments;
    private JLabel lbl_welcome;
    private JPanel pnl_patient;
    private JTextField fld_patient_tc;
    private JButton btn_search;
    private JPanel pnl_patient_info;
    private JLabel lbl_patient_name;
    private JLabel lbl_patient_phone;
    private JTable tbl_treatments;
    private JTable tbl_medicines;
    private JPanel pnl_patient_actions;
    private JButton btn_add_treatment;
    private JButton btn_add_medicine;
    private JButton btn_request_lab;

    private DefaultTableModel mdl_appointments;
    private DefaultTableModel mdl_treatments;
    private DefaultTableModel mdl_medicines;
    private Doctor doctor;
    private Patient selectedPatient;

    public DoctorPanel(Doctor doctor) {
        this.doctor = doctor;
        this.add(container);
        this.setTitle("Doktor Paneli");
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        int x = (Toolkit.getDefaultToolkit().getScreenSize().width - this.getSize().width) / 2;
        int y = (Toolkit.getDefaultToolkit().getScreenSize().height - this.getSize().height) / 2;
        this.setLocation(x, y);
        this.setVisible(false);

        lbl_welcome.setText("Hoş geldiniz, " + doctor.getName());

        // Initialize appointment table
        mdl_appointments = new DefaultTableModel();
        mdl_appointments.setColumnIdentifiers(new Object[]{"Hasta", "TC", "Tarih"});
        tbl_appointments.setModel(mdl_appointments);
        loadDoctorAppointments();

        // Initialize treatment table
        mdl_treatments = new DefaultTableModel();
        mdl_treatments.setColumnIdentifiers(new Object[]{"Tanı", "Detay"});
        tbl_treatments.setModel(mdl_treatments);

        // Initialize medicine table
        mdl_medicines = new DefaultTableModel();
        mdl_medicines.setColumnIdentifiers(new Object[]{"İlaç Adı", "Doz"});
        tbl_medicines.setModel(mdl_medicines);

        // Add action listeners
        btn_search.addActionListener(e -> searchPatient());
        btn_add_treatment.addActionListener(e -> addTreatment());
        btn_add_medicine.addActionListener(e -> addMedicine());
        btn_request_lab.addActionListener(e -> requestLabTest());

        // Initially disable patient action buttons
        setPatientActionsEnabled(false);
    }

    private void loadDoctorAppointments() {
        mdl_appointments.setRowCount(0);
        AppointmentDao appointmentDao = new AppointmentDao();
        appointmentDao.getAppointmentsByDoctor(doctor.getId()).forEach(appointment -> {
            mdl_appointments.addRow(new Object[]{
                    appointment.getPatientName(),
                    appointment.getPatientTc(),
                    appointment.getDay() + " " + appointment.getMonth() + " " + appointment.getHour()
            });
        });
    }

    private void searchPatient() {
        String tc = fld_patient_tc.getText().trim();
        if (tc.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Lütfen TC kimlik numarası giriniz!");
            return;
        }

        PatientDao patientDao = new PatientDao();
        selectedPatient = patientDao.getPatientByTc(tc);

        if (selectedPatient != null) {
            lbl_patient_name.setText(selectedPatient.getName());
            lbl_patient_phone.setText(selectedPatient.getPhone());
            loadPatientTreatments();
            loadPatientMedicines();
            setPatientActionsEnabled(true);
        } else {
            JOptionPane.showMessageDialog(this, "Hasta bulunamadı!");
            clearPatientInfo();
        }
    }

    private void loadPatientTreatments() {
        mdl_treatments.setRowCount(0);
        TreatmentDao treatmentDao = new TreatmentDao();
        treatmentDao.getTreatmentsByPatient(selectedPatient.getId()).forEach(treatment -> {
            mdl_treatments.addRow(new Object[]{
                    treatment.getDiagnosis(),
                    treatment.getAppliedTreatments()
            });
        });
    }

    private void loadPatientMedicines() {
        mdl_medicines.setRowCount(0);
        MedicineDao medicineDao = new MedicineDao();
        medicineDao.getMedicinesByPatient(selectedPatient.getId()).forEach(medicine -> {
            mdl_medicines.addRow(new Object[]{
                    medicine.getName(),
                    medicine.getDosage()
            });
        });
    }

    private void addTreatment() {
        if (selectedPatient == null) return;
        String diagnosis = JOptionPane.showInputDialog(this, "Tanı giriniz:");
        String appliedTreatment = JOptionPane.showInputDialog(this, "Tedavi detaylarını giriniz:");
        if ((appliedTreatment != null || diagnosis != null) && !appliedTreatment.trim().isEmpty()) {
            TreatmentDao treatmentDao = new TreatmentDao();
            Treatment treatment = new Treatment();
            treatment.setDoctorId(doctor.getId());
            treatment.setPatientId(selectedPatient.getId());
            treatment.setAppliedTreatments(appliedTreatment);
            treatment.setDiagnosis(diagnosis);
            boolean success = treatmentDao.createTreatment(treatment);
            if (success) {
                loadPatientTreatments();
                JOptionPane.showMessageDialog(this, "Tedavi başarıyla eklendi.");
            } else {
                JOptionPane.showMessageDialog(this, "Tedavi oluşturulurken hata oluştu!");
            }
        }
    }

    private void addMedicine() {
        if (selectedPatient == null) return;

        String[] options = {"Mevcut İlaç Seç", "Yeni İlaç Ekle"};
        int choice = JOptionPane.showOptionDialog(this,
                "İlaç ekleme yöntemini seçiniz",
                "İlaç Ekle",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);

        if (choice == 0) {
            MedicineDao medicineDao = new MedicineDao();
            java.util.List<Medicine> medicines = medicineDao.getAllMedicines();

            if (medicines.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Veritabanında kayıtlı ilaç bulunmamaktadır! Yeni ilaç kaydı yapabilirsiniz.");
                return;
            }

            Medicine selectedMedicine = (Medicine) JOptionPane.showInputDialog(
                    this,
                    "İlaç seçiniz:",
                    "İlaç Seç",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    medicines.toArray(),
                    medicines.get(0));

            if (selectedMedicine != null) {
                boolean success = medicineDao.assignMedicineToPatient(selectedPatient.getId(), selectedMedicine.getId());
                if (success) {
                    loadPatientMedicines();
                    JOptionPane.showMessageDialog(this, "İlaç başarıyla eklendi.");
                } else {
                    JOptionPane.showMessageDialog(this, "İlaç eklenirken bir hata oluştu!");
                }
            }
        } else if (choice == 1) {
            String name = JOptionPane.showInputDialog(this, "İlaç adını giriniz:");
            if (name != null && !name.trim().isEmpty()) {
                String dosage = JOptionPane.showInputDialog(this, "İlaç dozunu giriniz:");
                if (dosage != null && !dosage.trim().isEmpty()) {
                    MedicineDao medicineDao = new MedicineDao();
                    Medicine newMedicine = new Medicine();
                    newMedicine.setName(name);
                    newMedicine.setDosage(Integer.parseInt(dosage));
                    newMedicine.setStock(100);

                    boolean success = medicineDao.createMedicine(newMedicine);
                    if (success) {
                        int medicineId = medicineDao.getLastInsertedMedicineId();
                        boolean success2 = medicineDao.assignMedicineToPatient(selectedPatient.getId(), medicineId);
                        if (success2) {
                            loadPatientMedicines();
                            JOptionPane.showMessageDialog(this, "Yeni ilaç başarıyla eklendi.");
                        } else {
                            JOptionPane.showMessageDialog(this, "İlaç hasta ile ilişkilendirilirken hata oluştu!");
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Yeni ilaç oluşturulurken hata oluştu!");
                    }
                }
            }
        }
    }

    private void requestLabTest() {
        if (selectedPatient == null) return;
        String testName = JOptionPane.showInputDialog(this, "Test adını giriniz:");
        if (testName != null && !testName.trim().isEmpty()) {
            String testDetails = JOptionPane.showInputDialog(this, "Test detaylarını giriniz:");
            if (testDetails != null && !testDetails.trim().isEmpty()) {
                LabTestDao labTestDao = new LabTestDao();
                LabTest labTest = new LabTest();
                labTest.setPatientId(selectedPatient.getId());
                labTest.setDoctorName(doctor.getName());
                labTest.setTestName(testName);
                labTest.setTestDetails(testDetails);
                labTest.setRequestDate(java.time.LocalDate.now().toString());

                boolean success = labTestDao.createLabTest(labTest);
                if (success) {
                    JOptionPane.showMessageDialog(this, "Laboratuvar testi başarıyla istendi.");
                } else {
                    JOptionPane.showMessageDialog(this, "Laboratuvar testi istenirken bir hata oluştu!");
                }
            }
        }
    }

    private void clearPatientInfo() {
        selectedPatient = null;
        lbl_patient_name.setText("");
        lbl_patient_phone.setText("");
        mdl_treatments.setRowCount(0);
        mdl_medicines.setRowCount(0);
        setPatientActionsEnabled(false);
    }

    private void setPatientActionsEnabled(boolean enabled) {
        btn_add_treatment.setEnabled(enabled);
        btn_add_medicine.setEnabled(enabled);
        btn_request_lab.setEnabled(enabled);
    }
}