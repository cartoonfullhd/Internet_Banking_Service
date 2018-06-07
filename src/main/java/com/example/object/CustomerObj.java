package com.example.object;

import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Component
public class CustomerObj implements JsonNonNullVoInterface
{
	private String Login;
	private String Password;
	private String Name;
	private String Address;
	private String PhoneNum;
	private String Email;
	private String CreateDTM;
	private String UpdateDTM;
	
	public CustomerObj(String login, String password, String name, String address, String phoneNum, String email)
	{
		super();
		Login = login;
		Password = password;
		Name = name;
		Address = address;
		PhoneNum = phoneNum;
		Email = email;
	}
}
