package com.example.object;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonProperty;


@Component
public class Txn implements JsonNonNullVoInterface
{	
	private String TxnRefID;
	private String TxnDTM;
	private String TxnType;
	private String Amount;
	private String Result;
	private String SendAccountID;
	private String ReceiveAccountID;

	public String getTxnType()
	{
		return TxnType;
	}

	@JsonProperty("TxnType")
	public void setTxnType(String txnType)
	{
		TxnType = txnType;
	}

	
	public String getSendAccountID()
	{
		return SendAccountID;
	}

	@JsonProperty("SendAccountID")
	public void setSendAccountID(String sendAccountID)
	{
		SendAccountID = sendAccountID;
	}

	public String getReceiveAccountID()
	{
		return ReceiveAccountID;
	}

	@JsonProperty("ReceiveAccountID")
	public void setReceiveAccountID(String receiveAccountID)
	{
		ReceiveAccountID = receiveAccountID;
	}

	public String getTxnRefID()
	{
		return TxnRefID;
	}

	@JsonProperty("TxnRefID")
	public void setTxnRefID(String txnRefID)
	{
		TxnRefID = txnRefID;
	}

	public String getTxnDTM()
	{
		return TxnDTM;
	}

	@JsonProperty("TxnDTM")
	public void setTxnDTM(String txnDTM)
	{
		TxnDTM = txnDTM;
	}

	public String getAmount()
	{
		return Amount;
	}

	@JsonProperty("Amount")
	public void setAmount(String amount)
	{
		Amount = amount;
	}

	public String getResult()
	{
		return Result;
	}

	@JsonProperty("Result")
	public void setResult(String result)
	{
		Result = result;
	}
	
} 
