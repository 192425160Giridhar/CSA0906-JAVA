CREATE DATABASE IF NOT EXISTS employee_db;

USE employee_db;

CREATE TABLE IF NOT EXISTS employee (
                                        emp_id INT PRIMARY KEY,
                                        emp_name VARCHAR(100) NOT NULL,
    department VARCHAR(50) NOT NULL,
    designation VARCHAR(50) NOT NULL,
    date_of_joining DATE NOT NULL,
    salary DECIMAL(10,2) NOT NULL,
    email VARCHAR(100) NOT NULL,
    phone VARCHAR(15) NOT NULL,
    status VARCHAR(10) NOT NULL
    );

INSERT INTO employee
(emp_id, emp_name, department, designation, date_of_joining, salary, email, phone, status)
VALUES
    (101, 'Rahul Kumar', 'IT', 'Developer', '2024-01-15', 50000.00, 'rahul@gmail.com', '9876543210', 'Active'),
    (102, 'Priya Sharma', 'HR', 'Manager', '2023-06-20', 60000.00, 'priya@gmail.com', '9876543211', 'Active'),
    (103, 'Arun Kumar', 'Finance', 'Accountant', '2022-09-10', 55000.00, 'arun@gmail.com', '9876543212', 'Active'),
    (104, 'Sneha Reddy', 'IT', 'Tester', '2024-03-05', 48000.00, 'sneha@gmail.com', '9876543213', 'Active');