SET SERVEROUT ON;

CREATE OR REPLACE PROCEDURE UpdateSalary (
    p_EmployeeID IN Employees.EmployeeID%TYPE,
    p_Percentage IN NUMBER
) IS
    v_CurrentSalary Employees.Salary%TYPE;
BEGIN
    BEGIN
        -- Check if employee exists
        SELECT Salary INTO v_CurrentSalary
        FROM Employees
        WHERE EmployeeID = p_EmployeeID;

        -- Update salary
        UPDATE Employees
        SET Salary = Salary + (Salary * p_Percentage / 100)
        WHERE EmployeeID = p_EmployeeID;

        -- Commit the transaction
        COMMIT;
        
        DBMS_OUTPUT.PUT_LINE('Salary updated successfully for Employee ID: ' || p_EmployeeID);
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            DBMS_OUTPUT.PUT_LINE('Error: Employee ID ' || p_EmployeeID || ' does not exist.');
            ROLLBACK;
        WHEN OTHERS THEN
            DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
            ROLLBACK;
    END;
END UpdateSalary;
/

BEGIN
    -- Update salary of EmployeeID 1 by 5%
    UpdateSalary(1, 5);
    
    -- Update salary of EmployeeID 3 by 8%
    UpdateSalary(3, 8);
    
    -- Update salary of EmployeeID 4 by 3%
    UpdateSalary(4, 3);
END;
/