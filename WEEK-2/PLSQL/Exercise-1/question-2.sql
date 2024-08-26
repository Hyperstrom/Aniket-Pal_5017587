DECLARE
    CURSOR customer_cursor IS
        SELECT CustomerID, Balance
        FROM Customers;
BEGIN
    FOR customer_rec IN customer_cursor LOOP
        IF customer_rec.Balance > 10000 THEN
            UPDATE Customers
            SET ISVIP = 'YES'
            WHERE CustomerID = customer_rec.CustomerID;
        ELSE
            UPDATE Customers
            SET ISVIP = 'NO'
            WHERE CustomerID = customer_rec.CustomerID;
        END IF;
    END LOOP;
    COMMIT;
END;
/

SELECT * FROM CUSTOMERS;