--IMPORTANT MASSAGE 

/*Because the user is SYSTEM so cannot create triggers on objects owned by the SYS user.
It Rejects the Trigger 

To resolve this, you should ensure that you are creating the trigger in a user schema rather than the SYS schema.
1. Connect to non-SYS user Schema: In this case I connect to *DB_USER*
2. create the tables for the TRIGGER 
3. create the TRIGGER */

-- Connect to the database as SYSDBA and grant necessary quota
CONNECT SYS/&&ORCL_PASSWORD@&&DB_HOST AS SYSDBA;
/*1. in the &&ORCL_PASSWORD paste your sys password as a String input
2. in the &&DB_HOST paste the Database host */

-- Grant execute permission on DBMS_LOCK to the user
GRANT EXECUTE ON DBMS_LOCK TO &&DB_USER

ALTER USER &&DB_USER QUOTA UNLIMITED ON USERS;
CONNECT &&DB_USER/&&DB_PASSWORD@&&DB_HOST
/*3.in the &&DB_PASSWORD paste the password of DB_USER*/


-- Create the Customer table (if not already created)
CREATE TABLE Customer (
    CustomerID NUMBER PRIMARY KEY,
    CustomerName VARCHAR2(100),
    LastModified DATE
);

-- Create the trigger to update the LastModified column
CREATE OR REPLACE TRIGGER UpdateCustomerLastModified
BEFORE UPDATE ON Customer
FOR EACH ROW
BEGIN
    :NEW.LastModified := SYSDATE;
END;
/

-- Insert multiple customer records
INSERT INTO Customer (CustomerID, CustomerName, LastModified)
VALUES (1, 'John Doe', TO_DATE('2015-06-15', 'YYYY-MM-DD'));
INSERT INTO Customer (CustomerID, CustomerName, LastModified)
VALUES (2, 'Jane Smith', TO_DATE('2018-07-25', 'YYYY-MM-DD'));
INSERT INTO Customer (CustomerID, CustomerName, LastModified)
VALUES (3, 'Alice Johnson', TO_DATE('2015-10-15', 'YYYY-MM-DD'));
INSERT INTO Customer (CustomerID, CustomerName, LastModified)
VALUES (4, 'Bob Brown', TO_DATE('2020-09-10', 'YYYY-MM-DD'));

-- Verify inserted records
SELECT * FROM Customer;

-- Enable output for print statements
SET SERVEROUT ON;


-- Update customer records and print statements
BEGIN
    UPDATE Customer
    SET CustomerName = 'Johnathan Doe'
    WHERE CustomerID = 1;
    DBMS_OUTPUT.PUT_LINE('CustomerID 1 name changed to Johnathan Doe');

    UPDATE Customer
    SET CustomerName = 'Janet Smith'
    WHERE CustomerID = 2;
    DBMS_OUTPUT.PUT_LINE('CustomerID 2 name changed to Janet Smith');
END;
/

-- Verify the LastModified column before and after the updates
SELECT CustomerID, CustomerName, LastModified
FROM Customer;

-- Wait a few seconds to show the difference in LastModified timestamps
BEGIN
    DBMS_LOCK.SLEEP(2);
END;
/

-- Update another customer record to see a different timestamp
BEGIN
    UPDATE Customer
    SET CustomerName = 'Alicia Johnson'
    WHERE CustomerID = 3;
    DBMS_OUTPUT.PUT_LINE('CustomerID 3 name changed to Alicia Johnson');
END;
/

-- Verify the LastModified column after the last update
SELECT CustomerID, CustomerName, LastModified
FROM Customer;
