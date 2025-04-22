--liquibase formatted sql
--changeset sum:seeding-transaction

SET NOCOUNT ON;

DECLARE @BatchSize INT = 10000;
DECLARE @TotalRows INT = 100000000;
DECLARE @InsertedRows INT = 0;

WHILE @InsertedRows < @TotalRows
BEGIN
INSERT INTO [transaction] (amount, description, source_account_id, destination_account_id, created_at)
SELECT TOP (@BatchSize)
    CAST(RAND(CHECKSUM(NEWID())) * 1000000 + 1 AS BIGINT) AS amount,
        CASE ABS(CHECKSUM(NEWID()) % 5)
            WHEN 0 THEN 'Payment for invoice #123'
            WHEN 1 THEN 'Refund from order #456'
            WHEN 2 THEN 'Subscription charge'
            WHEN 3 THEN 'Salary payment'
            ELSE 'Loan repayment'
END AS description,
        CAST(RAND(CHECKSUM(NEWID())) * 1000 + 1 AS BIGINT) AS source_account_id,
        CAST(RAND(CHECKSUM(NEWID())) * 1000 + 1 AS BIGINT) AS destination_account_id,
        DATEADD(DAY, -ABS(CHECKSUM(NEWID()) % 1825), GETDATE()) AS created_at
    FROM sys.all_objects a
    CROSS JOIN sys.all_objects b
    WHERE @BatchSize > 0;

    SET @InsertedRows += @BatchSize;

    PRINT CONCAT('Inserted ', @InsertedRows, ' of ', @TotalRows, ' rows');
END;
