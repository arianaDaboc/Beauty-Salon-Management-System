package model;

public class service {
    private int serviceid;
    private String servicename;
    private double price;
    private int durationmin;
    private int categoryid;

    public service() {
    }
 public service(int serviceid, String servicename, double price, int durationmin, int categoryid) {
        this.serviceid = serviceid;
        this.servicename = servicename;
        this.price = price;
        this.durationmin = durationmin;
        this.categoryid = categoryid;
 }
 public int getServiceid() {
        return serviceid;
    }
    public void setServiceId(int serviceId) {
        this.serviceid = serviceId;
    }
    public String getServicename() {
        return servicename;
    }
    public void setServicename(String servicename) {
        this.servicename=servicename;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public int getDurationmin() {
        return durationmin;
    }
    public void setDurationmin(int durationmin) {
        this.durationmin = durationmin;
    }
    public int getCategoryid() {
        return categoryid;
    }
    public void setCategoryid(int categoryid) {
        this.categoryid = categoryid;
    }
    @Override
    public String toString() {
        return servicename;
    }
}