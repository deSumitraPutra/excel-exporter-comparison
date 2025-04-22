--liquibase formatted sql
--changeset sum:create-table-file-template

DROP TABLE IF EXISTS [mini-bank].dbo.[file_template];

CREATE TABLE [mini-bank].dbo.[file_template] (
    [id] [bigint] IDENTITY(1,1) NOT NULL,
    [code] [varchar](100) NULL,
    [description] [varchar](500) NULL,
    [content] [varbinary](MAX) NULL,
    CONSTRAINT [PK_FILE_TEMPLATE] PRIMARY KEY CLUSTERED([id] ASC) ON [PRIMARY]
) ON [PRIMARY];