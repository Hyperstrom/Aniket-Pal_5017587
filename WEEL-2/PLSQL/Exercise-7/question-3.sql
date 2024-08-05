--Create the Package Specification
CREATE OR REPLACE PACKAGE AccountOperations AS
  -- Procedure to open a new account
  PROCEDURE OpenAccount(p_customerid NUMBER, p_accounttype VARCHAR2, p_balance NUMBER);
  
  -- Procedure to close an account
  PROCEDURE CloseAccount(p_accountid NUMBER);
  
  -- Function to get total balance of a customer across all accounts
  FUNCTION GetTotalBalance(p_customerid NUMBER) RETURN NUMBER;
END AccountOperations;
/

--Create the Package Body
CREATE OR REPLACE PACKAGE BODY AccountOperations AS
  PROCEDURE OpenAccount(p_customerid NUMBER, p_accounttype VARCHAR2, p_balance NUMBER) IS
  BEGIN
    INSERT INTO Accounts (CUSTOMERID, ACCOUNTTYPE, BALANCE, LASTMODIFIED)
    VALUES (p_customerid, p_accounttype, p_balance, SYSDATE);
  END OpenAccount;

  PROCEDURE CloseAccount(p_accountid NUMBER) IS
  BEGIN
    DELETE FROM Accounts
    WHERE ACCOUNTID = p_accountid;
  END CloseAccount;

  FUNCTION GetTotalBalance(p_customerid NUMBER) RETURN NUMBER IS
    v_total_balance NUMBER;
  BEGIN
    SELECT SUM(BALANCE) INTO v_total_balance
    FROM Accounts
    WHERE CUSTOMERID = p_customerid;
    RETURN v_total_balance;
  END GetTotalBalance;
END AccountOperations;
/

--Test the Package
-- Test opening a new account
BEGIN
  AccountOperations.OpenAccount(1, 'Checking', 5000);
  DBMS_OUTPUT.PUT_LINE('Account opened successfully.');
EXCEPTION
  WHEN OTHERS THEN
    DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
END;
/

SET SERVEROUT ON;

-- Test closing an account
BEGIN
  AccountOperations.CloseAccount(1);
  DBMS_OUTPUT.PUT_LINE('Account closed successfully.');
EXCEPTION
  WHEN OTHERS THEN
    DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
END;
/

-- Test getting total balance of a customer
DECLARE
  v_total_balance NUMBER;
BEGIN
  v_total_balance := AccountOperations.GetTotalBalance(1);
  DBMS_OUTPUT.PUT_LINE('Total balance: ' || v_total_balance);
EXCEPTION
  WHEN OTHERS THEN
    DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
END;
/
