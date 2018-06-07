package com.example.object;

import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Component
public class UserObj implements JsonNonNullVoInterface
{
	private int CustomerId;
	private String LogIn;
	private String Password;
	private String Name;
	private String Address;
	private String PhoneNum;
	private String Email;
}
