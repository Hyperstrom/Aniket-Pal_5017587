SET SERVEROUT ON;

DECLARE
    CURSOR GenerateMonthlyStatements IS
        SELECT a.CUSTOMERID, t.ACCOUNTID, t.TRANSACTIONDATE, t.AMOUNT, t.TRANSACTIONTYPE
        FROM TRANSACTIONS t
        JOIN ACCOUNTS a ON t.ACCOUNTID = a.ACCOUNTID
        WHERE TO_CHAR(t.TRANSACTIONDATE, 'YYYY-MM') = TO_CHAR(SYSDATE, 'YYYY-MM')
        ORDER BY a.CUSTOMERID, t.TRANSACTIONDATE;

    v_customer_id ACCOUNTS.CUSTOMERID%TYPE;
    v_account_id TRANSACTIONS.ACCOUNTID%TYPE;
    v_transaction_date TRANSACTIONS.TRANSACTIONDATE%TYPE;
    v_amount TRANSACTIONS.AMOUNT%TYPE;
    v_transaction_type TRANSACTIONS.TRANSACTIONTYPE%TYPE;

    v_current_customer_id ACCOUNTS.CUSTOMERID%TYPE := NULL;
    v_total_amount NUMBER := 0;
BEGIN
    OPEN GenerateMonthlyStatements;

    LOOP
        FETCH GenerateMonthlyStatements INTO v_customer_id, v_account_id, v_transaction_date, v_amount, v_transaction_type;
        EXIT WHEN GenerateMonthlyStatements%NOTFOUND;

        -- Check if we are processing a new customer
        IF v_current_customer_id IS NULL OR v_current_customer_id != v_customer_id THEN
            -- Print the statement for the previous customer, if any
            IF v_current_customer_id IS NOT NULL THEN
                DBMS_OUTPUT.PUT_LINE('Customer ID: ' || v_current_customer_id);
                DBMS_OUTPUT.PUT_LINE('Total Amount: ' || v_total_amount);
                DBMS_OUTPUT.PUT_LINE('--------------------------');
            END IF;

            -- Reset totals for the new customer
            v_current_customer_id := v_customer_id;
            v_total_amount := 0;
        END IF;

        -- Print each transaction detail
        DBMS_OUTPUT.PUT_LINE('Account ID: ' || v_account_id || 
                             ', Date: ' || TO_CHAR(v_transaction_date, 'DD-MON-YY') || 
                             ', Amount: ' || v_amount || 
                             ', Type: ' || v_transaction_type);

        -- Accumulate totals for the current customer
        v_total_amount := v_total_amount + v_amount;
    END LOOP;

    -- Print the statement for the last customer
    IF v_current_customer_id IS NOT NULL THEN
        DBMS_OUTPUT.PUT_LINE('Customer ID: ' || v_current_customer_id);
        DBMS_OUTPUT.PUT_LINE('Total Amount: ' || v_total_amount);
        DBMS_OUTPUT.PUT_LINE('--------------------------');
    END IF;

    CLOSE GenerateMonthlyStatements;
END;
/

