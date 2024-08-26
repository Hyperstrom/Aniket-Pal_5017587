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

-- create the tables for the TRIGGER
CREATE TABLE Accounts (
    AccountID NUMBER PRIMARY KEY,
    CustomerID NUMBER,
    AccountType VARCHAR2(20),
    Balance NUMBER,
    LastModified DATE
);

-- make the Transactions table 

CREATE TABLE Transactions (
    TransactionID NUMBER PRIMARY KEY,
    TransactionType VARCHAR2(20),
    Amount NUMBER,
    AccountID NUMBER,
    FOREIGN KEY (AccountID) REFERENCES Accounts(AccountID)
);

INSERT INTO Accounts (AccountID, CustomerID, AccountType, Balance, LastModified) VALUES
(1, 101, 'Savings', 500, TO_DATE('2023-05-15', 'YYYY-MM-DD'));
INSERT INTO Accounts (AccountID, CustomerID, AccountType, Balance, LastModified) VALUES
 2, 102, 'Checking', 1000, TO_DATE('2024-03-20', 'YYYY-MM-DD'));

SELECT * FROM ACCOUNTS;

SET SERVEROUT ON;
CREATE OR REPLACE TRIGGER CheckTransactionRules
BEFORE INSERT ON Transactions
FOR EACH ROW
DECLARE
    current_balance NUMBER;
BEGIN
    -- Check if the transaction is a deposit or withdrawal
    IF :NEW.transactiontype = 'withdrawal' THEN
        -- Get the current balance of the account
        SELECT balance INTO current_balance
        FROM Accounts
        WHERE accountid = :NEW.accountid;

        -- Ensure that the withdrawal does not exceed the current balance
        IF :NEW.amount > current_balance THEN
            RAISE_APPLICATION_ERROR(-20001, 'Insufficient balance for the withdrawal.');
        END IF;

    ELSIF :NEW.transactiontype = 'deposit' THEN
        -- Ensure that the deposit amount is positive
        IF :NEW.amount <= 0 THEN
            RAISE_APPLICATION_ERROR(-20002, 'Deposit amount must be positive.');
        END IF;
    END IF;
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE(SQLERRM);
        RAISE;
END;

INSERT INTO Transactions (TransactionID, TransactionType, Amount, AccountID)
VALUES (1, 'deposit', 200, 1);

INSERT INTO Transactions (TransactionID, TransactionType, Amount, AccountID)
VALUES (2, 'withdrawal', 100, 1);

INSERT INTO Transactions (TransactionID, TransactionType, Amount, AccountID)
VALUES (3, 'withdrawal', 700, 1);
-- This should raise an error: ORA-20001: Insufficient balance for the withdrawal.

INSERT INTO Transactions (TransactionID, TransactionType, Amount, AccountID)
VALUES (4, 'deposit', -50, 1);
-- This should raise an error: ORA-20002: Deposit amount must be positive.

SELECT * FROM Accounts;
SELECT * FROM TRANSACTIONS;