--liquibase formatted sql
--changeset sum:create-table-transaction

DROP TABLE IF EXISTS [mini-bank].dbo.[transaction];

CREATE TABLE [mini-bank].dbo.[transaction] (
    [id] [bigint] IDENTITY(1,1) NOT NULL,
    [amount] [numeric](21,0) NULL,
    [description] [varchar](150) NULL,
    [source_account_id] [bigint] NULL,
    [destination_account_id] [bigint] NULL,
    [created_at] [datetimeoffset] NULL,
    CONSTRAINT [PK_TRANSACTION] PRIMARY KEY CLUSTERED([id] ASC) ON [PRIMARY]
) ON [PRIMARY];