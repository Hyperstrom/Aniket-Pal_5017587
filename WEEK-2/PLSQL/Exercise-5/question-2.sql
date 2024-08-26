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

ALTER USER &&DB_USER QUOTA UNLIMITED ON USERS;
CONNECT &&DB_USER/&&DB_PASSWORD@&&DB_HOST
/*3.in the &&DB_PASSWORD paste the password of DB_USER*/

-- create the tables for the TRIGGER
CREATE TABLE AuditLog (
    AuditID NUMBER PRIMARY KEY,
    TransactionID NUMBER,
    AccountID NUMBER,
    TransactionDate DATE,
    Amount NUMBER,
    TransactionType VARCHAR2(10),
    LogTimestamp DATE
);

CREATE TABLE Transactions (
    TransactionID NUMBER PRIMARY KEY,
    AccountID NUMBER,
    TransactionDate DATE,
    Amount NUMBER,
    TransactionType VARCHAR2(10)
);

-- Create Sequence for AuditLog
CREATE SEQUENCE AuditLog_seq
START WITH 1
INCREMENT BY 1;

-- Create Trigger
CREATE OR REPLACE TRIGGER LogTransaction
AFTER INSERT ON Transactions
FOR EACH ROW
BEGIN
    INSERT INTO AuditLog (AuditID, TransactionID, AccountID, TransactionDate, Amount, TransactionType, LogTimestamp)
    VALUES (
        AuditLog_seq.NEXTVAL,
        :NEW.TransactionID,
        :NEW.AccountID,
        :NEW.TransactionDate,
        :NEW.Amount,
        :NEW.TransactionType,
        SYSDATE
    );
END;
/

INSERT INTO Transactions (TransactionID, AccountID, TransactionDate, Amount, TransactionType)
VALUES (1, 123, SYSDATE, 500, 'Deposit');

INSERT INTO Transactions (TransactionID, AccountID, TransactionDate, Amount, TransactionType)
VALUES (2, 124, SYSDATE, 300, 'Withdrawal');

INSERT INTO Transactions (TransactionID, AccountID, TransactionDate, Amount, TransactionType)
VALUES (3, 125, SYSDATE, 1000, 'Deposit');

INSERT INTO Transactions (TransactionID, AccountID, TransactionDate, Amount, TransactionType)
VALUES (4, 126, SYSDATE, 450, 'Withdrawal');

-- Verify AuditLog Table
SELECT * FROM AuditLog;
SELECT * FROM Transactions;