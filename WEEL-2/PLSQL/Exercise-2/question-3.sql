CREATE OR REPLACE PROCEDURE AddNewCustomer (
    p_CustomerID IN Customers.CustomerID%TYPE,
    p_Name IN Customers.Name%TYPE,
    p_DOB IN Customers.DOB%TYPE,
    p_Balance IN Customers.Balance%TYPE
) IS
BEGIN
    -- Insert new customer
    INSERT INTO Customers (CustomerID, Name, DOB, Balance, LastModified)
    VALUES (p_CustomerID, p_Name, p_DOB, p_Balance, SYSDATE);

    -- Commit the transaction
    COMMIT;

    DBMS_OUTPUT.PUT_LINE('Customer added successfully with Customer ID: ' || p_CustomerID);

EXCEPTION
    WHEN DUP_VAL_ON_INDEX THEN
        DBMS_OUTPUT.PUT_LINE('Error: Customer ID ' || p_CustomerID || ' already exists.');
        ROLLBACK;
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
        ROLLBACK;
END AddNewCustomer;
/

SET SERVEROUT ON;

-- Call to add the first new customer
BEGIN
    AddNewCustomer(3, 'Michael Johnson', TO_DATE('1982-09-17', 'YYYY-MM-DD'), 20000);
END;
/

-- Call to add the second new customer
BEGIN
    AddNewCustomer(6, 'Sarah Williams', TO_DATE('1975-11-23', 'YYYY-MM-DD'), 25600);
END;
/