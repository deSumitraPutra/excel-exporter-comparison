--liquibase formatted sql
--changeset sum:seeding-account

SET NOCOUNT ON;

-- Step 1: Prepare name lists as in-memory tables
DECLARE @FirstNames TABLE (name VARCHAR(50));
DECLARE @LastNames TABLE (name VARCHAR(50));
INSERT INTO @FirstNames (name) VALUES ('Liam'), ('Olivia'), ('Noah'), ('Emma'), ('James'), ('Ava'), ('Lucas'), ('Sophia'), ('Mason'), ('Isabella');
INSERT INTO @LastNames (name) VALUES ('Smith'), ('Johnson'), ('Brown'), ('Taylor'), ('Anderson'), ('Thomas'), ('Jackson'), ('White'), ('Harris'), ('Martin');

-- Step 2: Declare variables for batching
DECLARE @BatchSize INT = 10000;
DECLARE @TotalRows INT = 100000000;
DECLARE @InsertedRows INT = 0;

-- Step 3: Main loop for batch insert
WHILE @InsertedRows < @TotalRows
BEGIN
INSERT INTO account (full_name, nick_name, email, number, initial_balance, remaining_balance, created_at, updated_at)
SELECT TOP (@BatchSize)
                   CONCAT(fn.name, ' ', ln.name) AS full_name,
        fn.name AS nick_name,
       LOWER(CONCAT(fn.name, '.', ln.name, '@',
                    CHOOSE(ABS(CHECKSUM(NEWID())) % 5 + 1,
                           'gmail.com', 'yahoo.com', 'outlook.com', 'hotmail.com', 'icloud.com')
             )) AS email,
    RIGHT(CONVERT(VARCHAR(20), ABS(CHECKSUM(NEWID()))), 10) + RIGHT(CONVERT(VARCHAR(20), ABS(CHECKSUM(NEWID()))), 5) AS number,
    CAST(RAND(CHECKSUM(NEWID())) * 1000000000 + 1 AS NUMERIC(21, 0)) AS initial_balance,
    CAST(RAND(CHECKSUM(NEWID())) * 1000000000 + 1 AS NUMERIC(21, 0)) AS remaining_balance,
    DATEADD(DAY, -ABS(CHECKSUM(NEWID()) % 1825), GETDATE()) AS created_at,
    DATEADD(DAY, -ABS(CHECKSUM(NEWID()) % 1825), GETDATE()) AS updated_at
FROM @FirstNames fn
    CROSS JOIN @LastNames ln
    CROSS JOIN sys.all_objects a
    CROSS JOIN sys.all_objects b;

SET @InsertedRows += @BatchSize;

    PRINT CONCAT('Inserted ', FORMAT(@InsertedRows, '#,##0'), ' of ', FORMAT(@TotalRows, '#,##0'), ' rows');
END;
