--liquibase formatted sql
--changeset sum:create-table-account

DROP TABLE IF EXISTS [mini-bank].dbo.[account];

CREATE TABLE [mini-bank].dbo.[account] (
    [id] [bigint] IDENTITY(1,1) NOT NULL,
    [full_name] [varchar](500) NULL,
    [nick_name] [varchar](100) NULL,
    [email] [varchar](500) NULL,
    [number] [varchar](30) NULL,
    [initial_balance] [numeric](21,0) NULL,
    [remaining_balance] [numeric](21,0) NULL,
    [created_at] [datetimeoffset] NULL,
    [updated_at] [datetimeoffset] NULL,
    CONSTRAINT [PK_ACCOUNT] PRIMARY KEY CLUSTERED([id] ASC) ON [PRIMARY]
) ON [PRIMARY];