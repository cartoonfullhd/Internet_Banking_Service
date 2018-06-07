package com.example.object;

import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Component
public class BankObj
{
	private int BankCode;
	private String BankName;
	private String BankAbbr;
}
