public class Employee {

    private int empId;
    private String empName;
    private String department;
    private String designation;
    private String dateOfJoining;
    private double salary;
    private String email;
    private String phone;
    private String status;

    public Employee(int empId, String empName, String department,
                    String designation, String dateOfJoining,
                    double salary, String email,
                    String phone, String status) {

        this.empId = empId;
        this.empName = empName;
        this.department = department;
        this.designation = designation;
        this.dateOfJoining = dateOfJoining;
        this.salary = salary;
        this.email = email;
        this.phone = phone;
        this.status = status;
    }

    public int getEmpId() {
        return empId;
    }

    public String getEmpName() {
        return empName;
    }

    public String getDepartment() {
        return department;
    }

    public String getDesignation() {
        return designation;
    }

    public String getDateOfJoining() {
        return dateOfJoining;
    }

    public double getSalary() {
        return salary;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getStatus() {
        return status;
    }
}