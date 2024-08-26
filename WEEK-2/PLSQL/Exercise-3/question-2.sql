SET SERVEROUTPUT ON;

CREATE OR REPLACE PROCEDURE UpdateEmployeeBonus(
    p_department VARCHAR2,
    p_bonus_percentage NUMBER
) AS
BEGIN
    UPDATE Employees
    SET Salary = Salary + (Salary * (p_bonus_percentage / 100))
    WHERE Department = p_department;
   
    DBMS_OUTPUT.PUT_LINE('Bonuses updated for ' || SQL%ROWCOUNT || ' employees in ' || p_department || ' department.');
    COMMIT;
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('Error updating employee bonuses: ' || SQLERRM);
END UpdateEmployeeBonus;
/


BEGIN
    -- Test updating bonuses for the IT department by 10%
    UpdateEmployeeBonus('IT', 10);
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
END;
/

-- Verify the updated salaries for the IT department
SELECT * FROM Employees WHERE Department = 'IT';
