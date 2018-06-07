package com.example.service;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.object.AccountObj;
import com.example.sqlmap.AccountSql;

@Component
public class TransferMoney
{
	@Autowired AccountSql accountSql;
	@Autowired AccountObj accountObj;
	
	public boolean checkDesAccount(String desAccount)
	{
		boolean result = false;
		accountObj = accountSql.checkAccount(desAccount);
		if(accountObj != null)
		{
			return result = true;
		}else 
		{
			return result;
		}
	}
	
	public boolean transfer(String myAccount, String desAccount, 
			Double amount, int customerId)
	{
		int resultMyAccount, resultDesAccount;
		Double amountMyAccount, amountDesAccount;
		amountMyAccount = accountSql.getAmount(myAccount);
		amountDesAccount = accountSql.getAmount(desAccount);
		
		if(checkAmountMyAccountTransfer(myAccount, amount))
		{
			resultMyAccount = accountSql.updateMyAccountTransfer(amountMyAccount-amount, myAccount, customerId, new Timestamp(System.currentTimeMillis()));
			resultDesAccount = accountSql.updateDesAccountTransfer(amountDesAccount+amount, desAccount, new Timestamp(System.currentTimeMillis()));
		
			if(resultMyAccount == 1 && resultDesAccount == 1)
			{
				accountSql.addTransferTxn(amount, "Success", "Samebank" , myAccount, desAccount, new Timestamp(System.currentTimeMillis()), "0", amount, "002");
				return true;
			}else
			{
				return false;
			}
		}else
		{
			return false;
		}
	}
	
	public boolean transferAPID(String myAccount, String desAccount, 
			Double amount, int customerId, String apid, String desCode)
	{
		int resultMyAccount;
		Double amountMyAccount;
		amountMyAccount = accountSql.getAmount(myAccount);
		
		if(checkAmountMyAccountTransfer(myAccount, amount))
		{
			resultMyAccount = accountSql.updateMyAccountTransfer(amountMyAccount-amount, myAccount, customerId, new Timestamp(System.currentTimeMillis()));
		
			if(resultMyAccount == 1)
			{
				accountSql.addTransferTxnAPID(amount, "Success", "Interbank" , myAccount, desAccount, new Timestamp(System.currentTimeMillis()), "0", amount, "002", apid, desCode);
				return true;
			}else
			{
				return false;
			}
		}else
		{
			return false;
		}
	}
	
	public boolean checkAmountMyAccountTransfer(String myAccount, Double amount)
	{
		Double amountMyAccount;
		amountMyAccount = accountSql.getAmount(myAccount);
		if(amount <= amountMyAccount)
		{
			return true;
		}else
		{
			return false;
		}
	}
}
