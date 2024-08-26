CREATE OR REPLACE FUNCTION CalculateAge (
    p_DOB IN DATE
) RETURN NUMBER
IS
    v_Age NUMBER;
BEGIN
    -- Calculate age by subtracting the year of birth from the current year
    v_Age := TRUNC(MONTHS_BETWEEN(SYSDATE, p_DOB) / 12);
    
    RETURN v_Age;
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
        RETURN NULL;
END CalculateAge;
/

SET SERVEROUT ON;

DECLARE
    CURSOR cust_cursor IS
        SELECT CustomerID, Name, DOB
        FROM Customers;
        
    v_CustomerID Customers.CustomerID%TYPE;
    v_Name Customers.Name%TYPE;
    v_DOB Customers.DOB%TYPE;
    v_Age NUMBER;
BEGIN
    -- Open the cursor
    OPEN cust_cursor;
    
    -- Loop through each customer
    LOOP
        FETCH cust_cursor INTO v_CustomerID, v_Name, v_DOB;
        EXIT WHEN cust_cursor%NOTFOUND;
        
        -- Calculate age using the CalculateAge function
        v_Age := CalculateAge(v_DOB);
        
        -- Display customer details and calculated age
        DBMS_OUTPUT.PUT_LINE('|| Customer ID: ' || v_CustomerID || ',|| Name: ' || v_Name || ',|| DOB: ' || TO_CHAR(v_DOB, 'YYYY-MM-DD') || ',|| Age: ' || v_Age||' ||');
    END LOOP;
    
    -- Close the cursor
    CLOSE cust_cursor;
END;
/

