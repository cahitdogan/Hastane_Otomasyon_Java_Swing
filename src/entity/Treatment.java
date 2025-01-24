package entity;

public class Treatment {
    private int id;
    private String diagnosis;
    private String appliedTreatments;
    private int doctorId;
    private int patientId;

    public Treatment() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getAppliedTreatments() {
        return appliedTreatments;
    }

    public void setAppliedTreatments(String appliedTreatments) {
        this.appliedTreatments = appliedTreatments;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    @Override
    public String toString() {
        return "Treatment{" +
                "id=" + id +
                ", diagnosis='" + diagnosis + '\'' +
                ", appliedTreatments='" + appliedTreatments + '\'' +
                ", doctorId=" + doctorId +
                ", patientId=" + patientId +
                '}';
    }
}