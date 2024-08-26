--Create the Package Specification
CREATE OR REPLACE PACKAGE EmployeeManagement AS
  -- Procedure to hire a new employee
  PROCEDURE HireEmployee(p_name VARCHAR2, p_position VARCHAR2, p_salary NUMBER, p_department VARCHAR2, p_hiredate DATE);
  
  -- Procedure to update employee details
  PROCEDURE UpdateEmployee(p_employeeid NUMBER, p_name VARCHAR2, p_position VARCHAR2, p_salary NUMBER, p_department VARCHAR2);
  
  -- Function to calculate annual salary
  FUNCTION CalculateAnnualSalary(p_employeeid NUMBER) RETURN NUMBER;
END EmployeeManagement;
/

--Create the Package Body
CREATE OR REPLACE PACKAGE BODY EmployeeManagement AS
  PROCEDURE HireEmployee(p_name VARCHAR2, p_position VARCHAR2, p_salary NUMBER, p_department VARCHAR2, p_hiredate DATE) IS
  BEGIN
    INSERT INTO Employees (NAME, POSITION, SALARY, DEPARTMENT, HIREDATE)
    VALUES (p_name, p_position, p_salary, p_department, p_hiredate);
  END HireEmployee;

  PROCEDURE UpdateEmployee(p_employeeid NUMBER, p_name VARCHAR2, p_position VARCHAR2, p_salary NUMBER, p_department VARCHAR2) IS
  BEGIN
    UPDATE Employees
    SET NAME = p_name,
        POSITION = p_position,
        SALARY = p_salary,
        DEPARTMENT = p_department
    WHERE EMPLOYEEID = p_employeeid;
  END UpdateEmployee;

  FUNCTION CalculateAnnualSalary(p_employeeid NUMBER) RETURN NUMBER IS
    v_annual_salary NUMBER;
  BEGIN
    SELECT SALARY * 12 INTO v_annual_salary
    FROM Employees
    WHERE EMPLOYEEID = p_employeeid;
    RETURN v_annual_salary;
  END CalculateAnnualSalary;
END EmployeeManagement;
/

-- Test the Package 
SET SERVEROUT ON;

-- Test hiring a new employee
BEGIN
  EmployeeManagement.HireEmployee('Bruce Banner', 'Scientist', 85000, 'Research', TO_DATE('22-DEC-20', 'DD-MON-YY'));
  DBMS_OUTPUT.PUT_LINE('Employee hired successfully.');
EXCEPTION
  WHEN OTHERS THEN
    DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
END;
/

-- Test updating an employee
BEGIN
  EmployeeManagement.UpdateEmployee(1, 'Alice Johnson', 'Senior Manager', 90000, 'HR');
  DBMS_OUTPUT.PUT_LINE('Employee updated successfully.');
EXCEPTION
  WHEN OTHERS THEN
    DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
END;
/

-- Test calculating annual salary
DECLARE
  v_annual_salary NUMBER;
BEGIN
  v_annual_salary := EmployeeManagement.CalculateAnnualSalary(1);
  DBMS_OUTPUT.PUT_LINE('Annual salary: ' || v_annual_salary);
EXCEPTION
  WHEN OTHERS THEN
    DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
END;
/

-- Check the DBMS_OUTPUT

-- Verify the employee hired
SELECT * FROM Employees WHERE NAME = 'Bruce Banner';

-- Verify the employee updated
SELECT * FROM Employees WHERE EMPLOYEEID = 1;

-- Verify the annual salary
SELECT SALARY * 12 AS ANNUAL_SALARY FROM Employees WHERE EMPLOYEEID = 1;

