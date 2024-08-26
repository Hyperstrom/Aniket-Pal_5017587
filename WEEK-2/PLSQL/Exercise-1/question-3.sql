SET SERVEROUT ON;

DECLARE
    CURSOR customer_cursor IS
        SELECT c.CustomerID, c.Name
        FROM Customers c;

    CURSOR loan_cursor(p_customer_id Customers.CustomerID%TYPE) IS
        SELECT l.LoanID, l.EndDate
        FROM Loans l
        WHERE l.CustomerID = p_customer_id
        AND l.EndDate BETWEEN SYSDATE AND SYSDATE + 30;

BEGIN
    FOR customer_rec IN customer_cursor LOOP
        DECLARE
            v_loan_due BOOLEAN := FALSE;
        BEGIN
            FOR loan_rec IN loan_cursor(customer_rec.CustomerID) LOOP
                DBMS_OUTPUT.PUT_LINE('Reminder: Customer ' || customer_rec.Name || ' (ID: ' || customer_rec.CustomerID || ') has a loan (Loan ID: ' || loan_rec.LoanID || ') due on ' || TO_CHAR(loan_rec.EndDate, 'YYYY-MM-DD') || '.');
                v_loan_due := TRUE;
            END LOOP;
            
            IF NOT v_loan_due THEN
                DBMS_OUTPUT.PUT_LINE('Customer ' || customer_rec.Name || ' (ID: ' || customer_rec.CustomerID || ') has no loans due in the next 30 days.');
            END IF;
        END;
    END LOOP;
END;
