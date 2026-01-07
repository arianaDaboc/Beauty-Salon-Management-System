package model;
import java.time.LocalDate;
import java.time.LocalTime;

public class appointment {
    private int appoitmentid;
    private int userid;
    private int sericeid;
    private int staffId;
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private String status;
    public appointment(){}
    public appointment(int appointmentId, int userId, int serviceId, int staffId, LocalDate appointmentDate, LocalTime appointmentTime, String status)
    {
        this.appoitmentid = appointmentId;
        this.userid = userId;
        this.sericeid = serviceId;
        this.staffId = staffId;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.status = status;
    }
    public int getAppoitmentid() {
        return appoitmentid;
    }
    public void setAppoitmentid(int appoitmentid) {
    this.appoitmentid = appoitmentid;
    }
    public int getUserid() {
    return userid;
    }
    public void setUserid(int userid) {
    this.userid = userid;
    }
    public int getSericeid() {
    return sericeid;
    }
    public void setSericeid(int sericeid) {
    this.sericeid = sericeid;
    }
    public int getStaffId() {
    return staffId;
    }
    public void setStaffId(int staffId) {
    this.staffId = staffId;
    }
    public LocalDate getAppointmentDate() {
    return appointmentDate;
    }
    public void setAppointmentDate(LocalDate appointmentDate) {
    this.appointmentDate = appointmentDate;
    }
    public LocalTime getAppointmentTime() {
    return appointmentTime;
    }
    public void setAppointmentTime(LocalTime appointmentTime) {
    this.appointmentTime = appointmentTime;
    }
    public String getStatus() {
    return status;
    }
    public void setStatus(String status) {
    this.status = status;
    }
    @Override
    public String toString() {
        return "Appointment{" +
                "appointmentId=" + appoitmentid +
                ", userId=" + userid +
                ", serviceId=" +sericeid +
                ", staffId=" + staffId +
                ", appointmentDate=" + appointmentDate +
                ", appointmentTime=" + appointmentTime +
                ", status='" + status + '\'' +
                '}';

    }
}

