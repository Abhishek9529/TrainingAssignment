package com.mednet.entity;

public class Staff {

    private int id;
    private String name;
    private String code;
    private String userType;
    private String phone;
    private String department;
    private String status;
    private String joiningDate;

    // Default Constructor
    public Staff() {
    }

    // Parameterized Constructor
    public Staff(int id, String name, String code, String userType,
                 String phone, String department, String status, String joiningDate) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.userType = userType;
        this.phone = phone;
        this.department = department;
        this.status = status;
        this.joiningDate = joiningDate;
    }

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(String joiningDate) {
        this.joiningDate = joiningDate;
    }
}