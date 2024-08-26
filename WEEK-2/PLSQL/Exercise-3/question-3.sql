SHOW ERRORS PROCEDURE TransferFunds;

CREATE OR REPLACE PROCEDURE TransferFunds(
    p_from_account NUMBER,
    p_to_account NUMBER,
    p_amount NUMBER
) AS
    v_from_balance NUMBER;
    insufficient_funds EXCEPTION;
BEGIN
    -- Check if source account has sufficient balance
    SELECT Balance INTO v_from_balance
    FROM Accounts
    WHERE AccountID = p_from_account;
   
    IF v_from_balance < p_amount THEN
        RAISE insufficient_funds;
    END IF;
   
    -- Deduct from source account
    UPDATE Accounts
    SET Balance = Balance - p_amount,
        LastModified = SYSDATE
    WHERE AccountID = p_from_account;
   
    -- Add to destination account
    UPDATE Accounts
    SET Balance = Balance + p_amount,
        LastModified = SYSDATE
    WHERE AccountID = p_to_account;
   
    -- Record the transaction
    INSERT INTO Transactions (TransactionID, AccountID, TransactionDate, Amount, TransactionType)
    VALUES (TransactionID_SEQ.NEXTVAL, p_from_account, SYSDATE, -p_amount, 'Transfer');
   
    INSERT INTO Transactions (TransactionID, AccountID, TransactionDate, Amount, TransactionType)
    VALUES (TransactionID_SEQ.NEXTVAL, p_to_account, SYSDATE, p_amount, 'Transfer');
   
    COMMIT;
    DBMS_OUTPUT.PUT_LINE('Funds transferred successfully.');
EXCEPTION
    WHEN insufficient_funds THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('Error: Insufficient funds in the source account.');
    WHEN OTHERS THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('Error transferring funds: ' || SQLERRM);
END TransferFunds;
/

-- Add sample accounts if not already present
BEGIN
  INSERT INTO Accounts (AccountID, CustomerID, AccountType, Balance, LastModified) VALUES (1, 1, 'Checking', 1000, SYSDATE);
  INSERT INTO Accounts (AccountID, CustomerID, AccountType, Balance, LastModified) VALUES (2, 1, 'Savings', 2000, SYSDATE);
  COMMIT;
END;
/

-- Create a sequence for TransactionID if not already present
CREATE SEQUENCE TransactionID_SEQ START WITH 1 INCREMENT BY 1;

-- Test transferring funds with sufficient balance
BEGIN
  TransferFunds(1, 2, 500);
END;
/

-- Test transferring funds with insufficient balance
BEGIN
  TransferFunds(1, 2, 1500);
END;
/

-- Verify the updated balances in the Accounts table
SELECT * FROM Accounts WHERE AccountID IN (1, 2);

-- Verify the transactions recorded
SELECT * FROM Transactions WHERE AccountID IN (1, 2);

