SET SERVEROUT ON;

DECLARE
    CURSOR ApplyAnnualFee IS
        SELECT ACCOUNTID, BALANCE
        FROM ACCOUNTS
        FOR UPDATE OF BALANCE;

    v_account_id ACCOUNTS.ACCOUNTID%TYPE;
    v_balance ACCOUNTS.BALANCE%TYPE;
    v_annual_fee CONSTANT NUMBER := 50; -- Set the annual fee amount here
BEGIN
    OPEN ApplyAnnualFee;

    LOOP
        FETCH ApplyAnnualFee INTO v_account_id, v_balance;
        EXIT WHEN ApplyAnnualFee%NOTFOUND;

        -- Deduct the annual fee from the balance
        v_balance := v_balance - v_annual_fee;

        -- Update the balance in the ACCOUNTS table
        UPDATE ACCOUNTS
        SET BALANCE = v_balance
        WHERE CURRENT OF ApplyAnnualFee;

        -- Optional: Log the fee deduction
        DBMS_OUTPUT.PUT_LINE('Account ID: ' || v_account_id || ' - New Balance: ' || v_balance);
    END LOOP;

    CLOSE ApplyAnnualFee;
END;
/
