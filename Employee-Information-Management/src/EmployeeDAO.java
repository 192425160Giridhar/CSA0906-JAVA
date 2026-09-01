import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;

public class EmployeeDAO {

    public boolean addEmployee(Employee emp) throws SQLException {

        String sql = "INSERT INTO employee " +
                "(emp_id, emp_name, department, designation, " +
                "date_of_joining, salary, email, phone, status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, emp.getEmpId());
            ps.setString(2, emp.getEmpName());
            ps.setString(3, emp.getDepartment());
            ps.setString(4, emp.getDesignation());
            ps.setDate(5, Date.valueOf(emp.getDateOfJoining()));
            ps.setDouble(6, emp.getSalary());
            ps.setString(7, emp.getEmail());
            ps.setString(8, emp.getPhone());
            ps.setString(9, emp.getStatus());

            return ps.executeUpdate() > 0;
        }
    }

    public boolean updateEmployee(Employee emp) throws SQLException {

        String sql = "UPDATE employee SET " +
                "emp_name=?, department=?, designation=?, " +
                "date_of_joining=?, salary=?, email=?, phone=?, status=? " +
                "WHERE emp_id=?";

        Connection con = null;

        try {
            con = DBConnection.getConnection();
            con.setAutoCommit(false);

            try (PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setString(1, emp.getEmpName());
                ps.setString(2, emp.getDepartment());
                ps.setString(3, emp.getDesignation());
                ps.setDate(4, Date.valueOf(emp.getDateOfJoining()));
                ps.setDouble(5, emp.getSalary());
                ps.setString(6, emp.getEmail());
                ps.setString(7, emp.getPhone());
                ps.setString(8, emp.getStatus());
                ps.setInt(9, emp.getEmpId());

                int result = ps.executeUpdate();

                con.commit();

                return result > 0;
            }

        } catch (SQLException e) {

            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException ignored) {
                }
            }

            throw e;

        } finally {

            if (con != null) {
                try {
                    con.setAutoCommit(true);
                    con.close();
                } catch (SQLException ignored) {
                }
            }
        }
    }

    public boolean deleteEmployee(int id) throws SQLException {

        String sql = "DELETE FROM employee WHERE emp_id=?";

        Connection con = null;

        try {
            con = DBConnection.getConnection();
            con.setAutoCommit(false);

            try (PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setInt(1, id);

                int result = ps.executeUpdate();

                con.commit();

                return result > 0;
            }

        } catch (SQLException e) {

            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException ignored) {
                }
            }

            throw e;

        } finally {

            if (con != null) {
                try {
                    con.setAutoCommit(true);
                    con.close();
                } catch (SQLException ignored) {
                }
            }
        }
    }

    public List<Employee> getAllEmployees() throws SQLException {

        List<Employee> employees = new ArrayList<>();

        String sql = "SELECT * FROM employee";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                employees.add(new Employee(
                        rs.getInt("emp_id"),
                        rs.getString("emp_name"),
                        rs.getString("department"),
                        rs.getString("designation"),
                        rs.getDate("date_of_joining").toString(),
                        rs.getDouble("salary"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("status")
                ));
            }
        }

        return employees;
    }

    public List<Employee> searchEmployees(
            String column, String keyword) throws SQLException {

        List<Employee> employees = new ArrayList<>();

        String sql;

        if (column.equals("ID")) {
            sql = "SELECT * FROM employee WHERE emp_id LIKE ?";
        } else if (column.equals("Department")) {
            sql = "SELECT * FROM employee WHERE department LIKE ?";
        } else {
            sql = "SELECT * FROM employee WHERE emp_name LIKE ?";
        }

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, "%" + keyword + "%");

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    employees.add(new Employee(
                            rs.getInt("emp_id"),
                            rs.getString("emp_name"),
                            rs.getString("department"),
                            rs.getString("designation"),
                            rs.getDate("date_of_joining").toString(),
                            rs.getDouble("salary"),
                            rs.getString("email"),
                            rs.getString("phone"),
                            rs.getString("status")
                    ));
                }
            }
        }

        return employees;
    }
}
