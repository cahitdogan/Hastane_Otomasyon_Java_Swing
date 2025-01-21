package entity;

public class Appointment {
    private int id;
    private String patientName;
    private String doctorName;
    private String branch;
    private String month;
    private int day;
    private String hour;
    private String patientTc;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getPatientTc() {
        return patientTc;
    }

    public void setPatientTc(String patientTc) {
        this.patientTc = patientTc;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "id=" + id +
                ", patientName='" + patientName + '\'' +
                ", doctorName='" + doctorName + '\'' +
                ", branch='" + branch + '\'' +
                ", month='" + month + '\'' +
                ", day=" + day +
                ", hour='" + hour + '\'' +
                ", patientTc='" + patientTc + '\'' +
                '}';
    }
}
