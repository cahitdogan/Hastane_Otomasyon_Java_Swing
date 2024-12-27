package entity;

public class Doctor {
    private int id;
    private long tc;
    private String name;
    private String phone;

    public Doctor(int id, long tc, String name, String phone) {
        this.id = id;
        this.tc = tc;
        this.name = name;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getTc() {
        return tc;
    }

    public void setTc(long tc) {
        this.tc = tc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "id=" + id +
                ", tc=" + tc +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
