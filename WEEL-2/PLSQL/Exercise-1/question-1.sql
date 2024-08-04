CREATE TABLE LoanRateChanges (
    CustomerID INT,
    Name VARCHAR(100),
    OldInterestRate DECIMAL(10, 2),
    NewInterestRate DECIMAL(10, 2)
);

DECLARE
    CURSOR customer_cursor IS
        SELECT c.CustomerID, c.Name, c.DOB, l.LoanID, l.InterestRate
        FROM Customers c
        JOIN Loans l ON c.CustomerID = l.CustomerID;
    v_age NUMBER;
    v_oldInterestRate DECIMAL(10,2);
    v_newInterestRate DECIMAL(10,2);
BEGIN
    FOR customer_rec IN customer_cursor LOOP
        v_age := FLOOR(MONTHS_BETWEEN(SYSDATE, customer_rec.DOB) / 12);

        IF v_age > 60 THEN
            v_oldInterestRate := customer_rec.InterestRate;
            v_newInterestRate := v_oldInterestRate - 1;

            UPDATE Loans
            SET InterestRate = v_newInterestRate
            WHERE LoanID = customer_rec.LoanID;

            INSERT INTO LoanRateChanges (CustomerID, Name, OldInterestRate, NewInterestRate)
            VALUES (customer_rec.CustomerID, customer_rec.Name, v_oldInterestRate, v_newInterestRate);
        END IF;
    END LOOP;
    COMMIT;
END;
/

SELECT * FROM LoanRateChanges;
