-- Table Bank
CREATE TABLE IF NOT EXISTS Bank
(
 BankCode Varchar(5) NOT NULL,
 BankName Varchar(200) NOT NULL,
 BankAbbr Varchar(40) NOT NULL,
 CONSTRAINT PK_Bank PRIMARY KEY (BankCode)
);

-- Table AccountStatus
CREATE TABLE IF NOT EXISTS AccountStatus
(
 StatusCode Varchar(20) NOT NULL,
 Description Varchar(500) NULL,
 CONSTRAINT PK_AccountStatus PRIMARY KEY (StatusCode),
 CONSTRAINT FK_AccountStatus_Account FOREIGN KEY (StatusCode) REFERENCES AccountStatus (StatusCode) ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- Table Customer
CREATE TABLE IF NOT EXISTS Customer
(
 CustomerID Bigint NOT NULL,
 Login Varchar(20) NOT NULL,
 Password Varchar(50) NOT NULL,
 Name Varchar(200) NOT NULL,
 Address Varchar(1000) NULL,
 PhoneNum Varchar(100) NULL,
 Email Varchar(100) NULL,
 CreateDTM Datetime NOT NULL,
 UpdateDTM Datetime NULL,
 CONSTRAINT PK_Customer PRIMARY KEY (CustomerID),
 CONSTRAINT FK_Customer_Account FOREIGN KEY (CustomerID) REFERENCES Customer (CustomerID) ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- Table Account
CREATE TABLE IF NOT EXISTS Account
(
 AccountID Varchar(15) NOT NULL,
 CustomerID Bigint NOT NULL,
 BankCode Varchar(5) NULL,
 AccountType Varchar(10) NULL,
 StatusCode Varchar(20) NULL,
 BalanceAmount Decimal(20,2) NULL,
 CreateDTM Datetime NOT NULL,
 UpdateDTM Datetime NULL,
 CONSTRAINT PK_Account PRIMARY KEY (AccountID),
 CONSTRAINT FK_Bank_Account FOREIGN KEY (BankCode) REFERENCES Bank (BankCode) ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- Table TransferTxn
CREATE TABLE IF NOT EXISTS TransferTxn
(
 TxnID Bigint auto_increment NOT NULL,
 TxnType Varchar(20) NOT NULL,
 TxnState Varchar(20) NOT NULL,
 FeeAmount Decimal(10,2) NOT NULL,
 SubmitAmount Decimal(20,2) NOT NULL,
 NetAmount Decimal(20,2) NOT NULL,
 SendBankCode Varchar(5) NOT NULL,
 SendAccountID Varchar(15) NOT NULL,
 AIPID Bigint NULL,
 ReceiveBankCode Varchar(5) NOT NULL,
 ReceiveAccountID Varchar(15) NOT NULL,
 CreateDTM Datetime NOT NULL,
 CONSTRAINT PK_TxnID PRIMARY KEY (TxnID),
 CONSTRAINT FK_Bank_TransferTxn_01 FOREIGN KEY (SendBankCode) REFERENCES Bank (BankCode) ON UPDATE NO ACTION ON DELETE NO ACTION,
 CONSTRAINT FK_Bank_TransferTxn_02 FOREIGN KEY (ReceiveBankCode) REFERENCES Bank (BankCode) ON UPDATE NO ACTION ON DELETE NO ACTION
);
