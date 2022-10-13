package views.tm;

public class DriverTM {
    private String driverId;
    private String driverName;
    private String driverAddress;
    private String driverLicense;
    private String driverPhone;

    public DriverTM() {
    }

    public DriverTM(String driverId, String driverName, String driverAddress, String driverLicense, String driverPhone) {
        this.driverId = driverId;
        this.driverName = driverName;
        this.driverAddress = driverAddress;
        this.driverLicense = driverLicense;
        this.driverPhone = driverPhone;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDriverAddress() {
        return driverAddress;
    }

    public void setDriverAddress(String driverAddress) {
        this.driverAddress = driverAddress;
    }

    public String getDriverLicense() {
        return driverLicense;
    }

    public void setDriverLicense(String driverLicense) {
        this.driverLicense = driverLicense;
    }

    public String getDriverPhone() {
        return driverPhone;
    }

    public void setDriverPhone(String driverPhone) {
        this.driverPhone = driverPhone;
    }

    @Override
    public String toString() {
        return "DriverTM{" +
                "driverId='" + driverId + '\'' +
                ", driverName='" + driverName + '\'' +
                ", driverAddress='" + driverAddress + '\'' +
                ", driverLicense='" + driverLicense + '\'' +
                ", driverPhone='" + driverPhone + '\'' +
                '}';
    }
}
