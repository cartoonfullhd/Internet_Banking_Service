package com.example.sqlmap;

import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.example.object.AccountObj;
import com.example.object.BankObj;
import com.example.object.CustomerObj;
import com.example.object.Txn;
import com.example.object.UserObj;

@Mapper
public interface AccountSql
{
	public static final String SQL_Get_AccountDetailList = ""
			+ 	" SELECT "
			+	"	ACCOUNTID, CUSTOMERID, BANKCODE, ACCOUNTTYPE, BALANCEAMOUNT"
			+	" FROM ACCOUNT "
			+ 	" WHERE CUSTOMERID = #{customerId}";
	@Select(SQL_Get_AccountDetailList)
	public List<AccountObj> getAccountDetailList(@Param("customerId") String customerId);
	
	public static final String SQL_Get_AccountDetail = ""
			+ 	" SELECT "
			+	"	ACCOUNTID, CUSTOMERID, BANKCODE, ACCOUNTTYPE, BALANCEAMOUNT"
			+	" FROM ACCOUNT "
			+ 	" WHERE ACCOUNTID = #{accountId}";
	@Select(SQL_Get_AccountDetail)
	public AccountObj getAccountDetail(@Param("accountId") String accountId);
	
	public static final String SQL_Get_AccountStatusCode = ""
			+ 	" SELECT "
			+	"	DESCRIPTION"
			+	" FROM ACCOUNTSTATUS "
			+ 	" WHERE STATUSCODE = #{statusCode}";
	@Select(SQL_Get_AccountStatusCode)
	public String getStatusCode(@Param("statusCode") String statusCode);
	
	public static final String SQL_Get_BankName = ""
			+	" SELECT "
			+ 	"	BANKCODE, BANKNAME, BANKABBR"
			+	"	FROM BANK "
			+	" WHERE BANKCODE = #{bankCode}";
	@Select(SQL_Get_BankName)
	public BankObj getBankName(@Param("bankCode") String bankCode);
	
	public static final String SQL_Update_My_Account_Transfer = ""
			+	"	UPDATE ACCOUNT "
			+	"	SET	BALANCEAMOUNT = #{amount}, UPDATEDTM = #{timestamp}"
			+	"	WHERE ACCOUNTID = #{account} AND CUSTOMERID = #{customer}";
	@Insert(SQL_Update_My_Account_Transfer)
	public Integer updateMyAccountTransfer(@Param("amount") Double amount,
			@Param("account") String account, @Param("customer") int customerId,
			@Param("timestamp") Timestamp timeStamp);
	
	public static final String SQL_Update_Des_Account_Transfer = ""
			+	"	UPDATE ACCOUNT "
			+	"	SET	BALANCEAMOUNT = #{amount}, UPDATEDTM = #{timestamp}"
			+	"	WHERE ACCOUNTID = #{account}";
	@Insert(SQL_Update_Des_Account_Transfer)
	public Integer updateDesAccountTransfer(@Param("amount") Double amount,
			@Param("account") String account, @Param("timestamp") Timestamp timeStamp);
	
	public static final String SQL_Check_Account = ""
			+ 	" SELECT "
			+	"	ACCOUNTID, CUSTOMERID, BANKCODE, ACCOUNTTYPE, BALANCEAMOUNT"
			+	" FROM ACCOUNT "
			+ 	" WHERE ACCOUNTID = #{accountId}";
	@Select(SQL_Check_Account)
	public AccountObj checkAccount(@Param("accountId") String accountId);
	
	public static final String SQL_Get_Amount = ""
			+	" SELECT "
			+ 	"	BALANCEAMOUNT"
			+	"	FROM ACCOUNT "
			+	" WHERE ACCOUNTID = #{account}";
	@Select(SQL_Get_Amount)
	public Double getAmount(@Param("account") String account);
	
	public static final String SQL_Get_CustomerDetail = ""
			+	" SELECT "
			+	" CUSTOMERID, NAME, ADDRESS, PHONENUM, EMAIL "
			+	" FROM CUSTOMER "
			+	" WHERE CUSTOMERID = #{customer_id}";
	@Select(SQL_Get_CustomerDetail)
	public UserObj getCustomerDetail(@Param("customer_id") int customer_id);
	
