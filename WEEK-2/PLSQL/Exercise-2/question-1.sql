SET SERVEROUT ON;

CREATE OR REPLACE PROCEDURE SafeTransferFunds (
    p_source_account_id IN NUMBER,
    p_target_account_id IN NUMBER,
    p_amount IN NUMBER
) IS
    insufficient_funds EXCEPTION;
    invalid_account EXCEPTION;
BEGIN
    -- Check if source account has sufficient balance
    DECLARE
        v_source_balance NUMBER;
    BEGIN
        SELECT balance INTO v_source_balance
        FROM Accounts
        WHERE AccountID = p_source_account_id
        FOR UPDATE;

        IF v_source_balance < p_amount THEN
            RAISE insufficient_funds;
        END IF;
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            RAISE invalid_account;
    END;

    -- Deduct the amount from the source account
    UPDATE Accounts
    SET Balance = Balance - p_amount
    WHERE AccountID = p_source_account_id;

    -- Add the amount to the target account
    UPDATE Accounts
    SET Balance = Balance + p_amount
    WHERE AccountID = p_target_account_id;

    -- Commit the transaction
    COMMIT;

EXCEPTION
    WHEN insufficient_funds THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('Error: Insufficient funds in the source account.');
    WHEN invalid_account THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('Error: Invalid account ID.');
    WHEN OTHERS THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
END SafeTransferFunds;
/

BEGIN
    SafeTransferFunds(1, 2, 200);
    SafeTransferFunds(3,1,400);
END;
/

-- Query to check the balances after the procedure calls
SELECT * FROM Accounts WHERE AccountID IN (1, 2);
SELECT * FROM Accounts WHERE AccountID IN (3, 1);