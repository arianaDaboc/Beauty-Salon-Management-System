package model;

public class staff {
    private int staff_id;
    private String staff_name;
    private String specialization;
    private String phone_number;

    public staff() {
    }
    public staff(int staff_id, String staff_name, String specialization, String phone_number) {
        this.staff_id=staff_id;
        this.staff_name=staff_name;
        this.specialization=specialization;
        this.phone_number=phone_number;
    }
    public int getStaff_id() {
        return staff_id;
    }
    public void setStaff_id(int staff_id) {
        this.staff_id = staff_id;
    }
    public String getStaff_name() {
        return staff_name;
    }
    public void setStaff_name(String staff_name) {
        this.staff_name = staff_name;
    }
    public String getSpecialization() {
        return specialization;
    }
    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }
    public String getPhone_number() {
        return phone_number;
    }
    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }
    @Override
    public String toString() {
        return staff_name;
    }
}