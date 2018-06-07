package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.object.UserObj;
import com.example.sqlmap.LoginSql;

@Component
public class LoginAuthentication
{
	@Autowired LoginSql loginSql;
	@Autowired UserObj userObj;
	
	public boolean checkLogin(String userName, String passWord)
	{
		boolean result = false;
		userObj = loginSql.LoginBankUserResult(userName, passWord);
		if(userObj != null)
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
			return String.valueOf(userObj.getCustomerId());
		}
}
