package com.weique.overhaul.v2.mvp.model.entity;

public class StaffTemperatureBean {
    String EmployeeInfoRegisterId;
    double AMTemperature = 0;
    double PMTemperature= 0;
    String EmpName;
    String CreateTime;

    public String getEmpName() {
        return EmpName;
    }

    public void setEmpName(String empName) {
        EmpName = empName;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    public String getEmployeeInfoRegisterId() {
        return EmployeeInfoRegisterId;
    }

    public void setEmployeeInfoRegisterId(String employeeInfoRegisterId) {
        EmployeeInfoRegisterId = employeeInfoRegisterId;
    }

    public double getAMTemperature() {
        return AMTemperature;
    }

    public void setAMTemperature(double AMTemperature) {
        this.AMTemperature = AMTemperature;
    }

    public double getPMTemperature() {
        return PMTemperature;
    }

    public void setPMTemperature(double PMTemperature) {
        this.PMTemperature = PMTemperature;
    }

    @Override
    public String toString() {
        return "{" +
                "\"EmployeeInfoRegisterId\":\"" + EmployeeInfoRegisterId + "\"" +
                ",\" AMTemperature\":" + AMTemperature +
                ", \"PMTemperature\":" + PMTemperature +
                '}';
    }
}
