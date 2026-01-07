package model;
import java.time.LocalTime;
public class staff_working_hour {
    private int id;
    private int staff_id;
    private String workday;
    private LocalTime start_time;
    private LocalTime end_time;
    public staff_working_hour(){}
    public staff_working_hour(int id,int staff_id,String workday,LocalTime start_time,LocalTime end_time) {
        this.id = id;
        this.staff_id = staff_id;
        this.workday = workday;
        this.start_time = start_time;
        this.end_time = end_time;
    }
    public int getid(){
        return id;}
    public void setid(int id){
        this.id = id;
    }
    public int getstaff_id(){
        return staff_id;
    }
    public void setstaff_id(int staff_id){
        this.staff_id = staff_id;
    }
    public String getworkday(){
        return workday;
    }
    public void setworkday(String workday){
        this.workday = workday;
    }
    public LocalTime getstart_time(){
        return start_time;
    }
    public void setstart_time(LocalTime start_time){
        this.start_time = start_time;
    }
    public LocalTime getend_time(){
        return end_time;
    }
    public void setend_time(LocalTime end_time){
        this.end_time = end_time;
    }
    @Override
    public String toString(){
        return "StaffWorkingHours{" +
                "id=" + id +
                ", staffId=" + staff_id +
                ", workDay='" + workday + '\'' +
                ", startTime=" + start_time +
                ", endTime=" + end_time +
                '}';

    }
}