	public static final String SQL_Update_Transfer_TXN = ""
			+	"	INSERT INTO TRANSFERTXN (RECEIVEBANKCODE , SENDBANKCODE ,SUBMITAMOUNT ,FEEAMOUNT, TXNTYPE ,TXNSTATE ,NETAMOUNT, SENDACCOUNTID, RECEIVEACCOUNTID, CREATEDTM)"
			+	"	VALUES (#{code}, #{code}, #{submitAmount} ,#{feeAmount}, #{txnType}, #{txnState}, #{amount}, #{myAccount}, #{desAccount}, #{timestamp})";
	@Insert(SQL_Update_Transfer_TXN)
	public Integer addTransferTxn(@Param("amount") Double amount, @Param("txnState") String txnState, @Param("txnType") String txnType,
			@Param("myAccount") String myAccount, @Param("desAccount") String desAccount, @Param("timestamp") Timestamp timeStamp,
			@Param("feeAmount") String feeAmount, @Param("submitAmount") Double submitAmount, @Param("code") String code);
	
	public static final String SQL_Update_Transfer_TXN_APID = ""
			+	"	INSERT INTO TRANSFERTXN (AIPID ,RECEIVEBANKCODE , SENDBANKCODE ,SUBMITAMOUNT ,FEEAMOUNT, TXNTYPE ,TXNSTATE ,NETAMOUNT, SENDACCOUNTID, RECEIVEACCOUNTID, CREATEDTM)"
			+	"	VALUES (#{apid} , #{desCode}, #{code}, #{submitAmount} ,#{feeAmount}, #{txnType}, #{txnState}, #{amount}, #{myAccount}, #{desAccount}, #{timestamp})";
	@Insert(SQL_Update_Transfer_TXN_APID)
	public Integer addTransferTxnAPID(@Param("amount") Double amount, @Param("txnState") String txnState, @Param("txnType") String txnType,
			@Param("myAccount") String myAccount, @Param("desAccount") String desAccount, @Param("timestamp") Timestamp timeStamp,
			@Param("feeAmount") String feeAmount, @Param("submitAmount") Double submitAmount, @Param("code") String code, @Param("apid") String apid,
			@Param("desCode") String desCode);
	
	public static final String SQL_Update_My_Account_Receive_Money = ""
			+	"	UPDATE ACCOUNT "
			+	"	SET	BALANCEAMOUNT = #{amount}, UPDATEDTM = #{timestamp}"
			+	"	WHERE ACCOUNTID = #{account}";
	@Insert(SQL_Update_My_Account_Receive_Money)
	public Integer updateMyAccountReceiveMoney(@Param("amount") Double amount,
			@Param("account") String account,
			@Param("timestamp") Timestamp timeStamp);
	
	public static final String SQL_Update_Transfer_TXN_APID_TXN = ""
			+	"	INSERT INTO TRANSFERTXN (RECEIVEBANKCODE , SENDBANKCODE ,SUBMITAMOUNT ,FEEAMOUNT, TXNTYPE ,TXNSTATE ,NETAMOUNT, SENDACCOUNTID, RECEIVEACCOUNTID, CREATEDTM)"
			+	"	VALUES (#{desCode}, #{code}, #{submitAmount} ,#{feeAmount}, #{txnType}, #{txnState}, #{amount}, #{myAccount}, #{desAccount}, #{timestamp})";
	@Insert(SQL_Update_Transfer_TXN_APID_TXN)
	public Integer addTransferTxnAPIDTxn(@Param("amount") Double amount, @Param("txnState") String txnState, @Param("txnType") String txnType,
			@Param("myAccount") String myAccount, @Param("desAccount") String desAccount, @Param("timestamp") Timestamp timeStamp,
			@Param("feeAmount") String feeAmount, @Param("submitAmount") Double submitAmount, @Param("code") String code,
			@Param("desCode") String desCode);
	
	public static final String SQL_Get_Txn_Receive = ""
			+	" SELECT "
			+	" TXNID AS TXNREFID, CREATEDTM AS TXNDTM, NETAMOUNT AS AMOUNT, TXNSTATE AS RESULT"
			+	" FROM TRANSFERTXN "
			+ 	" ORDER BY TXNID DESC LIMIT 1";
	@Select(SQL_Get_Txn_Receive)
	public Txn getTxnReceive();
	
	public static final String SQL_Get_Txn = ""
			+	" SELECT "
			+	" TXNID AS TXNREFID, CREATEDTM AS TXNDTM, NETAMOUNT AS AMOUNT, TXNSTATE AS RESULT, TXNTYPE, SENDACCOUNTID, RECEIVEACCOUNTID"
			+	" FROM TRANSFERTXN "
			+ 	" WHERE SENDACCOUNTID = #{accountID} OR RECEIVEACCOUNTID = #{accountID} ORDER BY CREATEDTM DESC";
	@Select(SQL_Get_Txn)
	public List<Txn> getTxn(@Param("accountID") String accountID);
}
