CREATE OR REPLACE PACKAGE CustomerManagement AS
  -- Procedure to add a new customer
  PROCEDURE AddCustomer(p_name VARCHAR2, p_dob DATE, p_balance NUMBER, p_isvip VARCHAR2);
  
  -- Procedure to update customer details
  PROCEDURE UpdateCustomer(p_customerid NUMBER, p_name VARCHAR2, p_dob DATE, p_balance NUMBER, p_isvip VARCHAR2);
  
  -- Function to get customer balance
  FUNCTION GetCustomerBalance(p_customerid NUMBER) RETURN NUMBER;
END CustomerManagement;
/


--CREATE the PACKAGE --Create a new Package Body
CREATE OR REPLACE PACKAGE BODY CustomerManagement AS
  PROCEDURE AddCustomer(p_name VARCHAR2, p_dob DATE, p_balance NUMBER, p_isvip VARCHAR2) IS
  BEGIN
    INSERT INTO Customers (NAME, DOB, BALANCE, LASTMODIFIED, ISVIP)
    VALUES (p_name, p_dob, p_balance, SYSDATE, p_isvip);
  END AddCustomer;

  PROCEDURE UpdateCustomer(p_customerid NUMBER, p_name VARCHAR2, p_dob DATE, p_balance NUMBER, p_isvip VARCHAR2) IS
  BEGIN
    UPDATE Customers
    SET NAME = p_name,
        DOB = p_dob,
        BALANCE = p_balance,
        LASTMODIFIED = SYSDATE,
        ISVIP = p_isvip
    WHERE CUSTOMERID = p_customerid;
  END UpdateCustomer;

  FUNCTION GetCustomerBalance(p_customerid NUMBER) RETURN NUMBER IS
    v_balance NUMBER;
  BEGIN
    SELECT BALANCE INTO v_balance
    FROM Customers
    WHERE CUSTOMERID = p_customerid;
    RETURN v_balance;
  END GetCustomerBalance;
END CustomerManagement;
/

-- Test the Package 

-- Test adding a new customer
BEGIN
  CustomerManagement.AddCustomer('Bruce Banner', TO_DATE('22-DEC-69', 'DD-MON-YY'), 1200, 'NO');
  DBMS_OUTPUT.PUT_LINE('Customer added successfully.');
EXCEPTION
  WHEN OTHERS THEN
    DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
END;
/

-- Test updating a customer
SET SERVEROUT ON;

BEGIN
  CustomerManagement.UpdateCustomer(1, 'John Doe', TO_DATE('15-MAY-85', 'DD-MON-YY'), 1000, 'NO');
  DBMS_OUTPUT.PUT_LINE('Customer updated successfully.');
EXCEPTION
  WHEN OTHERS THEN
    DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
END;
/

-- Test getting customer balance
DECLARE
  v_balance NUMBER;
BEGIN
  v_balance := CustomerManagement.GetCustomerBalance(1);
  DBMS_OUTPUT.PUT_LINE('Customer balance: ' || v_balance);
EXCEPTION
  WHEN OTHERS THEN
    DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
END;
/