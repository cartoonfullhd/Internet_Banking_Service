package com.example.object;

import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Component
public class AccountObj
{
	private String AccountId;
	private String CustomerId;
	private String BankCode;
	private String AccountType;
	private Double BalanceAmount;
}
