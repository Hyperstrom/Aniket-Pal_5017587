CREATE OR REPLACE FUNCTION HasSufficientBalance (
    p_AccountID IN ACCOUNTS.ACCOUNTID%TYPE,
    p_Amount IN NUMBER
) RETURN BOOLEAN IS
    v_Balance ACCOUNTS.BALANCE%TYPE;
BEGIN
    -- Get the balance of the specified account
    SELECT BALANCE INTO v_Balance
    FROM ACCOUNTS
    WHERE ACCOUNTID = p_AccountID;
    
    -- Check if the balance is greater than or equal to the specified amount
    IF v_Balance >= p_Amount THEN
        RETURN TRUE;
    ELSE
        RETURN FALSE;
    END IF;
EXCEPTION
    WHEN NO_DATA_FOUND THEN
        -- If the account does not exist, return FALSE
        RETURN FALSE;
END;

SET SERVEROUT ON;

DECLARE
    v_AccountID ACCOUNTS.ACCOUNTID%TYPE;
    v_Amount NUMBER := 1000; -- Example amount to check
    v_HasSufficientBalance BOOLEAN;
BEGIN
    -- Loop through accounts to test the function
    FOR account IN (SELECT ACCOUNTID, BALANCE FROM ACCOUNTS) LOOP
        v_AccountID := account.ACCOUNTID;
        
        -- Call the function
        v_HasSufficientBalance := HasSufficientBalance(v_AccountID, v_Amount);
        
        -- Display the result
        DBMS_OUTPUT.PUT_LINE('Account ID: ' || v_AccountID ||
                             ', Balance: ' || account.BALANCE ||
                             ', Has sufficient balance for ' || v_Amount || ': ' || 
                             CASE WHEN v_HasSufficientBalance THEN 'YES' ELSE 'NO' END);
    END LOOP;
END;
