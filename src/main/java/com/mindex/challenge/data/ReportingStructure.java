package com.mindex.challenge.data;

public class ReportingStructure {
    private Employee employee;
    private int numReports;

    public ReportingStructure(Employee employee, int numReports){
        this.employee = employee;
        this.numReports = numReports;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public int getNumReports() {
        return numReports;
    }

    public void setNumReports(int numReports) {
        this.numReports = numReports;
    }
}
