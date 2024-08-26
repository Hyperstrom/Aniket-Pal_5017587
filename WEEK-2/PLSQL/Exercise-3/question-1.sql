SET SERVEROUT ON;

CREATE OR REPLACE PROCEDURE ProcessMonthlyInterest AS
    CURSOR savings_accounts IS
        SELECT AccountID, Balance
        FROM Accounts
        WHERE AccountType = 'Savings';
    v_interest_rate CONSTANT NUMBER := 0.01; -- 1% interest rate
BEGIN
    FOR acc IN savings_accounts LOOP
        UPDATE Accounts
        SET Balance = Balance + (Balance * v_interest_rate),
            LastModified = SYSDATE
        WHERE AccountID = acc.AccountID;
    END LOOP;
    COMMIT;
    DBMS_OUTPUT.PUT_LINE('Monthly interest processed for all savings accounts.');
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('Error processing monthly interest: ' || SQLERRM);
END ProcessMonthlyInterest;
/

BEGIN
  ProcessMonthlyInterest;
END;
/

-- Verify the data in the Accounts table
SELECT * FROM Accounts WHERE AccountType = 'Savings';