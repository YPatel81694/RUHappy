package main.ruhappy;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Created by ypgia on 6/29/2016.
 */
public class BarLibrary implements Serializable{
    private String objectId;
    private String name;
    private double longitude;
    private double latitude;
    private String image;
    private int count;
    private String barImages;
    private String address;
    private String Monday;
    private String Tuesday;
    private String Wednesday;
    private String Thursday;
    private String Friday;
    private String Saturday;
    private String Sunday;
    private String HoursOfBarOperation;
    private boolean ent;
    private boolean hh;
    private double distance = -1.0;

    public void setDistance(double x){
        distance = x;
    }
    public double getDistance(){
        return distance;
    }
    public void setEnt(boolean x)
    {
        ent = x;
    }
    public boolean getEnt()
    {
        return ent;
    }
    public void setHh(boolean x)
    {
        hh = x;
    }
    public boolean getHh()
    {
        return hh;
    }
    public String getObjectId()
    {
        return objectId;
    }
    public void setObjectId(String objectId)
    {
        this.objectId = objectId;
    }
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public double getLongitude()
    {
        return longitude;
    }
    public void setLongitude(Double longitude)
    {
        this.longitude = longitude;
    }
    public double getLatitude()
    {
        return latitude;
    }
    public void setLatitude(Double latitude)
    {
        this.latitude = latitude;
    }
    public String getImage()
    {
        return image;
    }
    public void setImage (String image)
    {
        this.image = image;
    }
    public int getCount()
    {
        return count;
    }
    public void setCount (int count)
    {
        this.count = count;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getBarImages() {
        return barImages;
    }
    public void setBarImages(String barImages) {
        this.barImages = barImages;
    }
    public String getHoursOfBarOperation() {
        return HoursOfBarOperation;
    }
    public void setHoursOfBarOperation(String hoursOfBarOperation) {
        HoursOfBarOperation = hoursOfBarOperation;
    }
    public String getMonday() {
        return Monday;
    }
    public void setMonday(String Monday) {
        this.Monday = Monday;
    }
    public String getTuesday() {
        return Tuesday;
    }
    public void setTuesday(String Tuesday) {
        this.Tuesday = Tuesday;
    }
    public String getWednesday() {
        return Wednesday;
    }
    public void setWednesday(String Wednesday) {
        this.Wednesday = Wednesday;
    }
    public String getThursday() {
        return Thursday;
    }
    public void setThursday(String Thursday) {
        this.Thursday = Thursday;
    }
    public String getFriday() {
        return Friday;
    }
    public void setFriday(String Friday) {
        this.Friday = Friday;
    }
    public String getSaturday() {
        return Saturday;
    }
    public void setSaturday(String Saturday) {
        this.Saturday = Saturday;
    }
    public String getSunday() {
        return Sunday;
    }
    public void setSunday(String Sunday) {
        this.Sunday = Sunday;
    }




}
