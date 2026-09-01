import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class EmployeeGUI extends JFrame {

    JTextField idField, nameField, dateField, salaryField, emailField, phoneField, searchField;
    JComboBox<String> departmentBox, designationBox, searchBox;
    JRadioButton activeRadio, inactiveRadio;
    JButton addButton, updateButton, deleteButton, clearButton;
    JButton searchButton, refreshButton;
    JTable table;
    DefaultTableModel model;
    JLabel statusLabel, connectionLabel;

    EmployeeDAO dao = new EmployeeDAO();

    public EmployeeGUI() {

        setTitle("Employee Information Management System");
        setSize(1250, 750);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName()
            );
        } catch (Exception ignored) {
        }

        createGUI();
        loadEmployees();
        checkConnection();
    }

    void createGUI() {

        setLayout(new BorderLayout(10, 10));

        JLabel title = new JLabel(
                "EMPLOYEE INFORMATION MANAGEMENT SYSTEM",
                SwingConstants.CENTER
        );

        title.setFont(new Font("Arial", Font.BOLD, 26));
        title.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
        add(title, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(9, 2, 8, 8));
        formPanel.setBorder(BorderFactory.createTitledBorder(
                "Employee Details"
        ));

        idField = new JTextField();
        nameField = new JTextField();
        dateField = new JTextField();
        salaryField = new JTextField();
        emailField = new JTextField();
        phoneField = new JTextField();

        departmentBox = new JComboBox<>(
                new String[]{"IT", "HR", "Finance", "Marketing", "Sales"}
        );

        designationBox = new JComboBox<>(
                new String[]{"Developer", "Tester", "Manager",
                        "Accountant", "Analyst"}
        );

        activeRadio = new JRadioButton("Active", true);
        inactiveRadio = new JRadioButton("Inactive");

        ButtonGroup group = new ButtonGroup();
        group.add(activeRadio);
        group.add(inactiveRadio);

        JPanel statusPanel = new JPanel();
        statusPanel.add(activeRadio);
        statusPanel.add(inactiveRadio);

        formPanel.add(new JLabel("Employee ID"));
        formPanel.add(idField);

        formPanel.add(new JLabel("Employee Name"));
        formPanel.add(nameField);

        formPanel.add(new JLabel("Department"));
        formPanel.add(departmentBox);

        formPanel.add(new JLabel("Designation"));
        formPanel.add(designationBox);

        formPanel.add(new JLabel("Date of Joining"));
        formPanel.add(dateField);

        formPanel.add(new JLabel("Salary"));
        formPanel.add(salaryField);

        formPanel.add(new JLabel("Email"));
        formPanel.add(emailField);

        formPanel.add(new JLabel("Phone"));
        formPanel.add(phoneField);

        formPanel.add(new JLabel("Status"));
        formPanel.add(statusPanel);

        JPanel buttonPanel = new JPanel();

        addButton = new JButton("ADD");
        updateButton = new JButton("UPDATE");
        deleteButton = new JButton("DELETE");
        clearButton = new JButton("CLEAR");

        updateButton.setEnabled(false);
        deleteButton.setEnabled(false);

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);
        addButton.setFont(new Font("Arial", Font.BOLD, 13));
        updateButton.setFont(new Font("Arial", Font.BOLD, 13));
        deleteButton.setFont(new Font("Arial", Font.BOLD, 13));
        clearButton.setFont(new Font("Arial", Font.BOLD, 13));

        JPanel leftPanel = new JPanel(new BorderLayout(10, 10));
        leftPanel.add(formPanel, BorderLayout.CENTER);
        leftPanel.add(buttonPanel, BorderLayout.SOUTH);

        JPanel searchPanel = new JPanel();

        searchPanel.setBorder(
                BorderFactory.createTitledBorder("Search / Filter")
        );

        searchBox = new JComboBox<>(
                new String[]{"Name", "Department", "ID"}
        );

        searchField = new JTextField(15);

        searchButton = new JButton("SEARCH");
        refreshButton = new JButton("REFRESH");

        searchPanel.add(new JLabel("Search By"));
        searchPanel.add(searchBox);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(refreshButton);

        model = new DefaultTableModel();

        model.setColumnIdentifiers(new String[]{
                "ID", "Name", "Department", "Designation",
                "Date", "Salary", "Email", "Phone", "Status"
        });

        table = new JTable(model);
        table.setAutoCreateRowSorter(true);
        table.setRowHeight(28);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.getTableHeader().setFont(
                new Font("Arial", Font.BOLD, 14)
        );
        table.setRowHeight(28);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.getTableHeader().setFont(
                new Font("Arial", Font.BOLD, 14)
        );

        JScrollPane scrollPane = new JScrollPane(table);

        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));

        centerPanel.add(searchPanel, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());

        statusLabel = new JLabel("Ready");
        connectionLabel = new JLabel("Database: Checking...");

        statusLabel.setFont(new Font("Arial", Font.BOLD, 13));
        connectionLabel.setFont(new Font("Arial", Font.BOLD, 13));

        bottomPanel.add(statusLabel, BorderLayout.WEST);
        bottomPanel.add(connectionLabel, BorderLayout.EAST);

        add(leftPanel, BorderLayout.WEST);
        add(centerPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        addButton.addActionListener(e -> addEmployee());
        updateButton.addActionListener(e -> updateEmployee());
        deleteButton.addActionListener(e -> deleteEmployee());
        clearButton.addActionListener(e -> clearFields());
        refreshButton.addActionListener(e -> loadEmployees());
        searchButton.addActionListener(e -> searchEmployees());
        searchField.getDocument().addDocumentListener(
                new javax.swing.event.DocumentListener() {

                    public void insertUpdate(
                            javax.swing.event.DocumentEvent e) {
                        searchEmployees();
                    }

                    public void removeUpdate(
                            javax.swing.event.DocumentEvent e) {
                        searchEmployees();
                    }

                    public void changedUpdate(
                            javax.swing.event.DocumentEvent e) {
                        searchEmployees();
                    }
                }
        );

        table.getSelectionModel().addListSelectionListener(e -> {

            if (!e.getValueIsAdjusting() &&
                    table.getSelectedRow() != -1) {

                int row = table.getSelectedRow();

                idField.setText(
                        table.getValueAt(row, 0).toString()
                );

                nameField.setText(
                        table.getValueAt(row, 1).toString()
                );

                departmentBox.setSelectedItem(
                        table.getValueAt(row, 2).toString()
                );

                designationBox.setSelectedItem(
                        table.getValueAt(row, 3).toString()
                );

                dateField.setText(
                        table.getValueAt(row, 4).toString()
                );

                salaryField.setText(
                        table.getValueAt(row, 5).toString()
                );

                emailField.setText(
                        table.getValueAt(row, 6).toString()
                );

                phoneField.setText(
                        table.getValueAt(row, 7).toString()
                );

                if (table.getValueAt(row, 8)
                        .toString().equals("Active")) {

                    activeRadio.setSelected(true);

                } else {

                    inactiveRadio.setSelected(true);
                }

                idField.setEditable(false);
                updateButton.setEnabled(true);
                deleteButton.setEnabled(true);
            }
        });
    }

    void addEmployee() {

        try {

            if (!validateFields()) {
                return;
            }

            Employee emp = getEmployeeFromFields();

            dao.addEmployee(emp);

            JOptionPane.showMessageDialog(
                    this,
                    "Record added successfully"
            );

            clearFields();
            loadEmployees();

        } catch (SQLException e) {

            if (e.getErrorCode() == 1062) {

                JOptionPane.showMessageDialog(
                        this,
                        "Employee ID already exists. Please use a different ID.",
                        "Duplicate Employee ID",
                        JOptionPane.ERROR_MESSAGE
                );

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Database error: " + e.getMessage(),
                        "Database Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    void updateEmployee() {

        try {

            if (!validateFields()) {
                return;
            }

            Employee emp = getEmployeeFromFields();

            dao.updateEmployee(emp);

            JOptionPane.showMessageDialog(
                    this,
                    "Record updated successfully"
            );

            clearFields();
            loadEmployees();

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to update record.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    void deleteEmployee() {

        if (idField.getText().trim().isEmpty()) {
            return;
        }

        int choice = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete this employee?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
        );

        if (choice == JOptionPane.YES_OPTION) {

            try {

                int id = Integer.parseInt(idField.getText());

                dao.deleteEmployee(id);

                JOptionPane.showMessageDialog(
                        this,
                        "Record deleted successfully"
                );

                clearFields();
                loadEmployees();

            } catch (SQLException e) {

                JOptionPane.showMessageDialog(
                        this,
                        "Unable to delete record.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    void loadEmployees() {

        try {

            List<Employee> employees =
                    dao.getAllEmployees();

            model.setRowCount(0);

            for (Employee emp : employees) {

                model.addRow(new Object[]{
                        emp.getEmpId(),
                        emp.getEmpName(),
                        emp.getDepartment(),
                        emp.getDesignation(),
                        emp.getDateOfJoining(),
                        emp.getSalary(),
                        emp.getEmail(),
                        emp.getPhone(),
                        emp.getStatus()
                });
            }

            statusLabel.setText(
                    employees.size() + " records found"
            );

        } catch (SQLException e) {

            statusLabel.setText(
                    "Database error"
            );
        }
    }

    void searchEmployees() {

        try {

            String column =
                    searchBox.getSelectedItem().toString();

            String keyword =
                    searchField.getText().trim();

            List<Employee> employees =
                    dao.searchEmployees(column, keyword);

            model.setRowCount(0);

            for (Employee emp : employees) {

                model.addRow(new Object[]{
                        emp.getEmpId(),
                        emp.getEmpName(),
                        emp.getDepartment(),
                        emp.getDesignation(),
                        emp.getDateOfJoining(),
                        emp.getSalary(),
                        emp.getEmail(),
                        emp.getPhone(),
                        emp.getStatus()
                });
            }

            statusLabel.setText(
                    employees.size() + " records found"
            );

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Search failed.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    Employee getEmployeeFromFields() {

        int id = Integer.parseInt(idField.getText());

        double salary =
                Double.parseDouble(salaryField.getText());

        String status =
                activeRadio.isSelected()
                        ? "Active"
                        : "Inactive";

        return new Employee(
                id,
                nameField.getText().trim(),
                departmentBox.getSelectedItem().toString(),
                designationBox.getSelectedItem().toString(),
                dateField.getText().trim(),
                salary,
                emailField.getText().trim(),
                phoneField.getText().trim(),
                status
        );
    }

    boolean validateFields() {

        if (ValidationUtil.isEmpty(idField.getText()) ||
                ValidationUtil.isEmpty(nameField.getText()) ||
                ValidationUtil.isEmpty(dateField.getText()) ||
                ValidationUtil.isEmpty(salaryField.getText()) ||
                ValidationUtil.isEmpty(emailField.getText()) ||
                ValidationUtil.isEmpty(phoneField.getText())) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill all required fields."
            );

            return false;
        }

        try {

            Integer.parseInt(idField.getText());

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Employee ID must be a number."
            );

            return false;
        }

        if (!ValidationUtil.isValidSalary(
                salaryField.getText())) {

            JOptionPane.showMessageDialog(
                    this,
                    "Enter a valid salary."
            );

            return false;
        }
        if (!ValidationUtil.isValidDate(
                dateField.getText())) {

            JOptionPane.showMessageDialog(
                    this,
                    "Enter a valid date in YYYY-MM-DD format."
            );

            return false;
        }

        if (!ValidationUtil.isValidEmail(
                emailField.getText())) {

            JOptionPane.showMessageDialog(
                    this,
                    "Enter a valid email."
            );

            return false;
        }

        if (!ValidationUtil.isValidPhone(
                phoneField.getText())) {

            JOptionPane.showMessageDialog(
                    this,
                    "Phone number must contain 10 digits."
            );

            return false;
        }

        return true;
    }

    void clearFields() {

        idField.setText("");
        nameField.setText("");
        dateField.setText("");
        salaryField.setText("");
        emailField.setText("");
        phoneField.setText("");

        activeRadio.setSelected(true);

        idField.setEditable(true);

        updateButton.setEnabled(false);
        deleteButton.setEnabled(false);

        table.clearSelection();
    }

    void checkConnection() {

        try {

            DBConnection.getConnection();

            connectionLabel.setText(
                    "Database: CONNECTED"
            );

        } catch (SQLException e) {

            connectionLabel.setText(
                    "Database: NOT CONNECTED"
            );
        }
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            EmployeeGUI gui =
                    new EmployeeGUI();

            gui.setVisible(true);
        });
    }
}