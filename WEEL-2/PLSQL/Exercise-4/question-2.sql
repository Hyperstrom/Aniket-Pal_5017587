CREATE OR REPLACE FUNCTION CalculateMonthlyInstallment (
    p_LoanAmount IN NUMBER,
    p_AnnualInterestRate IN NUMBER,
    p_LoanDurationYears IN NUMBER
) RETURN NUMBER
IS
    v_MonthlyInterestRate NUMBER;
    v_LoanDurationMonths NUMBER;
    v_MonthlyInstallment NUMBER;
BEGIN
    -- Convert annual interest rate to monthly interest rate
    v_MonthlyInterestRate := p_AnnualInterestRate / 12 / 100;
    
    -- Convert loan duration in years to months
    v_LoanDurationMonths := p_LoanDurationYears * 12;
    
    -- Calculate the monthly installment using the EMI formula
    IF v_MonthlyInterestRate = 0 THEN
        v_MonthlyInstallment := p_LoanAmount / v_LoanDurationMonths;
    ELSE
        v_MonthlyInstallment := p_LoanAmount * v_MonthlyInterestRate * POWER((1 + v_MonthlyInterestRate), v_LoanDurationMonths) /
                                (POWER((1 + v_MonthlyInterestRate), v_LoanDurationMonths) - 1);
    END IF;
    
    RETURN v_MonthlyInstallment;
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
        RETURN NULL;
END CalculateMonthlyInstallment;
/

-- ON THE SERVEROUTPUT TO SEE THE OUTPUT 
SET SERVEROUT ON;

--TAKE THE DATA FROM THE LOANS TABLE 
DECLARE
    CURSOR loan_cursor IS
        SELECT LOANID, LOANAMOUNT, INTERESTRATE, STARTDATE, ENDDATE
        FROM Loans;
        
    v_LOANID Loans.LOANID%TYPE;
    v_LoanAmount Loans.LOANAMOUNT%TYPE;
    v_InterestRate Loans.INTERESTRATE%TYPE;
    v_StartDate Loans.STARTDATE%TYPE;
    v_EndDate Loans.ENDDATE%TYPE;
    v_LoanDurationYears NUMBER;
    v_MonthlyInstallment NUMBER;
BEGIN
    -- Open the cursor
    OPEN loan_cursor;
    
    -- Loop through each loan
    LOOP
        FETCH loan_cursor INTO v_LOANID, v_LoanAmount, v_InterestRate, v_StartDate, v_EndDate;
        EXIT WHEN loan_cursor%NOTFOUND;
        
        -- Calculate loan duration in years
        v_LoanDurationYears := MONTHS_BETWEEN(v_EndDate, v_StartDate) / 12;
        
        -- Calculate monthly installment using the CalculateMonthlyInstallment function
        v_MonthlyInstallment := CalculateMonthlyInstallment(v_LoanAmount, v_InterestRate, v_LoanDurationYears);
        
        -- Display loan details and calculated monthly installment with 2 decimal places
        DBMS_OUTPUT.PUT_LINE('Loan ID: ' || v_LOANID ||
                             ', Loan Amount: ' || v_LoanAmount ||
                             ', Annual Interest Rate: ' || v_InterestRate || '%' ||
                             ', Loan Duration: ' || ROUND(v_LoanDurationYears, 2) || ' years' ||
                             ', Monthly Installment: ' || TO_CHAR(v_MonthlyInstallment, '9999999999.99'));
    END LOOP;
    
    -- Close the cursor
    CLOSE loan_cursor;
END;
/
