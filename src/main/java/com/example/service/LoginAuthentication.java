package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.object.CustomerObj;
import com.example.sqlmap.LoginSql;

@Component
public class LoginAuthentication
{
	@Autowired LoginSql loginSql;
	@Autowired CustomerObj customerObj;
	
	public boolean checkLogin(String userName, String passWord)
	{
		boolean result = false;
		customerObj = loginSql.LoginBankUserResult(userName, passWord);
		if(customerObj != null)
		{
			return result = true;
		}else 
		{
			return result;
		}
	}
	
	public boolean checkUsername(String userName)
	{
		boolean result = false;
		customerObj = loginSql.CheckUsername(userName);
		if(customerObj != null)
		{
			return result = true;
		}else 
		{
			return result;
		}
	}
	
	//get user id for sent to header back
		public String getCustomerID()
		{
			return String.valueOf(customerObj.getCustomerID());
		}
}
