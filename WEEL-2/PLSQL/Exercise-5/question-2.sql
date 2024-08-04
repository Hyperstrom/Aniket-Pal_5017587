-- Connect to the database as SYSDBA and grant necessary quota
CONNECT SYS/"@NI_18_art"@localhost:1521/orcl AS SYSDBA;
ALTER USER C##hr QUOTA UNLIMITED ON USERS;
GRANT EXECUTE ON DBMS_LOCK TO C##hr;
CONNECT C##hr/"hr_password"@localhost:1521/orcl;

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