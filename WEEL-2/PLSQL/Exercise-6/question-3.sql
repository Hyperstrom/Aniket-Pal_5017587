SET SERVEROUT ON;

DECLARE
    CURSOR UpdateLoanInterestRates IS
        SELECT LOANID, INTERESTRATE
        FROM LOANS
        FOR UPDATE OF INTERESTRATE;

    v_loan_id LOANS.LOANID%TYPE;
    v_interest_rate LOANS.INTERESTRATE%TYPE;

    -- Example new interest rates based on certain conditions
    v_new_interest_rate CONSTANT NUMBER := 5.5; -- New interest rate for demonstration
BEGIN
    OPEN UpdateLoanInterestRates;

    LOOP
        FETCH UpdateLoanInterestRates INTO v_loan_id, v_interest_rate;
        EXIT WHEN UpdateLoanInterestRates%NOTFOUND;

        -- Apply the new policy to determine the new interest rate
        -- You can add more complex logic here based on the new policy
        v_interest_rate := v_new_interest_rate;

        -- Update the interest rate in the LOANS table
        UPDATE LOANS
        SET INTERESTRATE = v_interest_rate
        WHERE CURRENT OF UpdateLoanInterestRates;

        -- Optional: Log the interest rate update
        DBMS_OUTPUT.PUT_LINE('Loan ID: ' || v_loan_id || ' - New Interest Rate: ' || v_interest_rate);
    END LOOP;

    CLOSE UpdateLoanInterestRates;
END;
/
--- NEW Loans TABLE
SELECT * FROM LOANS;