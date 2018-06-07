package com.example.object;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.NoArgsConstructor;

@NoArgsConstructor
@Component
public class AnyID implements JsonNonNullVoInterface
{
	private String AIPID;
	private String IDValue;
	private String IDType;
	private String BankCode;
	private String Status;
	private String AccountID;
	private String AccountName;
	private String RegisterDTM;
	
	
	public AnyID(String aIPID, String iDValue, String iDType, String bankCode, String status, String accountID,
			String accountName, String registerDTM)
	{
		super();
		AIPID = aIPID;
		IDValue = iDValue;
		IDType = iDType;
		BankCode = bankCode;
		Status = status;
		AccountID = accountID;
		AccountName = accountName;
		RegisterDTM = registerDTM;
	}


	public String getAIPID()
	{
		return AIPID;
	}

	@JsonProperty("AIPID")
	public void setAIPID(String aIPID)
	{
		AIPID = aIPID;
	}


	public String getIDValue()
	{
		return IDValue;
	}

	@JsonProperty("IDValue")
	public void setIDValue(String iDValue)
	{
		IDValue = iDValue;
	}


	public String getIDType()
	{
		return IDType;
	}

	@JsonProperty("IDType")
	public void setIDType(String iDType)
	{
		IDType = iDType;
	}


	public String getBankCode()
	{
		return BankCode;
	}

	@JsonProperty("BankCode")
	public void setBankCode(String bankCode)
	{
		BankCode = bankCode;
	}


	public String getStatus()
	{
		return Status;
	}

	@JsonProperty("Status")
	public void setStatus(String status)
	{
		Status = status;
	}


	public String getAccountID()
	{
		return AccountID;
	}

	@JsonProperty("AccountID")
	public void setAccountID(String accountID)
	{
		AccountID = accountID;
	}


	public String getAccountName()
	{
		return AccountName;
	}

	@JsonProperty("AccountName")
	public void setAccountName(String accountName)
	{
		AccountName = accountName;
	}


	public String getRegisterDTM()
	{
		return RegisterDTM;
	}

	@JsonProperty("RegisterDTM")
	public void setRegisterDTM(String registerDTM)
	{
		RegisterDTM = registerDTM;
	}
	
	
}