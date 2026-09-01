# Employee Information Management System

## Project Description

The Employee Information Management System is a desktop-based application developed using Java Swing and MySQL. The application provides a graphical user interface for managing employee information through database connectivity using JDBC.

The system allows users to add, update, delete, search, and view employee records from a single GUI screen. It also provides input validation, duplicate employee ID checking, confirmation dialogs, dynamic search, and database transaction management.

## Technologies Used

- Java
- Java Swing
- JDBC
- MySQL
- IntelliJ IDEA
- MySQL Connector/J

## Database

Database Name: employee_db

Table Name: employee

The employee table contains:

- emp_id
- emp_name
- department
- designation
- date_of_joining
- salary
- email
- phone
- status

## Main Features

- Add employee records
- Update employee records
- Delete employee records
- Search employees
- Dynamic search using partial matches
- Display records using JTable
- Row selection for editing
- Input validation
- Email and phone validation
- Duplicate employee ID handling
- Delete confirmation
- JDBC database connectivity
- PreparedStatement for SQL queries
- Commit and rollback transaction management
- Database connection status
- User-friendly error messages

## Project Structure

src/
- DBConnection.java
- Employee.java
- EmployeeDAO.java
- EmployeeGUI.java
- ValidationUtil.java
- Main.java
- TestConnection.java

employee_database.sql

## Architecture

The application follows an MVC-lite architecture.

### GUI Layer

EmployeeGUI.java provides the graphical interface using Java Swing components such as JFrame, JPanel, JTextField, JComboBox, JButton, JTable, and JOptionPane.

### Model Layer

Employee.java represents the employee data and contains the employee attributes.

### DAO Layer

EmployeeDAO.java handles database operations such as insert, update, delete, search, and retrieving employee records.

### Database Layer

DBConnection.java manages the JDBC connection between the Java application and MySQL database.

### Validation Layer

ValidationUtil.java validates user input such as employee name, email, phone, salary, and date.

## JDBC Security

PreparedStatement is used for database operations to reduce the risk of SQL injection.

## Transaction Management

Update and delete operations use database transactions. Successful operations are committed, while database errors are rolled back to maintain data consistency.

## How to Run

1. Install Java JDK.
2. Install MySQL.
3. Create the employee_db database.
4. Execute employee_database.sql in MySQL Workbench.
5. Configure the MySQL username and password in DBConnection.java.
6. Add MySQL Connector/J to the project.
7. Run EmployeeGUI.java.

## Conclusion

The Employee Information Management System demonstrates how Java Swing can be integrated with a relational database using JDBC. The application provides a simple and user-friendly interface for performing complete CRUD operations while maintaining validation, security, and database consistency.