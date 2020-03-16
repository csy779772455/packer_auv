package com.auv.hardwaretest.Save_Meal;

import com.auv.hardwaretest.R;

import java.util.ArrayList;
import java.util.List;

public class Save_Bill_bean {


    public String getSave_item() {
        return save_item;
    }

    public void setSave_item(String save_item) {
        this.save_item = save_item;
    }

    public String getSerial_number() {
        return serial_number;
    }

    public void setSerial_number(String serial_number) {
        this.serial_number = serial_number;
    }

    public String getOod() {
        return ood;
    }

    public void setOod(String ood) {
        this.ood = ood;
    }

    public String getCuisine() {
        return cuisine;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStall() {
        return stall;
    }

    public void setStall(String stall) {
        this.stall = stall;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCell() {
        return cell;
    }

    public void setCell(String cell) {
        this.cell = cell;
    }
    private String save_item;
    private String serial_number;

    public Save_Bill_bean(String save_item, String serial_number, String ood, String cuisine, String phone, String stall, String time, String cell,String state) {
        this.save_item = save_item;
        this.serial_number = serial_number;
        this.ood = ood;
        this.cuisine = cuisine;
        this.phone = phone;
        this.stall = stall;
        this.time = time;
        this.cell = cell;
        this.state=state;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    private String state;
    private String ood;
    private String cuisine;
    private String phone;
    private String stall;
    private String time;
    private String cell;

}
