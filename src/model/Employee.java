package model;

public class Employee {
    private String EmployeeId;
    private String EmployeeName;
    private String EmployeeAddress;
    private String EmployeeNic;
    private String EmployeePhone;
    private String EmployeePosition;

    public Employee(String employeeId, String employeeName, String employeeAddress, String employeeNic, String employeePhone, String employeePosition) {
        EmployeeId = employeeId;
        EmployeeName = employeeName;
        EmployeeAddress = employeeAddress;
        EmployeeNic = employeeNic;
        EmployeePhone = employeePhone;
        EmployeePosition = employeePosition;
    }

    public String getEmployeeId() {
        return EmployeeId;
    }

    public void setEmployeeId(String employeeId) {
        EmployeeId = employeeId;
    }

    public String getEmployeeName() {
        return EmployeeName;
    }

    public void setEmployeeName(String employeeName) {
        EmployeeName = employeeName;
    }

    public String getEmployeeAddress() {
        return EmployeeAddress;
    }

    public void setEmployeeAddress(String employeeAddress) {
        EmployeeAddress = employeeAddress;
    }

    public String getEmployeeNic() {
        return EmployeeNic;
    }

    public void setEmployeeNic(String employeeNic) {
        EmployeeNic = employeeNic;
    }

    public String getEmployeePhone() {
        return EmployeePhone;
    }

    public void setEmployeePhone(String employeePhone) {
        EmployeePhone = employeePhone;
    }

    public String getEmployeePosition() {
        return EmployeePosition;
    }

    public void setEmployeePosition(String employeePosition) {
        EmployeePosition = employeePosition;
    }
}
